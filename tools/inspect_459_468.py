import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
by_id = {h['id']: h for h in cat}

check_ids = [459, 461, 463, 464, 465, 468]
for hid in check_ids:
    h = by_id[hid]
    print(f"\n=== #{hid}: {h['title']} ===")
    lines = [l.strip() for l in h['content'].splitlines() if l.strip()]
    for l in lines[:6]:
        print("  ", l)
