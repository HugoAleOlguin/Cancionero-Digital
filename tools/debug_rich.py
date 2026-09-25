import urllib.request, re, json

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

items = list(find_all_keys(data, 'richItemRenderer'))
if items:
    print("Keys in item 0 content:", list(items[0].get('content', {}).keys()))
    print("Content 0:", json.dumps(items[0].get('content', {}))[:300])
