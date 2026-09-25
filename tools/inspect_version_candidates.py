import json

with open("data/cuadernillo_analysis.json", "r", encoding="utf-8") as f:
    report = json.load(f)

vc = report["version_candidates"]
print(f"Total candidatas a versión 2: {len(vc)}")

for i, c in enumerate(vc, 1):
    note = f" (Nota: '{c['version_note']}')" if c.get("version_note") else ""
    print(f"\n{i}. PDF Pág {c['pdf_page']}: '{c['pdf_title']}' vs Catálogo #{c['catalog_id']} '{c['catalog_title']}'{note}")
    print(f"   Similitud: {c['similarity']:.1%} | Diferencia de caracteres: {c['char_diff']:+d}")
    print(f"   Muestra PDF: {c['pdf_sample'].replace(chr(10), ' ')[:100]}...")
