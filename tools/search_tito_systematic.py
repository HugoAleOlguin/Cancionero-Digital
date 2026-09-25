import urllib.request
import urllib.parse
import json
import re
import time
import unicodedata
import sys

sys.stdout.reconfigure(encoding='utf-8')

def norm(text):
    text = unicodedata.normalize('NFD', text)
    text = re.sub(r'[\u0300-\u036f]', '', text)
    text = re.sub(r'[^a-zA-Z0-9\s]', ' ', text)
    return re.sub(r'\s+', ' ', text).strip().lower()

def search_youtube_videos(query):
    url = "https://www.youtube.com/results?search_query=" + urllib.parse.quote(query)
    req = urllib.request.Request(
        url,
        headers={
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36',
            'Accept-Language': 'es-ES,es;q=0.9'
        }
    )
    try:
        with urllib.request.urlopen(req, timeout=12) as resp:
            html = resp.read().decode('utf-8', errors='ignore')
            video_matches = re.findall(
                r'"videoRenderer":\{"videoId":"([a-zA-Z0-9_-]{11})".*?"title":\{"runs":\[\{"text":"([^"]+)"\}.*?"ownerText":\{"runs":\[\{"text":"([^"]+)"',
                html
            )
            results = []
            seen = set()
            for vid, vtitle, channel in video_matches:
                if vid in seen:
                    continue
                seen.add(vid)
                results.append({
                    "videoId": vid,
                    "videoTitle": vtitle,
                    "channel": channel,
                    "url": f"https://www.youtube.com/watch?v={vid}"
                })
                if len(results) >= 5:
                    break
            return results
    except Exception as e:
        print(f"  Error en búsqueda '{query}': {e}")
        return []

def evaluate_song(hymn):
    title = hymn["title"]
    queries = [
        f"Tito Abarca {title}",
        f'Tito Abarca "{title}"'
    ]
    
    t_words = [w for w in norm(title).split() if len(w) > 2 and w not in ["que", "del", "las", "los", "por", "para", "con", "una", "uno"]]
    
    candidates = []
    seen_vids = set()
    
    for q in queries:
        vids = search_youtube_videos(q)
        for v in vids:
            if v["videoId"] not in seen_vids:
                seen_vids.add(v["videoId"])
                candidates.append(v)
        if len(candidates) >= 4:
            break
        time.sleep(0.3)
        
    scored = []
    for c in candidates:
        v_title_norm = norm(c["videoTitle"])
        v_channel_norm = norm(c["channel"])
        
        has_artist = ("abarca" in v_title_norm or "abarca" in v_channel_norm)
        matched_words = sum(1 for w in t_words if w in v_title_norm)
        ratio = matched_words / len(t_words) if t_words else 0
        
        score = 0
        if has_artist:
            score += 50
        score += int(ratio * 50)
        
        if norm(title) in v_title_norm:
            score += 20
            
        scored.append({
            **c,
            "has_artist": has_artist,
            "matched_words": matched_words,
            "score": score
        })
        
    scored.sort(key=lambda x: -x["score"])
    
    best = scored[0] if scored else None
    status = "NO_FOUND"
    if best:
        if best["score"] >= 80 and best["has_artist"]:
            status = "EXACT_MATCH"
        elif best["score"] >= 50 and best["has_artist"]:
            status = "HIGH_CONFIDENCE"
        elif best["score"] >= 30:
            status = "NEEDS_REVIEW"
            
    return {
        "id": hymn["id"],
        "title": title,
        "author": "Tito Abarca",
        "status": status,
        "best_match": best,
        "all_candidates": scored[:3]
    }

def main():
    cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
    tito_missing = [h for h in cat if 'abarca' in h.get('author', '').lower() and not h.get('isDeleted', False) and not h.get('link', '').strip()]
    
    print(f"Búsqueda sistemática para {len(tito_missing)} alabanzas sin enlace de Tito Abarca...")
    results = []
    for idx, h in enumerate(tito_missing, 1):
        res = evaluate_song(h)
        results.append(res)
        bm = res['best_match']
        status = res['status']
        if bm and status in ['EXACT_MATCH', 'HIGH_CONFIDENCE']:
            print(f"[{idx:2d}/{len(tito_missing):2d}] #{h['id']} '{h['title']}' -> [{status}] '{bm['videoTitle']}' ({bm['url']})")
        else:
            print(f"[{idx:2d}/{len(tito_missing):2d}] #{h['id']} '{h['title']}' -> [{status}]")
        time.sleep(0.4)
        
    with open('data/youtube_tito_systematic.json', 'w', encoding='utf-8') as f:
        json.dump(results, f, indent=2, ensure_ascii=False)
    print("\nResultados guardados en data/youtube_tito_systematic.json")

if __name__ == '__main__':
    main()
