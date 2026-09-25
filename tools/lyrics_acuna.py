import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
acuna = [h for h in cat if ('acuña' in h.get('author', '').lower() or 'acuna' in h.get('author', '').lower()) and not h.get('isDeleted', False)]

for h in acuna:
    lines = [line.strip() for line in h['content'].splitlines() if line.strip() and not line.strip().startswith(('I', 'II', 'III', 'IV', 'Coro', 'Presentación'))]
    snippet1 = lines[0] if len(lines) > 0 else ''
    snippet2 = lines[1] if len(lines) > 1 else ''
    print(f"#{h['id']} '{h['title']}'")
    print(f"   L1: {snippet1}")
    print(f"   L2: {snippet2}")
