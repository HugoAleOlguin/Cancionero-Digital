---
name: pdf-hymn-pipeline
description: Extrae y audita alabanzas desde archivos PDF con consumo mínimo de tokens (Zero-Token Ingestion) usando pdfplumber y cotejo matricial local.
---

# Habilidad: pdf-hymn-pipeline (Ingestión de PDFs sin Consumo de Tokens)

Esta habilidad permite procesar cuadernillos y cancioneros completos en formato PDF (de 50 a más de 500 páginas) de manera ultra-eficiente, realizando la extracción, formateo y cotejo matricial contra el catálogo en la máquina local usando Python y `pdfplumber`, sin gastar tokens de contexto del modelo de lenguaje.

---

## 1. Filosofía de Consumo Mínimo de Tokens

- **Problema**: Leer un PDF de cientos de páginas directamente en el prompt del chat consume entre 100,000 y 500,000 tokens de contexto, lo cual degrada la atención del modelo y agota las cuotas de tokens.
- **Solución**: La herramienta `tools/extract_pdf_hymns.py` realiza el 100% de la lectura, limpieza de saltos de línea y cotejo contra `data/catalog.json` de forma nativa en Python. Al agente solo se le devuelve un informe resumido con las estadísticas y los casos de atención requerida.

---

## 2. Flujo de Ejecución

1. **Recepción del PDF**: El usuario coloca el PDF en el proyecto (ej. `data/himnario.pdf`).
2. **Ejecución del Extractor Local**:
   ```powershell
   python tools/extract_pdf_hymns.py data/himnario.pdf data/pdf_extracted.json
   ```
3. **Clasificación Automática de Alabanzas**:
   - `exact_matches`: Ya existen con >92% de similitud (se descartan o se enriquecen autores/enlaces).
   - `version_candidates`: Misma alabanza/coro con 55%–91% de similitud (candidatas a Versión 2 con botón conmutador).
   - `new_hymns`: Alabanzas nuevas que no existen en el repertorio (se incorporan con ID correlativo).
4. **Presentación de Resumen**: El agente muestra un reporte conciso al usuario solicitando su confirmación para inyectar los cambios al catálogo v2.
