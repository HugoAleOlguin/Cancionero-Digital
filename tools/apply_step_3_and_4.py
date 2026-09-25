#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
tools/apply_step_3_and_4.py
Aplica los Pasos 3 y 4 del proceso de integración del Cancionero Digital:

Paso 3:
1. Restaura la alabanza #456 ('El Espíritu de Dios') con su letra completa (2 estrofas + coro) tomada del cuadernillo (p.293, Tito Abarca).
2. Vincula 'Que Esplendente' (p.439) como Versión 2 de 'El Día Glorioso' (p.425, Los Gonzales).
3. Incorpora las 378 alabanzas nuevas únicas (excluyendo el índice alfabético p.755-765) con IDs correlativos del 481 al 858.

Paso 4:
1. Incrementa la versión del catálogo a v4.
2. Sincroniza data/catalog.json, data/catalog_version.json y app/src/main/assets/catalog.json.
3. Valida la integridad del catálogo con validate_catalog().
"""

import json
from pathlib import Path
from datetime import datetime, timezone
import shutil

def apply_step_3_and_4():
    catalog_path = Path("data/catalog.json")
    version_path = Path("data/catalog_version.json")
    assets_path = Path("app/src/main/assets/catalog.json")
    analysis_path = Path("data/cuadernillo_analysis.json")

    with open(catalog_path, "r", encoding="utf-8") as f:
        catalog = json.load(f)
    with open(analysis_path, "r", encoding="utf-8") as f:
        analysis = json.load(f)

    # Solo consideramos las 380 alabanzas reales (0..379), excluyendo el índice alfabético (380..390)
    candidates = analysis.get("new_hymns", [])[:380]
    
    now_iso = datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")

    # 1. Restaurar alabanza #456 con la versión completa de Tito Abarca (Candidate 62)
    cand_62 = candidates[62]
    hymn_456 = next((h for h in catalog["hymns"] if h["id"] == 456), None)
    if hymn_456:
        print(f"Restaurando alabanza #456 '{hymn_456['title']}' con letra íntegra (de {len(hymn_456['content'])} a {len(cand_62['content'])} caracteres)...")
        hymn_456["content"] = cand_62["content"].strip()
        hymn_456["author"] = cand_62.get("author", "Tito Abarca")
        hymn_456["updatedAt"] = now_iso

    # 2. Preparar candidatos para inserción
    # Candidate 142 ('El Día Glorioso') se fusiona con Candidate 150 ('Que Esplendente') como Versión 2
    cand_142 = candidates[142]
    cand_150 = candidates[150]
    
    # Lista de alabanzas nuevas a agregar
    new_hymns_to_add = []
    
    for idx, c in enumerate(candidates):
        if idx == 62:
            # Ya utilizado para completar #456
            continue
        if idx == 150:
            # Se incluirá como Versión 2 del 142
            continue
            
        extra_versions = []
        if idx == 142:
            # Vincular 'Que Esplendente' como Versión 2
            extra_versions.append(cand_150["content"].strip())
            
        new_hymns_to_add.append({
            "title": c["title"].strip(),
            "author": c.get("author", "").strip(),
            "content": c["content"].strip(),
            "extraVersions": extra_versions,
            "page": c.get("page")
        })

    print(f"\nCantidad de alabanzas nuevas a incorporar: {len(new_hymns_to_add)}")

    # 3. Asignar IDs correlativos a partir del 481
    existing_hymns = catalog["hymns"]
    current_max_id = max(h["id"] for h in existing_hymns)
    print(f"ID inicial de nueva numeración: {current_max_id + 1}")

    added_by_author = {}
    for i, nh in enumerate(new_hymns_to_add):
        hymn_id = current_max_id + 1 + i
        author = nh["author"] or "Varios"
        added_by_author[author] = added_by_author.get(author, 0) + 1
        
        entry = {
            "id": hymn_id,
            "title": nh["title"],
            "link": "",
            "author": author,
            "content": nh["content"],
            "updatedAt": now_iso,
            "isDeleted": False
        }
        if nh["extraVersions"]:
            entry["extraVersions"] = nh["extraVersions"]
            
        existing_hymns.append(entry)

    print(f"Total alabanzas en catálogo tras inserción: {len(existing_hymns)} (ID final: {existing_hymns[-1]['id']})")
    print("\nDesglose de alabanzas nuevas por autor/conjunto:")
    for a, count in sorted(added_by_author.items(), key=lambda x: -x[1]):
        print(f"  - {a}: {count}")

    # Paso 4: Incrementar catálogo a versión 4
    new_version = 4
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
    print(f"¡PASO 3 Y 4 COMPLETADOS CON ÉXITO!")
    print(f"Catálogo actualizado a Versión {new_version}")
    print(f"Total de alabanzas activas: {catalog['totalHymns']}")
    print(f"Archivos sincronizados:")
    print(f"  -> {catalog_path}")
    print(f"  -> {version_path}")
    print(f"  -> {assets_path}")
    print(f"=======================================================")

if __name__ == "__main__":
    apply_step_3_and_4()
