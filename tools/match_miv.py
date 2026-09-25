import json
import unicodedata
import re

def norm(text):
    text = unicodedata.normalize('NFD', text)
    text = re.sub(r'[\u0300-\u036f]', '', text)
    text = re.sub(r'[^a-zA-Z0-9\s]', ' ', text)
    return re.sub(r'\s+', ' ', text).strip().lower()

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
miv_vids = json.load(open('data/musica_miv_videos.json', encoding='utf-8'))

print("=== COTEJO VIDEOS @Musica.MIV.1975 ===")
for v in miv_vids:
    vt_norm = norm(v['title'])
    for h in cat:
        ht_norm = norm(h['title'])
        if vt_norm == ht_norm or (len(vt_norm) > 4 and vt_norm in ht_norm) or (len(ht_norm) > 4 and ht_norm in vt_norm):
            curr_link = h.get('link', '').strip()
            print(f"Match: '{v['title']}' ({v['videoId']}) <---> #{h['id']} '{h['title']}' ({h.get('author')}) [Tiene link: {bool(curr_link)}]")
            break
