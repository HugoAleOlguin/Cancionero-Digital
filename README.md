# 📖 Cancionero Digital Android (v4.0)

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-purple.svg?logo=kotlin)](https://kotlinlang.org)
[![Android](https://img.shields.io/badge/Android-API%2023%20%E2%86%92%2036-green.svg?logo=android)](https://developer.android.com)
[![Jetpack Compose](https://img.shields.io/badge/Compose-BOM%202024.10.01-4285F4.svg?logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Room SQLite](https://img.shields.io/badge/Room-2.6.1-orange.svg?logo=sqlite)](https://developer.android.com/training/data-storage/room)
[![Version](https://img.shields.io/badge/Release-v4.0-gold.svg)](RELEASE_v4.0.md)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

> **Cuadernillo digital de alabanzas y cánticos cristianos para asambleas de adoración.**  
> Diseñado bajo una filosofía de **cero latencia**, **legibilidad extrema** en cualquier condición de luz y **arquitectura de peso pluma** (0 KB de incremento con minificación estricta R8).

---

## 🌟 Características Destacadas (Versión 4.0)

### 1. 🎭 Motor de Temas Dual (Arquitectura DLC de Peso Cero)
- **📜 Clásico Litúrgico (Predeterminado - 0 KB):**
  - Paleta canónica tradicional: fondo pergamino (`#FDFBF7`), texto carbono (`#1E242B`) y acentos litúrgicos en oro cálido (`#C5A03A`).
  - Layout reorganizado: cabecera con identificador numérico en relieve, autor, selector de versiones numérico `[ 1 ] [ 2 ]` y espaciado compacto armónico entre estrofas.
  - Formato asambleario: badges sutiles para `CORO` y números romanos de estrofa (`I`, `II`, etc.).

- **✨ Modern Glass V3.2 (DLC Opcional Descargable ~900 KB):**
  - **Visor Poligonal de 70px:** Fotografía HD con corte geométrico en V simétrico y línea inferior dorada litúrgica.
  - **100% Libre de Figuras Humanas:** 20 portadas HD de naturaleza pura (amaneceres en montaña, rayos de sol en pinares, trigales dorados, mar calmo, arroyos cristalinos, cielos estrellados y vitrales).
  - **Cargador Nativo Sin Librerías:** Decodificador nativo en `ModernImageLoader` usando `BitmapFactory` con `RGB_565` y caché `LruCache` en memoria sobre `Dispatchers.IO` (0 KB de dependencias como Coil o Glide).

### 2. ⚡ Búsqueda Difusa Instantánea (0ms)
- Motor en memoria precalculado (`SearchableHymn`) que busca por número de himno, título, autor o fragmento de la letra.
- Normalización diacrítica estricta (insensible a tildes, mayúsculas y diéresis).
- Navegación reactiva de coincidencias `< 1 / 14 >` con auto-desplazamiento suave y resaltador visual amarillo/naranja.
- Barra lateral interactiva `FastScrollbar` con respuesta fluida a 120 FPS.

### 3. 📲 Exportación y Compartir (Bottom Action Sheet tipo iOS)
Al presionar el botón de compartir en cualquier canto, se abre una hoja modal inferior con vista previa y cuatro acciones rápidas en un toque:
- 📥 **Descargar PDF:** Genera un PDF estructurado listo para imprimir o guardar.
- 📄 **Compartir como PDF:** Envía el archivo `.pdf` a contactos o grupos de WhatsApp.
- 💬 **Compartir Letra:** Envía el texto plano formateado con título, autor y estrofas completas.
- 📋 **Copiar al Portapapeles:** Copia toda la letra de forma instantánea.
- ▶️ **Botón YouTube Directo:** Ubicado directamente en la barra de herramientas de cada tarjeta al lado del botón de compartir para acceso en un toque.

### 4. 📴 100% Offline-First con Sincronización Silenciosa sobre Wi-Fi
- **Pre-población Offline:** Incluye un catálogo base empaquetado de 485 cantos que funciona sin conexión desde la primera apertura.
- **Sincronización Inteligente (`HymnSyncManager`):** Cuando el dispositivo se conecta a una red Wi-Fi, compara silenciosamente la versión del catálogo con GitHub Raw y actualiza la base de datos Room sin interrumpir la lectura y a costo **$0.00 perpetuo**.

---

## 🏗️ Arquitectura y Estructura del Código

El proyecto sigue los principios de **Clean Architecture** y **Unidirectional Data Flow (UDF)**:

```text
app/src/main/java/com/example/
├── MainActivity.kt                     # Entrada mínima (<50 líneas), FLAG_KEEP_SCREEN_ON
├── data/
│   ├── database/                       # Room SQLite: HymnEntity, HymnDao, AppDatabase
│   ├── repository/                     # HymnRepository (Fuente única de verdad reactiva)
│   ├── sync/                           # HymnSyncManager (Sincronizador Wi-Fi silencioso)
│   └── dlc/
│       └── ThemeDlcManager.kt          # Gestor de descarga, verificación y descompresión de temas
├── search/
│   └── FuzzyHymnSearchEngine.kt        # Motor SearchableHymn exacto sin desordenar IDs
├── ui/
│   ├── components/
│   │   ├── CanvasIcons.kt              # Íconos vectoriales dibujados en código nativo Compose
│   │   ├── AppDrawer.kt                # Menú lateral con filtro por autor, tipografía y DLC
│   │   ├── ModernActionSheet.kt        # Hoja inferior modal estilo iOS para compartir/exportar
│   │   ├── SearchHeader.kt             # Buscador superior con navegador de ocurrencias (< 3/12 >)
│   │   └── FastScrollbar.kt            # Barra lateral interactiva de desplazamiento rápido
│   ├── screens/
│   │   ├── FeedHymnCard.kt             # Tarjeta Clásica Litúrgica optimizada
│   │   ├── ModernHymnCard.kt           # Tarjeta Moderna con Visor Poligonal 70px HD
│   │   └── HymnFeedScreen.kt           # Pantalla principal del feed con alternancia de temas
│   ├── theme/
│   │   ├── Theme.kt                    # Tema Material 3, colores (GoldenMain, ParchmentLight)
│   │   └── ThemeMode.kt                # Enum de modo de tema (CLASSIC, MODERN)
│   └── viewmodel/
│       └── MainViewModel.kt            # ViewModel central desacoplado
└── util/
    ├── ModernImageLoader.kt            # Decodificador nativo RGB_565 con LruCache
    └── PdfGenerator.kt                 # Motor nativo de generación de PDFs imprimibles
```

---

## 🛠️ Herramientas CLI Incluidas

En el directorio `tools/` se encuentran scripts de automatización en Python:

| Script | Descripción |
| :--- | :--- |
| `tools/manage_hymns.py` | Asistente CLI interactivo todo-en-uno para agregar, editar, buscar, validar y publicar alabanzas con `git push` automático. |
| `tools/build_modern_theme_pack.py` | Constructor del paquete DLC `modern_theme_v1.zip` con portadas WebP HD sin personas y manifiesto de mapeo. |

Para construir o actualizar el paquete de temas descargable:
```bash
python tools/build_modern_theme_pack.py
```

---

## 🚀 Compilación y Ejecución

### Requisitos Previos:
- [Android Studio Ladybug o superior](https://developer.android.com/studio)
- JDK 17 o 21
- Android SDK 36 (compilación) y API 23+ (ejecución)

### Compilación por Línea de Comandos:
```bash
# Compilar versión de depuración (Debug APK)
./gradlew assembleDebug

# Compilar versión de producción optimizada con R8 (Release APK)
./gradlew assembleRelease
```
El archivo generado se ubicará en `app/build/outputs/apk/`.

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.
