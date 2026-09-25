import json

cat_data = json.load(open('data/catalog.json', encoding='utf-8'))
results = json.load(open('data/youtube_villanueva_results.json', encoding='utf-8'))
res_by_id = {r['id']: r for r in results}

hymns = [h for h in cat_data['hymns'] if 'villanueva' in h.get('author', '').lower() and not h.get('isDeleted', False)]

print(f"Total Alabanzas Villanueva en Catálogo: {len(hymns)}")
print("=" * 80)

for h in hymns:
    hid = h['id']
    title = h['title']
    curr_link = h.get('link', '')
    r = res_by_id.get(hid)
    bm = r.get('best_match') if r else None
    
    print(f"ID #{hid:3d}: {title}")
    print(f"  Link Actual : {curr_link if curr_link else '[SIN LINK]'}")
    if bm:
        print(f"  Nuevo Match : {bm['url']} (Score: {bm['score']}) - '{bm['videoTitle']}' [{bm['channel']}]")
    else:
        print(f"  Nuevo Match : [NO FOUND]")
    print("-" * 60)
