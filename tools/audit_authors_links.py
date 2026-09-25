import json
from collections import defaultdict

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']

authors = defaultdict(lambda: {'total': 0, 'with_link': 0, 'without_link': 0, 'ids_without_link': []})

for h in cat:
    if h.get('isDeleted', False):
        continue
    auth = h.get('author', '').strip()
    if not auth:
        auth = "(Sin Autor)"
    authors[auth]['total'] += 1
    if h.get('link', '').strip():
        authors[auth]['with_link'] += 1
    else:
        authors[auth]['without_link'] += 1
        authors[auth]['ids_without_link'].append(h['id'])

sorted_authors = sorted(authors.items(), key=lambda x: -x[1]['total'])

print(f"{'AUTOR / CONJUNTO':<35} | {'TOTAL':<6} | {'CON LINK':<8} | {'FALTAN':<6}")
print("-" * 65)
for auth, stats in sorted_authors:
    if stats['total'] >= 5:
        print(f"{auth[:34]:<35} | {stats['total']:<6} | {stats['with_link']:<8} | {stats['without_link']:<6}")
