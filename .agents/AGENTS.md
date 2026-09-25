# Reglas del Espacio de Trabajo - Cancionero Digital Android

¡Bienvenido! Este archivo define las directrices, la filosofía, la arquitectura y las reglas de diseño para que cualquier agente de IA o desarrollador entienda el proyecto y mantenga la coherencia del mismo.

---

## 1. Contexto y Propósito del Proyecto

El **Cancionero Digital** es una aplicación móvil para Android desarrollada en Kotlin y Jetpack Compose. Su objetivo es servir como un cuadernillo digital de himnos y cantos religiosos cristianos para su uso directo durante asambleas o reuniones de adoración.

### Objetivos Clave:
1. **Legibilidad Extrema**: Debe ser fácil de leer en cualquier condición de luz y evitar la fatiga visual.
2. **Rendimiento Instantáneo**: Las búsquedas de cantos deben ser de latencia cero (0ms) mientras el usuario escribe.
3. **Persistencia Ligera**: Guardar el estado de favoritos y la última alabanza leída de forma robusta.

---

## 2. Arquitectura y Estructura del Código (Versión 3.0 Modular)

El proyecto sigue una arquitectura limpia y desacoplada de Android estructurada bajo `app/src/main/java/com/example/`:

```text
├── MainActivity.kt                  # Punto de entrada mínimo (<50 líneas), FLAG_KEEP_SCREEN_ON y tema.
├── data/
│   ├── database/                    # Room SQLite: HymnEntity, HymnDao, AppDatabase (versión 2 unificada).
│   ├── repository/                  # HymnRepository: Fuente única de verdad con pre-población offline de assets.
│   └── sync/                        # HymnSyncManager: Sincronización silenciosa sobre Wi-Fi con GitHub Raw.
├── search/
│   └── FuzzyHymnSearchEngine.kt     # Motor de búsqueda instantáneo y exacto (SearchableHymn) con preservación estricta de orden.
├── ui/
│   ├── components/
│   │   ├── CanvasIcons.kt           # Iconos nativos dibujados en Compose y Canvas (AppLogo, Download, Theme).
│   │   ├── AppDrawer.kt             # Navigation Drawer lateral modular con filtrado por autor y tipografías.
│   │   ├── SearchHeader.kt          # Cabecera superior con buscador y navegación de coincidencias (< 3/12 >).
│   │   └── FastScrollbar.kt         # Barra lateral interactiva de desplazamiento rápido.
│   ├── screens/
│   │   ├── FeedHymnCard.kt          # Tarjeta individual de alabanza con resaltador de texto e interacciones.
│   │   └── HymnFeedScreen.kt        # Pantalla principal del feed continuo con LazyColumn y debounced scroll.
│   ├── viewmodel/
│   │   └── MainViewModel.kt         # ViewModel central desacoplado consumiendo HymnRepository y SearchEngine.
│   └── theme/                       # Paleta canónica (GoldenMain, ParchmentLight, JetCarbon), Theme y Tipografía.
└── util/
    └── PdfGenerator.kt              # Generador de archivos PDF para exportar letras de alabanzas.
```

### Componentes Clave:
- **`data/catalog.json` y `assets/catalog.json`**: Catálogo base estructurado que garantiza que la app arranque 100% offline con 485 cantos sin requerir conexión a internet.
- **`tools/manage_hymns.py`**: Asistente CLI interactivo todo-en-uno para agregar, editar, eliminar, validar y publicar alabanzas con `git push` automático.
- **`HymnSyncManager`**: Sincronizador en segundo plano que detecta Wi-Fi, compara versiones con GitHub Raw y actualiza Room silenciosamente a costo $0.00 perpetuo.
- **`SearchableHymn`**: Motor de búsqueda de latencia cero (0ms) precalculado y fiel al original, que busca por subcadena exacta (número/título/letra) manteniendo intacto el orden numérico de los himnos sin distorsiones.


---

## 3. Filosofía de Diseño y Estilos (Aesthetics)

El diseño de la aplicación destaca por un aire solemne y minimalista:
- **Pantalla Siempre Activa**: Se configura `FLAG_KEEP_SCREEN_ON` en la ventana para que el dispositivo no se apague mientras se lee un canto.
- **Colores Canónicos**:
  - `GoldenMain` (`#C5A03A`): Oro litúrgico cálido para destacar íconos, botones y estados activos.
  - `ParchmentLight` (`#FDFBF7`): Color pergamino cálido para fondos en modo claro, simulando hojas de papel tradicionales.
  - `JetCarbon` (`#1E242B`): Tono carbono oscuro para texto en modo claro, reduciendo el deslumbramiento.
