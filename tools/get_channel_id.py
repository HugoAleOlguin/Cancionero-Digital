import urllib.request
import re

url = "https://www.youtube.com/watch?v=7LU9dYUf5-4"
req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
html = urllib.request.urlopen(req).read().decode('utf-8', errors='ignore')

m_ch = re.search(r'"channelId":"(UC[a-zA-Z0-9_-]+)"', html)
if m_ch:
    print("Channel ID:", m_ch.group(1))
m_user = re.search(r'"ownerProfileUrl":"(.*?)"', html)
if m_user:
    print("Owner URL:", m_user.group(1))
