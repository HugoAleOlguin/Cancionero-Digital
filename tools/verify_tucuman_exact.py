import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
by_id = {h['id']: h for h in cat}

check_list = [
    (366, "WS9_SFhggBg", "LA ORACIÓN DE JESÚS (Conjunto de Tucumán)"),
    (388, "3kPj9z6rc3Y", "VEREMOS AL REY (Conjunto de Tucumán)"),
    (389, "KmhzrwIk1xc", "El ensueño de la Iglesia (Conjunto de Tucumán)"),
    (409, "OXbDat9yYgY", "PEDRO (Conjunto de Tucumán)"),
    (413, "U2qiOifs67U", "EL LEPROSO (Conjunto de Tucumán)"),
    (932, "2dGr2y8oEmU", "Mírame señor - conjunto Luján de Cuyo"),
    (934, "f6NTrz1p_9o", "Yo soy de Jesús - MCP Villa Mitre / Conjunto Luján"),
]

for hid, vid, desc in check_list:
    h = by_id[hid]
    print(f"#{hid} '{h['title']}' [{h.get('author')}] | Link actual: {repr(h.get('link'))}")
    print(f"   Letra: {h['content'][:140]!r}")
    print(f"   Video: https://www.youtube.com/watch?v={vid} -> {desc}")
    print()
