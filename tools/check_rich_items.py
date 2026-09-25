import json

# Let's inspect data from parse_channel_json
import urllib.request, re

url = "https://www.youtube.com/@AlabanzasDiosesAmor/videos"
req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36'})
html = urllib.request.urlopen(req).read().decode('utf-8', errors='ignore')

m = re.search(r'var ytInitialData = (\{.*?\});</script>', html)
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

# Let's see richItemRenderers
items = list(find_all_keys(data, 'richItemRenderer'))
print("RichItemRenderers found:", len(items))

for it in items[:10]:
    content = it.get('content', {})
    vr = content.get('videoRenderer', {})
    vid = vr.get('videoId')
    runs = vr.get('title', {}).get('runs', [])
    t = runs[0].get('text') if runs else 'No title'
    print(f"  {vid} -> {t}")
