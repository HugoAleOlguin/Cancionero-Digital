import sys
import os
sys.path.append(os.path.dirname(__file__))
from search_tito_phrases import search

queries = [
    'Tito Abarca "No temais dijo Jesus"',
    'Tito Abarca "Entrar en la barca"',
    'Tito Abarca "Pero en el monte el Maestro"',
    'Tito Abarca "No temais" "barca"',
    '"Entrar en la barca para llegar a la otra ribera"',
    '"No temáis dijo Jesús" alabanza',
]

for q in queries:
    print(f"\nQuery: {q}")
    for vid, title, ch in search(q)[:4]:
        print(f"  [{vid}] {title} | {ch}")
