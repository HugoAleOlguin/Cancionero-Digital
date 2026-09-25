import json
import unicodedata
import re

def norm(s):
    if not s: return ""
    s = s.lower()
    s = "".join(c for c in unicodedata.normalize("NFD", s) if unicodedata.category(c) != "Mn")
    return re.sub(r"[^a-z0-9\s]", " ", s).strip()

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']

candidates = [
    ("GExeIa6SAho", "Cuando al mundo me fui", "Conjunto de Tucumán"),
    ("WS9_SFhggBg", "La oración de Jesús", "Conjunto de Tucumán"),
    ("C49UceO_9ME", "Por amor", "Conjunto del NOA"),
    ("AHiBttuwNPU", "Una estrella en el Oriente", "Conjunto de Tucumán"),
    ("DUxhd-5NULI", "Lléname", "Conjunto de Tucumán"),
    ("ql7ODggPTCE", "Salvación", "Conjunto de Tucumán"),
    ("DoCEO4GokGs", "Historia de una Niña", "Conjunto de Tucumán"),
    ("KmhzrwIk1xc", "El ensueño de la Iglesia", "Conjunto de Tucumán"),
    ("h_uH1LkvJHA", "Zaqueo", "Conjunto de Perico"),
    ("aQvDFQXdNO8", "Testimonio", "Conjunto de Tucumán"),
    ("BFHH8h5mzSc", "La nueva Canaán", "Conjunto de Tucumán"),
    ("9Eg5KAbPbf8", "El amor de Cristo", "Conjunto de Tucumán"),
    ("LXtAGu2ihqM", "Bienvenido a casa", "Conjunto de Tucumán"),
    ("gG9Z30vMdh8", "La unidad", "Conjunto de Tucumán"),
    ("cAF1lUnICtA", "Cuando vi su cruz", "Conjunto de Tucumán"),
    ("0Ssin-Zkvd0", "Profundo amor de Dios", "Conjunto de Tucumán"),
    ("-Bb5SkFnmp0", "El pastor herido", "Conjunto de Tucumán"),
    ("8bW-erH48Fg", "Santo es el Señor", "Conjunto de Tucumán"),
    ("OXbDat9yYgY", "Pedro", "Conjunto de Tucumán"),
    ("Lf7w3-Reo5c", "Jesús Nazareno", "Conjunto de San Juan"),
    ("IWrUsyrZCes", "Salmo 150", "Conjunto de Tucumán"),
    ("9Y5mExikVsw", "Mi Rey Jesús", "Conjunto de Tucumán"),
    ("CKIUBq_weMY", "Alabad a Jehová", "Conjunto de Tucumán"),
    ("lYHmYVc6i2g", "La Samaritana", "Conjunto de Tucumán"),
    ("cvi_6Hh4hfE", "Dios es con nosotros", "Conjunto de Tucumán"),
    ("aWruaLnq52Q", "El Jornalero", "Conjunto de Tucumán"),
    ("Q3RVXycu6aI", "Camino del Mar", "Conjunto de Tucumán"),
    ("3kPj9z6rc3Y", "Veremos al Rey", "Conjunto de Tucumán"),
    ("U2qiOifs67U", "El Leproso", "Conjunto de Tucumán"),
    ("eUMBeI0x620", "Señor tu padeciste por mí", "Conjunto de Perico"),
    ("d31QsmJ3PqE", "No tengas temor", "Conjunto Unido de Tucumán"),
    ("hk6ZeERViAI", "Dios de amor", "Conjunto Iglesia central Tucumán"),
    ("y2C7TO3w5YA", "Una iglesia comprada con sangre", "Cantores Unidos Del Noa"),
    ("VlHAtN4XxPE", "Se Levantó", "Cantores Unidos del NOA"),
    ("Qnhw8flQNf4", "La gente busca a Jesús", "Conjunto del NOA"),
    ("hU9bR70AJew", "Hoy canto para ti", "Conjunto del NOA"),
    ("wNbHFshN14c", "Todo hizo por amor", "Conjunto del NOA"),
]

print("=== RESULTADOS DEL COTEJO CONTRA CATALOGO ===")
matches_to_apply = []
for vid, title, author in candidates:
    tn = norm(title)
    found = []
    for h in cat:
        htn = norm(h['title'])
        if tn == htn or (len(tn) > 5 and (tn in htn or htn in tn)):
            found.append(h)
    
    if found:
        print(f"\n>> Video: '{title}' [{author}] (vid: {vid})")
        for h in found:
            curr_link = h.get('link', '').strip()
            print(f"   Catálogo: #{h['id']} '{h['title']}' [{h.get('author')}] | Link actual: {repr(curr_link)}")
            print(f"             Letra: {' '.join(h['content'].splitlines()[:2])[:60]}")
            if not curr_link:
                matches_to_apply.append((h['id'], h['title'], vid, f"{title} ({author})"))
    else:
        print(f"\n-- No match: '{title}' [{author}]")

print(f"\n======================================")
print(f"Total coincidencias con link VACÍO listas para aplicar: {len(matches_to_apply)}")
for hid, htitle, vid, desc in matches_to_apply:
    print(f"  #{hid} '{htitle}' -> https://www.youtube.com/watch?v={vid} ({desc})")
