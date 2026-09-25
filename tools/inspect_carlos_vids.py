import subprocess
import json

vids = ['pcOlo3axiN0', '_eYCrRKu-KU', 'ffKWlq23gE8', 'VsmjmQd5GcQ', 'eQg1XyKSG_Y']

for vid in vids:
    p = subprocess.run(['yt-dlp', '--skip-download', '-j', f'https://www.youtube.com/watch?v={vid}'], capture_output=True, text=True, encoding='utf-8')
    try:
        d = json.loads(p.stdout)
        print(f"\n=== [{vid}] {d.get('title')} ===")
        print("Channel:", d.get('channel'))
        print("Desc:", repr(d.get('description', ''))[:300])
    except Exception as e:
        print(f"Error {vid}: {e}")
