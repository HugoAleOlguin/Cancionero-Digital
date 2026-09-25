import json

analysis = json.load(open('data/cuadernillo_analysis.json', encoding='utf-8'))
new_hymns = analysis['new_hymns']

print(f"Total new hymns: {len(new_hymns)}")
for i, h in enumerate(new_hymns[370:], 370):
    print(f"Idx {i} | Page {h.get('page')} | Title: '{h.get('title')}' | Lines: {h.get('line_count')} | Chars: {h.get('char_count')}")
    snippet = h.get('content', '')[:120].replace('\n', ' // ')
    print(f"   Snippet: {snippet}")
