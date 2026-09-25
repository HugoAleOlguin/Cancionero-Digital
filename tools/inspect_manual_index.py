import pdfplumber
from pathlib import Path
import re

pdf_path = Path("Manual de Alabanzas Cristianas.pdf")

with pdfplumber.open(pdf_path) as pdf:
    for i in range(1, 35):
        text = pdf.pages[i].extract_text() or ""
        lines = [l.strip() for l in text.split("\n") if l.strip()]
        first_line = lines[0] if lines else "[VACÍA]"
        is_index = any("...." in l or "INDICE" in l.upper() for l in lines[:5])
        print(f"Página {i+1:2d}: is_index={is_index:1} | Líneas: {len(lines):2d} | 1ra línea: '{first_line[:60]}'")
