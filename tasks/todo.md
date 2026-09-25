# Tareas de Implementación: Cancionero Digital v3.0

## Lista de Tareas

- [x] Task 1: Extraer catálogo inicial de alabanzas a `data/catalog.json` y `data/catalog_version.json`
  - Acceptance: Catálogo con 485 alabanzas extraídas fielmente de `HymnDataProvider.kt` con versión 1 y metadatos.
  - Verify: Validación de formato JSON y conteo de alabanzas (`totalHymns = 485`).
  - Files: `data/catalog.json`, `data/catalog_version.json`, `app/src/main/assets/catalog.json`

- [x] Task 2: Implementar herramienta CLI interactiva `tools/manage_hymns.py`
  - Acceptance: Menú interactivo con agregar, editar, eliminar, validar y publicar con git push automático.
  - Verify: Ejecución de `tools/test_manage_hymns.py` pasando las 14 pruebas unitarias.
  - Files: `tools/manage_hymns.py`, `tools/test_manage_hymns.py`

- [x] Task 3: Crear capa de datos Room para alabanzas (`HymnEntity`, `HymnDao`, `AppDatabase`)
  - Acceptance: Entidad con soporte de sincronización, DAO reactivo con `Flow`, y base de datos unificada con Room.
  - Verify: Código Kotlin estructurado bajo `data/database/`.
  - Files: `app/src/main/java/com/example/data/database/HymnEntity.kt`, `HymnDao.kt`, `AppDatabase.kt`

- [x] Task 4: Implementar `HymnRepository` y `HymnSyncManager`
  - Acceptance: Sincronización silenciosa en background con GitHub Raw verificando versión y Wi-Fi sin bloquear UI, con pre-población offline de fábrica desde assets.
  - Verify: Manejo robusto de excepciones y conectividad Wi-Fi.
  - Files: `app/src/main/java/com/example/data/repository/HymnRepository.kt`, `app/src/main/java/com/example/data/sync/HymnSyncManager.kt`

- [x] Task 5: Implementar motor de búsqueda inteligente `FuzzyHymnSearchEngine.kt`
  - Acceptance: Búsqueda instantánea multi-palabra con tolerancia a errores tipográficos de 1 letra (Fuzzy Levenshtein) y cálculo de ocurrencias para scroll y resaltado.
  - Verify: Pruebas unitarias en `FuzzyHymnSearchEngineTest.kt` y `FuzzyHymnSearchEngine.kt`.
  - Files: `app/src/main/java/com/example/search/FuzzyHymnSearchEngine.kt`, `app/src/test/java/com/example/search/FuzzyHymnSearchEngineTest.kt`

- [x] Task 6: Refactorizar componentes UI y pantallas desacopladas
  - Acceptance: Extraer iconos nativos a CanvasIcons, AppDrawer, SearchHeader, FastScrollbar y FeedHymnCard a módulos individuales bajo `ui/components/` y `ui/screens/`.
  - Verify: Componentes desacoplados, limpios y modulares.
  - Files: `app/src/main/java/com/example/ui/components/CanvasIcons.kt`, `FastScrollbar.kt`, `AppDrawer.kt`, `SearchHeader.kt`, `app/src/main/java/com/example/ui/screens/FeedHymnCard.kt`, `HymnFeedScreen.kt`

- [x] Task 7: Desacoplar `MainViewModel` y simplificar `MainActivity.kt`
  - Acceptance: `MainViewModel` limpio en `ui/viewmodel/MainViewModel.kt` consumiendo `HymnRepository`, y `MainActivity.kt` reducido de 2,135 líneas a 44 líneas.
  - Verify: Arquitectura UDF estricta y código limpio.
  - Files: `app/src/main/java/com/example/ui/viewmodel/MainViewModel.kt`, `app/src/main/java/com/example/MainActivity.kt`
