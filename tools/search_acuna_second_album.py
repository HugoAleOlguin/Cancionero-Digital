import urllib.request
import urllib.parse
import re
import sys

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
    with urllib.request.urlopen(req, timeout=10) as resp:
        html = resp.read().decode('utf-8', errors='ignore')
        matches = re.findall(
            r'"videoRenderer":\{"videoId":"([a-zA-Z0-9_-]{11})".*?"title":\{"runs":\[\{"text":"([^"]+)"\}.*?"ownerText":\{"runs":\[\{"text":"([^"]+)"',
            html
        )
        return [(m[0], m[1], m[2]) for m in matches[:5]]

second_album_queries = [
    ("328 Mas que Vencedores", "Acuña Mas que Vencedores"),
    ("328 Mas que Vencedores 2", "Somos mas que vencedores en Jesus Acuña"),
    ("329 El dia ya viene", "Acuña Cristo es mi refugio de quien temere"),
    ("335 Oh Jehova", "Solo en Dios mi alma espera Acuña"),
    ("337 Llename de Tu Poder", "Por que te sientes triste y abatido Acuña"),
    ("338 Apocalipsis", "Acuña Muy pronto Jesus vendra"),
    ("339 El Amor de Dios", "Acuña El amor de Dios podras oirlo"),
    ("340 La Samaritana", "Acuña Una mujer de Samaria se acerco"),
    ("340 La Samaritana 2", "Acuña Samaritana alabanza"),
    ("343 Salmo 121", "Alzare mis ojos a los montes Acuña"),
    ("344 Hombre Galileo", "Hace dos mil anos un hombre galileo Acuña"),
    ("344 Hombre Galileo 2", "Hombre galileo Acuña"),
    ("346 Te doy las Gracias", "Por esto Jesus te doy las gracias Acuña"),
]

for label, q in second_album_queries:
    print(f"=== {label} -> {q} ===")
    res = search(q)
    for vid, title, ch in res:
        print(f"   {vid} | {title} | {ch}")
    print()
