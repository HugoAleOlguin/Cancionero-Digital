from inspect_candidates import get_video_info
import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
by_id = {h['id']: h for h in cat}

print("=== Hymn #437 en Catálogo ===")
print(by_id[437]['title'], repr(by_id[437]['content'][:160]))
t1, d1 = get_video_info("k-TKjct8b5c")
print("Video k-TKjct8b5c:", t1)
print("Desc:", d1[:200])

print("\n=== Hymn #583 en Catálogo ===")
print(by_id[583]['title'], repr(by_id[583]['content'][:160]))
t2, d2 = get_video_info("B8bniWqVQnA")
print("Video B8bniWqVQnA:", t2)
print("Desc:", d2[:200])