- **Personalización de Lectura**: El usuario puede ajustar el tamaño del texto y cambiar la tipografía entre Serif, Sans y Mono desde el Drawer.
- **Ligereza del APK (Tamaño Mínimo)**: Una de las filosofías más críticas del proyecto es mantener el tamaño del APK al mínimo posible. Se deben evitar librerías de terceros pesadas, preferir iconos dibujados nativamente o programados en Compose, y asegurar que la optimización de recursos y la minificación R8 estén siempre habilitadas para producción.

---

## 4. Estándares de Programación para Agentes

Al modificar el código o añadir nuevas funcionalidades, debes seguir estas reglas estrictas:

1. **Estado Unidireccional (UDF)**:
   - Toda la lógica de negocio y filtros debe estar en `MainViewModel`.
   - Las vistas de Compose deben consumir el estado mediante `collectAsState()`.
2. **Base de Datos de Alabanzas**:
   - La lista en `HymnDataProvider.kt` se considera de solo lectura en tiempo de ejecución. Cualquier adición de alabanza permanente se realiza agregándola directamente al código fuente de este archivo.
   - Los IDs de alabanza deben ser consecutivos y ordenados de forma ascendente.
3. **Persistencia**:
   - No guardes información pesada o configuraciones directamente en archivos de texto planos personalizados; usa `SharedPreferences` para estados simples (como tipografía, tema claro/oscuro, tamaño de letra o última alabanza vista) y `Room` para los favoritos.
   - Las operaciones de guardado de configuraciones sencillas en `SharedPreferences` deben hacerse de forma asíncrona no bloqueante (usando `.apply()` en lugar de `.commit()`) para evitar bloquear el hilo principal.
4. **Construcción y Compilación (Builds)**:
   - **CRÍTICO**: No propongas ni ejecutes tareas de compilación o construcción de Gradle (como `gradlew.bat build`, `compileDebugSources`, `assembleDebug`, etc.) sin solicitar y obtener primero el permiso explícito del usuario.

---

## 5. Workflows de Agente Automatizados

Para facilitar el mantenimiento, se definen workflows específicos que automatizan tareas recurrentes:

