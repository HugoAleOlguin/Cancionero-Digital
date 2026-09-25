import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
acuna = [h for h in cat if 'acuña' in h.get('author', '').lower() or 'acuna' in h.get('author', '').lower()]

print(f"Total Alabanzas Trío Acuña: {len(acuna)}")
for idx, h in enumerate(acuna, 1):
    first_line = h['content'].splitlines()[0] if h['content'] else ''
    print(f"[{idx:2d}] #{h['id']:3d}: '{h['title']}' | Inicio: {first_line[:50]}")
