import urllib.request, re, json

url = 'https://www.youtube.com/watch?v=RheHRyks_QA'
req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
html = urllib.request.urlopen(req).read().decode('utf-8', errors='ignore')
desc = re.search(r'"shortDescription":"(.*?)"', html)
if desc:
    # replace \n
    raw = desc.group(1).replace(r'\n', '\n')
    print(raw[300:1500])
