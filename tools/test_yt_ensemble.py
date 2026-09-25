import urllib.request
import urllib.parse
import json
import re

def search_youtube_verified(ensemble, title):
    query = f"{ensemble} {title}"
    url = "https://www.youtube.com/results?search_query=" + urllib.parse.quote(query)
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
            
            # YouTube renders videoRenderer objects containing title, videoId, and channelName
            # Let's extract video entries: title and videoId
            # Pattern: "videoRenderer":{"videoId":"...","title":{"runs":[{"text":"..."}]
            video_matches = re.findall(r'"videoRenderer":\{"videoId":"([a-zA-Z0-9_-]{11})".*?"title":\{"runs":\[\{"text":"([^"]+)"', html)
            
            results = []
            for vid, vtitle in video_matches[:5]:
                # Check relevance: does video title contain words from song title?
                norm_vtitle = vtitle.lower()
                norm_song = title.lower()
                # Check if ensemble or title appears
                title_words = [w for w in re.sub(r'[^a-zA-Z0-9]', ' ', norm_song).split() if len(w) > 3]
                matches_words = sum(1 for w in title_words if w in norm_vtitle)
                confidence = matches_words / len(title_words) if title_words else 0
                results.append({
                    "videoId": vid,
                    "videoTitle": vtitle,
                    "url": f"https://www.youtube.com/watch?v={vid}",
                    "confidence": confidence
                })
            return results
    except Exception as e:
        print(f"Error searching '{query}': {e}")
        return []

def main():
    test_songs = [
        ("Conjunto Villanueva", "¿Qué está pasando?"),
        ("Conjunto Villanueva", "Hasta el enebro"),
        ("Conjunto Villanueva", "Yo sé que estás muy cansado"),
        ("Conjunto Villanueva", "El Hijo se va"),
        ("Conjunto Jerusalén", "Carta a Timoteo"),
        ("Conjunto Jerusalén", "Mi vida está en Tus manos"),
        ("Conjunto Jerusalén", "Jerusalén También Llora"),
        ("Tito Abarca", "El Camino a Emaús"),
        ("Tito Abarca", "Encorvada y Prisionera")
    ]

    for ens, title in test_songs:
        print(f"\nBuscando: [{ens}] - '{title}'")
        res = search_youtube_verified(ens, title)
        for r in res[:2]:
            print(f"  -> ({r['confidence']*100:.0f}% coincidencia) '{r['videoTitle']}' | {r['url']}")

if __name__ == '__main__':
    main()
