import urllib.request
import urllib.parse
import re
import json
import time

def find_youtube_link(query):
    url = 'https://www.youtube.com/results?search_query=' + urllib.parse.quote(query)
    req = urllib.request.Request(
        url,
        headers={
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36',
            'Accept-Language': 'es-ES,es;q=0.9'
        }
    )
    try:
        with urllib.request.urlopen(req, timeout=10) as resp:
            html = resp.read().decode('utf-8', errors='ignore')
            vids = re.findall(r'"videoId":"([a-zA-Z0-9_-]{11})"', html)
            seen = set()
            uniq = [v for v in vids if not (v in seen or seen.add(v))]
            if uniq:
                return f"https://www.youtube.com/watch?v={uniq[0]}"
    except Exception as e:
        print(f"Error fetching YouTube for '{query}': {e}")
    return ""

def main():
    songs = [
        "Mi Dios Es Real",
        "Hijo Yo No Te Olvide",
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
        "Espiritu Santo Renovador",
        "Te Amaré"
    ]

    results = {}
    print(f"Buscando links de YouTube para las {len(songs)} canciones de Polo Negrete...")
    for s in songs:
        query = f"Polo Negrete {s}"
        link = find_youtube_link(query)
        results[s] = link
        print(f"  '{s}' -> {link}")
        time.sleep(0.5)

    with open("data/polo_negrete_youtube.json", "w", encoding="utf-8") as f:
        json.dump(results, f, indent=2, ensure_ascii=False)
    print("\nGuardado en data/polo_negrete_youtube.json")

if __name__ == '__main__':
    main()
