import urllib.request
import urllib.parse
import re
import sys
import json

sys.stdout.reconfigure(encoding='utf-8')

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
by_id = {h['id']: h for h in cat}

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

gonzalez_queries = [
    '"Hermanos González" "Dios es Amor"',
    '"Hnos González" "Dios es Amor"',
    '"Los Gonzales" alabanzas',
    '"Hnos Gonzalez" "Alabanzas"',
    '"Hermanos Gonzalez" "Vol"',
    '"Jacobo Gonzalez" alabanzas',
    '"Jacobo González" himnos'
]

vids = {}
for q in gonzalez_queries:
    for vid, title, ch in search(q):
        if vid not in vids:
            vids[vid] = (title, ch)
            print(f"{vid} | {title} | {ch}")

print(f"\nTotal: {len(vids)}")
