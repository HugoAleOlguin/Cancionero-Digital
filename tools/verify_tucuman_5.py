import json
from inspect_candidates import get_video_info

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
by_id = {h['id']: h for h in cat}

new_tucuman_list = [
    (384, "wTxqLc_zFSs", "LA VISIÓN DE DANIEL (Asamblea Cristiana)"),
    (392, "KnsIyWVM37I", "El Sembrador - Conjunto Central Tucumán Vol. 2"),
    (403, "z0NgG0bfqyE", "Iglesia Central de Tucumán - Carta al Hijo Pródigo"),
    (408, "3lp4rdGYqm8", "Más que Vencedores - Conjunto Iglesia Central de Tucumán"),
    (411, "jBDFWjkK-iY", "Zorobabel (Hageo 1) - Conjunto Iglesia Central de Tucumán"),
]

for hid, vid, desc in new_tucuman_list:
    h = by_id[hid]
    t, d = get_video_info(vid)
    print(f"#{hid} '{h['title']}' [{h.get('author')}]")
    print(f"   Catálogo: {h['content'][:120]!r}")
    print(f"   Video: {t}")
    print(f"   Desc: {d[:120]}")
    print()
