import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
by_id = {h['id']: h for h in cat}

for hid in [355, 360, 363]:
    h = by_id[hid]
    print(f"=== #{hid} '{h['title']}' ===")
    print(h['content'])
    print()
