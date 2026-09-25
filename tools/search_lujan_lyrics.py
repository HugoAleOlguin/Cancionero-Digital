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

lujan_ids = [928, 929, 930, 931, 932, 933, 934]

for hid in lujan_ids:
    h = by_id[hid]
    lines = [l.strip() for l in h['content'].splitlines() if l.strip() and not l.startswith('CORO') and not l.startswith('I')]
    phrase = lines[0] if lines else h['title']
    print(f"\n======================================")
    print(f"Hymn #{hid}: {h['title']}")
    print(f"Lírica inicio: {phrase}")
    
    # query 1: title + Lujan
    q1 = f'"{h["title"]}" "Luján"'
    # query 2: phrase
    q2 = f'"{phrase[:40]}"'
    # query 3: title + Luján de Cuyo
    q3 = f'"{h["title"]}" "Luján de Cuyo"'

    for q in [q1, q3, q2]:
        res = search(q)
        for vid, title, ch in res[:3]:
            print(f"  [{q[:30]}] -> {vid} | {title} | {ch}")
