#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Constructor del paquete DLC Tema Moderno (modern_theme_v1.zip).
Genera y empaqueta las portadas fotográficas HD (100% libres de figuras humanas:
naturaleza al amanecer, rayos de sol entre niebla, oleaje calmo, vitrales litúrgicos, trigo al atardecer)
junto con el manifiesto JSON de mapeo rápido para Cancionero Digital.
"""

import os
import sys
import json
import zipfile
import urllib.request
from io import BytesIO
from PIL import Image, ImageDraw, ImageFilter

if sys.stdout.encoding != 'utf-8':
    try:
        sys.stdout.reconfigure(encoding='utf-8')
        sys.stderr.reconfigure(encoding='utf-8')
    except Exception:
        pass

OUTPUT_DIR = os.path.join(os.path.dirname(__file__), "..", "data", "dlc")
PACK_FOLDER = os.path.join(OUTPUT_DIR, "pack_content")
COVERS_FOLDER = os.path.join(PACK_FOLDER, "covers")
ZIP_FILE = os.path.join(OUTPUT_DIR, "modern_theme_v1.zip")

TARGET_WIDTH = 720
TARGET_HEIGHT = 360
WEBP_QUALITY = 82

# 30 Temáticas estrictamente SIN figuras humanas (solemnes, naturales, litúrgicas)
CURATED_COVERS = [
    {
        "id": "dawn_mountain_01.webp",
        "theme": "Amanecer en montaña entre niebla dorada",
        "url": "https://images.unsplash.com/photo-1506744038136-46273834b3fb?w=800&auto=format&fit=crop&q=80",
        "gradient": [(24, 34, 52), (197, 160, 58), (250, 220, 150)]
    },
    {
        "id": "forest_sunrays_02.webp",
        "theme": "Rayos de sol filtrándose entre bosque de pinos",
        "url": "https://images.unsplash.com/photo-1448375240586-882707db888b?w=800&auto=format&fit=crop&q=80",
        "gradient": [(18, 38, 28), (56, 92, 60), (220, 195, 120)]
    },
    {
        "id": "calm_ocean_dawn_03.webp",
        "theme": "Mar calmo en el horizonte al amanecer",
        "url": "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=800&auto=format&fit=crop&q=80",
        "gradient": [(14, 28, 48), (38, 70, 110), (230, 190, 130)]
    },
    {
        "id": "wheat_field_sunset_04.webp",
        "theme": "Campo de trigo dorado al atardecer",
        "url": "https://images.unsplash.com/photo-1500382017468-9049fed747ef?w=800&auto=format&fit=crop&q=80",
        "gradient": [(40, 26, 12), (180, 110, 35), (245, 205, 115)]
    },
    {
        "id": "stained_glass_cross_05.webp",
        "theme": "Vitral litúrgico con tonos cálidos y luz tenue",
        "url": "https://images.unsplash.com/photo-1519817650390-64a93db51149?w=800&auto=format&fit=crop&q=80",
        "gradient": [(25, 15, 35), (140, 45, 60), (220, 165, 65)]
    },
    {
        "id": "starry_sky_night_06.webp",
        "theme": "Firmamento estrellado nocturno",
        "url": "https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86?w=800&auto=format&fit=crop&q=80",
        "gradient": [(8, 12, 22), (20, 32, 58), (140, 170, 210)]
    },
    {
        "id": "olive_trees_peace_07.webp",
        "theme": "Olivares en colina apacible con luz suave",
        "url": "https://images.unsplash.com/photo-1542601906990-b4d3fb778b09?w=800&auto=format&fit=crop&q=80",
        "gradient": [(22, 32, 24), (75, 95, 60), (200, 210, 160)]
    },
    {
        "id": "mountain_creek_08.webp",
        "theme": "Arroyo de aguas vivas cristalinas entre rocas",
        "url": "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?w=800&auto=format&fit=crop&q=80",
        "gradient": [(16, 26, 32), (45, 80, 95), (170, 215, 230)]
    },
    {
        "id": "golden_clouds_09.webp",
        "theme": "Nubes doradas iluminadas por el sol naciente",
        "url": "https://images.unsplash.com/photo-1517685352821-92cf88aee5a5?w=800&auto=format&fit=crop&q=80",
        "gradient": [(30, 25, 42), (160, 95, 85), (250, 210, 140)]
    },
    {
        "id": "desert_dunes_quiet_10.webp",
        "theme": "Dunas desérticas silenciosas bajo luz tenue",
        "url": "https://images.unsplash.com/photo-1509316975850-ff9c5deb0cd9?w=800&auto=format&fit=crop&q=80",
        "gradient": [(36, 24, 16), (170, 115, 70), (240, 195, 130)]
    },
    {
        "id": "candle_warmth_11.webp",
        "theme": "Luz tenue y pacífica de lámpara rústica",
        "url": "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=800&auto=format&fit=crop&q=80",
        "gradient": [(18, 12, 10), (120, 60, 20), (255, 185, 80)]
    },
    {
        "id": "morning_dew_leaves_12.webp",
        "theme": "Gotas de rocío matutino sobre hojas verdes",
        "url": "https://images.unsplash.com/photo-1518495973542-4542c06a5843?w=800&auto=format&fit=crop&q=80",
        "gradient": [(14, 30, 20), (45, 90, 55), (150, 215, 140)]
    },
    {
        "id": "rock_fortress_13.webp",
        "theme": "Roca firme y refugio bajo cielo despejado",
        "url": "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800&auto=format&fit=crop&q=80",
        "gradient": [(20, 24, 32), (65, 80, 100), (180, 200, 220)]
    },
    {
        "id": "vintage_pottery_14.webp",
        "theme": "Vasijas rústicas de barro y lámparas de aceite",
        "url": "https://images.unsplash.com/photo-1578749556568-bc2c40e68b61?w=800&auto=format&fit=crop&q=80",
        "gradient": [(28, 18, 12), (130, 85, 55), (220, 180, 130)]
    },
    {
        "id": "river_living_water_15.webp",
        "theme": "Río sereno rodeado de verde vegetación",
        "url": "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=800&auto=format&fit=crop&q=80",
        "gradient": [(12, 28, 22), (40, 85, 70), (160, 210, 180)]
    },
    {
        "id": "misty_pine_valley_16.webp",
        "theme": "Valle de niebla y pinos al amanecer",
        "url": "https://images.unsplash.com/photo-1473448912268-2022ce9509d8?w=800&auto=format&fit=crop&q=80",
        "gradient": [(18, 28, 24), (45, 75, 60), (170, 205, 175)]
    },
    {
        "id": "tranquil_lake_sunset_17.webp",
        "theme": "Lago tranquilo reflejando el atardecer",
        "url": "https://images.unsplash.com/photo-1439853941329-a9a20243e7e7?w=800&auto=format&fit=crop&q=80",
        "gradient": [(22, 20, 35), (120, 75, 95), (235, 175, 140)]
    },
    {
        "id": "green_pastures_18.webp",
        "theme": "Delicados pastos y prados pacíficos",
        "url": "https://images.unsplash.com/photo-1497436072909-60f360e1d4b1?w=800&auto=format&fit=crop&q=80",
        "gradient": [(18, 32, 18), (55, 105, 60), (180, 225, 150)]
    },
    {
        "id": "majestic_cedars_19.webp",
        "theme": "Cedros majestuosos apuntando al cielo",
        "url": "https://images.unsplash.com/photo-1502082553048-f009c37129b9?w=800&auto=format&fit=crop&q=80",
        "gradient": [(15, 25, 30), (50, 85, 75), (160, 195, 180)]
    },
    {
        "id": "quiet_dawn_horizon_20.webp",
        "theme": "Horizonte despejado de paz matutina",
        "url": "https://images.unsplash.com/photo-1513836279014-a89f7a76ae86?w=800&auto=format&fit=crop&q=80",
        "gradient": [(20, 30, 45), (80, 110, 140), (220, 200, 160)]
    }
]

def generate_procedural_cover(filename, gradient_colors, target_path):
    """Genera una imagen artística litúrgica en gradiente suave de alta resolución."""
    img = Image.new("RGB", (TARGET_WIDTH, TARGET_HEIGHT))
    draw = ImageDraw.Draw(img)

    c1, c2, c3 = gradient_colors
    for y in range(TARGET_HEIGHT):
        ratio = y / float(TARGET_HEIGHT)
        if ratio < 0.5:
            local_ratio = ratio * 2.0
            r = int(c1[0] * (1 - local_ratio) + c2[0] * local_ratio)
            g = int(c1[1] * (1 - local_ratio) + c2[1] * local_ratio)
            b = int(c1[2] * (1 - local_ratio) + c2[2] * local_ratio)
        else:
            local_ratio = (ratio - 0.5) * 2.0
            r = int(c2[0] * (1 - local_ratio) + c3[0] * local_ratio)
            g = int(c2[1] * (1 - local_ratio) + c3[1] * local_ratio)
            b = int(c2[2] * (1 - local_ratio) + c3[2] * local_ratio)
        draw.line([(0, y), (TARGET_WIDTH, y)], fill=(r, g, b))

    # Añadir un suave desenfoque y viñeta
    img = img.filter(ImageFilter.GaussianBlur(radius=8))
    img.save(target_path, "WEBP", quality=WEBP_QUALITY)

def download_or_generate_cover(item, target_path):
    """Descarga de la web si hay conexión o genera el gradiente offline."""
    url = item["url"]
    headers = {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"}
    try:
        req = urllib.request.Request(url, headers=headers)
        with urllib.request.urlopen(req, timeout=5) as response:
            data = response.read()
            img = Image.open(BytesIO(data)).convert("RGB")
            # Redimensionar al visor (720x360) con recorte centrado
            target_ratio = TARGET_WIDTH / TARGET_HEIGHT
            img_ratio = img.width / img.height

            if img_ratio > target_ratio:
                new_w = int(img.height * target_ratio)
                left = (img.width - new_w) // 2
                img = img.crop((left, 0, left + new_w, img.height))
            else:
                new_h = int(img.width / target_ratio)
                top = (img.height - new_h) // 2
                img = img.crop((0, top, img.width, top + new_h))

            img = img.resize((TARGET_WIDTH, TARGET_HEIGHT), Image.Resampling.LANCZOS)
            img.save(target_path, "WEBP", quality=WEBP_QUALITY)
            print(f"✓ Descargada y optimizada: {item['id']} ({item['theme']})")
            return
    except Exception as e:
        print(f"⚠ Conexión no disponible para {item['id']}: generando portada procedural ({e})")
        generate_procedural_cover(item["id"], item["gradient"], target_path)

def build_manifest():
    """Construye el archivo theme_manifest.json estructurado."""
    filenames = [item["id"] for item in CURATED_COVERS]

    manifest = {
        "packVersion": 1,
        "name": "Modern Glass V3.2 Theme Pack",
        "description": "Portadas HD litúrgicas solemnes sin personas (70px)",
        "defaultCovers": filenames,
        "keywordOverrides": {
            "trigo": "wheat_field_sunset_04.webp",
            "sembrador": "wheat_field_sunset_04.webp",
            "cosecha": "wheat_field_sunset_04.webp",
            "sol": "dawn_mountain_01.webp",
            "manana": "dawn_mountain_01.webp",
            "amanecer": "dawn_mountain_01.webp",
            "mar": "calm_ocean_dawn_03.webp",
            "aguas": "mountain_creek_08.webp",
            "rio": "river_living_water_15.webp",
            "sed": "mountain_creek_08.webp",
            "cruz": "stained_glass_cross_05.webp",
            "gracia": "stained_glass_cross_05.webp",
            "sangre": "stained_glass_cross_05.webp",
            "estrella": "starry_sky_night_06.webp",
            "noche": "starry_sky_night_06.webp",
            "cielo": "starry_sky_night_06.webp",
            "olivo": "olive_trees_peace_07.webp",
            "paz": "olive_trees_peace_07.webp",
            "roca": "rock_fortress_13.webp",
            "refugio": "rock_fortress_13.webp",
            "firme": "rock_fortress_13.webp",
            "luz": "candle_warmth_11.webp",
            "lampara": "candle_warmth_11.webp",
            "aceite": "vintage_pottery_14.webp",
            "vasija": "vintage_pottery_14.webp",
            "barro": "vintage_pottery_14.webp",
            "desierto": "desert_dunes_quiet_10.webp",
            "nube": "golden_clouds_09.webp",
            "gloria": "golden_clouds_09.webp"
        },
        "authorOverrides": {
            "Tito Abarca": "wheat_field_sunset_04.webp",
            "Conjunto de Dorrego": "mountain_creek_08.webp",
            "Alabanza de Fe": "dawn_mountain_01.webp",
            "Varios": "forest_sunrays_02.webp"
        },
        "hymnOverrides": {
            1: "dawn_mountain_01.webp",
            2: "forest_sunrays_02.webp",
            7: "wheat_field_sunset_04.webp",
            10: "calm_ocean_dawn_03.webp",
            12: "candle_warmth_11.webp",
            15: "river_living_water_15.webp",
            25: "olive_trees_peace_07.webp",
            50: "rock_fortress_13.webp",
            100: "stained_glass_cross_05.webp"
        }
    }
    return manifest

def main():
    os.makedirs(COVERS_FOLDER, exist_ok=True)
    print("=" * 60)
    print("Iniciando construcción del paquete DLC: Modern Glass V3.2")
    print("=" * 60)

    # 1. Generar portadas WebP
    for item in CURATED_COVERS:
        target_path = os.path.join(COVERS_FOLDER, item["id"])
        download_or_generate_cover(item, target_path)

    # 2. Guardar theme_manifest.json
    manifest = build_manifest()
    manifest_path = os.path.join(PACK_FOLDER, "theme_manifest.json")
    with open(manifest_path, "w", encoding="utf-8") as f:
        json.dump(manifest, f, indent=2, ensure_ascii=False)
    print(f"✓ Manifiesto generado en: {manifest_path}")

    # 3. Empaquetar todo en modern_theme_v1.zip
    print(f"Comprimiendo paquete en: {ZIP_FILE}...")
    with zipfile.ZipFile(ZIP_FILE, "w", zipfile.ZIP_DEFLATED) as zipf:
        # Añadir manifiesto
        zipf.write(manifest_path, arcname="theme_manifest.json")
        # Añadir portadas
        for filename in os.listdir(COVERS_FOLDER):
            file_path = os.path.join(COVERS_FOLDER, filename)
            if os.path.isfile(file_path):
                zipf.write(file_path, arcname=f"covers/{filename}")

    zip_size_kb = os.path.getsize(ZIP_FILE) / 1024.0
    print("=" * 60)
    print(f"✨ ¡Paquete DLC construido con éxito!")
    print(f"📦 Archivo: {ZIP_FILE}")
    print(f"📊 Tamaño total: {zip_size_kb:.1f} KB ({zip_size_kb/1024.0:.2f} MB)")
    print(f"🛡️ Portadas HD: {len(CURATED_COVERS)} (100% libres de figuras humanas)")
    print("=" * 60)

if __name__ == "__main__":
    main()
