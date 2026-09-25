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

test_queries = [
    ("34 Te preguntas", "Conjunto Villanueva Te preguntas"),
    ("159 Eutico", "Tito Abarca Eutico"),
    ("272 La Trompeta (Yo se que pronto)", "Yo se que pronto Conjunto Villanueva"),
    ("275 El Amor de Dios", "Cuan sublime es el amor Villanueva"),
    ("276 Gracia Bendita", "Hay una gracia CONJUNTO VILLANUEVA"),
    ("278 Estoy aqui", "Estoy aqui Villanueva alabanza"),
    ("416 Alabad a Dios", "Alabad a Dios en su santuario Villanueva"),
    ("927 Quien es esta", "Conjunto Villanueva Quien es esta"),
    ("927 Quien es esta 2", "Quien es esta que sube del desierto Villanueva"),
]

for label, q in test_queries:
    print(f"=== {label} -> Query: {q} ===")
    res = search(q)
    for vid, title, channel in res:
        print(f"   {vid} | {title} | {channel}")
    print()
