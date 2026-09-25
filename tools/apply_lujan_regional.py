import json
from datetime import datetime, timezone
import sys

sys.path.append('tools')
from manage_hymns import load_catalog, save_catalog

regional_and_lujan_batch = {
    # Bariloche
    43: "https://www.youtube.com/watch?v=yMJo6GL4rpo",
    
    # Mansilla
    911: "https://www.youtube.com/watch?v=G27K_Gk1RqA",
    
    # Luján de Cuyo Topic
    448: "https://www.youtube.com/watch?v=NhCgt5ZAslo",
    553: "https://www.youtube.com/watch?v=U7IoQc9_rig",
    560: "https://www.youtube.com/watch?v=tHg9S6RcG0U",
    561: "https://www.youtube.com/watch?v=ylN4cIuykhg",
    562: "https://www.youtube.com/watch?v=Z8Zpf78rh_k",
    563: "https://www.youtube.com/watch?v=-Xf6gyby63g",
    564: "https://www.youtube.com/watch?v=WTRMqnUvUAk",
    565: "https://www.youtube.com/watch?v=KJfy6BIcOT0",
    566: "https://www.youtube.com/watch?v=4ScEpzfSZnc",
    567: "https://www.youtube.com/watch?v=N7zh3L2fQu4",
    568: "https://www.youtube.com/watch?v=AGW79G8uPcQ",
    571: "https://www.youtube.com/watch?v=TiCzJ5x8pZ4",
    574: "https://www.youtube.com/watch?v=oUmFUTxnzZg",
    577: "https://www.youtube.com/watch?v=2JiQNk9ywZQ",
    578: "https://www.youtube.com/watch?v=EmvY4r-V6ZE",
    580: "https://www.youtube.com/watch?v=y71zNuhMqz8",
}

catalog = load_catalog()
now_iso = datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")

applied = 0
for h in catalog["hymns"]:
    hid = h["id"]
    if hid in regional_and_lujan_batch:
        if not h.get("link", "").strip():
            h["link"] = regional_and_lujan_batch[hid]
            h["updatedAt"] = now_iso
            applied += 1
            print(f"APLICADO: #{hid} '{h['title']}' -> {regional_and_lujan_batch[hid]}")

catalog["version"] = catalog.get("version", 1) + 1
save_catalog(catalog)

print(f"\nAplicados {applied} enlaces. Nueva versión: {catalog['version']}")
