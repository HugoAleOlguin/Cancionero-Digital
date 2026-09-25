import json
from datetime import datetime, timezone
import sys

sys.path.append('tools')
from manage_hymns import load_catalog, save_catalog

new_verified_links = {
    # Cantores Unidos del NOA
    348: "https://www.youtube.com/watch?v=A2NTkepObR4",
    351: "https://www.youtube.com/watch?v=r_Lf36FrXKI",
    354: "https://www.youtube.com/watch?v=VlHAtN4XxPE",
    355: "https://www.youtube.com/watch?v=-rVoEQr1-RY",
    356: "https://www.youtube.com/watch?v=wNbHFshN14c",
    357: "https://www.youtube.com/watch?v=V63krLIP6oY",
    358: "https://www.youtube.com/watch?v=EqGC1qiI2ws",
    359: "https://www.youtube.com/watch?v=Y7ZTWd_Ow5g",
    360: "https://www.youtube.com/watch?v=hU9bR70AJew",
    361: "https://www.youtube.com/watch?v=aQSdQTEOiiQ",
    362: "https://www.youtube.com/watch?v=Qnhw8flQNf4",
    363: "https://www.youtube.com/watch?v=THkm0nZAAE0",
    368: "https://www.youtube.com/watch?v=g2W9hfTCnVM",
    369: "https://www.youtube.com/watch?v=y2C7TO3w5YA",

    # Conjunto Central de Tucumán
    379: "https://www.youtube.com/watch?v=DUxhd-5NULI",
    385: "https://www.youtube.com/watch?v=Q3RVXycu6aI",
    391: "https://www.youtube.com/watch?v=aQvDFQXdNO8",
    395: "https://www.youtube.com/watch?v=ql7ODggPTCE",
    396: "https://www.youtube.com/watch?v=WS9_SFhggBg",
    399: "https://www.youtube.com/watch?v=jcCvDhiXleE",
    401: "https://www.youtube.com/watch?v=6SFzTH1zQy4",
    405: "https://www.youtube.com/watch?v=LXtAGu2ihqM",
    407: "https://www.youtube.com/watch?v=xw6gLO1Ge6w",
    410: "https://www.youtube.com/watch?v=BMLpq7UF6_0",
    414: "https://www.youtube.com/watch?v=YMCJpMRB1B4",

    # Trío Lazarte
    720: "https://www.youtube.com/watch?v=SuakSQEEV3g",

    # Conjunto de Dorrego
    62: "https://www.youtube.com/watch?v=dHG_Lteubtc",
    66: "https://www.youtube.com/watch?v=odfhxyRf_XA",
    70: "https://www.youtube.com/watch?v=XCDmLV3F22c",

    # Conjunto Amigo Fiel
    326: "https://www.youtube.com/watch?v=Gf202PAaswQ",
}

catalog = load_catalog()
now_iso = datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")

applied = 0
skipped = 0

for h in catalog["hymns"]:
    hid = h["id"]
    if hid in new_verified_links:
        existing = h.get("link", "").strip()
        if existing:
            print(f"OMITIDO: #{hid} '{h['title']}' ya poseía enlace: {existing}")
            skipped += 1
        else:
            h["link"] = new_verified_links[hid]
            h["updatedAt"] = now_iso
            applied += 1
            print(f"APLICADO: #{hid} '{h['title']}' -> {new_verified_links[hid]}")

catalog["version"] = catalog.get("version", 1) + 1
save_catalog(catalog)

print(f"\nResultado:")
print(f"  - Nuevos enlaces aplicados : {applied}")
print(f"  - Omitidos por tener enlace: {skipped}")
print(f"  - Nueva versión de catálogo: {catalog['version']}")
