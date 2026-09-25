import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
tito = [h for h in cat if 'tito' in h.get('author', '').lower() or 'abarca' in h.get('author', '').lower()]

print("=== Hymn #469 Josué ===")
h469 = [h for h in tito if h['id'] == 469][0]
print(h469['title'])
print(h469['content'])

print("\n=== All pending Tito hymns titles ===")
for h in tito:
    if not h.get('link', '').strip():
        print(f"#{h['id']}: {h['title']}")
