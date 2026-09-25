from inspect_candidates import get_video_info
import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
by_id = {h['id']: h for h in cat}

print("=== Hymn #435 en Catálogo ===")
print(by_id[435]['content'][:200])

print("\n=== Video RheHRyks_QA ===")
t, d = get_video_info("RheHRyks_QA")
print("Title:", t)
print("Desc:", d[:300])

print("\n=== Video F7MUvcjslfo ===")
t2, d2 = get_video_info("F7MUvcjslfo")
print("Title:", t2)
print("Desc:", d2[:300])
