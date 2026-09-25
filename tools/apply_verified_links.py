import json
from datetime import datetime, timezone
from pathlib import Path
import sys

# Import save_catalog from manage_hymns
sys.path.append('tools')
from manage_hymns import load_catalog, save_catalog

verified_links = {
    # Conjunto Villanueva
    416: "https://www.youtube.com/watch?v=PpuEIXov1ug",
    425: "https://www.youtube.com/watch?v=-F0-8wshlck",
    426: "https://www.youtube.com/watch?v=QG5KsajlIgE",
    427: "https://www.youtube.com/watch?v=72B5TwT7SNA",
    428: "https://www.youtube.com/watch?v=iJhuj5VJIyg",
    429: "https://www.youtube.com/watch?v=qO_Ym6xLYdQ",
    430: "https://www.youtube.com/watch?v=6PjxrbNraM8",
    431: "https://www.youtube.com/watch?v=fvjXX5Ivomo",
    432: "https://www.youtube.com/watch?v=g9DzrFY-czc",

    # Trío Acuña
    330: "https://www.youtube.com/watch?v=QsuQUNBpmHY",
    331: "https://www.youtube.com/watch?v=Tgzvjyamlro",
    332: "https://www.youtube.com/watch?v=GKLd67tVXDI",
    333: "https://www.youtube.com/watch?v=lwqbXgmwd88",
    334: "https://www.youtube.com/watch?v=dwZwdaf5QwQ",
    336: "https://www.youtube.com/watch?v=8T5kR_m18yk",
    341: "https://www.youtube.com/watch?v=T3coUeO5tAI",
    342: "https://www.youtube.com/watch?v=7LU9dYUf5-4",
    345: "https://www.youtube.com/watch?v=NwZ-9tOg4JM",
    347: "https://www.youtube.com/watch?v=wQTBNEtZ9Bk",

    # Trío Redención
    696: "https://www.youtube.com/watch?v=vQ5X2-7OMsY",
    698: "https://www.youtube.com/watch?v=_Aoqnsv3xCk",
    699: "https://www.youtube.com/watch?v=yeecmHc4ocU",
    708: "https://www.youtube.com/watch?v=FMZwIEFqpbI",
    712: "https://www.youtube.com/watch?v=dxnm4uJDO7k",

    # Conjunto Trigales
    301: "https://www.youtube.com/watch?v=PBNERqwgyG8",
    773: "https://www.youtube.com/watch?v=QAtheEBiFN8",
    915: "https://www.youtube.com/watch?v=Gq9xFWjfd94",
    921: "https://www.youtube.com/watch?v=7RL5uFgIKgs",
    922: "https://www.youtube.com/watch?v=k5lSF8Oalks",
}

catalog = load_catalog()
now_iso = datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")

applied_count = 0
skipped_count = 0

for h in catalog["hymns"]:
    hid = h["id"]
    if hid in verified_links:
        existing_link = h.get("link", "").strip()
        new_link = verified_links[hid]
        if existing_link:
            print(f"Skipping #{hid} '{h['title']}': Ya tiene enlace ({existing_link})")
            skipped_count += 1
        else:
            h["link"] = new_link
            h["updatedAt"] = now_iso
            applied_count += 1
            print(f"Applied to #{hid} '{h['title']}': {new_link}")

catalog["version"] = catalog.get("version", 1) + 1
save_catalog(catalog)

print(f"\nProceso finalizado:")
print(f"  - Enlaces aplicados (vacíos previamente): {applied_count}")
print(f"  - Omitidos (ya tenían enlace previo)     : {skipped_count}")
print(f"  - Versión del catálogo incrementada a   : {catalog['version']}")