- **Habilidad `add-hymn`**: Workflow diseñado para transformar texto plano introducido por el usuario, limpiarlo, validar que no exista duplicado en `HymnDataProvider.kt`, auto-incrementar el ID del himno e inyectarlo directamente en la estructura de código Kotlin.
  - Ubicación de la Habilidad: [.agents/skills/add-hymn/](file:///c:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/.agents/skills/add-hymn/)
  - Instrucciones de Uso: Ver archivo [SKILL.md](file:///c:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/.agents/skills/add-hymn/SKILL.md).

---

## 6. Patrones de Diseño y Buenas Prácticas (Patterns)

Para optimizar el rendimiento y ahorrar tokens de contexto, el código utiliza los siguientes patrones que debes reutilizar obligatoriamente:

1. **Precalculado de Búsqueda Difusa (`SearchableHymn`)**:
   - *Ubicación*: [MainActivity.kt:L94-L101](file:///c:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/app/src/main/java/com/example/MainActivity.kt#L94-L101)
   - *Patrón*: En `MainViewModel` se transforma la lista de alabanzas estáticas en una lista de `SearchableHymn` en el bloque `init`. Esto pre-normaliza los textos (remueve acentos, convierte a minúsculas y separa estrofas) una sola vez en el ciclo de vida del ViewModel.
   - *Ventaja*: Evita el costo de procesar la normalización de cadenas de texto y las divisiones (`split`) en el hilo de UI en cada cambio de texto en el buscador.

2. **Extensión de Normalización Tolerante a Errores (`String.normalize`)**:
   - *Ubicación*: [MainActivity.kt:L119-L123](file:///c:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/app/src/main/java/com/example/MainActivity.kt#L119-L123)
   - *Patrón*: Una extensión de Kotlin que limpia las letras utilizando `java.text.Normalizer.Form.NFD` y un patrón regex de exclusión de marcas de combinación diacrítica para remover acentos (`á` -> `a`, `é` -> `e`, etc.).

3. **Debounce en Persistencia de Scroll**:
   - *Ubicación*: [MainActivity.kt:L497-L507](file:///c:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/app/src/main/java/com/example/MainActivity.kt#L497-L507)
   - *Patrón*: El estado de scroll de Compose se observa reactivamente y se le aplica un operador `debounce(500)` antes de persistir la última alabanza leída en `SharedPreferences`.
   - *Ventaja*: Reduce drásticamente las operaciones I/O en disco durante el scroll rápido, evitando congelamiento de pantalla.

4. **Iconos Programáticos Nativos en Compose (`ImageVector.Builder`)**:
   - *Ubicación*: [MainActivity.kt:L1487-L1519](file:///c:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/app/src/main/java/com/example/MainActivity.kt#L1487-L1519) (ejemplo de `DownloadIcon`)
   - *Patrón*: Creación de iconos utilizando la API nativa `ImageVector.Builder` con rutas de vectores de dibujo manuales.
   - *Ventaja*: Minimiza la dependencia de archivos XML externos pesados y librerías de iconos voluminosas, acelerando la compilación y reduciendo el tamaño de la APK final.

5. **Dibujo con Máscaras de Canvas en Compose (`ThemeToggleIcon`)**:
   - *Ubicación*: [MainActivity.kt:L1525-L1540](file:///c:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/app/src/main/java/com/example/MainActivity.kt#L1525-L1540)
   - *Patrón*: Se dibuja un círculo base y se superpone un segundo círculo con el color del fondo en una posición desplazada para crear una luna creciente de forma geométrica sin assets.

---

## 7. Antipatrones a Evitar (Anti-patterns)

Evita repetir o expandir estos problemas arquitectónicos presentes en el codebase actual:

1. **Archivo Monolítico MainActivity.kt**:
   - *Problema*: Contiene más de 1700 líneas que combinan lógica de ViewModel, definición de clases auxiliares de estado, representaciones vectoriales de iconos y el maquetado completo de vistas Compose.
   - *Solución*: Si creas nuevas pantallas o añades componentes pesados, **no** los agregues a `MainActivity.kt`. Crea archivos individuales bajo la estructura correspondiente (ej. `ui/components/` para iconos y botones, `ui/screens/` para vistas).

2. **Color Tokens y Valores Hardcodeados**:
   - *Problema*: Aunque existen variables globales (`GoldenMain`, `ParchmentLight`, etc.), se utilizan abundantes colores hardcodeados directamente en los modificadores como `Color(0xFF1E1E1E)` o `Color(0xFFFDFBF7)` condicionados a comprobaciones manuales de `isDarkMode`.
   - *Solución*: Utiliza el sistema de temas estándar de Material Theme (`MaterialTheme.colorScheme`) en lugar de checks condicionales repetitivos de colores globales.

3. **Cálculos de Resaltado Inline en Composable**:
   - *Problema*: `buildHighlightedText` realiza búsquedas de índices e instanciación de substrings cada vez que se recompone el elemento de texto.
   - *Solución*: Delegar este tipo de procesamiento al ViewModel en segundo plano para que el Composable solo consuma una estructura `AnnotatedString` ya formateada.

4. **Acoplamiento Directo al Contexto (AndroidViewModel)**:
   - *Problema*: `MainViewModel` se inyecta directamente con la clase `Application` para obtener el contexto de base de datos.
   - *Solución*: Para funcionalidades futuras, prefiere la inyección de repositorios o proveedores de datos limpios, facilitando la realización de pruebas unitarias.

---

## 8. Planificación y Roadmap: Versión 3.0 (Offline-First)

Para la futura versión 3.0, se implementará un sistema de actualización de la aplicación respetando estrictamente la filosofía **Offline-First**:

1. **Alabanzas en Código Estático**:
   - Para no romper la filosofía offline y garantizar velocidad de acceso instantánea sin configuración de bases de datos remotas complejas, las letras de las alabanzas se mantendrán hardcodeadas en `HymnDataProvider.kt`.
2. **Actualizaciones por Petición del Usuario (User-Triggered)**:
   - La aplicación no realizará ninguna consulta automática a redes externas ni en segundo plano.
   - La funcionalidad de búsqueda de actualizaciones de la app (conectando con el repositorio de GitHub) se ejecutará **únicamente** de forma manual cuando el usuario lo active presionando un botón en los Ajustes/Sidebar.
3. **Control y Privacidad**:
   - Ninguna función debe generar tráfico de red sin el consentimiento explícito del usuario.
