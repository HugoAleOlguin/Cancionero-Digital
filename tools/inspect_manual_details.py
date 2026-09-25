import pdfplumber
from pathlib import Path

pdf_path = Path("Manual de Alabanzas Cristianas.pdf")

with pdfplumber.open(pdf_path) as pdf:
    # Pagina 18
    print("=== PÁGINA 18 ===")
    print(pdf.pages[17].extract_text()[:400])
    
    # Pagina 19
    print("\n=== PÁGINA 19 ===")
    print(pdf.pages[18].extract_text()[:400])
    
    # Pagina 489, 490, 491
    print("\n=== PÁGINA 489 ===")
    print(pdf.pages[488].extract_text()[:300])
    print("\n=== PÁGINA 490 ===")
    print(pdf.pages[489].extract_text()[:300])
    print("\n=== PÁGINA 491 ===")
    print(pdf.pages[490].extract_text()[:300])
