import pdfplumber
from pathlib import Path
import re

pdf_path = Path("Manual de Alabanzas Cristianas.pdf")

with pdfplumber.open(pdf_path) as pdf:
    total_pages = len(pdf.pages)
    print(f"Buscando carátulas y secciones en {total_pages} páginas...")
    
    sections = []
    for i in range(17, total_pages): # From page 18 onwards
        page = pdf.pages[i]
        cropped = page.crop((30, 25, 550, 755))
        text = cropped.extract_text() or ""
        lines = [l.strip() for l in text.split("\n") if l.strip() and not l.strip().isdigit()]
        
        # Check if page is a section divider: few lines and has keywords or all uppercase title
        if len(lines) <= 5:
            full_txt = " ".join(lines)
            sections.append((i + 1, len(lines), full_txt))
            
    print(f"Total de páginas cortas / posibles secciones: {len(sections)}")
    for s in sections:
        print(f"  Página {s[0]:3d} ({s[1]} líneas): '{s[2]}'")
