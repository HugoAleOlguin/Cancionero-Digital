import pdfplumber
from pathlib import Path

pdf_path = Path("Cuadernillo de Alabanzas Cristianas.pdf")

with pdfplumber.open(pdf_path) as pdf:
    for page_num in [19, 20, 50, 100, 200, 350, 500, 650, 750]:
        page = pdf.pages[page_num - 1]
        text = page.extract_text() or ""
        lines = [l.strip() for l in text.split("\n") if l.strip()]
        first_few = lines[:3]
        last_few = lines[-2:]
        print(f"Pág {page_num:3d} (total líneas={len(lines)}):")
        print(f"   Primeras: {first_few}")
        print(f"   Últimas:  {last_few}")
