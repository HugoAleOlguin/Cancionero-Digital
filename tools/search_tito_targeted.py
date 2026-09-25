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
    try:
        with urllib.request.urlopen(req, timeout=10) as resp:
            html = resp.read().decode('utf-8', errors='ignore')
            matches = re.findall(
                r'"videoRenderer":\{"videoId":"([a-zA-Z0-9_-]{11})".*?"title":\{"runs":\[\{"text":"([^"]+)"\}.*?"ownerText":\{"runs":\[\{"text":"([^"]+)"',
                html
            )
            return [(m[0], m[1], m[2]) for m in matches]
    except Exception as e:
        return []

tito_pending = [h for h in cat if 'abarca' in h.get('author', '').lower() and not h.get('link', '').strip()]

results = {}
for h in tito_pending:
    hid = h['id']
    title = h['title']
    lines = [l.strip() for l in h['content'].splitlines() if l.strip() and not l.startswith('CORO') and not l.startswith('I') and not l.startswith('Presentación')]
    fline = lines[0] if lines else ""
    
    q1 = f'"{title}" "Tito Abarca"'
    res1 = search(q1)
    
    vids_found = []
    for vid, vtitle, ch in res1[:3]:
        vids_found.append((vid, vtitle, ch, q1))
        
    if not vids_found and fline:
        q2 = f'"{fline[:35]}" "Tito Abarca"'
        res2 = search(q2)
        for vid, vtitle, ch in res2[:2]:
            vids_found.append((vid, vtitle, ch, q2))
            
    if vids_found:
        print(f"\n==========================================")
        print(f"Hymn #{hid}: '{title}'")
        print(f"Letra: {fline[:65]}")
        for vid, vtitle, ch, q in vids_found:
            print(f"  [{vid}] {vtitle} | {ch}")
            results[hid] = (vid, vtitle, ch)

print(f"\nTotal himnos con posibles coincidencias directas: {len(results)}")
