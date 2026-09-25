import sys
from pathlib import Path
sys.path.insert(0, str(Path(__file__).resolve().parent))
from manage_hymns import load_catalog, save_catalog

TITO_MATCHES = [
    (437, "https://www.youtube.com/watch?v=k-TKjct8b5c", "Las Bodas"),
    (438, "https://www.youtube.com/watch?v=_mQKytCyTAY", "Sopla Señor"),
    (439, "https://www.youtube.com/watch?v=KyNum9IuS1w", "Yo Conozco"),
    (449, "https://www.youtube.com/watch?v=Y-1DZqpTboA", "El Jordán"),
    (478, "https://www.youtube.com/watch?v=VnCUWMiUBJo", "Bendice alma mía a Jehová"),
    (572, "https://www.youtube.com/watch?v=vX8zIqeJq0k", "Tu corazón en Horeb"),
    (581, "https://www.youtube.com/watch?v=fwyQG7gQyMo", "Despierta mi Primer Amor"),
]

def main():
    catalog = load_catalog()
    hymns = catalog.get("hymns", [])
    by_id = {h["id"]: h for h in hymns}

    for hid, link, title in TITO_MATCHES:
        h = by_id[hid]
        if not h.get("link", "").strip():
            h["link"] = link
            print(f"  + Inyectado #{hid} '{h['title']}' -> {link}")
        else:
            print(f"  - Ya tenía link #{hid}: {h.get('link')}")

    catalog["version"] = 15
    save_catalog(catalog)
    print("Catálogo v15 guardado y sincronizado con éxito.")

if __name__ == "__main__":
    main()
