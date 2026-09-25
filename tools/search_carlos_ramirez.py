import urllib.request
import urllib.parse
import re
import sys
import json

sys.stdout.reconfigure(encoding='utf-8')

# Extraer todos los videos del canal de Carlos Ramírez si es posible buscando sus subidas
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
    '"Carlos Ramírez" "conjunto"',
    '"Carlos Ramírez" "alabanza"',
    '"Carlos Ramírez" "Luján"',
    '"Carlos Ramírez" "Tucumán"',
    '"Carlos Ramírez" "Villanueva"',
    '"Carlos Ramírez" "San Juan"',
    '"Carlos Ramírez" "Dorrego"',
    '"Carlos Ramírez" "Abarca"',
]

vids = {}
for q in queries:
    for vid, title, ch in search(q):
        if "Carlos Ramírez" in ch or "Ramírez" in ch:
            vids[vid] = title

print(f"Total videos de Carlos Ramírez: {len(vids)}")
for vid, title in sorted(vids.items(), key=lambda x: x[1]):
    print(f"{vid} | {title}")
