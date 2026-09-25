import pdfplumber
from pathlib import Path

pdf_path = Path("Manual de Alabanzas Cristianas.pdf")

with pdfplumber.open(pdf_path) as pdf:
    for page_num in [19, 20, 21, 50, 100, 200, 300, 400, 480]:
        page = pdf.pages[page_num - 1]
        # Crop: left 30, top 25, right 550, bottom 755
        cropped = page.crop((30, 25, 550, 755))
        text = cropped.extract_text() or ""
        lines = [l.strip() for l in text.split("\n") if l.strip()]
        title = lines[0] if lines else "[VACÍA]"
        last_line = lines[-1] if lines else ""
        print(f"Página {page_num:3d}: Título: '{title}' | Líneas: {len(lines):2d} | Última: '{last_line[:50]}'")
