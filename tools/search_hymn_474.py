import sys
import os
sys.path.append(os.path.dirname(__file__))
from search_tito_phrases import search

queries = [
    'Tito Abarca Ezequiel',
    'Tito Abarca "huesos secos"',
    'Tito Abarca "valle de huesos"',
    'Tito Abarca "aquel valle"',
    'Ezequiel fue llevado por Dios a aquel valle',
    '"valle de los huesos secos" alabanza',
]
for q in queries:
    print(f"\nQuery: {q}")
    for vid, title, ch in search(q)[:4]:
        print(f"  [{vid}] {title} | {ch}")
