import pdfplumber
from pathlib import Path
import re

pdf_path = Path("Manual de Alabanzas Cristianas.pdf")

with pdfplumber.open(pdf_path) as pdf:
    print("=== EXTRAYENDO ÍNDICE (PÁGINAS 3 A 17) ===")
    index_items = []
    for i in range(2, 17):
        text = pdf.pages[i].extract_text() or ""
        lines = [l.strip() for l in text.split("\n") if l.strip()]
        for l in lines:
            if "INDICE" in l.upper(): continue
            # match title and page number: e.g. "TITULO ........ 123"
            m = re.search(r'^(.*?)\.{2,}\s*(\d+)$', l)
            if m:
                index_items.append((m.group(1).strip(), int(m.group(2))))
            else:
                # Could be a section header without dots
                index_items.append((l.strip(), None))

    print(f"Total entradas en el índice: {len(index_items)}")
    
    sections = [it for it in index_items if it[1] is None or "CONJUNTO" in it[0] or "TRIO" in it[0] or "DUO" in it[0]]
    print("\nSecciones o conjuntos detectados en el índice:")
    for s in sections[:30]:
        print(" ", s)

    print("\nPrimeras 20 canciones en el índice:")
    for it in index_items[:20]:
        print(f"  {it[0]} -> p.{it[1]}")
