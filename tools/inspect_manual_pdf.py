import pdfplumber
from pathlib import Path

pdf_path = Path("Manual de Alabanzas Cristianas.pdf")

with pdfplumber.open(pdf_path) as pdf:
    total_pages = len(pdf.pages)
    print(f"Total páginas en '{pdf_path.name}': {total_pages}")
    
    # Inspect first 15 pages
    for i in range(min(15, total_pages)):
        text = pdf.pages[i].extract_text() or ""
        lines = [l.strip() for l in text.split("\n") if l.strip()]
        first_line = lines[0] if lines else "[PÁGINA EN BLANCO]"
        print(f"Página {i+1:3d}: {len(lines):2d} líneas | Inicio: '{first_line[:70]}'")

    # Inspect last 15 pages
    print("\n--- ÚLTIMAS PÁGINAS ---")
    for i in range(max(0, total_pages - 15), total_pages):
        text = pdf.pages[i].extract_text() or ""
        lines = [l.strip() for l in text.split("\n") if l.strip()]
        first_line = lines[0] if lines else "[PÁGINA EN BLANCO]"
        print(f"Página {i+1:3d}: {len(lines):2d} líneas | Inicio: '{first_line[:70]}'")
