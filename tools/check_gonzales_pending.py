import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
gonzales = [h for h in cat if 'gonzal' in h.get('author', '').lower() and not h.get('link', '').strip()]

print(f"Total Los Gonzales sin link: {len(gonzales)}")
for h in gonzales[:25]:
    lines = [l.strip() for l in h['content'].splitlines() if l.strip()]
    fline = lines[0] if lines else ""
    print(f"#{h['id']} '{h['title']}' | {fline[:60]}")
