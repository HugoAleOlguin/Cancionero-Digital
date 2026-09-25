import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
tucuman_hymns = [h for h in cat if 'tucum' in h.get('author', '').lower()]

print(f"Total alabanzas de Tucumán: {len(tucuman_hymns)}")
sin_link = [h for h in tucuman_hymns if not h.get('link', '').strip()]
print(f"Sin link: {len(sin_link)}")
for h in sin_link:
    print(f"#{h['id']} '{h['title']}' | {h['content'].splitlines()[0] if h['content'] else ''}")
