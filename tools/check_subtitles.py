import urllib.request
import re
import json

def check_subtitles(vid):
    url = f"https://www.youtube.com/watch?v={vid}"
    req = urllib.request.Request(
        url,
        headers={
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36',
            'Accept-Language': 'es-ES,es;q=0.9'
        }
    )
    with urllib.request.urlopen(req, timeout=10) as resp:
        html = resp.read().decode('utf-8', errors='ignore')
        # Check captions
        captions_match = re.search(r'"captionTracks":\[(.*?)\]', html)
        if captions_match:
            try:
                tracks_raw = "[" + captions_match.group(1) + "]"
                tracks = json.loads(tracks_raw)
                for tr in tracks:
                    base_url = tr.get("baseUrl")
                    if base_url:
                        # Fetch timedtext
                        sub_req = urllib.request.Request(base_url, headers={'User-Agent': 'Mozilla/5.0'})
                        with urllib.request.urlopen(sub_req, timeout=10) as sresp:
                            xml = sresp.read().decode('utf-8', errors='ignore')
                            return f"LEN: {len(xml)}, RAW: {xml[:200]}"
            except Exception as e:
                return f"Error parse tracks: {e}"
        return "No subtitles"

vids = [
    ("2dGr2y8oEmU", "Mírame Señor"),
    ("6Ec9OIybCGQ", "La viuda de Naím"),
    ("FeIEKoP65PA", "Lázaro"),
    ("7H1sbrH7LqY", "María en Betania"),
    ("SsnkKD47zt8", "Bautízame"),
]

for vid, title in vids:
    print(f"{vid} ({title}): {check_subtitles(vid)}")
