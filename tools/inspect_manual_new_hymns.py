import json
import unicodedata
import re
from difflib import SequenceMatcher

def norm(text):
    text = unicodedata.normalize('NFD', text)
    text = re.sub(r'[\u0300-\u036f]', '', text)
    text = re.sub(r'[^a-zA-Z0-9\s]', ' ', text)
    return re.sub(r'\s+', ' ', text).strip().lower()

analysis = json.load(open('data/manual_analysis.json', encoding='utf-8'))
new_hymns = analysis['new_hymns']

print(f"Auditando {len(new_hymns)} alabanzas nuevas de 'Manual de Alabanzas Cristianas.pdf'...")

# 1. Chequeo de duplicados internos
dups = []
for i in range(len(new_hymns)):
    ni = norm(new_hymns[i]['title'])
    ci = norm(new_hymns[i]['content']).split()
    for j in range(i + 1, len(new_hymns)):
        nj = norm(new_hymns[j]['title'])
        cj = norm(new_hymns[j]['content']).split()
        if ni == nj and len(ni) > 3:
            dups.append((i, new_hymns[i]['page'], new_hymns[i]['title'], j, new_hymns[j]['page'], new_hymns[j]['title'], "mismo_titulo"))
        else:
            set_i = set(ci)
            set_j = set(cj)
            if len(set_i & set_j) / max(len(set_i), len(set_j)) > 0.6:
                sim = SequenceMatcher(None, ci[:200], cj[:200]).ratio()
                if sim > 0.70:
                    dups.append((i, new_hymns[i]['page'], new_hymns[i]['title'], j, new_hymns[j]['page'], new_hymns[j]['title'], f"sim_{sim:.2f}"))

print(f"Duplicados internos encontrados: {len(dups)}")
for d in dups:
    print(" ", d)

# 2. Chequeo de títulos sospechosos o muy cortos
suspicious = []
for idx, h in enumerate(new_hymns):
    t = h['title']
    c = h['content']
    if len(t) < 3 or len(c) < 100 or 'indice' in c.lower() or 'página' in c.lower():
        suspicious.append((idx, h['page'], t, len(c)))

print(f"\nTítulos o contenidos sospechosos: {len(suspicious)}")
for s in suspicious:
    print(" ", s)
