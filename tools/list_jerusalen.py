import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
jerusalen = [h for h in cat if 'jerusal' in h.get('author', '').lower() and not h.get('isDeleted', False)]

print(f"Total Alabanzas Conjunto Jerusalén: {len(jerusalen)}")
con_link = sum(1 for h in jerusalen if h.get('link', '').strip())
print(f"Con enlace: {con_link}, Sin enlace: {len(jerusalen) - con_link}\n")

for idx, h in enumerate(jerusalen[:20], 1):
    link = h.get('link', '')
    status = f"TIENE LINK ({link[-11:]})" if link else "[SIN LINK]"
    print(f"[{idx:2d}] #{h['id']:3d}: '{h['title']}' | {status}")
