import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
noa = [h for h in cat if 'noa' in h.get('author', '').lower() and not h.get('isDeleted', False)]

tracks = [
    ("A2NTkepObR4", "Casa de Dios"),
    ("r_Lf36FrXKI", "!Oh que amor tan grande¡"),
    ("wNbHFshN14c", "Todo hizo por amor"),
    ("-rVoEQr1-RY", "Todo termina"),
    ("Y7ZTWd_Ow5g", "Pedro, tira la red"),
    ("VlHAtN4XxPE", "Se Levantó"),
    ("EqGC1qiI2ws", "!Cuán amables son tus moradas¡"),
    ("aQSdQTEOiiQ", "Fuente de Vida es Cristo"),
    ("y2C7TO3w5YA", "Una iglesia comprada con sangre"),
    ("hU9bR70AJew", "Hoy canto para ti"),
    ("Qnhw8flQNf4", "La gente busca a Jesús"),
    ("V63krLIP6oY", "Diez vírgenes"),
    ("THkm0nZAAE0", "Ayúdame a ser fiel"),
    ("g2W9hfTCnVM", "Ciertamente"),
    ("C49UceO_9ME", "Por amor"),
    ("GGOHHQMN0pc", "¡Oh Cristiano Meditad!"),
]

for h in noa:
    hid = h['id']
    title = h['title']
    content_lower = h['content'].lower()
    first_lines = ' '.join(h['content'].splitlines()[:3])
    print(f"#{hid} '{title}'")
    print(f"  Letra: {first_lines[:70]}")
    # Match with tracks
    matched = []
    for vid, tname in tracks:
        # Check if title or keywords match
        t_words = [w for w in tname.lower().replace('¡','').replace('!','').split() if len(w) > 3]
        if any(w in title.lower() or w in content_lower for w in t_words):
            matched.append((vid, tname))
    for m in matched:
        print(f"    -> Posible match: {m[1]} ({m[0]})")
    print()
