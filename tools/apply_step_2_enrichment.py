#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
tools/apply_step_2_enrichment.py
Aplica el Paso 2:
1. Enriquece las 64 alabanzas existentes con sus autores auténticos extraídos del PDF.
2. Vincula las 27 versiones alternativas como extraVersions = [pdf_content] (Versión 2).
3. Incrementa la versión del catálogo a v3 y sincroniza data/catalog.json, data/catalog_version.json y app/src/main/assets/catalog.json.
"""

import json
from pathlib import Path
from datetime import datetime, timezone

def apply_step_2():
    catalog_path = Path("data/catalog.json")
    version_path = Path("data/catalog_version.json")
    assets_path = Path("app/src/main/assets/catalog.json")
    analysis_path = Path("data/cuadernillo_analysis.json")
    extracted_path = Path("data/cuadernillo_extracted.json")
    
    with open(catalog_path, "r", encoding="utf-8") as f:
        catalog = json.load(f)
    with open(analysis_path, "r", encoding="utf-8") as f:
        analysis = json.load(f)
    with open(extracted_path, "r", encoding="utf-8") as f:
        extracted = json.load(f)
        
    extracted_by_page = {x["page"]: x for x in extracted}
    hymn_dict = {h["id"]: h for h in catalog["hymns"]}
    
    now_iso = datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")
    
    # 1. Enriquecer autores en coincidencias exactas
    authors_enriched_count = 0
    for em in analysis.get("exact_matches", []):
        hid = em["catalog_id"]
        pdf_author = em.get("pdf_author", "").strip()
        if hid in hymn_dict and pdf_author:
            current_author = hymn_dict[hid].get("author", "").strip()
            if not current_author:
                hymn_dict[hid]["author"] = pdf_author
                hymn_dict[hid]["updatedAt"] = now_iso
                authors_enriched_count += 1
                
    print(f"Alabanzas existentes enriquecidas con autor: {authors_enriched_count}")
    
    # 2. Vincular las 27 versiones alternativas (Versión 2)
    versions_linked_count = 0
    for vc in analysis.get("version_candidates", []):
        hid = vc["catalog_id"]
        page_num = vc["pdf_page"]
        pdf_item = extracted_by_page.get(page_num)
        
        if hid in hymn_dict and pdf_item:
            target = hymn_dict[hid]
            pdf_content = pdf_item["content"].strip()
            
            # Asegurarse de no duplicar si es idéntica
            if pdf_content != target["content"].strip():
                existing_extras = target.get("extraVersions", [])
                if pdf_content not in existing_extras:
                    existing_extras.append(pdf_content)
                    target["extraVersions"] = existing_extras
                    target["updatedAt"] = now_iso
                    versions_linked_count += 1
                    
            # Si el autor está vacío, aprovechar el autor del PDF
            pdf_author = pdf_item.get("author", "").strip()
            if not target.get("author", "").strip() and pdf_author:
                target["author"] = pdf_author
                
    print(f"Alabanzas con Versión 2 vinculada exitosamente: {versions_linked_count}")
    
    # 3. Incrementar versión a v3 y guardar
    new_version = 3
    catalog["version"] = new_version
    catalog["updatedAt"] = now_iso
    catalog["totalHymns"] = len([h for h in catalog["hymns"] if not h.get("isDeleted", False)])
    
    catalog_str = json.dumps(catalog, indent=2, ensure_ascii=False)
    
    with open(catalog_path, "w", encoding="utf-8") as f:
        f.write(catalog_str)
        
    version_info = {
        "version": new_version,
        "updatedAt": now_iso,
        "totalHymns": catalog["totalHymns"]
    }
    with open(version_path, "w", encoding="utf-8") as f:
        json.dump(version_info, f, indent=2, ensure_ascii=False)
        
    with open(assets_path, "w", encoding="utf-8") as f:
        f.write(catalog_str)
        
    print(f"\nCatálogo v{new_version} guardado y sincronizado exitosamente:")
    print(f" - {catalog_path}")
    print(f" - {version_path}")
    print(f" - {assets_path}")

if __name__ == "__main__":
    apply_step_2()
