import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
by_id = {h['id']: h for h in cat}

candidates = [
    (397, "¿Dónde está el Niño?", "mBfgA6pBAcs"),
    (579, "Ayer y Hoy", "RIzjb1-xHqc"),
    (647, "Adorarte por la eternidad", "SizC1tr0Meo"),
    (686, "Creo en Ti", "4ADl8OwXAPg"),
    (730, "Cuan Gloriosa será la Mañana", "8u0uOGSKZ08"),
    (826, "Hay una Senda", "EWlgf2KUNM4"),
    (873, "Estoy Luchando", "ZNlKLR5hE3U"),
]

for hid, title, vid in candidates:
    h = by_id[hid]
    print(f"=== #{hid} '{title}' ({h.get('author')}) ===")
    print(h['content'][:150])
    print()
