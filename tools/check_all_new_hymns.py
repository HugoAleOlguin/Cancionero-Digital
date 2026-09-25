import json
import unicodedata
import re
from difflib import SequenceMatcher

def norm(text):
    text = unicodedata.normalize('NFD', text)
    text = re.sub(r'[\u0300-\u036f]', '', text)
    text = re.sub(r'[^a-zA-Z0-9\s]', ' ', text)
    return re.sub(r'\s+', ' ', text).strip().lower()

def main():
    catalog_data = json.load(open('data/catalog.json', encoding='utf-8'))
    catalog = catalog_data['hymns']
    analysis = json.load(open('data/cuadernillo_analysis.json', encoding='utf-8'))
    new_hymns = analysis['new_hymns']

    print(f"Total catalog hymns: {len(catalog)}")
    print(f"Total new hymns candidates: {len(new_hymns)}")

    # 1. Check if any new_hymn contains the text of a catalog hymn (like truncated #456)
    contained_in_cat = []
    for idx, nh in enumerate(new_hymns):
        nh_norm = norm(nh['content'])
        for ch in catalog:
            ch_norm = norm(ch['content'])
            # If catalog hymn is short and contained in new hymn
            if len(ch_norm) > 50 and ch_norm[:100] in nh_norm:
                contained_in_cat.append((idx, nh['title'], ch['id'], ch['title']))
    
    print("\n--- NUEVOS QUE CONTIENEN TEXTO DE UN HIMNO DEL CATÁLOGO ---")
    for item in contained_in_cat:
        print(" ", item)

    # 2. Check internal pairwise similarity > 0.70 across all 391 new hymns
    print("\n--- BUSCANDO VERSIONES O DUPLICADOS INTERNOS ENTRE LOS 391 NUEVOS ---")
    pairs = []
    for i in range(len(new_hymns)):
        ni_words = norm(new_hymns[i]['content']).split()
        if not ni_words: continue
        for j in range(i + 1, len(new_hymns)):
            nj_words = norm(new_hymns[j]['content']).split()
            if not nj_words: continue
            
            # Quick check: shared words ratio
            set_i = set(ni_words)
            set_j = set(nj_words)
            jaccard = len(set_i & set_j) / max(len(set_i), len(set_j))
            if jaccard > 0.50:
                sim = SequenceMatcher(None, ni_words, nj_words).ratio()
                if sim > 0.60:
                    pairs.append((i, new_hymns[i]['title'], j, new_hymns[j]['title'], sim))

    for p in pairs:
        print(f"  NEW #{p[0]} '{p[1]}' vs NEW #{p[2]} '{p[3]}' -> sim={p[4]:.2f}")

if __name__ == '__main__':
    main()
