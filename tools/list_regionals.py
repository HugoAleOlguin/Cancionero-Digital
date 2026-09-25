import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']

regional_authors = ["bariloche", "salta", "luján", "lujan", "mansilla"]

for auth_kw in regional_authors:
    hymns = [h for h in cat if auth_kw in h.get('author', '').lower() and not h.get('isDeleted', False)]
    if not hymns:
        continue
    print(f"=== {hymns[0].get('author')} ({len(hymns)} cantos) ===")
    for h in hymns:
        link = h.get('link', '').strip()
        first_line = h['content'].splitlines()[0] if h['content'] else ''
        status = f"TIENE LINK ({link[-11:]})" if link else "[SIN LINK]"
        print(f"  #{h['id']:3d}: '{h['title']}' | {status} | Inicio: {first_line[:40]}")
    print()
