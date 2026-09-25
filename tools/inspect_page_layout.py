import pdfplumber
from pathlib import Path

pdf_path = Path("Manual de Alabanzas Cristianas.pdf")

with pdfplumber.open(pdf_path) as pdf:
    for page_num in [19, 20, 21, 50, 100, 200, 300, 400]:
        page = pdf.pages[page_num - 1]
        text = page.extract_text() or ""
        lines = [l.strip() for l in text.split("\n") if l.strip()]
        print(f"=== PÁGINA {page_num} ({len(lines)} líneas) ===")
        print("Primeras 6 líneas:")
        for l in lines[:6]:
            print("  ", l)
        print("Últimas 4 líneas:")
        for l in lines[-4:]:
            print("  ", l)
        print("-" * 50)
