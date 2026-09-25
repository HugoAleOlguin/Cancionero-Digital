import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
total = len(cat)
with_link = [h for h in cat if h.get('link', '').strip()]

print(f"Total himnos: {total}")
print(f"Con enlace oficial de YouTube: {len(with_link)} ({len(with_link)/total*100:.1f}%)")
print(f"Pendientes: {total - len(with_link)}")

# Desglose por autor principal
by_author = {}
for h in cat:
    auth = h.get('author', 'Sin Autor').strip() or 'Sin Autor'
    if auth not in by_author:
        by_author[auth] = {'total': 0, 'with_link': 0}
    by_author[auth]['total'] += 1
    if h.get('link', '').strip():
        by_author[auth]['with_link'] += 1

print("\n=== COBERTURA POR AUTOR / CONJUNTO ===")
for auth, stats in sorted(by_author.items(), key=lambda x: -x[1]['with_link']):
    pct = stats['with_link'] / stats['total'] * 100
    print(f"  {auth:32} : {stats['with_link']:3d} / {stats['total']:3d} ({pct:5.1f}%)")
