import urllib.request
import re
import json

def get_video_info(vid):
    url = f"https://www.youtube.com/watch?v=s{vid}" # wait, let's fix URL
    url = f"https://www.youtube.com/watch?v={vid}"
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
            # Extract title
            title_m = re.search(r'<title>(.*?)</title>', html)
            title = title_m.group(1) if title_m else ""
            # Extract description / shortDescription
            desc_m = re.search(r'"shortDescription":"(.*?)"', html)
            desc = desc_m.group(1).encode('utf-8').decode('unicode_escape') if desc_m else ""
            return title, desc
    except Exception as e:
        return str(e), ""

test_vids = [
    ("2dGr2y8oEmU", "Mírame Señor"),
    ("y71zNuhMqz8", "Siervo"),
    ("6Ec9OIybCGQ", "La viuda de Naím"),
    ("FeIEKoP65PA", "Lázaro"),
    ("7H1sbrH7LqY", "María en Betania"),
    ("SsnkKD47zt8", "Bautízame"),
]

for vid, label in test_vids:
    t, d = get_video_info(vid)
    print(f"=== {label} ({vid}) ===")
    print(f"Title: {t}")
    print(f"Desc: {d[:200]}")
    print()
