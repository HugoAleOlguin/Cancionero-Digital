import urllib.request
import urllib.parse
import re
import json
import time
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
    try:
        with urllib.request.urlopen(req, timeout=10) as resp:
            html = resp.read().decode('utf-8', errors='ignore')
            matches = re.findall(
                r'"videoRenderer":\{"videoId":"([a-zA-Z0-9_-]{11})".*?"title":\{"runs":\[\{"text":"([^"]+)"\}.*?"ownerText":\{"runs":\[\{"text":"([^"]+)"',
                html
            )
            res = []
            seen = set()
            for m in matches:
                if m[0] not in seen:
                    seen.add(m[0])
                    res.append({'videoId': m[0], 'videoTitle': m[1], 'channel': m[2], 'url': f'https://www.youtube.com/watch?v={m[0]}'})
                if len(res) >= 4:
                    break
            return res
    except Exception as e:
        print(f"Error {e}")
        return []

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
trigales_missing = [h for h in cat if 'trigales' in h.get('author', '').lower() and not h.get('isDeleted', False) and not h.get('link', '')]

results = {}
for h in trigales_missing:
    hid = h['id']
    title = h['title']
    lines = [line.strip() for line in h['content'].splitlines() if line.strip() and not line.strip().startswith(('I', 'II', 'III', 'IV', 'Coro', 'Final', 'Recitado'))]
    l1 = lines[0] if lines else ''
    
    queries = [
        f"Conjunto Trigales {title}",
        f"Los Trigales {title}",
        f"Los Trigales {l1[:35]}",
    ]
    
    print(f"Buscando #{hid} '{title}'...")
    found = []
    seen_vids = set()
    for q in queries:
        vids = search(q)
        for v in vids:
            if v['videoId'] not in seen_vids:
                seen_vids.add(v['videoId'])
                found.append(v)
        time.sleep(0.3)
        if len(found) >= 4:
            break
            
    results[hid] = {'id': hid, 'title': title, 'l1': l1, 'candidates': found}
    for c in found[:2]:
        print(f"   [{c['videoId']}] {c['videoTitle']} | {c['channel']}")
    print()

with open('data/youtube_trigales_detailed.json', 'w', encoding='utf-8') as f:
    json.dump(results, f, indent=2, ensure_ascii=False)
print("Resultados guardados en data/youtube_trigales_detailed.json")
