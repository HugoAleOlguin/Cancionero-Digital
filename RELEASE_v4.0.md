# 📜 Cancionero Digital v4.0 — Notas de Lanzamiento (Release Notes)

¡Damos la bienvenida a **Cancionero Digital v4.0**! Esta actualización representa la evolución visual, arquitectónica y funcional más importante del proyecto hasta la fecha, combinando la sobriedad litúrgica tradicional con un nuevo motor de diseño de vanguardia y arquitectura de peso cero.

---

## 🌟 Lo Nuevo en la Versión 4.0

### 1. 🎭 Motor de Temas Dual (Arquitectura DLC de Peso Cero)
- **📜 Tema Clásico Litúrgico (Por Defecto - 0 KB):**
  - Mantiene la esencia solemne del papel pergamino tradicional (`ParchmentLight`) con texto carbono (`JetCarbon`) y acentos en oro litúrgico (`GoldenMain`).
  - **Estructura Rediseñada:** Cabecera limpia con identificador numérico en relieve, nombre del autor y título destacado sin saturación visual.
  - **Espaciado Armónico Compacto:** Reducción drástica del espacio muerto entre estrofas (6px) con un divisor sutil de 1.5px.
  - **Etiquetas de Lectura Asamblearia:** Detección y formato automático de badges discretos para `CORO` y numeración de estrofas (`I`, `II`, etc.).

- **✨ Tema Modern Glass V3.2 (Paquete Descargable Opcional ~900 KB):**
  - **Visor Poligonal de 70px:** Fotografía HD con corte geométrico en V simétrico y línea inferior dorada litúrgica.
  - **Fotografía 100% Libre de Figuras Humanas:** 20 portadas HD cuidadosamente seleccionadas de amaneceres en montaña, rayos de sol filtrándose entre niebla, trigales al atardecer, arroyos cristalinos, cielos estrellados y vitrales.
  - **Selector Elástico de Versiones:** Control segmentado estilo iOS (`[ 1 ] [ 2 ]`) para alternar instantáneamente entre versiones sin recargar la lista.
  - **Cargador Nativo Ligero:** Decodificación en segundo plano con `BitmapFactory`, configuración de memoria `RGB_565` y caché `LruCache` (sin librerías externas pesadas como Coil o Glide, 0 KB de sobrepeso en APK).

---

### 2. 📲 Hoja de Acciones Rápida (Bottom Action Sheet tipo iOS)
Al presionar el botón de compartir en cualquiera de los dos temas, se despliega una elegante hoja inferior con vista previa de la alabanza y acceso a 4 acciones en 1 toque:
1. **📥 Descargar PDF:** Genera y almacena el documento `.pdf` con tipografía litúrgica en la carpeta de Descargas del dispositivo.
2. **📄 Compartir como PDF:** Envía el archivo `.pdf` generado a través de WhatsApp, Telegram, correo o cualquier app externa.
3. **💬 Compartir Letra:** Comparte el texto completo formateado con título, autor y estrofas para mensajes rápidos.
4. **📋 Copiar al Portapapeles:** Copia la alabanza al portapapeles con confirmación visual instantánea.

---

### 3. ▶️ Acceso Directo a YouTube en la Barra de Tarjeta
- El botón de video de YouTube (icono rojo característico) ahora se ubica **directamente en la barra de herramientas de la tarjeta**, al lado del botón de compartir.
- Permite abrir el canto en la aplicación de YouTube con una sola pulsación y latencia cero.

---

### 4. ⚡ Rendimiento & Búsqueda Instantánea de 0ms
- Motor de búsqueda difusa exacto en memoria (`SearchableHymn`) que indexa número, título, autor y letras con normalización diacrítica (ignora tildes y mayúsculas).
- Navegación reactiva de coincidencias `< 1 / 4 >` con auto-scroll debounced y resaltado visual en amarillo/naranja.
- Barra de desplazamiento lateral interactiva (`FastScrollbar`) con respuesta a 120 FPS.

---

### 5. 🔄 Sincronización Silenciosa y Catálogo Base Offline
- Catálogo base offline integrado con **485 alabanzas canónicas** listas para funcionar sin internet desde el primer segundo.
- Sincronizador en segundo plano (`HymnSyncManager`) que detecta Wi-Fi automáticamente, compara versiones con el repositorio de GitHub y actualiza la base de datos local SQLite (Room) silenciosamente a costo perpetuo de **$0.00**.

---

## 📊 Especificaciones Técnicas de la Versión

| Parámetro | Detalle |
| :--- | :--- |
| **Versión de la App** | `4.0` |
| **Código de Versión (versionCode)** | `9` |
| **SDK Mínimo (minSdk)** | Android 6.0 Marshmallow (API 23) |
| **SDK Objetivo (targetSdk)** | Android 15 / 16 Preview (API 36) |
| **Framework de UI** | Jetpack Compose + Material 3 (100% Declarativo) |
| **Persistencia Local** | Room SQLite 2.6 + SharedPreferences (Asíncrono) |
| **Optimización de APK** | Minificación R8 + Reducción estricta de recursos activada |
| **Arquitectura** | Clean Architecture + MVVM + Unidirectional Data Flow (UDF) |

---

## 🛠️ Instrucciones de Instalación
1. Descarga el archivo APK generado (`app-release.apk` o `app-debug.apk`).
2. Habilita la instalación desde orígenes desconocidos si tu dispositivo lo solicita.
3. Abre la app: funcionará 100% offline de inmediato con el catálogo base completo.
4. Si deseas activar el Tema Moderno con portadas HD, abre el menú lateral izquierdo (Drawer) y pulsa en *"Descargar Tema HD (3.2 MB)"*.

---
*Cancionero Digital — Adoración, Solemnidad y Excelencia Técnica.*
