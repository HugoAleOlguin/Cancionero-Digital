import json
import collections

analysis = json.load(open('data/manual_analysis.json', encoding='utf-8'))

print("=== 1. AUTORES ENRIQUECIBLES (15 casos) ===")
enrichable = [em for em in analysis['exact_matches'] if em.get('author_can_enrich')]
for e in enrichable:
    print(f"  CAT #{e['catalog_id']:3d} '{e['catalog_title']}' (actual: '{e['catalog_author']}') -> PDF p.{e['manual_page']}: '{e['manual_author']}'")

print(f"\n=== 2. DISTRIBUCIÓN DE AUTORES EN LAS 136 ALABANZAS NUEVAS ===")
new_hymns = analysis['new_hymns']
author_counts = collections.Counter(h.get('author', 'Varios') for h in new_hymns)
for a, count in author_counts.most_common():
    print(f"  - {a}: {count} alabanzas")

print(f"\n=== 3. MUESTRA DE VARIANTES / CANDIDATAS A MULTIVERSIÓN (70 casos) ===")
variants = analysis['version_candidates']
for v in variants[:10]:
    print(f"  CAT #{v['catalog_id']:3d} '{v['catalog_title']}' vs MANUAL p.{v['manual_page']} '{v['manual_title']}' (sim={v['similarity']:.2f})")

print(f"\n=== 4. MUESTRA DE ALABANZAS NUEVAS (Primeras 15) ===")
for nh in new_hymns[:15]:
    print(f"  Pág {nh['page']:3d} [{nh['author']}] - {nh['title']} ({nh['char_count']} chars, {nh['line_count']} líneas)")
