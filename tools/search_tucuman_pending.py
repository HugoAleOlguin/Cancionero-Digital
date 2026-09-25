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

pending_ids = [380, 381, 382, 383, 384, 386, 387, 390, 392, 393, 394, 398, 400, 402, 403, 404, 408, 411, 412, 415, 925, 926]

for hid in pending_ids:
    h = by_id[hid]
    title = h['title']
    print(f"\n======================================")
    print(f"Hymn #{hid}: {title}")
    
    q1 = f'"{title}" "Tucumán"'
    q2 = f'"{title}" "Conjunto de Tucumán"'
    q3 = f'"{title}" "Asamblea Cristiana"'

    found = False
    for q in [q1, q2]:
        res = search(q)
        for vid, vtitle, ch in res[:3]:
            if "tucum" in vtitle.lower() or "tucum" in ch.lower() or "asamblea" in ch.lower() or "cristiana" in ch.lower():
                print(f"  MATCH? [{q}] -> {vid} | {vtitle} | {ch}")
                found = True
            else:
                print(f"  (other) {vid} | {vtitle} | {ch}")
