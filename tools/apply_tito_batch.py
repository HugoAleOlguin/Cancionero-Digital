import json
from datetime import datetime, timezone
import sys

sys.path.append('tools')
from manage_hymns import load_catalog, save_catalog

tito_verified_batch = {
    433: "https://www.youtube.com/watch?v=VsmjmQd5GcQ",
    441: "https://www.youtube.com/watch?v=Z2scHOqUzek",
    443: "https://www.youtube.com/watch?v=pcOlo3axiN0",
    445: "https://www.youtube.com/watch?v=UZF2pC_xhkI",
    452: "https://www.youtube.com/watch?v=bkD8IpacGY8",
    455: "https://www.youtube.com/watch?v=C3087InnZ-8",
    458: "https://www.youtube.com/watch?v=ffKWlq23gE8",
    460: "https://www.youtube.com/watch?v=3WHjn-JPc8E",
    471: "https://www.youtube.com/watch?v=13hRzONC5NA",
    473: "https://www.youtube.com/watch?v=GjPQjU0YYo0",
    544: "https://www.youtube.com/watch?v=aS03nXNiBbY",
    545: "https://www.youtube.com/watch?v=YjpmN9nwn-Y",
    547: "https://www.youtube.com/watch?v=IYq_Y56kcvY",
    548: "https://www.youtube.com/watch?v=F7MUvcjslfo",
    556: "https://www.youtube.com/watch?v=BqvREFt1dOE",
}

catalog = load_catalog()
now_iso = datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")

applied = 0
for h in catalog["hymns"]:
    hid = h["id"]
    if hid in tito_verified_batch:
        if not h.get("link", "").strip():
            h["link"] = tito_verified_batch[hid]
            h["updatedAt"] = now_iso
            applied += 1
            print(f"APLICADO: #{hid} '{h['title']}' -> {tito_verified_batch[hid]}")

catalog["version"] = catalog.get("version", 1) + 1
save_catalog(catalog)

print(f"\nAplicados {applied} enlaces de Tito Abarca. Nueva versión: {catalog['version']}")
