import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']

print("=== CONJUNTO DE DORREGO FALTANTES ===")
for h in cat:
    if 'dorrego' in h.get('author', '').lower() and not h.get('isDeleted', False):
        if not h.get('link', '').strip():
            print(f"#{h['id']} '{h['title']}' -> {h['content'][:50]}")

print("\n=== CONJUNTO AMIGO FIEL FALTANTES ===")
for h in cat:
    if 'amigo fiel' in h.get('author', '').lower() and not h.get('isDeleted', False):
        if not h.get('link', '').strip():
            print(f"#{h['id']} '{h['title']}' -> {h['content'][:50]}")
