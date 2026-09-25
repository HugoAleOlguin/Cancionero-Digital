import json

results = json.load(open('data/youtube_tito_systematic.json', encoding='utf-8'))
cat = {h['id']: h for h in json.load(open('data/catalog.json', encoding='utf-8'))['hymns']}

print("=== EXACT MATCHES TITO ABARCA ===")
exact_matches = []
for r in results:
    bm = r.get('best_match')
    if bm and bm.get('has_artist') and (r['status'] == 'EXACT_MATCH' or bm['score'] >= 80):
        hid = r['id']
        h = cat[hid]
        print(f"#{hid} '{h['title']}' -> '{bm['videoTitle']}' ({bm['url']}) [Sc: {bm['score']}]")
        print(f"   Letra: {h['content'][:60]}...")
        exact_matches.append((hid, h['title'], bm['url'], bm['videoTitle']))

print(f"\nTotal Exact Matches: {len(exact_matches)}")
