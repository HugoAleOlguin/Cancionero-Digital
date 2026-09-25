import json

results = json.load(open('data/youtube_villanueva_results.json', encoding='utf-8'))
cat = {h['id']: h for h in json.load(open('data/catalog.json', encoding='utf-8'))['hymns']}

print("--- REVISIÓN DETALLADA DE DUDOSAS / MENOS DE 100 PTS ---")
for r in results:
    bm = r.get('best_match')
    score = bm['score'] if bm else 0
    if score < 100:
        hid = r['id']
        h = cat[hid]
        title = h['title']
        print(f"#{hid} {title}")
        print(f"  Letra inicial: {h['content'][:60]}...")
        if bm:
            print(f"  Best Match ({score} pts): {bm['videoTitle']} | Ch: {bm['channel']} | {bm['url']}")
        else:
            print(f"  Sin match")
        for idx, c in enumerate(r.get('all_candidates', [])[:3], 1):
            print(f"    cand {idx}: [{c['score']}] {c['videoTitle']} ({c['channel']})")
        print()
