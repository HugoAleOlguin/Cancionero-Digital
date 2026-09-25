import json
from difflib import SequenceMatcher
import unicodedata
import re

def norm(text):
    text = unicodedata.normalize('NFD', text)
    text = re.sub(r'[\u0300-\u036f]', '', text)
    text = re.sub(r'[^a-zA-Z0-9\s]', ' ', text)
    return re.sub(r'\s+', ' ', text).strip().lower()

def main():
    catalog_data = json.load(open('data/catalog.json', encoding='utf-8'))
    catalog_by_id = {h['id']: h for h in catalog_data['hymns']}
    analysis = json.load(open('data/manual_analysis.json', encoding='utf-8'))
    extracted = json.load(open('data/manual_extracted.json', encoding='utf-8'))
    ext_by_page = {x['page']: x for x in extracted}

    candidates = analysis.get('version_candidates', [])
    print(f"Auditando {len(candidates)} candidatos a versión alternativa...")

    valid_variants = []
    homonym_diff_songs = []
    almost_identical = []

    for c in candidates:
        cid = c['catalog_id']
        page = c['manual_page']
        ch = catalog_by_id[cid]
        mh = ext_by_page[page]

        c_text = ch['content'].strip()
        m_text = mh['content'].strip()

        # Check existing extraVersions so we don't duplicate
        existing_extras = ch.get('extraVersions', [])
        already_in_extras = any(m_text == ex.strip() for ex in existing_extras)
        if m_text == c_text or already_in_extras:
            almost_identical.append((cid, ch['title'], page, mh['title'], "Ya idéntico"))
            continue

        c_words = norm(c_text).split()
        m_words = norm(m_text).split()
        sim = SequenceMatcher(None, c_words[:350], m_words[:350]).ratio()

        # If similarity is between 0.45 and 0.88, check if they share a chorus or major theme
        if sim >= 0.50:
            # Genuine lyric variant
            valid_variants.append({
                "catalog_id": cid,
                "catalog_title": ch['title'],
                "catalog_author": ch.get('author', ''),
                "manual_page": page,
                "manual_title": mh['title'],
                "manual_author": mh.get('author', ''),
                "manual_content": m_text,
                "similarity": round(sim, 2)
            })
        elif norm(ch['title']) == norm(mh['title']) and sim < 0.40:
            # Homonym title but completely different song
            homonym_diff_songs.append((cid, ch['title'], page, mh['title'], sim))

    print(f"\n1. Variantes legítimas a vincular como Versión 2/3: {len(valid_variants)}")
    print(f"2. Canciones distintas con título homónimo (deben ir como nuevas alabanzas): {len(homonym_diff_songs)}")
    print(f"3. Idénticas o ya vinculadas: {len(almost_identical)}")

    print("\nMuestra de Variantes Legítimas (Primeras 15):")
    for v in valid_variants[:15]:
        print(f"  CAT #{v['catalog_id']:3d} '{v['catalog_title']}' vs MANUAL p.{v['manual_page']} '{v['manual_title']}' ({v['manual_author']}) | sim={v['similarity']}")

    print("\nCanciones con título homónimo pero letra diferente:")
    for h in homonym_diff_songs:
        print(f"  CAT #{h[0]:3d} '{h[1]}' vs MANUAL p.{h[2]} '{h[3]}' | sim={h[4]:.2f}")

if __name__ == '__main__':
    main()
