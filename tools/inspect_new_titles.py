import json
import re

analysis = json.load(open('data/cuadernillo_analysis.json', encoding='utf-8'))
candidates = analysis['new_hymns'][:380]

special_titles = []
for idx, h in enumerate(candidates):
    t = h['title']
    if re.search(r'[\"\'\(\)\d\?\¿\!¡]', t):
        special_titles.append((idx, h['page'], t))

print(f"Titles with punctuation or numbers ({len(special_titles)}):")
for s in special_titles:
    print(f"  Page {s[1]:3d} | Idx {s[0]:3d} | '{s[2]}'")
