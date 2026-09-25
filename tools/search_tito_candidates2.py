import sys
import os
sys.path.append(os.path.dirname(__file__))
from search_tito_phrases import search
from inspect_candidates import get_video_info

queries = [
    ("Tizón Arrebatado", 'Tito Abarca "Tizon Arrebatado"'),
    ("Tizón Arrebatado 2", 'Tito Abarca Tizon Arrebatado'),
    ("Quiero se limpio", 'Tito Abarca "Quiero se limpio"'),
    ("El Leproso", 'Tito Abarca "El Leproso"'),
    ("El Leproso 2", 'Tito Abarca Leproso'),
    ("Caleb", 'Tito Abarca Caleb'),
    ("Mefiboset", 'Tito Abarca Mefiboset'),
    ("No Hay Cadenas Ni Cerrojos", 'Tito Abarca "No hay cadenas"'),
    ("Ponte de pie", 'Tito Abarca "Ponte de pie"'),
    ("Albañil", 'Tito Abarca Albanil'),
    ("Job", 'Tito Abarca Job'),
    ("José", 'Tito Abarca Jose'),
    ("La Barca", 'Tito Abarca "La Barca"'),
    ("El Talento", 'Tito Abarca "El Talento"'),
    ("El Buen Samaritano", 'Tito Abarca "Buen Samaritano"'),
    ("Lluvias de Bendición", 'Tito Abarca "Lluvias de Bendicion"'),
    ("Yo Vengo Pronto", 'Tito Abarca "Yo Vengo Pronto"'),
]

for label, q in queries:
    print(f"\n=== Query: {label} [{q}] ===")
    res = search(q)
    for vid, title, ch in res[:5]:
        print(f"  [{vid}] {title} | {ch}")
