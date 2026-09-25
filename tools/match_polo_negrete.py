import json
import unicodedata
import re
from difflib import SequenceMatcher

def norm(text):
    text = unicodedata.normalize('NFD', text)
    text = re.sub(r'[\u0300-\u036f]', '', text)
    text = re.sub(r'[^a-zA-Z0-9\s]', ' ', text)
    return re.sub(r'\s+', ' ', text).strip().lower()

def main():
    # Load extracted songs from the previous step output
    step_output = open(r"C:\Users\HuGOD777\.gemini\antigravity-cli\brain\b9081596-73f0-4fdc-b7eb-64aabd7e1455\.system_generated\steps\1123\output.txt", encoding="utf-8").read()
    # Extract json array from output.txt
    json_start = step_output.find("[{")
    json_end = step_output.rfind("}]") + 2
    polo_songs = json.loads(step_output[json_start:json_end])

    catalog_data = json.load(open("data/catalog.json", encoding="utf-8"))
    catalog = catalog_data["hymns"]

    print(f"Total canciones de Polo Negrete en letras.com: {len(polo_songs)}")
    print(f"Total alabanzas en catálogo: {len(catalog)}")

    matches = []
    new_songs = []

    for ps in polo_songs:
        p_title = ps["title"]
        p_lyrics = ps["lyrics"]
        p_title_norm = norm(p_title)
        p_words = norm(p_lyrics).split()
        p_set = set(p_words)

        best_match = None
        best_sim = 0.0

        for ch in catalog:
            if ch.get("isDeleted", False):
                continue
            c_title_norm = norm(ch["title"])
            exact_title = (c_title_norm == p_title_norm)

            c_words = norm(ch["content"]).split()
            c_set = set(c_words)

            shared = len(p_set & c_set)
            jaccard = shared / max(len(p_set), len(c_set)) if max(len(p_set), len(c_set)) > 0 else 0

            if not exact_title and jaccard < 0.25:
                continue

            sim = SequenceMatcher(None, p_words[:300], c_words[:300]).ratio()
            if sim > best_sim:
                best_sim = sim
                best_match = ch

        if best_match and (best_sim >= 0.60 or (norm(best_match["title"]) == p_title_norm and best_sim >= 0.40)):
            matches.append({
                "polo_title": p_title,
                "catalog_id": best_match["id"],
                "catalog_title": best_match["title"],
                "current_author": best_match.get("author", ""),
                "current_link": best_match.get("link", ""),
                "similarity": round(best_sim, 2),
                "lyrics": p_lyrics
            })
        else:
            new_songs.append(ps)

    print(f"\n1. CANCIONES QUE YA ESTÁN EN LA APP ({len(matches)}):")
    for m in matches:
        print(f"  Polo: '{m['polo_title']}' == CAT #{m['catalog_id']} '{m['catalog_title']}' (autor actual: '{m['current_author']}', sim: {m['similarity']})")

    print(f"\n2. CANCIONES NUEVAS PARA AGREGAR ({len(new_songs)}):")
    for ns in new_songs:
        print(f"  - '{ns['title']}' ({ns['lyricsLength']} chars)")

if __name__ == '__main__':
    main()
