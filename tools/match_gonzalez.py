import json
import unicodedata
import re
import sys

sys.stdout.reconfigure(encoding='utf-8')

def norm(text):
    text = unicodedata.normalize('NFD', text)
    text = re.sub(r'[\u0300-\u036f]', '', text)
    text = re.sub(r'[^a-zA-Z0-9\s]', ' ', text)
    return re.sub(r'\s+', ' ', text).strip().lower()

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
gonzalez_hymns = [h for h in cat if 'gonzale' in h.get('author', '').lower() and not h.get('isDeleted', False)]
vids = json.load(open('data/gonzalez_youtube_tracks.json', encoding='utf-8'))

print(f"Total alabanzas Los Gonzales en catálogo: {len(gonzalez_hymns)}")
matches = []

for v in vids:
    clean_vtitle = norm(re.sub(r'-(.*?)$', '', v['title']).replace('hermanos gonzalez', '').replace('los gonzalez', '').replace('jacob gonzalez', '').replace('jacobo gonzalez', ''))
    for h in gonzalez_hymns:
        htitle_norm = norm(h['title'])
        if clean_vtitle == htitle_norm or (len(clean_vtitle) > 4 and clean_vtitle in htitle_norm) or (len(htitle_norm) > 4 and htitle_norm in clean_vtitle):
            curr_link = h.get('link', '').strip()
            print(f"Match: '{v['title']}' ({v['videoId']}) <---> #{h['id']} '{h['title']}' [Tiene link: {bool(curr_link)}]")
            matches.append({
                'id': h['id'],
                'title': h['title'],
                'videoId': v['videoId'],
                'videoTitle': v['title'],
                'has_link': bool(curr_link)
            })
            break

print(f"\nTotal matches encontrados: {len(matches)}")
with open('data/gonzalez_matched.json', 'w', encoding='utf-8') as f:
    json.dump(matches, f, indent=2, ensure_ascii=False)
