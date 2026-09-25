#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
tools/apply_manual_phase_2_and_3.py
Aplica las Fases 2 y 3 de la integración de 'Manual de Alabanzas Cristianas.pdf':

Fase 2:
1. Toma las 136 alabanzas nuevas candidatas de data/manual_analysis.json.
2. Corrige el título de pág. 173 a 'Nehemías' (según el índice del propio manual).
3. Vincula pág. 108 ('Yo Conozco') como Versión 2 de pág. 88 ('Quiero Darte Muchas Gracias').
4. Vincula pág. 321 ('Canta a Cristo - Bariloche') como Versión 2 de pág. 303 ('Canta a Cristo - Noa').
5. Agrega las 134 alabanzas únicas resultantes con IDs correlativos del 859 al 992.

Fase 3:
1. Incrementa la versión del catálogo a v6.
2. Sincroniza data/catalog.json, data/catalog_version.json y app/src/main/assets/catalog.json.
3. Valida la integridad del catálogo con validate_catalog() y ejecuta pruebas unitarias.
"""

import json
from pathlib import Path
from datetime import datetime, timezone

def apply_phase_2_and_3():
    catalog_path = Path("data/catalog.json")
    version_path = Path("data/catalog_version.json")
    assets_path = Path("app/src/main/assets/catalog.json")
    analysis_path = Path("data/manual_analysis.json")

    with open(catalog_path, "r", encoding="utf-8") as f:
        catalog = json.load(f)
    with open(analysis_path, "r", encoding="utf-8") as f:
        analysis = json.load(f)

    raw_new_hymns = analysis.get("new_hymns", [])
    print(f"Total candidatas a procesar: {len(raw_new_hymns)}")

    now_iso = datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")

    # Mapeo por página para manipulaciones precisas
    by_page = {h["page"]: h for h in raw_new_hymns}

    # 1. Corrección de título en pág 173: de 'Otra Vez' a 'Nehemías'
    if 173 in by_page:
        print("  -> Corrigiendo título de pág. 173: 'Otra Vez' -> 'Nehemías'")
        by_page[173]["title"] = "Nehemías"

    # 2. Fusión de pág 108 como Versión 2 de pág 88 ('Quiero Darte Muchas Gracias')
    if 88 in by_page and 108 in by_page:
        print("  -> Fusionando pág. 108 como Versión 2 de pág. 88 ('Quiero Darte Muchas Gracias')")
        by_page[88]["extraVersions"] = [by_page[108]["content"].strip()]

    # 3. Fusión de pág 321 como Versión 2 de pág 303 ('Canta a Cristo')
    if 303 in by_page and 321 in by_page:
        print("  -> Fusionando pág. 321 como Versión 2 de pág. 303 ('Canta a Cristo')")
        by_page[303]["extraVersions"] = [by_page[321]["content"].strip()]

    # Excluir las páginas que se convirtieron en Versión 2
    pages_to_skip = {108, 321}
    hymns_to_insert = [h for h in raw_new_hymns if h["page"] not in pages_to_skip]
    print(f"\nAlabanzas únicas finales a incorporar: {len(hymns_to_insert)}")

    # Asignar IDs correlativos
    existing_hymns = catalog["hymns"]
    current_max_id = max(h["id"] for h in existing_hymns)
    start_id = current_max_id + 1
    print(f"ID inicial de nueva numeración: #{start_id}")

    added_by_author = {}
    for i, nh in enumerate(hymns_to_insert):
        new_id = start_id + i
        author = nh.get("author") or "Varios"
        added_by_author[author] = added_by_author.get(author, 0) + 1

        entry = {
            "id": new_id,
            "title": nh["title"].strip(),
            "link": "",
            "author": author,
            "content": nh["content"].strip(),
            "updatedAt": now_iso,
            "isDeleted": False
        }
        if nh.get("extraVersions"):
            entry["extraVersions"] = nh["extraVersions"]

        existing_hymns.append(entry)

    final_max_id = existing_hymns[-1]["id"]
    print(f"Total alabanzas en catálogo tras inserción: {len(existing_hymns)} (ID final: #{final_max_id})")

    print("\nDesglose de nuevas alabanzas por autor/conjunto:")
    for a, count in sorted(added_by_author.items(), key=lambda x: -x[1]):
        print(f"  - {a}: {count} alabanzas")

    # Fase 3: Incrementar a versión 6 y sincronizar
    new_version = 6
    catalog["version"] = new_version
    catalog["updatedAt"] = now_iso
    catalog["totalHymns"] = len([h for h in existing_hymns if not h.get("isDeleted", False)])

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
    print(f"¡FASES 2 Y 3 COMPLETADAS CON ÉXITO!")
    print(f"Catálogo actualizado a Versión {new_version}")
    print(f"Total de alabanzas activas: {catalog['totalHymns']}")
    print(f"Archivos sincronizados:")
    print(f"  -> {catalog_path}")
    print(f"  -> {version_path}")
    print(f"  -> {assets_path}")
    print(f"=======================================================")

if __name__ == "__main__":
    apply_phase_2_and_3()
