import pdfplumber
import json
import re

ext = json.load(open('data/cuadernillo_extracted.json', encoding='utf-8'))
captured_pages = set(x['page'] for x in ext)
all_song_pages = set(range(21, 755))
missing_pages = sorted(all_song_pages - captured_pages)

songs_in_missing = []

with pdfplumber.open('Cuadernillo de Alabanzas Cristianas.pdf') as pdf:
    for p in missing_pages:
        text = pdf.pages[p-1].extract_text() or ''
        lines = [l.strip() for l in text.split("\n") if l.strip()]
        # Filter lines that are just the page number
        content_lines = [l for l in lines if not re.match(r'^\d+$', l)]
        joined = " ".join(content_lines)
        if len(content_lines) > 2 and len(joined) > 35:
            songs_in_missing.append((p, content_lines))
        else:
            print(f"Página {p}: Divisor/Blanco ({content_lines})")

print(f"\nTotal de páginas faltantes con contenido real: {len(songs_in_missing)}")
for p, lines in songs_in_missing:
    print(f"\nPág {p}: Título potencial: '{lines[0]}'")
    print(f"   Líneas: {lines[:4]}")
