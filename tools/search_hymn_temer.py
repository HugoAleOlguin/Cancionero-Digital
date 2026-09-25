import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
tito = [h for h in cat if 'tito' in h.get('author', '').lower() or 'abarca' in h.get('author', '').lower()]

for h in tito:
    t = h['title'].lower()
    c = h['content'].lower()
    if 'temer' in t or 'temer' in c or 'temais' in t or 'temáis' in t or 'temáis' in c:
        print(f"Hymn #{h['id']}: '{h['title']}' | Link: {repr(h.get('link'))}")
        lines = [l for l in h['content'].splitlines() if l.strip()]
        print("  " + " / ".join(lines[:2]))
