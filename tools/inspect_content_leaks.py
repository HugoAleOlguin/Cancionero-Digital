import json
import re

analysis = json.load(open('data/cuadernillo_analysis.json', encoding='utf-8'))
candidates = analysis['new_hymns'][:380]

leaks = []
for idx, h in enumerate(candidates):
    lines = h['content'].split('\n')
    for l_idx, line in enumerate(lines):
        line_clean = line.strip()
        # Look for things like "Página 123", "Cancionero", or standalone numbers
        if re.match(r'^(p[aá]gina|\d+|\s*-\s*\d+\s*-\s*)$', line_clean, re.IGNORECASE):
            leaks.append((idx, h['page'], h['title'], l_idx, line_clean))
        elif 'cuadernillo' in line_clean.lower():
            leaks.append((idx, h['page'], h['title'], l_idx, line_clean))

print(f"Content leak issues detected: {len(leaks)}")
for l in leaks:
    print(f"  Page {l[1]} | Hymn '{l[2]}' | Line {l[3]}: '{l[4]}'")
