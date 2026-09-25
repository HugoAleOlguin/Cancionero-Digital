import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
redencion = [h for h in cat if 'redenci' in h.get('author', '').lower() and not h.get('isDeleted', False)]

print(f"Total Alabanzas Trío Redención: {len(redencion)}")
for idx, h in enumerate(redencion, 1):
    first_line = h['content'].splitlines()[0] if h['content'] else ''
    print(f"[{idx:2d}] #{h['id']:3d}: '{h['title']}' | Inicio: {first_line[:50]}")
