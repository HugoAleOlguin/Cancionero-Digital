import pdfplumber
from pathlib import Path

pdf_path = Path("Cuadernillo de Alabanzas Cristianas.pdf")

with pdfplumber.open(pdf_path) as pdf:
    for page_num in range(21, 27):
        page = pdf.pages[page_num - 1]
        text = page.extract_text() or ""
        print(f"\n{'='*60}\nPÁGINA {page_num}:\n{'='*60}")
        print(text)
