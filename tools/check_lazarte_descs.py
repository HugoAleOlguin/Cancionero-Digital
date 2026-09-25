import urllib.request
import re

vids = [
    ("SuakSQEEV3g", "Jesucristo Viene Pronto"),
    ("TEjDdIywHws", "Que Inmenso Amor"),
    ("-mzh_YiYJUo", "Cuan Grande Amor"),
    ("9j2PI18x0Bs", "Voy Caminando a Los Cielos"),
    ("3sJF-A7qchc", "Espero ese Día"),
    ("lhNxLLqR19k", "Él está Aquí"),
    ("IcYAITkRPfA", "Día de Gran Victoria"),
    ("C2Wxy02CT1o", "Ay de Mí"),
    ("8wZ76ta31kM", "Mi Anhelo"),
    ("S-TzhpkQdL8", "Me Diste Amor"),
]

for vid, label in vids:
    url = f"https://www.youtube.com/watch?v={vid}"
    req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
    try:
        html = urllib.request.urlopen(req, timeout=8).read().decode('utf-8', errors='ignore')
        m_desc = re.search(r'"shortDescription":"(.*?)"', html)
        desc = m_desc.group(1).encode('utf-8', errors='ignore').decode('unicode_escape', errors='ignore') if m_desc else ''
        print(f"=== {label} ({vid}) ===")
        print(f"Desc: {desc[:250]}")
    except Exception as e:
        print(f"Error {vid}: {e}")
    print()
