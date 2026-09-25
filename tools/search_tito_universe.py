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

general_queries = [
    '"Tito Abarca" alabanzas',
    '"Tito Abarca" himnos',
    '"Tito Abarca" cassette',
    '"Tito Abarca" volumen',
    '"Tito Abarca" canciones',
    '"Tito Abarca" completo',
    '"Tito Abarca" Vol 1',
    '"Tito Abarca" Vol 2',
    '"Tito Abarca" Vol 3',
    '"Tito Abarca" Vol 4',
    '"Tito Abarca" Vol 5',
    '"Tito Abarca" Vol 6',
]

all_vids = {}
for q in general_queries:
    for vid, title, ch in search(q):
        if vid not in all_vids:
            all_vids[vid] = (title, ch)
            print(f"{vid} | {title} | {ch}")

print(f"\nTotal videos de Tito Abarca encontrados: {len(all_vids)}")
