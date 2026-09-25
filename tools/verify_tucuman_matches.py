import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
by_id = {h['id']: h for h in cat}

tucuman_matches = [
    (379, "Lléname Señor", "DUxhd-5NULI", "LLÉNAME (Conjunto de Tucumán)"),
    (385, "Camino del Mar", "Q3RVXycu6aI", "Camino del Mar (Conjunto de Tucumán)"),
    (391, "Mi Testimonio", "aQvDFQXdNO8", "TESTIMONIO (Conjunto de Tucumán)"),
    (395, "Vi la Salvación", "ql7ODggPTCE", "SALVACIÓN (Conjunto de Tucumán)"),
    (396, "La Oración de Jesús", "WS9_SFhggBg", "LA ORACIÓN DE JESÚS (Conjunto de Tucumán)"),
    (399, "Bautízame Juan", "jcCvDhiXleE", "bautizame juan, clamaba el señor - Conjunto Tucuman"),
    (401, "Vuelve Señor", "6SFzTH1zQy4", "Vuelve Señor - Conjunto Iglesia Central de Tucumán"),
    (405, "Bienvenido a Casa", "LXtAGu2ihqM", "BIENVENIDO A CASA (Conjunto de Tucumán)"),
    (407, "La Unidad", "xw6gLO1Ge6w", "La Unidad- Conjunto Unido de tucuman"),
    (410, "El Dulce Cantor", "BMLpq7UF6_0", "EL DULCE CANTOR (Conjunto de Tucumán)"),
    (414, "A la casa de Tres Amigos", "YMCJpMRB1B4", "TRES AMIGOS (Conjunto de Tucumán)"),
]

for hid, title, vid, yt_title in tucuman_matches:
    h = by_id[hid]
    curr_link = h.get('link', '').strip()
    first_lines = ' '.join(h['content'].splitlines()[:3])
    print(f"#{hid} '{h['title']}' [CurrLink: {bool(curr_link)}]")
    print(f"  Letra: {first_lines[:70]}")
    print(f"  Video: https://www.youtube.com/watch?v={vid} -> {yt_title}")
    print()
