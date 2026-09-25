# Especificación Técnica: Cancionero Digital v3.0 (Arquitectura Sync & Modular)

## 1. Objetivo y Visión General
Transformar el proyecto **Cancionero Digital** en una solución profesional, robusta y escalable sin costos de infraestructura ($0.00 perpetuo):
- **Problema Actual**: Cada modificación de alabanzas requiere editar código Kotlin hardcodeado en `HymnDataProvider.kt` y recompilar un APK. Además, `MainActivity.kt` es un archivo monolítico de más de 1700 líneas y el buscador no soporta búsqueda multi-palabra con tolerancia a errores tipográficos.
- **Solución**:
  1. Catálogo de alabanzas desacoplado y versionado en GitHub (`catalog.json`).
  2. Herramienta CLI de administración interactiva (`tools/manage_hymns.py`) para agregar, editar y eliminar cantos con validación de duplicados y git push automático.
  3. Módulo de persistencia y sincronización offline-first en Android (Room SQLite pre-cargado desde assets + sincronización silenciosa sobre Wi-Fi vía GitHub Raw).
  4. Motor de búsqueda instantáneo con soporte multi-palabra y tolerancia a errores tipográficos (Fuzzy Matching).
  5. Refactorización modular de la UI en Jetpack Compose, eliminando el antipatrón monolítico de `MainActivity.kt`.

---

## 2. Mapa de Capacidades (Capability Map)

| Módulo ID | Responsabilidad | Depende de |
|---|---|---|
| `catalog-backend` | Esquema del catálogo, almacenamiento en repo GitHub y CI validation | — |
| `admin-cli` | Asistente interactivo en Python para agregar/editar/borrar alabanzas y sincronizar git | `catalog-backend` |
| `android-data-sync` | Base de datos Room para Alabanzas, pre-población offline de assets y SyncManager Wi-Fi | `catalog-backend` |
| `android-search-engine`| Motor de búsqueda en memoria con normalización, multi-palabra y fuzzy search | `android-data-sync` |
| `android-ui-refactor` | Descomposición modular de `MainActivity.kt` en pantallas, componentes y ViewModel limpio | `android-search-engine`, `android-data-sync` |

**Orden de Construcción**:
`catalog-backend` → `admin-cli` → `android-data-sync` → `android-search-engine` → `android-ui-refactor`

---

## 3. Especificación por Módulo

### Módulo 1: `catalog-backend`
- **Ubicación**: `data/catalog.json` y `data/catalog_version.json`
- **Esquema JSON**:
  ```json
  {
    "version": 1,
    "updatedAt": "2026-09-25T00:00:00Z",
    "totalHymns": 180,
    "hymns": [
      {
        "id": 1,
        "title": "A Solas Con Jesús",
        "author": "C. Austin Miles",
        "content": "A solas al huerto yo voy...\n\nCoro:\nÉl conmigo está...",
        "link": "https://www.youtube.com/watch?v=...",
        "updatedAt": "2026-09-25T00:00:00Z",
        "isDeleted": false
      }
    ]
  }
  ```
- **Endpoint Raw**: `https://raw.githubusercontent.com/HugoAleOlguin/Cancionero-Digital/main/data/catalog.json` y `.../data/catalog_version.json` (solo ~50 bytes para chequear si hay nueva versión antes de descargar el catálogo completo).

### Módulo 2: `admin-cli`
- **Ubicación**: `tools/manage_hymns.py`
- **Capacidades**:
  - Menú interactivo en terminal con navegación amigable.
  - Opción 1: **Agregar Alabanza**: ingreso por archivo de texto o interactivo; validación de duplicados (algoritmo >70% de coincidencia de estrofas con alabanzas existentes); asignación automática de ID secuencial.
  - Opción 2: **Editar Alabanza**: búsqueda por ID o término, edición de título, autor, enlace o contenido.
  - Opción 3: **Eliminar Alabanza**: borrado lógico (`isDeleted = true`) o físico con confirmación.
  - Opción 4: **Validar Catálogo**: verificación de integridad referencial, IDs duplicados o faltantes y formato JSON.
  - Opción 5: **Publicar a GitHub**: incrementa `version`, actualiza `catalog_version.json` y `updatedAt`, hace `git add`, `git commit -m "Actualizar catálogo (vX)"` y `git push origin main`.
- **Tests**: `tools/test_manage_hymns.py` con cobertura completa de las operaciones.

