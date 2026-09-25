import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
lazarte = [h for h in cat if 'lazarte' in h.get('author', '').lower() and not h.get('isDeleted', False)]

for h in lazarte:
    hid = h['id']
    title = h['title']
    content = h['content']
    print(f"=== #{hid} '{title}' ===")
    for line in content.splitlines()[:5]:
        if line.strip():
            print("  ", line.strip())
    print()
