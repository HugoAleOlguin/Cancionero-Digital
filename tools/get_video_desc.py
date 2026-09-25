import urllib.request
import re
import json

url = "https://www.youtube.com/watch?v=7LU9dYUf5-4"
req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
html = urllib.request.urlopen(req).read().decode('utf-8', errors='ignore')

# Extract description
m_desc = re.search(r'"shortDescription":"(.*?)"', html)
if m_desc:
    print("Description:", m_desc.group(1).encode('utf-8', errors='ignore').decode('unicode_escape', errors='ignore'))
else:
    print("No description found")
