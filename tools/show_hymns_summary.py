import json

data = json.load(open('data/catalog.json', encoding='utf-8'))
hymns = data['hymns']

print(f"Total en catalogo: {len(hymns)}")
print("Primeros 5 agregados (#481 a #485):")
for h in hymns[480:485]:
    print(f"  #{h['id']} - {h['title']} ({h['author']})")

print("\nAlabanza #456 restaurada:")
h456 = next(h for h in hymns if h['id'] == 456)
print(f"  #{h456['id']} - {h456['title']} ({h456['author']}) -> {len(h456['content'])} caracteres")

print("\nAlabanza con Versión 2 vinculada:")
with_v2 = [h for h in hymns if h.get('extraVersions')]
print(f"Total con extraVersions: {len(with_v2)}")
for h in with_v2[-3:]:
    print(f"  #{h['id']} - {h['title']} ({h['author']}) -> {len(h['extraVersions'])} versiones extra")

print("\nÚltimos 5 agregados (#854 a #858):")
for h in hymns[-5:]:
    print(f"  #{h['id']} - {h['title']} ({h['author']})")
