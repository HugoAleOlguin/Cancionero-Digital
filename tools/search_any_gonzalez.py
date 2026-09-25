import json
import unicodedata
import re

def norm(s):
    if not s: return ""
    s = s.lower()
    s = "".join(c for c in unicodedata.normalize("NFD", s) if unicodedata.category(c) != "Mn")
    return re.sub(r"[^a-z0-9\s]", " ", s).strip()

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']

no_match_hints = [
    ("x1vy7xXVkbQ", "Lamentaciones"),
    ("YnT77niPt6w", "Agobiado"),
    ("Ung1OVm9804", "Cantad algunos Cánticos de Sion"),
    ("AW_GLyD7U3c", "Estando Jesús en Betania"),
    ("4dlW7wpb3L0", "Vanidad"),
    ("mQMpryvIzQU", "El Mendigo"),
    ("4TojjVKeWM4", "Quieres Ir"),
    ("QKQkbGU8ZmI", "Mi Gran Maestro"),
    ("uvqfjpMTYec", "Dios Ama al Dador Alegre"),
    ("cmAXv-wI7OM", "Es lo único que Tengo"),
    ("PirJ409QOlE", "Desde ese Día"),
    ("VgtcSpWbweE", "Y si Todos seguimos la Unidad"),
    ("JMbReQMdHkY", "Hoy Debemos"),
    ("_VRGEMxn2yQ", "La Playa de Los Cielos"),
    ("CX57iyuJOKI", "Envíame a mí"),
    ("WcE85SpEQLg", "Al Pie de la Cruz"),
    ("IMuZUzKVLYs", "No Tengo Nada que Esconder"),
    ("yFYM07KEChI", "El Mismo de Ayer y Hoy"),
    ("wG03JlSpymM", "Si Recibes a Cristo en tu Corazón"),
]

for vid, hint in no_match_hints:
    hn = norm(hint)
    found = []
    for h in cat:
        tn = norm(h['title'])
        if hn in tn or tn in hn:
            found.append(h)
    if found:
        print(f"\n>> Hint: {hint} ({vid})")
        for h in found:
            print(f"   #{h['id']} '{h['title']}' [{h.get('author')}] | Link: {repr(h.get('link'))}")
