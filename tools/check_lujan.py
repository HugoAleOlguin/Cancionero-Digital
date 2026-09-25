import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
lujan = [h for h in cat if 'luj' in h.get('author', '').lower() or (925 <= h['id'] <= 940)]
for h in lujan:
    print(f"#{h['id']} [{h.get('author')}] {h['title']} | Link: {repr(h.get('link'))}")
    first_lines = ' '.join(h['content'].splitlines()[:2])
    print(f"   Letra: {first_lines[:75]}")
