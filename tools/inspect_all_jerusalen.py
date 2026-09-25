import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
jerusalen = [h for h in cat if 'jerusal' in h.get('author', '').lower() and not h.get('isDeleted', False)]

print(f"Total canciones Conjunto Jerusalén: {len(jerusalen)}")
for idx, h in enumerate(jerusalen, 1):
    link = h.get('link', '').strip()
    status = f"TIENE LINK ({link[-11:]})" if link else "[SIN LINK]"
    first_line = h['content'].splitlines()[0] if h['content'] else ''
    print(f"[{idx:2d}] #{h['id']:3d}: '{h['title']}' | {status} | {first_line[:40]}")
