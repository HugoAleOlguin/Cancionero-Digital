import json
import re
import shutil
from pathlib import Path
from datetime import datetime, timezone

def clean_and_deduplicate():
    catalog_path = Path("data/catalog.json")
    version_path = Path("data/catalog_version.json")
    assets_path = Path("app/src/main/assets/catalog.json")
    backup_path = Path("data/catalog.backup.json")
    
    # 1. Crear respaldo
    shutil.copyfile(catalog_path, backup_path)
    print(f"Respaldo creado en {backup_path}")
    
    with open(catalog_path, "r", encoding="utf-8") as f:
        catalog = json.load(f)
        
    hymns = catalog["hymns"]
    print(f"Total inicial de alabanzas: {len(hymns)}")
    
    # 2. Correcciones de idioma y tipografía en todas las alabanzas
    replacements = [
        (r'\bBecause\b', 'Porque'),
        (r'\bbecause\b', 'porque'),
        (r'\bBut\b', 'Pero'),
        (r'\bbut\b', 'pero'),
        (r'\bWith\b', 'Con'),
        (r'\bwith\b', 'con'),
        (r'\band\b', 'y'),
        (r'\bIn\b', 'En'),
        (r'\bin\b', 'en'),
        (r'\bAt\b', 'Al'),
        (r'\bOf\b', 'De'),
        (r'\bGlory\b', 'Gloria'),
        (r'\bglory\b', 'gloria'),
        (r'\bThen\b', 'Entonces'),
        (r'\bthen\b', 'entonces'),
        (r'\bby\b', 'por'),
        (r'\bvoice\b', 'voz'),
        (r'\bMase\b', 'Más'),
        (r'\bmirarer\b', 'mirar'),
    ]
    
    total_text_fixes = 0
    for h in hymns:
        content = h["content"]
        if "\\n" in content:
            content = content.replace("\\n", "\n")
            total_text_fixes += 1
            
        lines = content.split("\n")
        new_lines = []
        for line in lines:
            mod_line = line
            for pat, rep in replacements:
                if re.search(pat, mod_line):
                    mod_line = re.sub(pat, rep, mod_line)
                    total_text_fixes += 1
            new_lines.append(mod_line)
        h["content"] = "\n".join(new_lines)
        
    print(f"Total de correcciones textuales/de idioma aplicadas: {total_text_fixes}")
    
    # 3. Tratamiento de Duplicados (dejar las versiones más largas y completas)
    h_dict = {h["id"]: h for h in hymns}
    
    # Caso 1: ID 257 ('Es la fe', 1223) vs ID 411 ('La Fe', 1518 con recitado completo de Hebreos 11)
    # Conservamos 411, le transferimos el link de YouTube de 257 y eliminamos 257.
    if 411 in h_dict and 257 in h_dict:
        if not h_dict[411].get("link") and h_dict[257].get("link"):
            h_dict[411]["link"] = h_dict[257]["link"]
            print("Transferido link de YouTube de #257 a #411 ('La Fe')")
            
    # Caso 2: ID 33 ('A veces en pesar', 721) vs ID 295 ('La Senda', 707)
    # Conservamos 33 (más larga y mejor formateada) y eliminamos 295.
    if 33 in h_dict and 295 in h_dict:
        print("Conservando #33 ('A veces en pesar', 721 chars) y descartando duplicado más corto #295")
        
    # Caso 3: ID 103 ('El Hijo se va', 785) vs ID 185 ('Hijo pródigo (Villanueva)', 785)
    # Mergear información: Título enriquecido y Autor Villanueva.
    if 103 in h_dict and 185 in h_dict:
        h_dict[103]["title"] = "El Hijo se va (Hijo pródigo)"
        h_dict[103]["author"] = "Villanueva"
        if not h_dict[103].get("link"):
            h_dict[103]["link"] = h_dict[185].get("link", "")
        print("Mergear #103 y #185 -> 'El Hijo se va (Hijo pródigo)' de Villanueva")

    # Caso 4: ID 139 ('No dejes de luchar', 487) vs ID 197 ('No dejes de luchar (Medina)', 487)
    # Mergear autores: 'Conjunto Amigo Fiel / Medina'
    if 139 in h_dict and 197 in h_dict:
        h_dict[139]["author"] = "Conjunto Amigo Fiel / Medina"
        print("Mergear autores en #139: 'Conjunto Amigo Fiel / Medina'")
        
    # Caso 5: ID 6 ('Vaso Nuevo', 275) vs ID 166 ('El alfarero', 145)
    # Completar ID 6 con ambas estrofas tradicionales completas.
    if 6 in h_dict and 166 in h_dict:
        h_dict[6]["content"] = (
            "I\n"
            "Yo quiero ser Señor amado\n"
            "Como el barro en las manos del Alfarero\n"
            "Toma mi vida, hazla de nuevo\n"
            "Yo quiero ser, yo quiero ser\n"
            "Un vaso nuevo.\n\n"
            "II\n"
            "Señor yo quiero abandonarme\n"
            "Como el barro en las manos del alfarero\n"
            "Toma mi vida y hazla de nuevo,\n"
            "Yo quiero ser, yo quiero ser\n"
            "Un vaso nuevo."
        )
        print("Completado #6 ('Vaso Nuevo') con ambas estrofas tradicionales completas")
        
    # IDs duplicados más cortos a descartar
    ids_to_remove = {257, 295, 185, 197, 166}
    
    filtered_hymns = [h for h in hymns if h["id"] not in ids_to_remove]
    print(f"Alabanzas restantes tras eliminar duplicados: {len(filtered_hymns)}")
    
    # 4. Reindexar secuencialmente 1..N
    now_iso = datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")
    for new_idx, h in enumerate(filtered_hymns, 1):
        h["id"] = new_idx
        h["updatedAt"] = now_iso
        h["isDeleted"] = False
        
    # 5. Incrementar versión del catálogo a v2
    catalog["version"] = 2
    catalog["updatedAt"] = now_iso
    catalog["totalHymns"] = len(filtered_hymns)
    catalog["hymns"] = filtered_hymns
    
    # 6. Guardar en data/catalog.json, data/catalog_version.json y app/src/main/assets/catalog.json
    catalog_json_str = json.dumps(catalog, indent=2, ensure_ascii=False)
    with open(catalog_path, "w", encoding="utf-8") as f:
        f.write(catalog_json_str)
        
    version_info = {
        "version": 2,
        "updatedAt": now_iso,
        "totalHymns": len(filtered_hymns)
    }
    with open(version_path, "w", encoding="utf-8") as f:
        json.dump(version_info, f, indent=2, ensure_ascii=False)
        
    with open(assets_path, "w", encoding="utf-8") as f:
        f.write(catalog_json_str)
        
    print(f"Catálogo v2 guardado exitosamente con {len(filtered_hymns)} alabanzas.")
    print(f"Archivos sincronizados:\n - {catalog_path}\n - {version_path}\n - {assets_path}")

if __name__ == "__main__":
    clean_and_deduplicate()
