import json
import unicodedata
import re

def norm(s):
    if not s: return ""
    s = s.lower()
    s = "".join(c for c in unicodedata.normalize("NFD", s) if unicodedata.category(c) != "Mn")
    return re.sub(r"[^a-z0-9\s]", " ", s).strip()

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']

ramirez_vids = [
    ("QG5KsajlIgE", "A ti - conjunto Villanueva", "A Ti"),
    ("wFIYuCNnwO8", "Bastate en mi gracia - conjunto Dorrego", "Bástate mi Gracia"),
    ("zzznKj1VLJg", "Cómo debo orar - Alabanza", "Cómo debo orar"),
    ("AB7RNnTQrn0", "El leproso - conjunto Luján de cuyo", "Jesús y el leproso"),
    ("1B29pWM-jKc", "El señor de Galilea - conjunto Luján de Cuyo", "El Señor de Galilea"),
    ("qR3kyPuCvd8", "El vive - conjunto Dorrego", "Él Vive"),
    ("m1tUuopAReg", "Esfuérzate y se valiente - Tito Abarca", "Esfuérzate y sé Valiente"),
    ("ffKWlq23gE8", "Fue por ti - Tito abarca", "Fue por Ti"),
    ("gpihkW-tAlY", "Gracias te doy señor - conjunto Luján de Cuyo", "Gracias te doy Señor"),
    ("FqvhfCXNKEE", "Hoy tengo paz - conjunto Luján de Cuyo", "Hoy tengo paz"),
    ("6Ec9OIybCGQ", "La viuda de Naím - conjunto san Juan", "La Viuda de Naín"),
    ("LulS2uYRgZU", "Lléname de fuerzas - conjunto Luján de cuyo", "Lléname de fuerzas"),
    ("FeIEKoP65PA", "Lázaro - conjunto san Juan", "Lázaro"),
    ("7H1sbrH7LqY", "María en Betania - Conjunto Tucumán", "María en Betania"),
    ("2dGr2y8oEmU", "Mírame señor - conjunto Luján de Cuyo", "Mírame Señor"),
    ("ENyCqDYilg0", "Necesito de ti - conjunto Dorrego", "Necesito de Ti"),
    ("_npLSQcHvmI", "No temere  (alabanza) - hno de catriel, Rio negro", "No Temeré"),
    ("Jz8aUWuXTQg", "Orare por ti - conjunto San Juan", "Oraré por Ti"),
    ("oxQ6dH8b9rg", "Pido perdón - Alabanza", "Pido Perdón"),
    ("VsmjmQd5GcQ", "Porque no me lo pedistes- Tito abarca", "Porque no me lo pediste"),
    ("Zp8X2z-4xQY", "Pronto viene cristo - conjunto Luján de Cuyo", "Pronto Viene Cristo"),
    ("pcOlo3axiN0", "Puedo cantar -Tito abarca", "Puedo Cantar"),
    ("uKSAUBPzqxk", "Que está pasando - conjunto Villanueva", "Qué está pasando"),
    ("c4KB-neT6rc", "Quien me apartara del amor de Jesucristo - conjunto Dorrego", "Quién me Apartará"),
    ("eQg1XyKSG_Y", "Salmo 137 - Tito Abarca", "Salmo 137"),
    ("SsnkKD47zt8", "Señor te pido bautizame - conjunto", "Bautízame"),
    ("y71zNuhMqz8", "Siervo de Dios - conjunto Luján de cuyo", "El Siervo"),
    ("Ba2DiiDR0tE", "Tinieblas tenebrosas - conjunto Luján Cuyo", "Tinieblas Tenebrosas"),
    ("k8VA50AoaqI", "Un milagro - conjunto Dorrego", "Un Milagro"),
    ("_eYCrRKu-KU", "Yo te alabo señor - Tito abarca", "Yo te alabo Señor"),
    ("fvjXX5Ivomo", "yo en ellos - conjunto villanueva", "Yo en Ellos")
]

print("=== COTEJANDO VIDEOS DE CARLOS RAMÍREZ ===")
for vid, yt_title, search_hint in ramirez_vids:
    hint_norm = norm(search_hint)
    found = []
    for h in cat:
        tn = norm(h['title'])
        if hint_norm in tn or tn in hint_norm:
            found.append(h)
    
    if found:
        print(f"\n>> Video: {yt_title} ({vid})")
        for h in found:
            has_link = bool(h.get('link', '').strip())
            curr = h.get('link', '') if has_link else 'VACÍO'
            print(f"   Match: #{h['id']} '{h['title']}' [{h.get('author')}] | Link: {curr}")
            print(f"          Letra: {' '.join(h['content'].splitlines()[:2])[:60]}")
    else:
        print(f"\n-- NO MATCH for: {yt_title} (hint: {search_hint})")
