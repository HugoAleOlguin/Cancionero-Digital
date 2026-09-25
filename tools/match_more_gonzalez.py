import json
import unicodedata
import re
import sys

sys.stdout.reconfigure(encoding='utf-8')

def norm(text):
    text = unicodedata.normalize('NFD', text)
    text = re.sub(r'[\u0300-\u036f]', '', text)
    text = re.sub(r'[^a-zA-Z0-9\s]', ' ', text)
    return re.sub(r'\s+', ' ', text).strip().lower()

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
gonzalez_hymns = [h for h in cat if 'gonzale' in h.get('author', '').lower() and not h.get('isDeleted', False)]

additional_tracks = [
    ("wG03JlSpymM", "Si Recibes a Cristo en tu Corazón"),
    ("PirJ409QOlE", "Desde ese Día"),
    ("cmAXv-wI7OM", "Es lo único que Tengo"),
    ("yFYM07KEChI", "El Mismo de Ayer y Hoy"),
    ("qnaNeqUw5nE", "Lamentaciones"),
    ("CX57iyuJOKI", "Envíame a mí"),
    ("oC4hwI9ebcs", "Que Gloriosa será la Mañana"),
    ("4dlW7wpb3L0", "Vanidad"),
    ("c8bC-vTZ1Kk", "Dios de Amor"),
    ("IMuZUzKVLYs", "No Tengo Nada que Esconder"),
    ("JMbReQMdHkY", "Hoy Debemos"),
    ("VgtcSpWbweE", "Y si Todos seguimos la Unidad"),
    ("Ung1OVm9804", "Cantad algunos Cánticos de Sion"),
    ("AW_GLyD7U3c", "Estando Jesús en Betania"),
    ("QIxd2HLLpko", "Carro de Israel"),
    ("4TojjVKeWM4", "Quieres Ir"),
    ("mQMpryvIzQU", "El Mendigo"),
    ("kj3YqonN9q4", "A Jesucristo Ven sin Tardar"),
]

for vid, tname in additional_tracks:
    tn_norm = norm(tname)
    words = [w for w in tn_norm.split() if len(w) > 3]
    for h in gonzalez_hymns:
        ht_norm = norm(h['title'])
        hc_norm = norm(h['content'])
        if tn_norm in ht_norm or ht_norm in tn_norm or (len(words) >= 2 and all(w in hc_norm for w in words[:2])):
            curr_link = h.get('link', '').strip()
            print(f"Track: '{tname}' ({vid}) <---> #{h['id']} '{h['title']}' [Tiene link: {bool(curr_link)}]")
            print(f"   Letra: {h['content'][:60]}...")
            break
