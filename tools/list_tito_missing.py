import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
tito_missing = [h for h in cat if 'abarca' in h.get('author', '').lower() and not h.get('isDeleted', False) and not h.get('link', '').strip()]

print(f"Total Tito Abarca sin enlace: {len(tito_missing)}")
for idx, h in enumerate(tito_missing[:30], 1):
    first_line = h['content'].splitlines()[0] if h['content'] else ''
    print(f"[{idx:2d}] #{h['id']:3d}: '{h['title']}' | {first_line[:40]}")
