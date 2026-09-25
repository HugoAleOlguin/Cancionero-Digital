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

# Search for videoId patterns
vids = re.findall(r'"videoId":"([a-zA-Z0-9_-]{11})"', html)
print(f"Total videoId occurrences: {len(vids)}")

# Find richItemRenderer or videoRenderer titles
titles = re.findall(r'"title":\{"runs":\[\{"text":"([^"]+)"\}\],"accessibility"', html)
print(f"Total titles: {len(titles)}")

# Pair them up if possible or extract pairs
matches = re.findall(r'"videoId":"([a-zA-Z0-9_-]{11})".*?"title":\{"runs":\[\{"text":"([^"]+)"\}\]', html)
print(f"Total pairs matched: {len(matches)}")
for vid, title in matches[:20]:
    print(f"  {vid} | {title}")
