import json

data = json.load(open('data/catalog.json', encoding='utf-8'))
hymns = data['hymns']

print(f"Total en catalogo: {len(hymns)}")
print("\nPrimeros 5 de la nueva tanda (#859 a #863):")
for h in hymns[858:863]:
    print(f"  #{h['id']} - {h['title']} ({h['author']})")

print("\nHimno #918 ('Nehemías') corregido:")
h_neh = next(h for h in hymns if h['title'] == 'Nehemías')
print(f"  #{h_neh['id']} - {h_neh['title']} ({h_neh['author']}) -> {h_neh['content'][:80].replace(chr(10), ' / ')}")

print("\nHimnos con Versión 2 de la nueva tanda:")
h_gracias = next(h for h in hymns if h['title'] == 'Quiero Darte Muchas Gracias')
print(f"  #{h_gracias['id']} - {h_gracias['title']} -> {len(h_gracias.get('extraVersions', []))} versiones extra")

h_canta = next(h for h in hymns if h['title'] == 'Canta a Cristo')
print(f"  #{h_canta['id']} - {h_canta['title']} -> {len(h_canta.get('extraVersions', []))} versiones extra")

print("\nÚltimos 5 del cancionero (#988 a #992):")
for h in hymns[-5:]:
    print(f"  #{h['id']} - {h['title']} ({h['author']})")
