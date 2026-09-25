import json

analysis = json.load(open('data/cuadernillo_analysis.json', encoding='utf-8'))
candidates = analysis['new_hymns'][:380]

print("Title and author sample of the first 25 new hymns:")
for i, h in enumerate(candidates[:25]):
    print(f"#{i+1:03d} (p.{h['page']}) [{h.get('author')}] - {h['title']}")

print("\nTitle and author sample of the middle 25 new hymns:")
for i, h in enumerate(candidates[180:205], 180):
    print(f"#{i+1:03d} (p.{h['page']}) [{h.get('author')}] - {h['title']}")

print("\nTitle and author sample of the last 25 new hymns:")
for i, h in enumerate(candidates[355:380], 355):
    print(f"#{i+1:03d} (p.{h['page']}) [{h.get('author')}] - {h['title']}")
