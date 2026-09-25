import json
import urllib.request
import re

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
by_id = {h['id']: h for h in cat}

checks = [
    (438, "_mQKytCyTAY", "Sopla"),
    (439, "KyNum9IuS1w", "Yo Conozco"),
    (449, "Y-1DZqpTboA", "El Jordan"),
    (478, "VnCUWMiUBJo", "Bendice Alma mía a Jehová"),
    (572, "vX8zIqeJq0k", "Tu corazón en Horeb"),
    (581, "fwyQG7gQyMo", "Despierta mi Primer Amor"),
]

for hid, vid, label in checks:
    h = by_id[hid]
    url = f"https://www.youtube.com/watch?v={vid}"
    req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
    try:
        html = urllib.request.urlopen(req, timeout=10).read().decode('utf-8', errors='ignore')
        title_m = re.search(r'<title>(.*?)</title>', html)
        title = title_m.group(1) if title_m else ""
        desc_m = re.search(r'"shortDescription":"(.*?)"', html)
        desc = desc_m.group(1).replace(r'\n', ' ') if desc_m else ""
    except Exception as e:
        title = str(e)
        desc = ""
    
    print(f"\n==========================================")
    print(f"Hymn #{hid}: '{h['title']}' [{h.get('author')}] | Link actual: {repr(h.get('link'))}")
    print(f"Letra Catálogo: {' '.join(h['content'].splitlines()[:3])[:100]}")
    print(f"YouTube Video : {title} ({vid})")
    print(f"YouTube Desc  : {desc[:150]}")
