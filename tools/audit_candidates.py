import json
import re

analysis = json.load(open('data/cuadernillo_analysis.json', encoding='utf-8'))
new_hymns = analysis['new_hymns'][:380] # Exclude index pages 380..390

print(f"Total candidates to evaluate: {len(new_hymns)}")

suspicious = []
for idx, h in enumerate(new_hymns):
    title = h['title']
    content = h['content']
    lines = h['line_count']
    chars = h['char_count']
    
    # Check for keywords that indicate non-hymn pages
    lower_c = content.lower()
    if 'indice' in lower_c or 'pagina' in lower_c or 'índice' in lower_c:
        suspicious.append((idx, h['page'], title, 'Contains index/pagina word', lines, chars))
    elif lines < 6 or chars < 120:
        suspicious.append((idx, h['page'], title, 'Too short', lines, chars))
    elif re.search(r'\b(pagina|página)\b', title, re.IGNORECASE):
        suspicious.append((idx, h['page'], title, 'Title contains pagina', lines, chars))

print(f"Suspicious items count: {len(suspicious)}")
for s in suspicious:
    print(s)
    h = new_hymns[s[0]]
    print("  Snippet:", h['content'][:120].replace('\n', ' // '))
