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
        return [(m[0], m[1], m[2]) for m in matches]

queries = [
    '"El Aceite de las Lámparas" "Luján"',
    '"El Siervo" "Luján de Cuyo"',
    '"Las Parábolas del Maestro" "Luján"',
    '"Los Dos Cimientos" "Luján de Cuyo"',
    '"Mirame Señor" "Luján de Cuyo"',
    '"No Se Que Harás" "Luján de Cuyo"',
    '"Yo Soy de Jesús" "Luján de Cuyo"',
    '"Conjunto de Luján de Cuyo"',
    '"Conjunto Juvenil de Luján de Cuyo"'
]

for q in queries:
    print(f"=== BUSCANDO: {q} ===")
    res = search(q)
    for vid, title, ch in res[:5]:
        print(f"  {vid} | {title} | {ch}")
