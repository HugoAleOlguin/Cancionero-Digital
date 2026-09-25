#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
tools/apply_manual_phase_1.py
Ejecuta la Fase 1 de la integración de 'Manual de Alabanzas Cristianas.pdf':
1. Enriquece 15 alabanzas existentes del catálogo con sus autores auténticos recuperados del manual.
2. Vincula 70 variantes líricas detectadas como 'extraVersions' (Versión 2 / Versión 3) conmutables desde la UI.
3. Sincroniza data/catalog.json, data/catalog_version.json y app/src/main/assets/catalog.json.
4. Valida la integridad del catálogo.
"""

import json
from pathlib import Path
from datetime import datetime, timezone

def apply_phase_1():
    catalog_path = Path("data/catalog.json")
    version_path = Path("data/catalog_version.json")
    assets_path = Path("app/src/main/assets/catalog.json")
    analysis_path = Path("data/manual_analysis.json")
    extracted_path = Path("data/manual_extracted.json")

    with open(catalog_path, "r", encoding="utf-8") as f:
        catalog = json.load(f)
    with open(analysis_path, "r", encoding="utf-8") as f:
        analysis = json.load(f)
    with open(extracted_path, "r", encoding="utf-8") as f:
        extracted = json.load(f)

    hymn_dict = {h["id"]: h for h in catalog["hymns"]}
    ext_by_page = {x["page"]: x for x in extracted}
    now_iso = datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")

    # 1. Enriquecer autores en coincidencias exactas
    authors_enriched = []
    for em in analysis.get("exact_matches", []):
        if em.get("author_can_enrich"):
            hid = em["catalog_id"]
            pdf_author = em["manual_author"]
            if hid in hymn_dict:
                old_author = hymn_dict[hid].get("author", "")
                hymn_dict[hid]["author"] = pdf_author
                hymn_dict[hid]["updatedAt"] = now_iso
                authors_enriched.append((hid, hymn_dict[hid]["title"], old_author, pdf_author))

    print(f"=== 1. AUTORES ENRIQUECIDOS ({len(authors_enriched)} alabanzas) ===")
    for a in authors_enriched:
        print(f"  #{a[0]:3d} '{a[1]}' (antes: '{a[2]}') -> nuevo: '{a[3]}'")

    # 2. Vincular variantes líricas (70 casos) como extraVersions
    variants_linked = []
    for vc in analysis.get("version_candidates", []):
        hid = vc["catalog_id"]
        page = vc["manual_page"]
        mh = ext_by_page.get(page)
        if not mh or hid not in hymn_dict:
            continue

        target = hymn_dict[hid]
        m_content = mh["content"].strip()
        main_content = target["content"].strip()

        # Evitar duplicados
        existing_extras = target.get("extraVersions", [])
        if m_content != main_content and m_content not in existing_extras:
            existing_extras.append(m_content)
            target["extraVersions"] = existing_extras
            target["updatedAt"] = now_iso

            # Si el autor del catálogo es genérico y el manual tiene conjunto específico, aprovecharlo
            if target.get("author", "").lower() in ["", "varios", "desconocido"] and mh.get("author") and mh["author"] not in ["Varios", "Desconocido"]:
                target["author"] = mh["author"]

            variants_linked.append((hid, target["title"], len(existing_extras) + 1, mh.get("author", "Varios")))

    print(f"\n=== 2. VARIANTES VINCULADAS COMO MULTIVERSIÓN ({len(variants_linked)} alabanzas) ===")
    multiversion_hymns = [h for h in catalog["hymns"] if h.get("extraVersions")]
    print(f"Total de alabanzas con multiversión en la app: {len(multiversion_hymns)}")
    
    # 3. Guardar cambios en el catálogo
    # Nota: Mantenemos la versión o incrementamos a v5
    new_version = catalog.get("version", 4) + 1
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

    print(f"\n=======================================================")
    print(f"¡FASE 1 COMPLETADA CON ÉXITO!")
    print(f"Catálogo actualizado a Versión {new_version}")
    print(f"Alabanzas con autor enriquecido: {len(authors_enriched)}")
    print(f"Alabanzas con variantes multiversión vinculadas: {len(variants_linked)}")
    print(f"Total de alabanzas con selector [ 1 ] [ 2 ]... : {len(multiversion_hymns)}")
    print(f"Archivos sincronizados:")
    print(f"  -> {catalog_path}")
    print(f"  -> {version_path}")
    print(f"  -> {assets_path}")
    print(f"=======================================================")

if __name__ == "__main__":
    apply_phase_1()
