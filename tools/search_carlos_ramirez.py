import sys
import os
sys.path.append(os.path.dirname(__file__))
from search_tito_phrases import search
from inspect_candidates import get_video_info

queries = [
    'Carlos Ramirez "Tito abarca"',
    'Carlos Ramírez "Tito abarca"',
    '"Tito abarca" Carlos Ramirez',
    '"Tito abarca" Carlos Ramírez',
]

all_vids = {}
for q in queries:
    for vid, title, ch in search(q):
        if 'carlos' in ch.lower() or 'ramirez' in ch.lower() or 'ramírez' in ch.lower():
            all_vids[vid] = (title, ch)

print(f"Total videos from Carlos Ramirez found: {len(all_vids)}")
for vid, (title, ch) in all_vids.items():
    print(f"  [{vid}] {title} | {ch}")
