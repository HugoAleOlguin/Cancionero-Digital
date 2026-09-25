import sys
from pathlib import Path
sys.path.insert(0, str(Path(__file__).resolve().parent))
sys.path.insert(0, str(Path(__file__).resolve().parent.parent))
from manage_hymns import load_catalog, save_catalog

NEW_MATCHES = [
    # Perico Jujuy / Cantando para Cristo (17)
    (481, "https://www.youtube.com/watch?v=Q_Ul-JLAmDI", "Carta a Timoteo"),
    (484, "https://www.youtube.com/watch?v=r6OXCF_xIvw", "Mi Rey Jesús"),
    (485, "https://www.youtube.com/watch?v=1UEHrrHiY1Q", "Ahora Todo es con mi Jesús"),
    (489, "https://www.youtube.com/watch?v=j7sJ28Gla4g", "Testimonio"),
    (494, "https://www.youtube.com/watch?v=w1rn9vReu8A", "Perdóname Señor"),
    (496, "https://www.youtube.com/watch?v=9hJ6T-K33JA", "Yo Soy tu Dios"),
    (501, "https://www.youtube.com/watch?v=_9elZ3Ehf54", "Nunca te abandonó"),
    (511, "https://www.youtube.com/watch?v=qVS594HkkO4", "Algo va a Pasar"),
    (512, "https://www.youtube.com/watch?v=zv6fushcxx8", "El Hijo Prodigo"),
    (514, "https://www.youtube.com/watch?v=wriDOxrGY-8", "Jesús el Gran Capitán"),
    (515, "https://www.youtube.com/watch?v=IuNgH0KSWNM", "Tengo miedo que te Olvides"),
    (518, "https://www.youtube.com/watch?v=SJVtuMtFtA8", "La Voz de Jehová"),
    (519, "https://www.youtube.com/watch?v=JFFIAWSafP0", "Solo Tú Señor"),
    (522, "https://www.youtube.com/watch?v=1j7aSJa_tXU", "El Dulce Hogar"),
    (523, "https://www.youtube.com/watch?v=TR8_WLUGmRQ", "Todo Pasará"),
    (526, "https://www.youtube.com/watch?v=l4xfNZibSsI", "Tu Presencia aquí"),
    (530, "https://www.youtube.com/watch?v=Dh1EtFBZ87Y", "La Trompeta Sonará"),

    # Conjunto Luján (2)
    (932, "https://www.youtube.com/watch?v=2dGr2y8oEmU", "Mirame Señor"),
    (934, "https://www.youtube.com/watch?v=f6NTrz1p_9o", "Yo Soy de Jesús"),

    # Conjunto Central de Tucumán (9)
    (384, "https://www.youtube.com/watch?v=wTxqLc_zFSs", "La Visión de Daniel"),
    (388, "https://www.youtube.com/watch?v=3kPj9z6rc3Y", "Veremos al Rey"),
    (389, "https://www.youtube.com/watch?v=KmhzrwIk1xc", "El Ensueño de la Iglesia"),
    (392, "https://www.youtube.com/watch?v=KnsIyWVM37I", "Sembrador"),
    (403, "https://www.youtube.com/watch?v=z0NgG0bfqyE", "Carta al Hijo Prodigo"),
    (408, "https://www.youtube.com/watch?v=3lp4rdGYqm8", "Más que Vencedores"),
    (409, "https://www.youtube.com/watch?v=OXbDat9yYgY", "Pedro"),
    (411, "https://www.youtube.com/watch?v=jBDFWjkK-iY", "Zorobabel"),
    (413, "https://www.youtube.com/watch?v=U2qiOifs67U", "El Leproso"),

    # Los Gonzales / Hermanos González (5)
    (591, "https://www.youtube.com/watch?v=eZq2X6Zl3Gg", "Cantar para Dios"),
    (615, "https://www.youtube.com/watch?v=u6QAnmxZ4ZY", "Te alabo Padre Dios"),
    (625, "https://www.youtube.com/watch?v=1_D_si2hRSs", "Mi Buen Jesús"),
    (872, "https://www.youtube.com/watch?v=CX57iyuJOKI", "Envíame a Mí"),
    (969, "https://www.youtube.com/watch?v=QKQkbGU8ZmI", "Mi Gran Maestro"),
]

def main():
    catalog = load_catalog()
    hymns = catalog.get("hymns", [])
    by_id = {h["id"]: h for h in hymns}

    print(f"Cargado catálogo v{catalog.get('version')}. Total himnos: {len(hymns)}")
    print(f"Alabanzas a inyectar: {len(NEW_MATCHES)}")

    # 1. Auditoría estricta de seguridad
    applied_count = 0
    for hid, new_link, label in NEW_MATCHES:
        if hid not in by_id:
            raise ValueError(f"ID #{hid} no encontrado en catálogo!")
        target = by_id[hid]
        current_link = target.get("link", "").strip()
        if current_link:
            raise ValueError(f"VIOLACIÓN DE SEGURIDAD: #{hid} '{target['title']}' ya tiene link: {current_link}")
        
        # Inyectar link
        target["link"] = new_link
        applied_count += 1
        print(f"  + Inyectado #{hid} '{target['title']}' -> {new_link}")

    # 2. Incrementar versión a 14
    old_version = catalog.get("version", 13)
    new_version = old_version + 1
    catalog["version"] = new_version
    print(f"\nIncrementando versión: {old_version} -> {new_version}")

    # 3. Guardar catálogo (data/catalog.json, data/catalog_version.json, app/src/main/assets/catalog.json)
    save_catalog(catalog)
    print("Catálogo guardado y sincronizado exitosamente en todos los destinos offline-first.")
    print(f"Total enlaces agregados: {applied_count}")

if __name__ == "__main__":
    main()
