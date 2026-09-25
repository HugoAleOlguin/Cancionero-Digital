import json
from datetime import datetime, timezone
import sys

sys.path.append('tools')
from manage_hymns import load_catalog, save_catalog

gonzalez_verified_batch = {
    586: "https://www.youtube.com/watch?v=rr7bqV6YkCM",
    587: "https://www.youtube.com/watch?v=2Hc83LQ8xIw",
    590: "https://www.youtube.com/watch?v=oC4hwI9ebcs",
    592: "https://www.youtube.com/watch?v=MeYd15IYY8A",
    593: "https://www.youtube.com/watch?v=NcfwdMwYrQA",
    598: "https://www.youtube.com/watch?v=TlIvCUTDq5A",
    600: "https://www.youtube.com/watch?v=FNU4NixnhQs",
    606: "https://www.youtube.com/watch?v=AW_GLyD7U3c",
    609: "https://www.youtube.com/watch?v=Ung1OVm9804",
    610: "https://www.youtube.com/watch?v=__JH_rIrc4Y",
    612: "https://www.youtube.com/watch?v=GjG4Tj5SEQ4",
    614: "https://www.youtube.com/watch?v=T2mbgP5hoOQ",
    619: "https://www.youtube.com/watch?v=_i5UFgnb4IY",
    621: "https://www.youtube.com/watch?v=7-4uT8ycTQU",
    623: "https://www.youtube.com/watch?v=qR6P_vLcpDo",
    657: "https://www.youtube.com/watch?v=yFYM07KEChI",
}

catalog = load_catalog()
now_iso = datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")

applied = 0
for h in catalog["hymns"]:
    hid = h["id"]
    if hid in gonzalez_verified_batch:
        if not h.get("link", "").strip():
            h["link"] = gonzalez_verified_batch[hid]
            h["updatedAt"] = now_iso
            applied += 1
            print(f"APLICADO: #{hid} '{h['title']}' -> {gonzalez_verified_batch[hid]}")

catalog["version"] = catalog.get("version", 1) + 1
save_catalog(catalog)

print(f"\nAplicados {applied} enlaces de Los Gonzales. Nueva versión: {catalog['version']}")
