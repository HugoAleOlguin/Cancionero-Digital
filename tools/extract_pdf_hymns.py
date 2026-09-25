#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
tools/extract_pdf_hymns.py
Extractor de alabanzas desde archivos PDF con consumo mínimo de tokens (Zero-Token Ingestion).
Procesa el PDF localmente en la máquina usando pdfplumber, estructura las alabanzas y
las coteja matemáticamente contra el catálogo existente en data/catalog.json.
"""

import sys
import json
import re
from pathlib import Path
from difflib import SequenceMatcher
import unicodedata

try:
    import pdfplumber
except ImportError:
    print("Error: pdfplumber no está instalado. Ejecute: pip install pdfplumber")
    sys.exit(1)

def normalize_text(text: str) -> str:
    if not text:
        return ""
    text = unicodedata.normalize('NFD', text)
    text = re.sub(r'[\u0300-\u036f]', '', text)
    text = re.sub(r'[^a-zA-Z0-9\s]', ' ', text)
    return ' '.join(text.lower().split())

def stanza_similarity(s1: str, s2: str) -> float:
    return SequenceMatcher(None, normalize_text(s1), normalize_text(s2)).ratio()

def extract_text_from_pdf(pdf_path: Path) -> list:
    """Extrae el texto página por página del PDF usando pdfplumber."""
    pages_text = []
    print(f"Abriendo PDF: {pdf_path.name}...")
    with pdfplumber.open(pdf_path) as pdf:
        total_pages = len(pdf.pages)
        print(f"Total de páginas detectadas: {total_pages}")
        for idx, page in enumerate(pdf.pages, 1):
            text = page.extract_text(layout=False) or ""
            # Limpiar pies de página numéricos típicos
            cleaned_lines = []
            for line in text.split("\n"):
                stripped = line.strip()
                # Filtrar líneas que son solo números de página aislados
                if re.match(r'^\d+$', stripped) and len(stripped) <= 3:
                    continue
                # Filtrar encabezados comunes repetitivos
                if "cancionero" in stripped.lower() or "himnario" in stripped.lower():
                    if len(stripped) < 40:
                        continue
                cleaned_lines.append(line)
            pages_text.append((idx, "\n".join(cleaned_lines)))
    return pages_text

def parse_hymns_from_text(pages_text: list) -> list:
    """
    Agrupa el texto en bloques de alabanzas reconociendo patrones comunes de separación:
    - Números iniciales (ej: '1.', '1 -', 'HIMNO 1')
    - Títulos en mayúsculas sostenidas
    - Espaciados amplios
    """
    full_text = "\n\n".join(t for _, t in pages_text)
    
    # Intento de partición por cabeceras de número o títulos
    # Patrón común: número al inicio de línea seguido de título
    hymn_pattern = re.compile(
        r'(?:^|\n\n+)(?:(?:HIMNO|CORO|ALABANZA|CANTICO|N[°ºo\.]?)\s*)?(\d{1,4})[\.\-\s]+([^\n]+)\n',
        re.IGNORECASE
    )
    
    matches = list(hymn_pattern.finditer(full_text))
    parsed = []
    
    if len(matches) >= 5:
        # Partición por patrón numérico detectada exitosamente
        for i, match in enumerate(matches):
            num = match.group(1)
            title = match.group(2).strip()
            start_pos = match.end()
            end_pos = matches[i + 1].start() if i + 1 < len(matches) else len(full_text)
            content = full_text[start_pos:end_pos].strip()
            
            parsed.append({
                "source_num": int(num) if num.isdigit() else None,
                "title": title,
                "content": content
            })
    else:
        # Partición genérica por bloques de doble salto de línea
        print("Patrón numérico no detectado unívocamente; usando partición por bloques...")
        raw_blocks = re.split(r'\n{3,}', full_text)
        for idx, block in enumerate(raw_blocks, 1):
            lines = [l.strip() for l in block.split("\n") if l.strip()]
            if len(lines) >= 3:
                title = lines[0]
                content = "\n".join(lines[1:])
                parsed.append({
                    "source_num": idx,
                    "title": title,
                    "content": content
                })
                
    return parsed

def analyze_against_catalog(parsed_hymns: list, catalog_path: Path) -> dict:
    """Coteja cada alabanza extraída contra el catálogo existente en catalog.json."""
    with open(catalog_path, "r", encoding="utf-8") as f:
        catalog = json.load(f)
        
    existing = catalog.get("hymns", [])
    report = {
        "exact_matches": [],
        "version_candidates": [],
        "enrich_candidates": [],
        "new_hymns": [],
        "total_extracted": len(parsed_hymns)
    }
    
    for h in parsed_hymns:
        norm_t = normalize_text(h["title"])
        norm_c = normalize_text(h["content"])
        words_c = set(norm_c.split())
        
        best_match = None
        best_sim = 0.0
        
        for ex in existing:
            ex_t = normalize_text(ex["title"])
            ex_c = normalize_text(ex["content"])
            ex_words = set(ex_c.split())
            
            # 1. Similitud de título
            title_sim = SequenceMatcher(None, norm_t, ex_t).ratio()
            
            # 2. Similitud de contenido
            inter = len(words_c & ex_words)
            union = len(words_c | ex_words)
            jaccard = inter / union if union > 0 else 0
            
            if jaccard > 0.3 or title_sim > 0.7:
                seq_sim = SequenceMatcher(None, norm_c, ex_c).ratio()
                if seq_sim > best_sim:
                    best_sim = seq_sim
                    best_match = ex
                    
        if best_match:
            if best_sim >= 0.92:
                report["exact_matches"].append({
                    "extracted": h,
                    "matched_id": best_match["id"],
                    "matched_title": best_match["title"],
                    "similarity": round(best_sim, 3)
                })
            elif best_sim >= 0.55:
                # Misma temática/coro pero estrofas con variantes -> Candidato a Versión 2!
                report["version_candidates"].append({
                    "extracted": h,
                    "matched_id": best_match["id"],
                    "matched_title": best_match["title"],
                    "similarity": round(best_sim, 3),
                    "char_diff": len(h["content"]) - len(best_match["content"])
                })
            else:
                report["enrich_candidates"].append({
                    "extracted": h,
                    "matched_id": best_match["id"],
                    "matched_title": best_match["title"],
                    "similarity": round(best_sim, 3)
                })
        else:
            report["new_hymns"].append(h)
            
    return report

def main():
    if len(sys.argv) < 2:
        print("Uso: python tools/extract_pdf_hymns.py <ruta_al_pdf> [ruta_salida_json]")
        sys.exit(1)
        
    pdf_path = Path(sys.argv[1])
    if not pdf_path.exists():
        print(f"Error: El archivo no existe: {pdf_path}")
        sys.exit(1)
        
    output_path = Path(sys.argv[2]) if len(sys.argv) >= 3 else Path("data/pdf_extracted.json")
    catalog_path = Path("data/catalog.json")
    
    print("=" * 60)
    print("PROCESAMIENTO DE PDF CON CONSUMO MÍNIMO DE TOKENS")
    print("=" * 60)
    
    pages = extract_text_from_pdf(pdf_path)
    hymns = parse_hymns_from_text(pages)
    print(f"Alabanzas extraídas y estructuradas: {len(hymns)}")
    
    print("\nCotejando contra el catálogo de 480 alabanzas...")
    analysis = analyze_against_catalog(hymns, catalog_path)
    
    # Guardar resultado estructurado localmente
    output_path.parent.mkdir(parents=True, exist_ok=True)
    with open(output_path, "w", encoding="utf-8") as f:
        json.dump({
            "source_pdf": str(pdf_path),
            "analysis": analysis,
            "extracted_hymns": hymns
        }, f, indent=2, ensure_ascii=False)
        
    print(f"\nDatos guardados localmente en: {output_path}")
    print("\n" + "=" * 60)
    print("RESUMEN DE AUDITORÍA (0 TOKENS CONSUMIDOS EN CHAT)")
    print("=" * 60)
    print(f"Total alabanzas extraídas: {analysis['total_extracted']}")
    print(f" • Coincidencias exactas (ya existentes): {len(analysis['exact_matches'])}")
    print(f" • Candidatas a Versión 2 (variantes):    {len(analysis['version_candidates'])}")
    print(f" • Alabanzas nuevas a incorporar:        {len(analysis['new_hymns'])}")
    print("=" * 60)

if __name__ == "__main__":
    main()
