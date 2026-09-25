import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
tucuman = [h for h in cat if 'tucum' in h.get('author', '').lower() and not h.get('isDeleted', False)]

print(f"Total Alabanzas Conjunto Central de Tucumán: {len(tucuman)}")
for idx, h in enumerate(tucuman, 1):
    link = h.get('link', '')
    first_line = h['content'].splitlines()[0] if h['content'] else ''
    status = f"TIENE LINK ({link[-11:]})" if link else "[SIN LINK]"
    print(f"[{idx:2d}] #{h['id']:3d}: '{h['title']}' | {status} | Inicio: {first_line[:40]}")
