import urllib.request
import urllib.parse
import re
import json
import time
import unicodedata
import sys

sys.stdout.reconfigure(encoding='utf-8')

def norm(text):
    text = unicodedata.normalize('NFD', text)
    text = re.sub(r'[\u0300-\u036f]', '', text)
    text = re.sub(r'[^a-zA-Z0-9\s]', ' ', text)
    return re.sub(r'\s+', ' ', text).strip().lower()

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
                    res.append((m[0], m[1], m[2]))
                if len(res) >= 5:
                    break
            return res
    except Exception as e:
        print(f"Error {e}")
        return []

# Load Acuña hymns
cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
acuna = [h for h in cat if ('acuña' in h.get('author', '').lower() or 'acuna' in h.get('author', '').lower()) and not h.get('isDeleted', False)]

results = {}

for h in acuna:
    hid = h['id']
    title = h['title']
    lines = [line.strip() for line in h['content'].splitlines() if line.strip() and not line.strip().startswith(('I', 'II', 'III', 'IV', 'Coro', 'Presentación'))]
    l1 = lines[0] if lines else ''
    
    # Custom queries per song
    queries = [
        f"Trio Acuña {title}",
        f"Hnos Acuña {title}",
        f"Trio Acuña {l1[:35]}",
        f"Hermanos Acuña {l1[:35]}"
    ]
    
    print(f"=== Buscando #{hid} '{title}' ===")
    found_candidates = []
    seen_vids = set()
    for q in queries:
        vids = search(q)
        for vid, vtitle, vch in vids:
            if vid not in seen_vids:
                seen_vids.add(vid)
                found_candidates.append({'videoId': vid, 'videoTitle': vtitle, 'channel': vch, 'url': f'https://www.youtube.com/watch?v={vid}'})
        time.sleep(0.3)
        if len(found_candidates) >= 5:
            break
            
    results[hid] = {
        'id': hid,
        'title': title,
        'l1': l1,
        'candidates': found_candidates
    }
    for c in found_candidates[:3]:
        print(f"   [{c['videoId']}] {c['videoTitle']} | {c['channel']}")
    print()

with open('data/youtube_acuna_detailed.json', 'w', encoding='utf-8') as f:
    json.dump(results, f, indent=2, ensure_ascii=False)

print("Detalle guardado en data/youtube_acuna_detailed.json")
