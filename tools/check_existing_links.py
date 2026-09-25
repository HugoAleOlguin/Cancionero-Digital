import urllib.request
import re
import json

cat_data = json.load(open('data/catalog.json', encoding='utf-8'))
villanueva_hymns = [h for h in cat_data['hymns'] if 'villanueva' in h.get('author', '').lower() and not h.get('isDeleted', False)]

print(f"Checking existing links for {len(villanueva_hymns)} Villanueva hymns...")

for h in villanueva_hymns:
    link = h.get('link', '')
    if not link:
        print(f"#{h['id']} {h['title']} -> [NO LINK]")
        continue
    m_vid = re.search(r'v=([a-zA-Z0-9_-]{11})', link)
    if not m_vid:
        print(f"#{h['id']} {h['title']} -> [INVALID LINK FORMAT: {link}]")
        continue
    vid = m_vid.group(1)
    url = f"https://www.youtube.com/watch?v={vid}"
    req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
    try:
        html = urllib.request.urlopen(req, timeout=8).read().decode('utf-8', errors='ignore')
        m_title = re.search(r'<title>(.*?)</title>', html)
        title = m_title.group(1).replace(' - YouTube', '') if m_title else 'UNKNOWN'
        is_unavailable = 'Video unavailable' in html or 'Este video no está disponible' in html
        status = 'UNAVAILABLE' if is_unavailable else 'OK'
        print(f"#{h['id']} {h['title']} -> [{status}] {vid}: {title}")
    except Exception as e:
        print(f"#{h['id']} {h['title']} -> [ERROR: {e}] {vid}")
