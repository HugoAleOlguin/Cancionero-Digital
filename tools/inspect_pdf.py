import pdfplumber
from pathlib import Path

pdf_path = Path("Cuadernillo de Alabanzas Cristianas.pdf")

with pdfplumber.open(pdf_path) as pdf:
    print(f"Total de páginas: {len(pdf.pages)}")
    
    # Inspect first 5 pages
    for i in range(min(8, len(pdf.pages))):
        page = pdf.pages[i]
        text = page.extract_text() or ""
        print(f"\n{'='*50}\nPÁGINA {i+1} (ancho={page.width}, alto={page.height}):\n{'='*50}")
        print(text[:1000]) # First 1000 chars of each page

