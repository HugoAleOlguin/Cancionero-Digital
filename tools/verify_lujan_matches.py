import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
by_id = {h['id']: h for h in cat}

lujan_matches = [
    (448, "La Oración de la Iglesia", "NhCgt5ZAslo", "La Oración de la Iglesia"),
    (553, "Qué Gran Amor", "U7IoQc9_rig", "Qué Gran Amor"),
    (560, "Gracias te doy Señor", "tHg9S6RcG0U", "Gracias Te Doy Señor"),
    (561, "Tinieblas Tenebrosas", "ylN4cIuykhg", "Tinieblas Tenebrosas"),
    (562, "Camino Voy", "Z8Zpf78rh_k", "Camino Voy"),
    (563, "Maravilloso Será", "-Xf6gyby63g", "Maravilloso Será"),
    (564, "Hoy tengo Paz", "WTRMqnUvUAk", "Hoy Tengo Paz"),
    (565, "Un Encuentro con Jesús", "KJfy6BIcOT0", "Un Encuentro Con Jesús"),
    (566, "Joven Rico", "4ScEpzfSZnc", "Joven Rico"),
    (567, "Muy Pronto", "N7zh3L2fQu4", "Muy Pronto"),
    (568, "La Gloria Venidera", "AGW79G8uPcQ", "La Gloria Venidera"),
    (571, "Jesús y el Leproso", "TiCzJ5x8pZ4", "Jesús y el Leproso"),
    (574, "Mírame Señor", "oUmFUTxnzZg", "Quiero Que Me Mires Hoy"),
    (577, "El Propósito", "2JiQNk9ywZQ", "El Propósito"),
    (578, "Quiero ir en Pos de Ti", "EmvY4r-V6ZE", "En Pos de Ti"),
    (580, "El Siervo de Dios", "y71zNuhMqz8", "Siervo de Dios"),
]

for hid, title, vid, yt_title in lujan_matches:
    h = by_id[hid]
    curr_link = h.get('link', '').strip()
    first_lines = ' '.join(h['content'].splitlines()[:3])
    print(f"#{hid} '{h['title']}' [CurrLink: {bool(curr_link)}]")
    print(f"  Letra: {first_lines[:70]}")
    print(f"  Pista: https://www.youtube.com/watch?v={vid} -> {yt_title}")
    print()
