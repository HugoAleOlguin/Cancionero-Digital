import urllib.request
import re

url = "https://www.youtube.com/watch?v=dxnm4uJDO7k"
req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
html = urllib.request.urlopen(req).read().decode('utf-8', errors='ignore')

m_title = re.search(r'<title>(.*?)</title>', html)
m_desc = re.search(r'"shortDescription":"(.*?)"', html)
print("Title:", m_title.group(1) if m_title else 'N/A')
desc = m_desc.group(1).encode('utf-8', errors='ignore').decode('unicode_escape', errors='ignore') if m_desc else ''
print("Desc:", desc[:300])
