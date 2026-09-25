#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
tools/process_manual_pdf.py
Extrae y analiza 1:1 las alabanzas de 'Manual de Alabanzas Cristianas.pdf' contra el catálogo actual v4 (858 alabanzas).

Flujo Zero-Token:
1. Lee las 491 páginas del PDF localmente con pdfplumber.
2. Descarta portada e índice (págs 1 a 17) y carátulas de conjuntos.
3. Elimina la marca de agua lateral del margen derecho mediante recorte (crop) exacto.
4. Mapea la autoría por secciones y conjuntos.
5. Coteja matricialmente 1:1 contra data/catalog.json (858 alabanzas).
6. Clasifica en:
   - exact_matches: Coincidencia directa (analiza si enriquece autor).
   - version_candidates: Misma alabanza con letra o estrofas variantes (candidatas a Multiversión).
   - new_hymns: Alabanzas totalmente nuevas no presentes en el cancionero.
7. Guarda resultados en data/manual_extracted.json y data/manual_analysis.json.
"""

import json
import re
import unicodedata
from pathlib import Path
from difflib import SequenceMatcher
import pdfplumber

def norm(text: str) -> str:
    text = unicodedata.normalize('NFD', text)
    text = re.sub(r'[\u0300-\u036f]', '', text)
    text = re.sub(r'[^a-zA-Z0-9\s]', ' ', text)
    return re.sub(r'\s+', ' ', text).strip().lower()

# Mapeo de páginas separadoras de sección a su autor correspondiente
SECTION_PAGES = {
    18: "Varios",
    115: "Moisés y Claudia",
    121: "Manantial de Cristo",
    129: "Conjunto Dorrego",
    146: "Conjunto Mansilla",
    155: "Conjunto Lazarte",
    161: "Conjunto Trigales",
    182: "Conjunto Tucumán",
    218: "Conjunto Villanueva",
    257: "Conjunto Luján",
    288: "Conjunto San Juan",
    300: "Conjunto de Noa",
    319: "Conjunto de Bariloche",
    330: "Tito Abarca",
    363: "Varios",
}

# Páginas en blanco que acompañan a las carátulas
BLANK_PAGES = {116, 122, 130, 147, 156, 183, 219, 258, 289, 301, 320, 331, 364}

def clean_title(title_raw: str) -> str:
    t = title_raw.strip()
    # Si viene en mayúsculas completas, formatear a Title Case limpio respetando preposiciones
    if t.isupper() and len(t) > 3:
        words = t.split()
        lower_words = {"de", "del", "la", "las", "el", "los", "en", "con", "por", "para", "a", "al", "y", "o", "tu", "su", "mi"}
        title_cased = []
        for i, w in enumerate(words):
            wl = w.lower()
            if i > 0 and wl in lower_words:
                title_cased.append(wl)
            else:
                title_cased.append(w.capitalize())
        return " ".join(title_cased)
    return t

def extract_manual():
    pdf_path = Path("Manual de Alabanzas Cristianas.pdf")
    if not pdf_path.exists():
        print(f"Error: No se encontró {pdf_path}")
        return []

    print(f"Iniciando extracción 1:1 de '{pdf_path.name}'...")
    extracted_hymns = []
    current_author = "Varios"

    with pdfplumber.open(pdf_path) as pdf:
        total_pages = len(pdf.pages)
        print(f"Total páginas a procesar: {total_pages}")

        for page_idx in range(17, total_pages): # Desde página 18 (0-indexed 17)
            page_num = page_idx + 1

            # Detectar cambio de sección
            if page_num in SECTION_PAGES:
                current_author = SECTION_PAGES[page_num]
                print(f"  [Pág {page_num:3d}] Sección detectada: '{current_author}'")
                continue

            if page_num in BLANK_PAGES:
                continue

            page = pdf.pages[page_idx]
            # Recorte exacto para eliminar margen derecho (marca de agua lateral)
            # ancho = 612, alto = 792
            cropped = page.crop((30, 25, 550, 755))
            text = cropped.extract_text() or ""
            lines = [l.strip() for l in text.split("\n") if l.strip()]

            if not lines:
                continue

            # Eliminar número de página al final si existe
            if lines[-1].isdigit():
                lines = lines[:-1]

            if len(lines) < 3:
                # Muy pocas líneas para ser alabanza, probablemente carátula no registrada
                continue

            raw_title = lines[0]
            title = clean_title(raw_title)
            content = "\n".join(lines[1:]).strip()

            if not content:
                continue

            extracted_hymns.append({
                "page": page_num,
                "title": title,
                "raw_title": raw_title,
                "author": current_author,
                "content": content,
                "line_count": len(lines),
                "char_count": len(content)
            })

    print(f"Extracción completada: {len(extracted_hymns)} alabanzas válidas extraídas de {total_pages} páginas.")
    
    out_extracted = Path("data/manual_extracted.json")
    with open(out_extracted, "w", encoding="utf-8") as f:
        json.dump(extracted_hymns, f, indent=2, ensure_ascii=False)
    print(f"Guardado en: {out_extracted}")

    return extracted_hymns

def analyze_against_catalog(extracted_hymns):
    catalog_path = Path("data/catalog.json")
    with open(catalog_path, "r", encoding="utf-8") as f:
        catalog_data = json.load(f)
    catalog = catalog_data["hymns"]

    print(f"\nIniciando cotejo matricial 1:1 contra catálogo actual ({len(catalog)} alabanzas)...")

    # Pre-calcular normalizaciones del catálogo
    cat_items = []
    for h in catalog:
        if h.get("isDeleted", False):
            continue
        c_title_norm = norm(h["title"])
        c_content_norm = norm(h["content"])
        c_words = c_content_norm.split()
        cat_items.append({
            "id": h["id"],
            "title": h["title"],
            "author": h.get("author", ""),
            "title_norm": c_title_norm,
            "content_norm": c_content_norm,
            "content_words": c_words,
            "content_set": set(c_words),
            "extra_versions": h.get("extraVersions", [])
        })

    exact_matches = []
    version_candidates = []
    new_hymns = []

    for item in extracted_hymns:
        m_title = item["title"]
        m_author = item["author"]
        m_content = item["content"]
        m_title_norm = norm(m_title)
        m_content_norm = norm(m_content)
        m_words = m_content_norm.split()
        m_set = set(m_words)

        best_match = None
        best_sim = 0.0

        for ch in cat_items:
            # 1. Chequeo por título idéntico
            exact_title = (m_title_norm == ch["title_norm"] and len(m_title_norm) > 3)

            # Filtro rápido Jaccard de palabras compartidas
            shared = len(m_set & ch["content_set"])
            max_len = max(len(m_set), len(ch["content_set"]))
            jaccard = shared / max_len if max_len > 0 else 0

            if not exact_title and jaccard < 0.35:
                continue

            # Comparación SequenceMatcher precisa
            # Para rapidez, comparamos primeros 300 tokens si hay coincidencia
            sim = SequenceMatcher(None, m_words[:350], ch["content_words"][:350]).ratio()

            if sim > best_sim:
                best_sim = sim
                best_match = ch

            if exact_title and sim > best_sim:
                best_sim = max(best_sim, sim)
                best_match = ch

        # Clasificación
        if best_match and best_sim >= 0.88:
            exact_matches.append({
                "manual_page": item["page"],
                "manual_title": m_title,
                "manual_author": m_author,
                "catalog_id": best_match["id"],
                "catalog_title": best_match["title"],
                "catalog_author": best_match["author"],
                "similarity": round(best_sim, 3),
                "author_can_enrich": (not best_match["author"] or best_match["author"].lower() in ["varios", "desconocido", "sin autor"]) and m_author not in ["Varios", "Desconocido"]
            })
        elif best_match and (best_sim >= 0.50 or (norm(best_match["title"]) == m_title_norm and best_sim >= 0.30)):
            version_candidates.append({
                "manual_page": item["page"],
                "manual_title": m_title,
                "manual_author": m_author,
                "catalog_id": best_match["id"],
                "catalog_title": best_match["title"],
                "catalog_author": best_match["author"],
                "similarity": round(best_sim, 3),
                "manual_snippet": m_content[:150],
                "catalog_snippet": best_match["content_norm"][:150]
            })
        else:
            new_hymns.append(item)

    print(f"\n=======================================================")
    print(f"RESULTADOS DEL ANÁLISIS 1:1:")
    print(f"  - Coincidencias Exactas en catálogo: {len(exact_matches)}")
    enrich_count = sum(1 for em in exact_matches if em["author_can_enrich"])
    print(f"    (de las cuales {enrich_count} pueden enriquecer autores faltantes)")
    print(f"  - Variantes / Candidatas a Multiversión: {len(version_candidates)}")
    print(f"  - Alabanzas Totalmente Nuevas: {len(new_hymns)}")
    print(f"=======================================================")

    analysis_data = {
        "source_pdf": "Manual de Alabanzas Cristianas.pdf",
        "total_extracted": len(extracted_hymns),
        "exact_matches_count": len(exact_matches),
        "authors_enrichable_count": enrich_count,
        "version_candidates_count": len(version_candidates),
        "new_hymns_count": len(new_hymns),
        "exact_matches": exact_matches,
        "version_candidates": version_candidates,
        "new_hymns": new_hymns
    }

    out_analysis = Path("data/manual_analysis.json")
    with open(out_analysis, "w", encoding="utf-8") as f:
        json.dump(analysis_data, f, indent=2, ensure_ascii=False)
    print(f"Informe analítico guardado en: {out_analysis}")

if __name__ == "__main__":
    extracted = extract_manual()
    if extracted:
        analyze_against_catalog(extracted)
