import json

catalog = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
no_author = [h for h in catalog if not h.get('author', '').strip() and not h.get('isDeleted', False)]
print(f"Total canciones sin autor en catálogo: {len(no_author)}")
for h in no_author[:25]:
    print(f"  #{h['id']:3d} - {h['title']}")
