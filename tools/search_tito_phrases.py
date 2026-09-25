import urllib.request
import urllib.parse
import re
import sys
import json

sys.stdout.reconfigure(encoding='utf-8')

def search(q):
    url = "https://www.youtube.com/results?search_query=" + urllib.parse.quote(q)
    req = urllib.request.Request(
        url,
        headers={
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36',
            'Accept-Language': 'es-ES,es;q=0.9'
        }
    )
    try:
        with urllib.request.urlopen(req, timeout=10) as resp:
            html = resp.read().decode('utf-8', errors='ignore')
            matches = re.findall(
                r'"videoRenderer":\{"videoId":"([a-zA-Z0-9_-]{11})".*?"title":\{"runs":\[\{"text":"([^"]+)"\}.*?"ownerText":\{"runs":\[\{"text":"([^"]+)"',
                html
            )
            return [(m[0], m[1], m[2]) for m in matches]
    except Exception as e:
        return []

phrase_queries = [
    (474, 'Tito Abarca "Huesos Secos"'),
    (459, 'Tito Abarca "Huerto del Edén"'),
    (469, 'Tito Abarca Josué'),
    (437, 'Tito Abarca "bodas de Caná"'),
    (555, 'Tito Abarca "precio del alma"'),
    (543, 'Tito Abarca "tiempos de Elías"'),
    (569, 'Tito Abarca "Buen Samaritano"'),
    (570, 'Tito Abarca "tiempos de Moisés"'),
    (480, 'Tito Abarca "ciudades y aldeas"'),
    (559, 'Tito Abarca "Soy un enfermo"'),
    (440, 'Tito Abarca "pescador de Galilea"'),
    (444, 'Tito Abarca "José"'),
    (447, 'Tito Abarca "El Talento"'),
    (450, 'Tito Abarca "Restáurame"'),
    (451, 'Tito Abarca "Incomparable es Nuestro Dios"'),
    (477, 'Tito Abarca "Presencia de mi Dios"'),
    (585, 'Tito Abarca "tu manto quiero tocar"'),
    (582, 'Tito Abarca "Cuenta la Biblia"'),
    (583, 'Tito Abarca "Débil y cargado"'),
]

for hid, q in phrase_queries:
    res = search(q)
    found = []
    for vid, title, ch in res[:4]:
        if "tito" in title.lower() or "abarca" in title.lower() or "tito" in ch.lower() or "abarca" in ch.lower() or "asamblea" in ch.lower() or "dios es amor" in ch.lower():
            found.append((vid, title, ch))
    if found:
        print(f"\n>> Hymn #{hid} [{q}]")
        for vid, title, ch in found:
            print(f"   [{vid}] {title} | {ch}")
