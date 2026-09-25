import urllib.request
import urllib.parse
import json
import re
import time
import unicodedata

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
            
            # Extract video entries: videoId, title, and channel
            # In YouTube JSON inside HTML:
            # "videoRenderer":{"videoId":"...","thumbnail":...,"title":{"runs":[{"text":"..."}]
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
        f"Conjunto Villanueva {title}",
        f"Villanueva {title}"
    ]
    
    # Clean title keywords
    t_words = [w for w in norm(title).split() if len(w) > 2 and w not in ["que", "del", "las", "los", "por", "para", "con", "una", "uno"]]
    
    candidates = []
    seen_vids = set()
    
    for q in queries:
        vids = search_youtube_videos(q)
        for v in vids:
            if v["videoId"] not in seen_vids:
                seen_vids.add(v["videoId"])
                candidates.append(v)
        if len(candidates) >= 3:
            break
        time.sleep(0.3)
        
    scored = []
    for c in candidates:
        v_title_norm = norm(c["videoTitle"])
        v_channel_norm = norm(c["channel"])
        
        has_artist = ("villanueva" in v_title_norm or "villanueva" in v_channel_norm)
        
        # Word overlap
        matched_words = sum(1 for w in t_words if w in v_title_norm)
        ratio = matched_words / len(t_words) if t_words else 0
        
        score = 0
        if has_artist:
            score += 50
        score += int(ratio * 50)
        
        # Bonus for exact title substring
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
        if best["score"] >= 80:
            status = "EXACT_MATCH"
        elif best["score"] >= 50:
            status = "HIGH_CONFIDENCE"
        elif best["score"] >= 30:
            status = "NEEDS_REVIEW"
            
    return {
        "id": hymn["id"],
        "title": title,
        "author": hymn.get("author", "Conjunto Villanueva"),
        "status": status,
        "best_match": best,
        "all_candidates": scored[:3]
    }

def main():
    catalog_data = json.load(open("data/catalog.json", encoding="utf-8"))
    catalog = catalog_data["hymns"]
    villanueva_hymns = [h for h in catalog if "villanueva" in h.get("author", "").lower() and not h.get("isDeleted", False)]
    
    print(f"Iniciando búsqueda y verificación en YouTube para {len(villanueva_hymns)} alabanzas de Conjunto Villanueva...")
    
    results = []
    for idx, h in enumerate(villanueva_hymns, 1):
        print(f"[{idx:2d}/{len(villanueva_hymns):2d}] Procesando #{h['id']} '{h['title']}'...")
        res = evaluate_song(h)
        results.append(res)
        best = res["best_match"]
        if best and res["status"] in ["EXACT_MATCH", "HIGH_CONFIDENCE"]:
            print(f"   -> [{res['status']}] '{best['videoTitle']}' ({best['url']})")
        elif best:
            print(f"   -> [{res['status']}] '{best['videoTitle']}' (Score: {best['score']})")
        else:
            print(f"   -> [NO_FOUND]")
        time.sleep(0.5)
        
    out_path = "data/youtube_villanueva_results.json"
    with open(out_path, "w", encoding="utf-8") as f:
        json.dump(results, f, indent=2, ensure_ascii=False)
    print(f"\nResultados guardados en {out_path}")
    
    # Resumen
    statuses = [r["status"] for r in results]
    print("\n--- RESUMEN FINAL ---")
    print(f"Coincidencias Exactas: {statuses.count('EXACT_MATCH')}")
    print(f"Alta Confianza: {statuses.count('HIGH_CONFIDENCE')}")
    print(f"Requiere Revisión: {statuses.count('NEEDS_REVIEW')}")
    print(f"No Encontrados: {statuses.count('NO_FOUND')}")

if __name__ == '__main__':
    main()
