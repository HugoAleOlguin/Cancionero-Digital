import urllib.request
import urllib.parse
import re
import sys

sys.stdout.reconfigure(encoding='utf-8')

def search(q):
    url = "https://www.youtube.com/results?search_query=" + urllib.parse.quote(q)
    req = urllib.request.Request(
        url,
        headers={
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36',
            'Accept-Language': 'es-ES,es;q=0.9'
        }
    )
    with urllib.request.urlopen(req, timeout=10) as resp:
        html = resp.read().decode('utf-8', errors='ignore')
        matches = re.findall(
            r'"videoRenderer":\{"videoId":"([a-zA-Z0-9_-]{11})".*?"title":\{"runs":\[\{"text":"([^"]+)"\}.*?"ownerText":\{"runs":\[\{"text":"([^"]+)"',
            html
        )
        return [(m[0], m[1], m[2]) for m in matches[:5]]

queries = [
    ("062 Pablo", "062 El Apóstol Pablo"),
    ("066 El vive", "066 Él vive"),
    ("070 Enemigo", "070 Cuál es tu enemigo"),
    ("070 Enemigo 2", "070 Cual es tu enemigo"),
    ("326 Job", "Conjunto Amigo Fiel Job"),
    ("327 Nain", "Conjunto Amigo Fiel Nain"),
    ("327 Nain 2", "Una viuda de Naín Amigo Fiel"),
]

for label, q in queries:
    print(f"=== {label} -> {q} ===")
    res = search(q)
    for vid, title, ch in res:
        print(f"  {vid} | {title} | {ch}")
    print()