### Módulo 3: `android-data-sync`
- **Entidades Room**:
  - `HymnEntity(id, title, author, content, link, isFavorite, updatedAt, isDeleted)`
  - `HymnDao`: consulta de todas las alabanzas activas (`isDeleted = 0`), inserción masiva (`upsert`), actualización de favoritos.
  - `AppDatabase`: base de datos unificada con migración/pre-población.
- **Pre-población Offline**:
  - Se genera `app/src/main/assets/catalog.json` para que la app siempre arranque con la totalidad de los cantos sin requerir conexión a internet.
- **Sincronizador (`HymnSyncManager`)**:
  - Detecta estado de red (Wi-Fi).
  - Consulta `catalog_version.json`. Si `remoteVersion > localVersion`:
    - Descarga `catalog.json`.
    - Aplica upsert a la base de datos Room en Dispatchers.IO.
    - Actualiza `localVersion` en `SharedPreferences`.
  - No bloquea la UI, no genera latencia y maneja fallos de red silenciosamente.

### Módulo 4: `android-search-engine`
- **Componente**: `FuzzyHymnSearchEngine`
- **Algoritmo**:
  - Normalización estricta (`NFD` sin acentos ni diacríticos).
  - Búsqueda multi-palabra: Si el usuario escribe `nube señor`, descompone en tokens `["nube", "senor"]` y verifica presencia en la estrofa o título.
  - Tolerancia a typos (Levenshtein con distancia 1 para tokens de longitud > 3).
  - Ranking de resultados:
    1. Coincidencia exacta de número de canto (ID).
    2. Coincidencia en título.
    3. Coincidencia exacta de frase en estrofa.
    4. Coincidencia de todas las palabras en una estrofa.
    5. Coincidencia difusa (fuzzy).
  - Devuelve `MatchOccurrence` con la posición exacta para resaltado y scroll instantáneo.

### Módulo 5: `android-ui-refactor`
- **Estructura Modular Limpia**:
  ```text
  app/src/main/java/com/example/
  ├── MainActivity.kt                  # Entry point mínimo, configuración de Window e inyección
  ├── data/
  │   ├── database/                    # Room Database, DAO y Entidades unificadas
  │   ├── repository/                  # HymnRepository (Room + Cache en memoria)
  │   └── sync/                        # HymnSyncManager y Worker de sincronización
  ├── search/
  │   └── FuzzyHymnSearchEngine.kt     # Motor de búsqueda multi-palabra y difuso
  ├── ui/
  │   ├── components/                  # Drawer, AppLogo, Canvas Icons, SearchBar, FastScrollbar
  │   ├── screens/                     # HymnFeedScreen y FeedHymnCard desacoplados
  │   ├── theme/                       # Color, Theme, Type Material3
  │   └── viewmodel/                   # MainViewModel desacoplado (UDF estricto)
  └── util/
      └── PdfGenerator.kt              # Exportación a PDF
  ```

---

## 4. Estrategia de Pruebas (Testing Strategy)
- **Unit Tests Python**: `tools/test_manage_hymns.py` para validar duplicados, incrementos de versión, serialización JSON y operaciones de catálogo.
- **Unit Tests Kotlin**: pruebas de `FuzzyHymnSearchEngine` y normalización de texto.
- **Pruebas de Integración Room**: verificación de lectura y escritura en base de datos.

---

## 5. Fronteras (Boundaries)
- **Siempre hacer**: Mantener compatibilidad 100% offline; preservar colores litúrgicos canónicos (`GoldenMain`, `ParchmentLight`, `JetCarbon`); verificar integridad antes de guardar cambios.
- **Preguntar primero**: Tareas de compilación pesada de Gradle (según regla de AGENTS.md).
- **Nunca hacer**: Agregar librerías de terceros innecesarias que aumenten el peso del APK; comprometer credenciales en repositorios; romper la búsqueda instantánea de 0ms.

---

## 6. Criterios de Aceptación (Success Criteria)
1. `data/catalog.json` generado con todas las alabanzas existentes extraídas de `HymnDataProvider.kt` sin pérdida de datos.
2. `tools/manage_hymns.py` completamente funcional e interactivo con validación de duplicados y tests aprobados.
3. Base de datos Room unificada con pre-población offline desde `assets/catalog.json`.
4. Motor de búsqueda fuzzy y multi-palabra funcionando en milisegundos.
5. Código desacoplado de `MainActivity.kt` en submódulos ordenados respetando las reglas de AGENTS.md.
