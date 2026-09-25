import urllib.request
import re

vids = ["UIiP6IMzc10", "YZata6WsjA4", "za8mHbkOya4"]
for vid in vids:
    url = f"https://www.youtube.com/watch?v={vid}"
    req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
    try:
        html = urllib.request.urlopen(req).read().decode('utf-8', errors='ignore')
        m_title = re.search(r'<title>(.*?)</title>', html)
        m_owner = re.search(r'"ownerText":\{"runs":\[\{"text":"([^"]+)"', html)
        print(f"{vid} -> Title: {m_title.group(1) if m_title else 'N/A'} | Owner: {m_owner.group(1) if m_owner else 'N/A'}")
    except Exception as e:
        print(f"Error {vid}: {e}")
