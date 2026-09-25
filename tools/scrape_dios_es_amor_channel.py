import urllib.request
import re
import json

url = "https://www.youtube.com/@AlabanzasDiosesAmor/videos"
req = urllib.request.Request(
    url,
    headers={
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36',
        'Accept-Language': 'es-ES,es;q=0.9'
    }
)

html = urllib.request.urlopen(req, timeout=12).read().decode('utf-8', errors='ignore')

# Extract videoRenderers
matches = re.findall(
    r'"videoRenderer":\{"videoId":"([a-zA-Z0-9_-]{11})".*?"title":\{"runs":\[\{"text":"([^"]+)"\}',
    html
)

print(f"Total videos extraídos de @AlabanzasDiosesAmor: {len(matches)}")
seen = set()
vids = []
for vid, title in matches:
    if vid not in seen:
        seen.add(vid)
        vids.append({'videoId': vid, 'title': title, 'url': f'https://www.youtube.com/watch?v={vid}'})
        print(f"  {vid} | {title}")

with open('data/alabanzas_dios_es_amor_videos.json', 'w', encoding='utf-8') as f:
    json.dump(vids, f, indent=2, ensure_ascii=False)
