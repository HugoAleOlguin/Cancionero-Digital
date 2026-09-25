# Plan de Implementación: Cancionero Digital v3.0

## Fases de Ejecución

### Fase 1: Backend de Datos y Esquema de Catálogo (`catalog-backend`)
1. Extraer la totalidad de las alabanzas existentes desde `HymnDataProvider.kt` hacia `data/catalog.json`.
2. Generar `data/catalog_version.json` con metadatos (`version: 1`, `updatedAt`, `totalHymns`).
3. Copiar `data/catalog.json` a `app/src/main/assets/catalog.json` para pre-población offline de fábrica.

### Fase 2: Herramienta de Administración e Ingestión (`admin-cli`)
1. Implementar `tools/manage_hymns.py` con menú interactivo completo:
   - Agregar alabanza (con algoritmo de duplicados de estrofas >70%).
   - Editar / corregir alabanza existente.
   - Eliminar / desactivar alabanza.
   - Validar consistencia del catálogo.
   - Publicación y Push automático a GitHub (`git commit` + `git push`).
2. Implementar suite de pruebas unitarias exhaustivas `tools/test_manage_hymns.py`.
3. Ejecutar y validar que todas las pruebas pasen al 100%.

### Fase 3: Persistencia y Sincronización en Android (`android-data-sync`)
1. Definir `HymnEntity` en Room con campos para id, título, autor, contenido, link, isFavorite, updatedAt y isDeleted.
2. Crear `HymnDao` con consultas optimizadas y Flow reactivo.
3. Unificar `AppDatabase` con callback de pre-población desde `assets/catalog.json`.
4. Crear `HymnRepository` que sirva como fuente única de verdad (Single Source of Truth).
5. Implementar `HymnSyncManager`: detector de Wi-Fi, comprobación de versión contra GitHub Raw y actualización atómica en Room.

### Fase 4: Motor de Búsqueda Difuso y Multi-palabra (`android-search-engine`)
1. Implementar `FuzzyHymnSearchEngine.kt`:
   - Normalización de acentos y caracteres diacríticos.
   - Descomposición multi-token (búsqueda de palabras no consecutivas en estrofas).
   - Tolerancia a errores de tipeo (Fuzzy distance).
   - Cálculo de ocurrencias para scroll y resaltado visual.
2. Pruebas unitarias de rendimiento y precisión del algoritmo de búsqueda.

### Fase 5: Refactorización Modular de UI (`android-ui-refactor`)
1. Extraer componentes visuales a `ui/components/`:
   - Iconos nativos (`DownloadIcon`, `ThemeToggleIcon`, etc.).
   - `AppDrawer` y cabeceras.
   - `SearchHeader` con contador de ocurrencias y navegación `<` `3/12` `>`.
   - `FastScrollbar`.
2. Extraer pantallas y tarjetas a `ui/screens/`:
   - `FeedHymnCard.kt`.
   - `HymnFeedScreen.kt`.
3. Desacoplar `MainViewModel` en `ui/viewmodel/MainViewModel.kt` inyectando `HymnRepository` y `HymnSyncManager`.
4. Dejar `MainActivity.kt` limpio, solemne y minimalista (<120 líneas).
