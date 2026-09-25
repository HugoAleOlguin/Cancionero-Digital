import json
import unicodedata
import re
from difflib import SequenceMatcher

def norm(text):
    text = unicodedata.normalize('NFD', text)
    text = re.sub(r'[\u0300-\u036f]', '', text)
    text = re.sub(r'[^a-zA-Z0-9\s]', ' ', text)
    return re.sub(r'\s+', ' ', text).strip().lower()

def main():
    catalog_data = json.load(open('data/catalog.json', encoding='utf-8'))
    catalog = {h['id']: h for h in catalog_data['hymns']}
    analysis = json.load(open('data/cuadernillo_analysis.json', encoding='utf-8'))
    new_hymns = analysis['new_hymns']

    overlaps = [
        (14, 'Te Alabaré', 12, 'Te alabaré'),
        (31, 'El Hijo Prodigo', 383, 'El Hijo Prodigo'),
        (31, 'El Hijo Prodigo', 427, 'El Hijo Prodigo'),
        (36, 'Por Amor', 20, 'Por amor'),
        (62, 'El Espíritu de Dios', 456, 'El Espíritu de Dios'),
        (73, 'Qué Gran Amor', 80, '¡Qué gran amor!'),
        (86, 'Joven Rico', 394, 'Joven Rico'),
        (153, 'Te Alabaré', 12, 'Te alabaré'),
        (160, 'Quiero alabarte', 184, 'Quiero alabarte'),
        (169, 'Jesús Nazareno', 76, 'Jesús Nazareno'),
        (174, 'Quédate Señor', 113, 'Quédate Señor'),
        (239, 'Te seguiré', 24, 'Te seguiré'),
        (252, 'Tú estás Aquí', 322, 'Tú estás aquí'),
        (290, 'Padre Mio', 266, 'Padre Mío'),
        (334, 'Hay Momentos', 92, 'Hay momentos'),
    ]

    print("--- EVALUANDO COINCIDENCIAS CON CATÁLOGO ---")
    for idx, title_new, cat_id, cat_title in overlaps:
        nh = new_hymns[idx]
        ch = catalog[cat_id]
        sim = SequenceMatcher(None, norm(nh['content']).split(), norm(ch['content']).split()).ratio()
        print(f"NEW #{idx} '{title_new}' (p.{nh['page']}, {nh['author']}) vs CAT #{cat_id} '{cat_title}'")
        print(f"  Similarity: {sim:.2f}")
        print(f"  NEW snippet: {nh['content'][:80].replace(chr(10), ' / ')}")
        print(f"  CAT snippet: {ch['content'][:80].replace(chr(10), ' / ')}")
        if sim > 0.40:
            print("  ==> ALERTA: Posible misma alabanza o versión alternativa!")
        else:
            print("  ==> OK: Tema/título bíblico homónimo pero letra y música totalmente diferente.")
        print()

    print("\n--- EVALUANDO DUPLICADOS INTERNOS EN NEW_HYMNS ---")
    internal_dups = [
        (14, 'Te Alabaré', 153, 'Te Alabaré'),
        (24, 'David y Goliat', 259, 'David y Goliat'),
        (142, 'El Día Glorioso', 150, 'Que Esplendente'),
        (170, 'La viuda de Naín', 187, 'La viuda de Naín'),
        (177, 'Zaqueo', 206, 'Zaqueo'),
    ]
    for i, t1, j, t2 in internal_dups:
        h1 = new_hymns[i]
        h2 = new_hymns[j]
        sim = SequenceMatcher(None, norm(h1['content']).split(), norm(h2['content']).split()).ratio()
        print(f"NEW #{i} '{t1}' (p.{h1['page']}, {h1['author']}) vs NEW #{j} '{t2}' (p.{h2['page']}, {h2['author']})")
        print(f"  Similarity: {sim:.2f}")
        print(f"  H1 snippet: {h1['content'][:80].replace(chr(10), ' / ')}")
        print(f"  H2 snippet: {h2['content'][:80].replace(chr(10), ' / ')}")
        print()

if __name__ == '__main__':
    main()
