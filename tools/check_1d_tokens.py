import urllib.request
import re

url = "https://www.youtube.com/watch?v=1-dAaYJgs7E"
req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
html = urllib.request.urlopen(req).read().decode('utf-8', errors='ignore')

# Search for any lyrics or text in html
matches = re.findall(r'"text":"([^"]+)"', html)
print(f"Total text tokens: {len(matches)}")
# Let's filter interesting ones
for m in matches:
    if any(k in m.lower() for k in ["redención", "redencion", "pastor", "cerca", "lirio", "zorras", "vuelve"]):
        print("  ->", m)
