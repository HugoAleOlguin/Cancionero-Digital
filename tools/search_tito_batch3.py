import sys
import os
sys.path.append(os.path.dirname(__file__))
from search_tito_phrases import search

queries = [
    (288, 'Tito Abarca "frente al Tribunal"'),
    (288, 'Tito Abarca "Mi abogado"'),
    (436, 'Tito Abarca "Hijo no te sorprendas"'),
    (436, 'Tito Abarca "Te sorprendio la prueba"'),
    (444, 'Tito Abarca "Era amado por su padre"'),
    (444, 'Tito Abarca Jose'),
    (450, 'Tito Abarca "Restaurame"'),
    (451, 'Tito Abarca "Quien midio en el hueco de su mano"'),
    (451, 'Tito Abarca "Incomparable"'),
    (456, 'Tito Abarca "Fuego Celestial"'),
    (475, 'Tito Abarca "Pedro fue un pescador"'),
    (559, 'Tito Abarca "Pasa Jesus por el camino"'),
    (559, 'Tito Abarca "Soy un enfermo"'),
    (569, 'Tito Abarca "De Jerusalen salia"'),
    (569, 'Tito Abarca "Buen Samaritano"'),
    (585, 'Tito Abarca "tu manto quiero tocar"'),
    (585, 'Tito Abarca "Senor permiteme"'),
]

for hid, q in queries:
    res = search(q)
    found = []
    for vid, title, ch in res[:3]:
        found.append((vid, title, ch))
    if found:
        print(f"\n>> Hymn #{hid} [{q}]")
        for vid, title, ch in found:
            print(f"   [{vid}] {title} | {ch}")
