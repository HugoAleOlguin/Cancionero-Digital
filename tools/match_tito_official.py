import json
import unicodedata
import re

def norm(text):
    text = unicodedata.normalize('NFD', text)
    text = re.sub(r'[\u0300-\u036f]', '', text)
    text = re.sub(r'[^a-zA-Z0-9\s]', ' ', text)
    return re.sub(r'\s+', ' ', text).strip().lower()

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
tito_hymns = [h for h in cat if 'abarca' in h.get('author', '').lower() and not h.get('isDeleted', False)]
tito_vids = json.load(open('data/tito_abarca_channel_videos.json', encoding='utf-8'))

print(f"Total Alabanzas Tito Abarca en Catálogo: {len(tito_hymns)}")
print("=== COTEJO CON CANAL OFICIAL TITO ABARCA ===")

matched_count = 0
for v in tito_vids:
    clean_vtitle = norm(v['title'].replace('Tito Abarca -', '').replace('- Tito Abarca', ''))
    for h in tito_hymns:
        htitle_norm = norm(h['title'])
        if clean_vtitle == htitle_norm or (len(clean_vtitle) > 4 and clean_vtitle in htitle_norm) or (len(htitle_norm) > 4 and htitle_norm in clean_vtitle):
            curr_link = h.get('link', '').strip()
            print(f"Match: '{v['title']}' ({v['videoId']}) <---> #{h['id']} '{h['title']}' [Tiene link: {bool(curr_link)}]")
            matched_count += 1
            break

print(f"\nTotal matches encontrados en canal oficial: {matched_count}")
