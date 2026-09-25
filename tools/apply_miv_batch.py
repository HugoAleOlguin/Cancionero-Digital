import json
from datetime import datetime, timezone
import sys

sys.path.append('tools')
from manage_hymns import load_catalog, save_catalog

miv_batch = {
    397: "https://www.youtube.com/watch?v=mBfgA6pBAcs",
    579: "https://www.youtube.com/watch?v=RIzjb1-xHqc",
    647: "https://www.youtube.com/watch?v=SizC1tr0Meo",
    686: "https://www.youtube.com/watch?v=4ADl8OwXAPg",
    730: "https://www.youtube.com/watch?v=8u0uOGSKZ08",
    826: "https://www.youtube.com/watch?v=EWlgf2KUNM4",
    873: "https://www.youtube.com/watch?v=ZNlKLR5hE3U",
}

catalog = load_catalog()
now_iso = datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")

applied = 0
for h in catalog["hymns"]:
    hid = h["id"]
    if hid in miv_batch:
        if not h.get("link", "").strip():
            h["link"] = miv_batch[hid]
            h["updatedAt"] = now_iso
            applied += 1
            print(f"APLICADO: #{hid} '{h['title']}' -> {miv_batch[hid]}")

catalog["version"] = catalog.get("version", 1) + 1
save_catalog(catalog)

print(f"\nAplicados {applied} enlaces. Nueva versión: {catalog['version']}")
