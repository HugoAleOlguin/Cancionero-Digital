import pdfplumber
from pathlib import Path

pdf_path = Path("Manual de Alabanzas Cristianas.pdf")

with pdfplumber.open(pdf_path) as pdf:
    page = pdf.pages[18] # Page 19 (0-indexed 18)
    print(f"Page width: {page.width}, height: {page.height}")
    words = page.extract_words()
    print(f"Total words: {len(words)}")
    
    # Check coordinates of the vertical watermark words vs song words
    margin_words = [w for w in words if w['x0'] > page.width - 60 or w['x0'] < 60]
    print(f"Words near margins (<60 or >{page.width-60}): {len(margin_words)}")
    for mw in margin_words[:20]:
        print(f"  x0={mw['x0']:.1f}, top={mw['top']:.1f}, text='{mw['text']}'")
        
    print("\nCentral content bbox:")
    # Let's crop the page excluding the margin!
    crop_box = (40, 30, page.width - 40, page.height - 30)
    cropped = page.crop(crop_box)
    cropped_text = cropped.extract_text()
    print("Cropped text sample:")
    print(cropped_text[:300])
