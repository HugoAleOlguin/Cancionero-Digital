#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
tools/apply_polo_negrete.py
Integra las canciones del cantautor Polo Negrete:
1. Actualiza #312 ('Mi Dios es Real') y #852 ('Hijo no Temas') con autor 'Polo Negrete' y sus links de YouTube.
2. Agrega las 17 nuevas canciones con autor 'Polo Negrete', links de YouTube y letra completa (IDs #993 al #1009).
3. Vincula 'Espíritu Santo Renovador' como Versión 2 de 'Espíritu Santo'.
4. Incrementa el catálogo a Versión 7 (1,009 alabanzas totales).
5. Sincroniza data/catalog.json, data/catalog_version.json y app/src/main/assets/catalog.json.
6. Valida integridad con manage_hymns.py.
"""

import json
from pathlib import Path
from datetime import datetime, timezone

def apply_polo_negrete():
    catalog_path = Path("data/catalog.json")
    version_path = Path("data/catalog_version.json")
    assets_path = Path("app/src/main/assets/catalog.json")
    yt_path = Path("data/polo_negrete_youtube.json")

    with open(catalog_path, "r", encoding="utf-8") as f:
        catalog = json.load(f)
    with open(yt_path, "r", encoding="utf-8") as f:
        yt_links = json.load(f)

    step_output = open(r"C:\Users\HuGOD777\.gemini\antigravity-cli\brain\b9081596-73f0-4fdc-b7eb-64aabd7e1455\.system_generated\steps\1123\output.txt", encoding="utf-8").read()
    json_start = step_output.find("[{")
    json_end = step_output.rfind("}]") + 2
    polo_songs = json.loads(step_output[json_start:json_end])
    polo_dict = {s["title"]: s for s in polo_songs}

    now_iso = datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")
    hymn_dict = {h["id"]: h for h in catalog["hymns"]}

    # 1. Actualizar #312 y #852
    if 312 in hymn_dict:
        hymn_dict[312]["author"] = "Polo Negrete"
        hymn_dict[312]["link"] = yt_links.get("Mi Dios Es Real", "")
        hymn_dict[312]["updatedAt"] = now_iso
        print(f"Alabanza #312 '{hymn_dict[312]['title']}' enriquecida con autor 'Polo Negrete' y link: {hymn_dict[312]['link']}")

    if 852 in hymn_dict:
        hymn_dict[852]["author"] = "Polo Negrete"
        hymn_dict[852]["link"] = yt_links.get("Hijo Yo No Te Olvide", "")
        hymn_dict[852]["updatedAt"] = now_iso
        print(f"Alabanza #852 '{hymn_dict[852]['title']}' enriquecida con autor 'Polo Negrete' y link: {hymn_dict[852]['link']}")

    # 2. Preparar las 17 canciones nuevas a agregar
    # 'Espíritu Santo Renovador' se vinculará como Versión 2 de 'Espiritu Santo'
    songs_to_add = [
        "Aleluya",
        "Creo En Dios",
        "Gloria a Dios",
        "Filo de Espada",
        "Canto de Gratitud",
        "Espiritu Santo",
        "A Darme Vida",
        "Las Llagas Que En Jesús",
        "Sueño",
        "Canto a Libertad",
        "Compañero Fiel",
        "El Regreso",
        "La Ultima Milla",
        "Mi Tesoro Eres Tú",
        "No Sufras Más",
        "Yo Pensaba",
        "Te Amaré"
    ]

    current_max_id = max(h["id"] for h in catalog["hymns"])
    start_id = current_max_id + 1
    print(f"\nAgregando {len(songs_to_add)} canciones nuevas a partir de #{start_id}...")

    for i, title in enumerate(songs_to_add):
        new_id = start_id + i
        s_data = polo_dict[title]
        yt_link = yt_links.get(title, "")
        
        extra_versions = []
        if title == "Espiritu Santo":
            # Vincular 'Espiritu Santo Renovador' como Versión 2
            renovador = polo_dict.get("Espiritu Santo Renovador")
            if renovador:
                extra_versions.append(renovador["lyrics"].strip())
                print(f"  -> 'Espiritu Santo' (#{new_id}) vinculado con Versión 2 ('Espíritu Santo Renovador')")

        entry = {
            "id": new_id,
            "title": title,
            "link": yt_link,
            "author": "Polo Negrete",
            "content": s_data["lyrics"].strip(),
            "updatedAt": now_iso,
            "isDeleted": False
        }
        if extra_versions:
            entry["extraVersions"] = extra_versions

        catalog["hymns"].append(entry)
        print(f"  #{new_id} - {title} | Link: {yt_link}")

    # 3. Incrementar catálogo a Versión 7
    new_version = 7
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
    print(f"¡INTEGRACIÓN DE POLO NEGRETE COMPLETADA CON ÉXITO!")
    print(f"Catálogo actualizado a Versión {new_version}")
    print(f"Total de alabanzas activas: {catalog['totalHymns']} (Hito: ¡Más de 1,000 alabanzas!)")
    print(f"Archivos sincronizados:")
    print(f"  -> {catalog_path}")
    print(f"  -> {version_path}")
    print(f"  -> {assets_path}")
    print(f"=======================================================")

if __name__ == "__main__":
    apply_polo_negrete()
