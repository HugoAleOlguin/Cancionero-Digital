import urllib.request
import re
import json

url = "https://www.youtube.com/@titoabarca8370/videos"
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
    
    def find_all_keys(obj, target):
        if isinstance(obj, dict):
            if target in obj:
                yield obj[target]
            for v in obj.values():
                yield from find_all_keys(v, target)
        elif isinstance(obj, list):
            for item in obj:
                yield from find_all_keys(item, target)

    items = list(find_all_keys(data, 'lockupViewModel'))
    print(f"Total videos extraídos de @titoabarca8370: {len(items)}")
    
    vids = []
    seen = set()
    for it in items:
        content_id = it.get('contentId')
        title = it.get('metadata', {}).get('lockupMetadataViewModel', {}).get('title', {}).get('content')
        if content_id and title and content_id not in seen:
            seen.add(content_id)
            vids.append({'videoId': content_id, 'title': title, 'url': f'https://www.youtube.com/watch?v={content_id}'})
            print(f"  {content_id} -> {title}")
            
    with open('data/tito_abarca_channel_videos.json', 'w', encoding='utf-8') as f:
        json.dump(vids, f, indent=2, ensure_ascii=False)
    print(f"\nGuardados {len(vids)} videos en data/tito_abarca_channel_videos.json")
else:
    print("ytInitialData not found")
