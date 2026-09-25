import json
import unicodedata
import re

def norm(s):
    if not s: return ""
    s = s.lower()
    s = "".join(c for c in unicodedata.normalize("NFD", s) if unicodedata.category(c) != "Mn")
    return re.sub(r"[^a-z0-9\s]", " ", s).strip()

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
by_id = {h['id']: h for h in cat}

gonzalez_vids = [
    ("u6QAnmxZ4ZY", "Te Alabo Padre Dios", "Te Alabo Padre Dios"),
    ("eZq2X6Zl3Gg", "Cantar para Dios", "Cantar para Dios"),
    ("2Hc83LQ8xIw", "Abraham", "Abraham"),
    ("NcfwdMwYrQA", "Hogar Incomparable", "Hogar Incomparable"),
    ("1_D_si2hRSs", "Mi Buen Jesús", "Mi Buen Jesús"),
    ("x1vy7xXVkbQ", "Lamentaciones", "Lamentaciones"),
    ("YnT77niPt6w", "Agobiado", "Agobiado"),
    ("Ung1OVm9804", "Cantad algunos Cánticos de Sion (Salmos 137)", "Junto a los Ríos de Babilonia"),
    ("AW_GLyD7U3c", "Estando Jesús en Betania", "Estando Jesús en Betania"),
    ("4dlW7wpb3L0", "Vanidad", "Vanidad"),
    ("MeYd15IYY8A", "Si Hablamos de Amigo", "Si hablamos de Amigos"),
    ("FNU4NixnhQs", "Culpable", "Culpable"),
    ("mQMpryvIzQU", "El Mendigo", "El Mendigo"),
    ("4TojjVKeWM4", "Quieres Ir", "Quieres Ir"),
    ("QKQkbGU8ZmI", "Mi Gran Maestro", "Mi Gran Maestro"),
    ("uvqfjpMTYec", "Dios Ama al Dador Alegre", "Dios Ama al Dador Alegre"),
    ("cmAXv-wI7OM", "Es lo único que Tengo", "Es lo Único que Tengo"),
    ("PirJ409QOlE", "Desde ese Día", "Desde ese Día"),
    ("VgtcSpWbweE", "Y si Todos seguimos la Unidad", "Y si Todos seguimos la Unidad"),
    ("JMbReQMdHkY", "Hoy Debemos", "Hoy Debemos"),
    ("_i5UFgnb4IY", "Amigo Reconoce", "Amigo Reconoce"),
    ("_VRGEMxn2yQ", "La Playa de Los Cielos", "La Playa de los Cielos"),
    ("CX57iyuJOKI", "Envíame a mí", "Envíame a Mí"),
    ("rr7bqV6YkCM", "Quiero Volver", "Quiero Volver"),
    ("GjG4Tj5SEQ4", "La Flor Temprana", "La Flor Temprana"),
    ("WcE85SpEQLg", "Al Pie de la Cruz", "Al Pie de la Cruz"),
    ("IMuZUzKVLYs", "No Tengo Nada que Esconder", "No tengo nada que Esconder"),
    ("T2mbgP5hoOQ", "Paz Gloriosa", "Paz Gloriosa"),
    ("yFYM07KEChI", "El Mismo de Ayer y Hoy", "El mismo de ayer y hoy"),
    ("wG03JlSpymM", "Si Recibes a Cristo en tu Corazón", "Si recibes a Cristo en tu corazón"),
]

print("=== COTEJO AUTOMÁTICO DE LOS GONZALES ===")
verified_gonzales = []
for vid, yt_title, hint in gonzalez_vids:
    hint_norm = norm(hint)
    matches = []
    for h in cat:
        if 'gonzal' not in h.get('author', '').lower():
            continue
        tn = norm(h['title'])
        if hint_norm == tn or (len(hint_norm) > 4 and (hint_norm in tn or tn in hint_norm)):
            matches.append(h)
    
    if matches:
        for m in matches:
            curr_link = m.get('link', '').strip()
            print(f"OK Match: #{m['id']} '{m['title']}' | Link actual: {repr(curr_link)}")
            print(f"   Letra: {' '.join(m['content'].splitlines()[:2])[:70]}")
            print(f"   Video: https://www.youtube.com/watch?v={vid} -> {yt_title}")
            if not curr_link:
                verified_gonzales.append((m['id'], f"https://www.youtube.com/watch?v={vid}", m['title'], yt_title))
    else:
        print(f"NO MATCH: {hint} ({yt_title})")

print(f"\nTotal verificados sin link para Los Gonzales: {len(verified_gonzales)}")
