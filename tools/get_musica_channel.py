import urllib.request
import re

url = "https://www.youtube.com/watch?v=UIiP6IMzc10"
req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
html = urllib.request.urlopen(req).read().decode('utf-8', errors='ignore')

m_ch = re.search(r'"channelId":"(UC[a-zA-Z0-9_-]+)"', html)
m_user = re.search(r'"ownerProfileUrl":"(.*?)"', html)
m_owner = re.search(r'"author":"([^"]+)"', html)

print("Channel ID:", m_ch.group(1) if m_ch else "N/A")
print("Owner URL:", m_user.group(1) if m_user else "N/A")
print("Author:", m_owner.group(1) if m_owner else "N/A")
