import sys
import os
sys.path.append(os.path.dirname(__file__))
from search_tito_phrases import search
from inspect_candidates import get_video_info

queries = [
    'Debil y cargado estoy mi Senor',
    '"Debil y cargado" alabanza',
    '"Debil y cargado" Tito Abarca',
    '"Puedo oir tu voz" Tito Abarca',
    '"Puedo oir su voz" alabanza',
    'Pablo Lazo "Debil y cargado"',
]

for q in queries:
    print(f"\nQuery: {q}")
    for vid, title, ch in search(q)[:4]:
        print(f"  [{vid}] {title} | {ch}")
