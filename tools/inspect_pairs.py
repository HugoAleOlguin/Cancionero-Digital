import json

analysis = json.load(open('data/manual_analysis.json', encoding='utf-8'))
new_hymns = analysis['new_hymns']

extracted = json.load(open('data/manual_extracted.json', encoding='utf-8'))
ext_by_page = {x['page']: x for x in extracted}

pairs = [(88, 108), (173, 174), (303, 321)]

for p1, p2 in pairs:
    h1 = ext_by_page.get(p1)
    h2 = ext_by_page.get(p2)
    print(f"=== COMPARANDO PÁG {p1} '{h1['title']}' ({h1['author']}) vs PÁG {p2} '{h2['title']}' ({h2['author']}) ===")
    print("--- H1 ---")
    print(h1['content'][:200])
    print("--- H2 ---")
    print(h2['content'][:200])
    print()
