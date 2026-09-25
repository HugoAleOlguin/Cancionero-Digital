import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
tito = [h for h in cat if 'tito' in h.get('author', '').lower() or 'abarca' in h.get('author', '').lower()]
nl = [h for h in tito if not h.get('link', '').strip()]

print(f"Total sin link: {len(nl)}")
for h in nl:
    lines = [l.strip() for l in h['content'].splitlines() if l.strip() and not l.startswith('I') and not l.startswith('Coro')]
    fl = lines[0] if lines else ''
    print(f"#{h['id']}: '{h['title']}' -> '{fl[:60]}'")
