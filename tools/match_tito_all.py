import json
import unicodedata
import re

def norm(text):
    text = unicodedata.normalize('NFD', text)
    text = re.sub(r'[\u0300-\u036f]', '', text)
    text = re.sub(r'[^a-zA-Z0-9\s]', ' ', text)
    return re.sub(r'\s+', ' ', text).strip().lower()

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
tito_vids = json.load(open('data/tito_abarca_channel_videos.json', encoding='utf-8'))

for v in tito_vids:
    clean_vtitle = norm(v['title'].replace('Tito Abarca -', '').replace('- Tito Abarca', ''))
    for h in cat:
        htitle_norm = norm(h['title'])
        hcontent_norm = norm(h['content'])
        if clean_vtitle in htitle_norm or htitle_norm in clean_vtitle or clean_vtitle in hcontent_norm[:100]:
            print(f"Vid: '{v['title']}' ({v['videoId']}) <---> #{h['id']} '{h['title']}' ({h.get('author')}) [Link: {bool(h.get('link'))}]")
            break
