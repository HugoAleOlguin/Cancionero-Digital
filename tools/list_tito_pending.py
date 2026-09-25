import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
tito_pending = [h for h in cat if 'abarca' in h.get('author', '').lower() and not h.get('link', '').strip()]

print(f"Total pendientes de Tito Abarca: {len(tito_pending)}")
for h in tito_pending:
    lines = [l.strip() for l in h['content'].splitlines() if l.strip() and not l.startswith('CORO')]
    fline = lines[0] if lines else ""
    sline = lines[1] if len(lines) > 1 else ""
    print(f"#{h['id']:3d} | '{h['title']}'")
    print(f"       Lírica: {fline[:60]} // {sline[:50]}")
