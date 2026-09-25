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
    ("iFv8gVsjyzM", "Si vivo", "Si vivo"),
    ("s8w-q8pfIE8", "La Oración de Ana", "Ana"),
    ("vHTuIrT-F6Q", "Quiero Señor", "Quiero Señor"),
    ("uKS1kQnIdbw", "Allá en el Olvido", "Allá en el olvido"),
    ("ySEyVRisGwM", "Ponte de pie", "Ponte de pie"),
    ("ORWW1G7aMDo", "Aunque todos te nieguen", "Aunque todos te nieguen"),
    ("DSg_tlZWFbo", "El Milagro", "Milagro"),
    ("3WHjn-JPc8E", "El afán y la ansiedad", "El afán y la ansiedad"),
    ("F7MUvcjslfo", "Porque Temer?", "No Temáis"),
    ("Nfr_mu5fehM", "MUJER VIRTUOSA", "Mujer virtuosa"),
    ("-I2Zx9kkQgk", "Encorvada y Prisionera", "Encorvada y prisionera"),
]

for vid, label, hint in candidates:
    hn = norm(hint)
    found = []
    for h in cat:
        tn = norm(h['title'])
        if hn in tn or tn in hn:
            found.append(h)
    print(f"\n>> Candidate: {label} ({vid})")
    for h in found:
        curr = h.get('link', '').strip() or 'VACÍO'
        print(f"   #{h['id']} '{h['title']}' [{h.get('author')}] | Link: {curr}")
        print(f"      Letra: {' '.join(h['content'].splitlines()[:2])[:60]}")
