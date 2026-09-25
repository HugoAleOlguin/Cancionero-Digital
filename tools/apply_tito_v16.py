import sys
from pathlib import Path
sys.path.insert(0, str(Path(__file__).resolve().parent))
from manage_hymns import load_catalog, save_catalog

TITO_MATCHES_V16 = [
    (434, "https://www.youtube.com/watch?v=_eYCrRKu-KU", "¿Hasta Cuándo Señor?", None),
    (469, "https://www.youtube.com/watch?v=m1tUuopAReg", "Josué", None),
    (477, "https://www.youtube.com/watch?v=jpbYB3DAaug", "Señor quiero ser Limpio", None),
    (480, "https://www.youtube.com/watch?v=s2fmtH7vARQ", "El Leproso", None),
    (583, "https://www.youtube.com/watch?v=_OgaEa_dF30", "Puedo oír Tu Voz", None),
    (876, "https://www.youtube.com/watch?v=BCHr3QJGOaQ", "Eliseo y Naamán", "Tito Abarca"),
    (920, "https://www.youtube.com/watch?v=KnOBBzSgYiE", "Nehemías", "Tito Abarca"),
]

def main():
    catalog = load_catalog()
    hymns = catalog.get("hymns", [])
    by_id = {h["id"]: h for h in hymns}

    for hid, link, title, new_author in TITO_MATCHES_V16:
        h = by_id[hid]
        old_link = h.get("link", "").strip()
        h["link"] = link
        if new_author:
            old_author = h.get("author", "")
            h["author"] = new_author
            print(f"  + #{hid} '{h['title']}' [Autor: '{old_author}' -> '{new_author}'] -> {link}")
        else:
            print(f"  + #{hid} '{h['title']}' [{h.get('author')}] -> {link}")

    catalog["version"] = 16
    save_catalog(catalog)
    print("\nCatálogo v16 guardado y sincronizado en data/ y app/src/main/assets/ con éxito.")

if __name__ == "__main__":
    main()
