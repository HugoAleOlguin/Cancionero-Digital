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

m = re.search(r'var ytInitialData = (\{.*?\});</script>', html)
if not m:
    m = re.search(r'>ytInitialData = (\{.*?\});<', html)

if m:
    data = json.loads(m.group(1))
    # Recursively find all videoRenderer or richItemRenderer
    def find_videos(obj, res):
        if isinstance(obj, dict):
            if 'videoRenderer' in obj:
                vr = obj['videoRenderer']
                vid = vr.get('videoId')
                t_runs = vr.get('title', {}).get('runs', [])
                title = t_runs[0].get('text', '') if t_runs else ''
                if vid and title:
                    res.append((vid, title))
            for k, v in obj.items():
                find_videos(v, res)
        elif isinstance(obj, list):
            for item in obj:
                find_videos(item, res)
    
    videos = []
    find_videos(data, videos)
    print(f"Encontrados {len(videos)} videos en @AlabanzasDiosesAmor:")
    seen = set()
    unique_vids = []
    for vid, title in videos:
        if vid not in seen:
            seen.add(vid)
            unique_vids.append({'videoId': vid, 'title': title, 'url': f'https://www.youtube.com/watch?v={vid}'})
            print(f"  {vid} | {title}")
            
    with open('data/dios_es_amor_all_videos.json', 'w', encoding='utf-8') as f:
        json.dump(unique_vids, f, indent=2, ensure_ascii=False)
else:
    print("ytInitialData not found")
