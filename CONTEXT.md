# Contexto del proyecto - Cancionero Digital Android

## Última actualización
2026-09-25 02:34

## Estado actual
La aplicación cuenta con arquitectura **v3.0 Modular & Serverless ($0.00 Perpetuo)** con soporte **Multiversión integrado** (Room SQLite v3 con `versionsJson` y selector UI numérico minimalista `[ 1 ] [ 2 ] ...` sin alterar títulos), catálogo base pre-cargado offline en assets, enlaces multimedia directos a YouTube (`link`), sincronización inteligente en segundo plano sobre Wi-Fi con GitHub Raw, motor de búsqueda fuzzy Damerau-Levenshtein tolerante a errores tipográficos y de transposición de teclas, y un asistente CLI todo-en-uno para gestión de alabanzas con validación automática de duplicados.

MainActivity se redujo de **2,135 líneas monolíticas a 44 líneas limpias**. Todos los componentes de UI, pantallas, ViewModels y algoritmos de búsqueda se encuentran modularizados y desacoplados.

El catálogo base ha superado el hito histórico de las **1,000 alabanzas**, ubicándose en la **Versión 7 (1,009 alabanzas activas)**:
- **1,009 alabanzas únicas e íntegras** (Catálogo v7).
- **87 alabanzas con multiversión conmutable** (`extraVersions` / `versionsJson`) con botones numéricos `[ 1 ] [ 2 ] ...`.
- **Integración del cantautor Polo Negrete**: 2 alabanzas existentes enriquecidas (#312 'Mi Dios es Real' y #852 'Hijo no Temas') y 17 canciones nuevas incorporadas (#993 al #1009), todas con sus enlaces oficiales a YouTube verificados.
- **Pre-población offline de fábrica actualizada** en `app/src/main/assets/catalog.json` con las 1,009 alabanzas.

## Decisiones técnicas
1. **Sincronización Serverless sin costo ($0.00)**: Se utiliza GitHub Raw como CDN estático para la sincronización de catálogos (`data/catalog.json` y `data/catalog_version.json`), eliminando la necesidad de servidores dedicados de pago (Firebase/Supabase) y evitando la necesidad de compilar un nuevo APK por cada nueva alabanza agregada.
2. **Offline-First con Pre-Población Inmediata**: El catálogo base de 480 alabanzas pulidas se encuentra incluido directamente en `app/src/main/assets/catalog.json`. Al instalar la aplicación, Room se pre-puebla en milisegundos en frío sin requerir conexión a internet.
3. **Persistencia Reactiva en Room v2**: Entidad `HymnEntity` y DAO `HymnDao` con `Flow` reactivo para observar cambios en tiempo real en la UI cuando se actualiza el catálogo silenciosamente en background.
4. **Búsqueda Fuzzy Damerau-Levenshtein Multi-Palabra**: `FuzzyHymnSearchEngine` soporta matching de palabras dispersas y tolera errores de tipeo y transposiciones adyacentes de letras (ej. "grnade" -> "grande") con latencia cero (0ms).
5. **Arquitectura Modular UDF**:
   - `data/database/`: `HymnEntity`, `HymnDao`, `AppDatabase`.
   - `data/repository/`: `HymnRepository`.
   - `data/sync/`: `HymnSyncManager`.
   - `search/`: `FuzzyHymnSearchEngine`.
   - `ui/components/`: `AppDrawer`, `CanvasIcons`, `FastScrollbar`, `SearchHeader`.
   - `ui/screens/`: `FeedHymnCard`, `HymnFeedScreen`.
   - `ui/viewmodel/`: `MainViewModel`.
   - `MainActivity.kt`: Punto de entrada mínimo con `FLAG_KEEP_SCREEN_ON`.
6. **CLI de Gestión de Alabanzas**: Herramienta `tools/manage_hymns.py` interactiva con chequeo de duplicados basado en similitud de estrofas (>70%), normalización y comandos automáticos de `git commit` y `push origin main`.

## Problemas conocidos y resueltos
- **Monolito en MainActivity.kt**: Se eliminó el archivo de más de 2,100 líneas y se distribuyeron responsabilidades en componentes testeables.
- **Tolerancia a Transposición en Teclado Móvil**: Se implementó Damerau-Levenshtein para corregir inversiones de letras rápidas durante el culto.
- **Soporte de Robolectric para targetSdk 36**: Se configuró `@Config(sdk = [34])` en los tests de Compose/Robolectric ya que el SDK 36 aún no cuenta con shadows estables en el runner local.
- **Caché bloqueada de Gradle en Windows**: Se limpiaron los transforms conflictivos de la versión 9.3.1.

## Próximos pasos
1. Ejecutar `.\gradlew assembleDebug` cuando el usuario decida empaquetar el APK final de producción v3.0.
2. Hacer commit y push de la versión 3.0 al repositorio remoto de GitHub (`origin/main`).
3. Probar en un dispositivo físico o emulador la sincronización silenciosa sobre Wi-Fi.

---

## Historial de sesiones

### Sesión 2026-09-25

**Completado:**
- Extracción de las 485 alabanzas originales de `HymnDataProvider.kt` a `data/catalog.json` y `app/src/main/assets/catalog.json`.
- Implementación del script CLI `tools/manage_hymns.py` con 14 pruebas unitarias en `tools/test_manage_hymns.py` (100% de éxito).
- Implementación de Room SQLite v2 (`HymnEntity`, `HymnDao`, `AppDatabase`) y `HymnRepository`.
- Implementación del gestor de sincronización en segundo plano `HymnSyncManager`.
- Implementación y prueba del motor `FuzzyHymnSearchEngine` (Damerau-Levenshtein y multi-palabra).
- Modularización de UI: `CanvasIcons`, `AppDrawer`, `SearchHeader`, `FastScrollbar`, `FeedHymnCard`, `HymnFeedScreen`, `MainViewModel`.
- Reducción de `MainActivity.kt` de 2,135 líneas a 44 líneas.
- Validación de compilación completa de Kotlin (`compileDebugKotlin`: BUILD SUCCESSFUL).
- Ejecución y aprobación de suite de pruebas unitarias (`FuzzyHymnSearchEngineTest`, `ExampleUnitTest`, `ExampleRobolectricTest`: BUILD SUCCESSFUL).
- Auditoría lingüística profunda de las 485 alabanzas: detección y corrección de 52 errores de traducción (Because -> Porque, But -> Pero, With -> Con, in -> en, Of -> De, At -> Al, Then -> Entonces, Glory -> Gloria, by -> por, voice -> voz, Mase -> Más, mirarer -> mirar).
- Detección matricial de alabanzas duplicadas: resolución de 5 casos preservando siempre las versiones más largas y completas (ej. 'La Fe' con recitado completo de Hebreos 11, 'Vaso Nuevo' con dos estrofas, 'A veces en pesar', 'El Hijo se va' y 'No dejes de luchar').
- Reindexación correlativa limpia de 1 a 480 alabanzas, incremento de catálogo a versión 2 y sincronización en assets.
- Creación de habilidad `.agents/skills/pdf-hymn-pipeline/` con pipeline local Zero-Token usando `pdfplumber`.
- Extracción e indexación 1:1 de 754 páginas de `Cuadernillo de Alabanzas Cristianas.pdf`: 687 alabanzas reales catalogadas y 58 carátulas/páginas vacías descartadas.
- Cotejo matricial 1:1 contra el catálogo actual: 269 coincidencias directas, 27 candidatas a Versión 2 y 391 alabanzas nuevas.
- Implementación de arquitectura Multiversión (Paso 1):
  - Room SQLite v3 con soporte `versionsJson` y método `getVersions()`.
  - Integración en `HymnRepository`, `HymnSyncManager` y `FuzzyHymnSearchEngine`.
  - UI en `FeedHymnCard` con selector numérico minimalista `[ 1 ] [ 2 ]` (manteniendo el título limpio).
  - Exportación de texto y PDF respetando la versión activa seleccionada.
- Enriquecimiento y vinculación de versiones (Paso 2):
  - 64 autores restaurados en el catálogo v3.
  - 27 versiones alternativas vinculadas como `extraVersions`.
  - Catálogo v3 validado con `python tools/manage_hymns.py --validate`.
- Ejecución y validación de pruebas unitarias de Android (`testDebugUnitTest`: BUILD SUCCESSFUL en 2m 42s).
- Incorporación masiva y enriquecimiento final (Paso 3 y 4):
  - Restauración al 100% de la alabanza #456 ('El Espíritu de Dios', Tito Abarca) con letra íntegra.
  - Fusión de 'Que Esplendente' como Versión 2 de la alabanza #622 ('El Día Glorioso', Los Gonzales).
  - Adición de 378 alabanzas nuevas únicas del cuadernillo (descartando índices de páginas 755-765).
  - Asignación de IDs correlativos del 481 al 858.
  - Incremento del catálogo a versión 4 con 858 alabanzas activas.
  - Sincronización completa en `data/catalog.json`, `data/catalog_version.json` y `app/src/main/assets/catalog.json`.
  - Validación de integridad con `tools/manage_hymns.py --validate` (SUCCESS).
- Ejecución de Fase 1 del 'Manual de Alabanzas Cristianas.pdf':
  - Enriquecimiento de 15 autores legítimos en alabanzas existentes (Conjunto Trigales, Moisés y Claudia, Conjunto Dorrego, Conjunto Mansilla, Conjunto Lazarte, Conjunto de Bariloche).
  - Vinculación de 70 variantes líricas detectadas como 'extraVersions' (activando selectores [ 1 ] [ 2 ] ... en un total de 84 alabanzas).
  - Incremento del catálogo a versión 5 (858 alabanzas activas).
- Ejecución de Fase 2 y 3 del 'Manual de Alabanzas Cristianas.pdf':
  - Curaduría lírica y corrección de título de la pág. 173 a 'Nehemías' (Conjunto Trigales).
  - Fusión de variantes internas ('Quiero Darte Muchas Gracias' / 'Yo Conozco' y 'Canta a Cristo').
  - Incorporación de 134 alabanzas únicas nuevas (#859 al #992).
  - Incremento del catálogo a versión 6 con 992 alabanzas activas y 86 multiversiones conmutables.
  - Sincronización completa de activos y validación con `manage_hymns.py --validate` (SUCCESS) y 14/14 tests OK.
- Integración de Polo Negrete desde Letras.com y YouTube:
  - Enriquecimiento de #312 ('Mi Dios es Real') y #852 ('Hijo no Temas') con autoría oficial y enlaces a YouTube.
  - Fusión de 'Espíritu Santo Renovador' como Versión 2 de 'Espíritu Santo'.
  - Adición de 17 canciones nuevas (#993 al #1009) con letras íntegras y enlaces a YouTube.
  - Superación del hito de 1,000 cantos: Catálogo Versión 7 con 1,009 alabanzas activas y 87 multiversiones.
  - Validación completa con `manage_hymns.py --validate` (SUCCESS) y 14/14 tests OK.

**Modificado:**
- `data/catalog.json` — Catálogo limpio v7 con 1,009 alabanzas, 87 multiversiones y enlaces de YouTube verificados.
- `data/catalog_version.json` — Actualizado a versión 7 (`totalHymns = 1009`).
- `app/src/main/assets/catalog.json` — Sincronizado para arranque offline inmediato con el catálogo v7 (1,009 cantos).
- `app/build.gradle.kts` — Actualizado a versionCode 8, versionName 3.0 y activado `isIncludeAndroidResources`.
- `app/src/main/AndroidManifest.xml` — Añadidos permisos de red y FileProvider.
- `app/src/main/java/com/example/MainActivity.kt` — Simplificado a 44 líneas como punto de entrada limpio.
- `app/src/main/java/com/example/data/database/HymnEntity.kt` — Agregado campo `versionsJson` y helper `getVersions()`.
- `app/src/main/java/com/example/data/database/AppDatabase.kt` — Bump a versión 3.
- `app/src/main/java/com/example/data/repository/HymnRepository.kt` — Soporte de parseo para multiversiones.
- `app/src/main/java/com/example/data/sync/HymnSyncManager.kt` — Sincronización de `versionsJson`.
- `app/src/main/java/com/example/search/FuzzyHymnSearchEngine.kt` — Indexación de todas las versiones combinadas.
- `app/src/main/java/com/example/ui/screens/FeedHymnCard.kt` — Botones numéricos `[ 1 ] [ 2 ]` y exportación según versión activa.
- `app/src/main/java/com/example/ui/theme/Color.kt` — Incorporados tokens canónicos `GoldenMain`, `ParchmentLight`, `JetCarbon`.
- `app/src/test/java/com/example/ExampleRobolectricTest.kt` — Ajustado SDK a 34 para compatibilidad con sombras locales.
- `app/src/test/java/com/example/GreetingScreenshotTest.kt` — Ajustado SDK a 34 para compatibilidad con sombras locales.

**Pendiente:**
- Generación del archivo APK final (`assembleDebug` o `assembleRelease`) a petición del usuario.
- Commit y push a GitHub (`origin/main`).
