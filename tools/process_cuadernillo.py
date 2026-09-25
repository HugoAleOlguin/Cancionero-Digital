#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
tools/process_cuadernillo.py
Procesa las 766 páginas de 'Cuadernillo de Alabanzas Cristianas.pdf' de forma 100% local (0 tokens).
Extrae títulos, letras, autores por sección, notas de versión al pie (ej: Tito Abarca)
y realiza un análisis 1:1 contra data/catalog.json (480 alabanzas).
"""

import sys
import json
import re
from pathlib import Path
from difflib import SequenceMatcher
import unicodedata
import pdfplumber

def normalize_text(text: str) -> str:
    if not text:
        return ""
    text = unicodedata.normalize('NFD', text)
    text = re.sub(r'[\u0300-\u036f]', '', text)
    text = re.sub(r'[^a-zA-Z0-9\s]', ' ', text)
    return ' '.join(text.lower().split())

def main():
    pdf_path = Path("Cuadernillo de Alabanzas Cristianas.pdf")
    catalog_path = Path("data/catalog.json")
    out_extracted = Path("data/cuadernillo_extracted.json")
    out_analysis = Path("data/cuadernillo_analysis.json")
    
    if not pdf_path.exists():
        print(f"Error: {pdf_path} no encontrado.")
        sys.exit(1)
        
    print(f"=== INICIANDO EXTRACCIÓN DE {pdf_path.name} ===")
    
    known_authors = [
        "Conjunto de Dorrego",
        "Conjunto Amigo Fiel",
        "Trío Acuña",
        "Cantores unidos del Noa",
        "Conjunto Jerusalén",
        "Conjunto Central de Tucumán",
        "Conjunto de Salta",
        "Conjunto Cantando para Cristo",
        "Conjunto Cantores del Rey",
        "Conjunto Cree Solamente no temas",
        "Conjunto Villanueva",
        "Tito Abarca",
        "Dante Abarca",
        "Conjunto Juvenil de Luján de Cuyo",
        "Conjunto de Luján de Cuyo",
        "Los Gonzales",
        "Conjunto de San Juan",
        "Conjunto de San Luis",
        "Conjunto de Rio Grande",
        "Conjunto de Perico",
        "Coro de Niños",
        "Trío Redención",
        "Conjunto de Ranchillos",
        "Trío Lazarte",
        "Varios"
    ]
    current_author = ""
    extracted_songs = []
    
    if out_extracted.exists():
        print(f"Cargando alabanzas ya extraídas desde {out_extracted}...")
        with open(out_extracted, "r", encoding="utf-8") as f:
            extracted_songs = json.load(f)
        print(f"Total alabanzas cargadas: {len(extracted_songs)}")
    else:
        with pdfplumber.open(pdf_path) as pdf:
            total_pages = len(pdf.pages)
            print(f"Páginas totales a procesar: {total_pages}")
            
            # Páginas 1 a 20 son prólogo e índice; el contenido inicia en la pág 21
            for p_idx in range(20, total_pages):
                page_num = p_idx + 1
                page = pdf.pages[p_idx]
                text = page.extract_text() or ""
                lines = [l.strip() for l in text.split("\n") if l.strip()]
                
                if not lines:
                    continue
                    
                # Verificar si es una portada o divisor de sección de autor
                joined_lines = " ".join(lines)
                is_section_divider = False
                for ka in known_authors:
                    if normalize_text(ka) in normalize_text(joined_lines) and len(lines) <= 6:
                        current_author = ka
                        is_section_divider = True
                        print(f"Pág {page_num}: Sección detectada -> {current_author}")
                        break
                        
                if is_section_divider:
                    continue
                    
                # Si tiene muy pocas líneas y no parece una alabanza
                if len(lines) < 4:
                    continue
                    
                # La última línea suele ser el número de página
                last_line = lines[-1]
                content_lines = lines[:]
                if re.match(r'^\d+$', last_line):
                    content_lines = lines[:-1]
                    
                if not content_lines:
                    continue
                    
                title = content_lines[0]
                # Limpiar número o viñeta inicial en el título si la hubiera
                title = re.sub(r'^\d+[\.\-\s]+', '', title).strip()
                
                # Revisar si hay notas de versión al pie (ej: "*Versión hno Tito Abarca")
                version_note = ""
                lyrics_lines = []
                for cl in content_lines[1:]:
                    if cl.startswith("*") or "versión" in cl.lower() or "version" in cl.lower():
                        if len(cl) < 60:
                            version_note = cl.lstrip("*").strip()
                            continue
                    lyrics_lines.append(cl)
                    
                lyrics = "\n".join(lyrics_lines).strip()
                
                # Solo guardar si parece una letra real (más de 40 caracteres)
                if len(lyrics) > 40:
                    extracted_songs.append({
                        "page": page_num,
                        "title": title,
                        "author": current_author,
                        "version_note": version_note,
                        "content": lyrics,
                        "char_count": len(lyrics),
                        "line_count": len(lyrics_lines)
                    })
                
    print(f"\nExtracción finalizada. Total de alabanzas identificadas en el PDF: {len(extracted_songs)}")
    
    # Guardar extracción en JSON
    with open(out_extracted, "w", encoding="utf-8") as f:
        json.dump(extracted_songs, f, indent=2, ensure_ascii=False)
    print(f"Extracción guardada en {out_extracted}")
    
    # === ANÁLISIS 1:1 CONTRA EL CATÁLOGO EXISTENTE ===
    print("\n=== INICIANDO COTEJO MATRICIAL 1:1 CONTRA CATALOG.JSON ===")
    with open(catalog_path, "r", encoding="utf-8") as f:
        catalog = json.load(f)
        
    catalog_hymns = catalog.get("hymns", [])
    print(f"Alabanzas en catálogo actual: {len(catalog_hymns)}")
    
    exact_matches = []
    version_candidates = []
    new_hymns = []
    
    # Precomputar tokens para catálogo
    cat_precalc = []
    for ch in catalog_hymns:
        norm_t = normalize_text(ch["title"])
        norm_c = normalize_text(ch["content"])
        cat_precalc.append({
            "hymn": ch,
            "norm_title": norm_t,
            "norm_content": norm_c,
            "words": set(norm_c.split())
        })
        
    for item in extracted_songs:
        pdf_title_norm = normalize_text(item["title"])
        pdf_content_norm = normalize_text(item["content"])
        pdf_words = set(pdf_content_norm.split())
        
        best_match = None
        best_score = 0.0
        match_type = ""
        
        for citem in cat_precalc:
            ch = citem["hymn"]
            # 1. Similitud de título
            t_sim = SequenceMatcher(None, pdf_title_norm, citem["norm_title"]).ratio()
            
            # 2. Solapamiento de vocabulario (Jaccard)
            inter = len(pdf_words & citem["words"])
            union = len(pdf_words | citem["words"])
            jaccard = inter / union if union > 0 else 0
            
            # Si hay sospecha razonable de coincidencia
            if jaccard > 0.30 or t_sim > 0.65:
                seq_sim = SequenceMatcher(None, pdf_content_norm, citem["norm_content"], autojunk=False).ratio()
                if seq_sim > best_score:
                    best_score = seq_sim
                    best_match = ch
                    
        # Clasificar según umbrales rigurosos
        if best_match and best_score >= 0.88:
            # Misma alabanza (coincidencia casi total)
            char_diff = item["char_count"] - len(best_match["content"])
            exact_matches.append({
                "pdf_page": item["page"],
                "pdf_title": item["title"],
                "pdf_author": item["author"],
                "catalog_id": best_match["id"],
                "catalog_title": best_match["title"],
                "catalog_author": best_match.get("author", ""),
                "similarity": round(best_score, 3),
                "char_diff": char_diff,
                "version_note": item["version_note"]
            })
        elif best_match and (best_score >= 0.50 or (best_score >= 0.40 and pdf_title_norm == normalize_text(best_match["title"]))):
            # Misma alabanza pero con variantes notables -> CANDIDATA A VERSIÓN 2
            version_candidates.append({
                "pdf_page": item["page"],
                "pdf_title": item["title"],
                "pdf_author": item["author"],
                "catalog_id": best_match["id"],
                "catalog_title": best_match["title"],
                "similarity": round(best_score, 3),
                "char_diff": item["char_count"] - len(best_match["content"]),
                "version_note": item["version_note"],
                "pdf_sample": item["content"][:200]
            })
        else:
            # Alabanza nueva que no existe en la app
            new_hymns.append(item)
            
    analysis_report = {
        "total_pdf_songs": len(extracted_songs),
        "total_catalog_songs": len(catalog_hymns),
        "exact_matches_count": len(exact_matches),
        "version_candidates_count": len(version_candidates),
        "new_hymns_count": len(new_hymns),
        "version_candidates": version_candidates,
        "exact_matches": exact_matches,
        "new_hymns": new_hymns
    }
    
    with open(out_analysis, "w", encoding="utf-8") as f:
        json.dump(analysis_report, f, indent=2, ensure_ascii=False)
        
    print(f"\nAnálisis guardado en {out_analysis}")
    print("\n" + "="*60)
    print("RESUMEN DE AUDITORÍA 1:1:")
    print("="*60)
    print(f"Total alabanzas extraídas del PDF:          {len(extracted_songs)}")
    print(f" • Coincidencias exactas (ya en catálogo):  {len(exact_matches)}")
    print(f" • Candidatas a VERSIÓN 2 (con variantes):  {len(version_candidates)}")
    print(f" • Alabanzas NUEVAS (no están en la app):   {len(new_hymns)}")
    print("="*60)

if __name__ == "__main__":
    main()
