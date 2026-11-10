## Resultado de la auditoría

No puedo guardar `web_forensic_audit.md` en la raíz: el perfil de esta sesión permite **leer archivos, pero no escribirlos**. El informe detallado queda a continuación para que pueda revisarse; las referencias apuntan a rutas y líneas del árbol inspeccionado. Revisé los archivos de `web/`, incluidas utilidades, componentes, configuración, catálogo y pruebas. No ejecuté pruebas ni compilación.

### Resumen ejecutivo: prioridades

1. **Corregir el orden de hooks** en `PdfStudioModal` y `InstrumentCompanionPanel`: ambos retornan antes de ejecutar todos sus hooks.
2. **Rehacer la paginación del PDF** para manejar estrofas más altas que el espacio disponible, impedir cortes fuera de página y garantizar que vista previa y PDF usen la misma geometría.
3. **Validar y unificar el silabeador**; la clasificación de vocales contiene caracteres sospechosos y no cubre toda la casuística declarada.
4. **Endurecer la inicialización y recuperación de audio** para navegadores móviles, además de liberar nodos de audio y evitar fallos no observables.
5. **Validar en runtime los datos externos y persistidos**: `response.json()` y varios `JSON.parse()` se tratan como datos tipados sin validación de esquema.
6. **Centralizar la persistencia** y corregir temporizadores/efectos que pueden quedar activos tras desmontar componentes.
7. **Consolidar `util/` y `utils/`**, tipos compartidos y reglas de configuración para reducir divergencias.

## Hallazgos detallados

### 1. Arquitectura, tipos y datos

- **Dos carpetas para utilidades**: coexisten `web/src/util/` y `web/src/utils/`. Las reglas de ubicación quedan divididas: por ejemplo, `syllabifier.ts` y `chordTransposer.ts` están en `util/`, mientras `searchEngine.ts` y `pdfGenerator.ts` están en `utils/`. Esto es un problema de consistencia y localización, aunque no demuestra por sí solo un error de ejecución.

- **Tipos del catálogo**: [hymn.ts](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/types/hymn.ts:1) define `Hymn` con `id`, `title`, `content` y campos opcionales `author`, `link`, `updatedAt`, `isDeleted` y `extraVersions`. Los primeros registros inspeccionados de [catalog.json](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/public/catalog.json:1) usan esos mismos campos. No encontré en esas muestras una discrepancia de forma; no afirmo una auditoría exhaustiva de los 1009 objetos por campo.

- **Validación ausente de datos en runtime**: [App.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/App.tsx:73) convierte la respuesta con `await res.json()` y la asigna a `HymnCatalog` en la línea 75. El tipo TypeScript no valida el JSON descargado. La ruta de caché también parsea y asigna el resultado sin comprobar el esquema en las líneas 85–88. Una respuesta válida como JSON pero con forma incorrecta puede llevar datos inválidos al render.

- **Persistencia de catálogo sin comprobar coherencia**: `catalog_version.json` declara versión 16 y total 1009 en [catalog_version.json](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/public/catalog_version.json:1). La carga de `App.tsx` no consulta este archivo ni coteja versión o conteo con `catalog.json`; solo reemplaza la caché cuando la descarga funciona. El JSON y el metadato pueden divergir sin ser detectados por esta lógica.

- **Un `any` explícito**: [App.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/App.tsx:108) declara `scrollTimeout: any`. En navegador, puede tiparse como `number | null` o con `ReturnType<typeof setTimeout>`.

- **Casts de almacenamiento**: [useSettings.ts](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/hooks/useSettings.ts:11) y línea 21 convierten valores de `localStorage` a `ThemeMode` y `TypographyType` antes de validarlos. Luego sí se comparan contra opciones permitidas, por lo que las comparaciones protegen el valor retornado, pero el cast es innecesario y puede ocultar errores durante cambios futuros.

- **Otros casts poco justificados**: [AppDrawer.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/AppDrawer.tsx:158) y [PdfStudioModal.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/PdfStudioModal.tsx:193) fuerzan arrays literales a `TypographyType[]`. Conviene definir una constante tipada compartida.

- **Exports**: los componentes y utilidades usan principalmente exports nombrados; `App.tsx` también exporta `App` con nombre. No observé mezcla significativa de exports por defecto y nombrados. Hay diferencias menores entre declarar componentes como `React.FC` y funciones ordinarias, pero no son un defecto funcional por sí mismas.

### 2. Hooks, efectos y responsabilidades

- **Orden de hooks condicionado por props — prioridad alta**: [PdfStudioModal.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/PdfStudioModal.tsx:34) retorna cuando `!isOpen || !hymn` antes de los hooks de estado y efectos que siguen. Si el componente permanece montado y `isOpen` o `hymn` cambia, React puede observar distinto número/orden de hooks entre renders. [InstrumentCompanionPanel.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/InstrumentCompanionPanel.tsx:89) hace lo mismo con `if (!isOpen) return null`, antes de cálculos y JSX pero después de sus hooks actuales; hoy sus hooks están antes de ese retorno, así que **no** viola el orden de hooks. El caso de `PdfStudioModal` sí es problemático: su retorno precede a sus hooks.

- **Estado del PDF sin reinicialización por himno**: `PdfStudioModal` inicializa `storedChords` y `config` en las líneas 40 y 58. El efecto de acordes depende de `hymn.id` en las líneas 43–55, pero no limpia el estado cuando cambia el himno y la lectura nueva no encuentra acordes guardados. Si la instancia del modal se reutiliza para otro himno, puede conservar acordes anteriores. La configuración inicial también conserva contenido/ajustes de la primera instancia salvo lo que el efecto de líneas 69–75 actualiza.

- **Lista extensa de responsabilidades en `HymnCard`**: el componente tiene 393 líneas y combina selección de versión, favoritos, copiado, lectura, controles de edición de acordes, transposición, persistencia, reproducción y render de tarjeta. Referencias: estado y carga en [HymnCard.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/HymnCard.tsx:59), persistencia en línea 79, operaciones de acordes en líneas 113–159 y render desde línea 166. La cifra de líneas es una señal de complejidad, no una métrica suficiente por sí sola.

- **`PdfStudioModal` concentra editor y presentación**: 422 líneas; administra configuración, almacenamiento, paginación, controles editoriales, preview y descarga. La paginación se recalcula en `useMemo` en [PdfStudioModal.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/PdfStudioModal.tsx:82). Candidatos naturales a separación: estado/configuración, controles y flujo de descarga.

- **`InstrumentCompanionPanel` es un componente muy grande**: 479 líneas. Contiene iconos SVG, persistencia, búsqueda/filtros, selección de instrumento, selección de acordes, audio, panel de familias y panel de inspección. Sus datos derivados `allChordsList` se filtran en cada render en [InstrumentCompanionPanel.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/InstrumentCompanionPanel.tsx:140); valorar `useMemo` si el catálogo crece o el render se vuelve costoso.

- **Escaneo del DOM en cada scroll**: [App.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/App.tsx:119) selecciona todas las tarjetas y recorre sus rectángulos tras cada evento, con debounce de escritura pero no de la búsqueda de posición. El listener es pasivo y sí se elimina en cleanup (líneas 138–142), pero para listas largas sería más eficiente un `IntersectionObserver` sobre tarjetas visibles.

- **Crecimiento repetido del límite en scroll**: líneas 112–114 incrementan `visibleLimit` en cada evento cuando se está cerca del final. Puede disparar múltiples actualizaciones antes de que crezca la página. No es una condición infinita probada, pero es una carga redundante; conviene cargar por umbral una sola vez hasta que el listado renderizado crezca.

- **Timeouts sin cleanup**: hay temporizadores en [HymnCard.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/HymnCard.tsx:107), [InstrumentCompanionPanel.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/InstrumentCompanionPanel.tsx:118), [PdfStudioModal.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/PdfStudioModal.tsx:106), [ShareModal.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/ShareModal.tsx:48) y [YouTubeLinksModal.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/YouTubeLinksModal.tsx:48). Si el componente se desmonta antes de que venzan, el callback puede intentar actualizar estado después del desmontaje. El listener principal de scroll sí limpia su timer.

- **Tiempos de deep link sin cleanup**: [App.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/App.tsx:191) programa el scroll y línea 196 programa la retirada de clases. El efecto no devuelve cleanup. Cambios posteriores de catálogo o desmontaje pueden dejar callbacks con referencias al DOM o afectar el elemento objetivo.

### 3. Repetición y persistencia

- **Persistencia distribuida**:
  - ajustes y tema: [useSettings.ts](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/hooks/useSettings.ts:4);
  - favoritos: [useFavorites.ts](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/hooks/useFavorites.ts:3);
  - catálogo y lectura: [App.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/App.tsx:78);
  - acordes: [HymnCard.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/HymnCard.tsx:68);
  - instrumento: [InstrumentCompanionPanel.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/InstrumentCompanionPanel.tsx:61).

  Cada sitio tiene manejo propio de errores, parseo y claves. Un módulo común de persistencia debería validar esquemas y centralizar claves, evitando duplicación y errores de datos heredados.

- **Silabeo repetido en rutas de producto**: [HymnLyricsWithChords.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/HymnLyricsWithChords.tsx:54) silabea líneas al renderizar; [pdfGenerator.ts](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/utils/pdfGenerator.ts:162) vuelve a hacerlo al paginar. Cada operación cumple una función distinta, pero comparte algoritmo y convención de IDs; cambios en contenido, separación de estrofas o numeración de línea pueden desalinear acordes entre pantalla y PDF.

- **Normalización repetida**: `createSearchableHymns` guarda texto normalizado en [searchEngine.ts](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/utils/searchEngine.ts:19), mientras `extractMatches` vuelve a normalizar cada estrofa en líneas 94–96. Puede precalcularse por estrofa para reducir trabajo en cada búsqueda.

### 4. Casos borde en lógica

#### Audio

- `getAudioContext()` crea un contexto compartido en [chordAudio.ts](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/util/chordAudio.ts:42). Cuando está suspendido, invoca `resume()` y descarta el rechazo con `.catch(() => {})` en líneas 50–52. La función no espera a que la reanudación termine antes de programar fuentes en `playAcousticChord` (líneas 60–67). En dispositivos con política de activación por gesto o resume rechazado, puede no sonar y no hay feedback al usuario.

- La reproducción puede llamarse en handlers asíncronos y no siempre propagarse/esperarse igual. `HymnCard` silencia rechazos en [HymnCard.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/HymnCard.tsx:154); el panel hace lo mismo en [InstrumentCompanionPanel.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/InstrumentCompanionPanel.tsx:123). Esto hace indistinguible una restricción del navegador de un fallo de síntesis.

- Los osciladores se detienen explícitamente en [chordAudio.ts](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/util/chordAudio.ts:98), lo cual es positivo. Pero no se desconectan explícitamente ni se programa limpieza del `GainNode`, `BiquadFilter` y ganancia maestra (líneas 71–99). El navegador suele liberar nodos terminados al quedar desconectados o recolectables, así que la fuga no queda demostrada; la vida de nodos y conexiones puede hacerse más explícita desconectando en `ended`.

- **Riesgo de frecuencia inválida**: [getChordFrequencies](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/util/chordAudio.ts:24) acepta cualquier `fret >= 0`. No valida longitud, enteros ni límites de traste. Datos dañados podrían producir frecuencias absurdas o programación inválida.

#### Silabeador

- Las tablas [syllabifier.ts](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/util/syllabifier.ts:11)–13 contienen caracteres mostrados como `ǭ`, `Ǹ`, `��`, `�?`, `�%`, `�"` y `�o`. Esto exige verificar los bytes/codificación del archivo y los datos fuente: la representación observada no corresponde a un inventario legible y confiable de vocales españolas. Si esos caracteres están realmente en el archivo, letras como `á`, `é`, `í`, `ó`, `ú`, `ü` no quedan correctamente clasificadas por esos `Set`; si la corrupción pertenece solo a la visualización, la conclusión debe revisarse contra el contenido en UTF-8.

- El código reconoce hiatos por pares adyacentes en líneas 19–28, pero no representa explícitamente secuencias de tres vocales. Tampoco aplica reglas contextuales de acentuación ni excepciones lexicales; por tanto, la afirmación de que cubre casos de acentuación y diptongos es demasiado amplia para este algoritmo.

- La separación de consonantes solo contempla grupos de dos o tres consonantes entre vocales en líneas 69–92. Hay excepciones ortográficas y grupos que requieren conocimiento lingüístico no capturado por una tabla corta.

- `syllabifyWord` recibe toda la cadena como palabra en líneas 37–43 y su comentario de “separar puntuación externa” en 40 no se implementa en esa función. La limpieza de signos se hace aparte en `splitLineIntoSyllables` con regexes en líneas 127–137; símbolos como guiones, elipsis, comillas tipográficas y signos de apertura no cubiertos pueden quedar pegados a tokens o no separarse como se espera.

#### Transporte armónico y notación

- `transposeChord` interpreta solo raíz + un accidental (`#` o `b`) mediante regex en [chordTransposer.ts](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/util/chordTransposer.ts:27). Conserva el resto como sufijo sin analizarlo, lo cual admite muchos sufijos sin validarlos, pero no entiende grafías de doble sostenido/bemol ni reescritura contextual de `slash chords`.

- Las enharmonías se colapsan a una escala canónica fija en líneas 1–3 y se acepta Re# como índice de Mi bemol en líneas 8–10. La transposición puede cambiar grafía de `D#` a `Eb` o viceversa, sin preservar la preferencia tonal del usuario. La salida es consistente, pero no musicalmente contextual.

- El mapa de notación latina usa solfeo con alteraciones mixtas en [chordNotation.ts](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/util/chordNotation.ts:3). Por ejemplo, `c#`→`DO#`, `db`→`REb`; la conversión es raíz literal, no enharmónica. Está bien si esa política es intencional; falta documentar y unificarla con el transpositor.

#### PDF

- **Una estrofa puede exceder el área imprimible**: en [pdfGenerator.ts](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/utils/pdfGenerator.ts:211) `willFit` se fuerza a verdadero cuando `currentPageStanzas.length === 0` (línea 213). Una estrofa muy larga se coloca completa aunque exceda `maxY`; no se subdivide.

- **El renderer puede dibujar fuera de los márgenes**: la paginación reserva `footerBottomY` en líneas 146–152, pero el dibujo avanza `currentY` línea por línea en [pdfGenerator.ts](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/utils/pdfGenerator.ts:362) y no comprueba límites ni inicia una página nueva. Para líneas largas tampoco se ajusta el ancho: el texto sin acordes se pinta con alineación centrada en línea 422, y el ancho de línea con acordes se centra en líneas 378–388 sin limitarse a `contentWidth`.

- **Geometría fija duplicada**: preview usa proporción A4 en [PdfPagePreview.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/PdfPagePreview.tsx:35) y padding `p-[8%]` en línea 37. El generador usa `pageWidth = 210`, `margin = 22`, encabezado 22 y pie 278 en [pdfGenerator.ts](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/utils/pdfGenerator.ts:286). No hay una configuración compartida que garantice que el preview reproduzca los mismos saltos y márgenes del documento.

- **Desajuste de métricas**: `paginateHymnContent` calcula `titleBlockHeight = 26` en línea 148, pero el render del título puede consumir más de eso cuando se parte en varias líneas; `buildHymnPdfDocument` usa 18 pt y suma 8 mm por línea en líneas 334–343. Una alabanza con título largo puede invalidar los saltos calculados.

- **Números mágicos sin configuración común**: alturas, márgenes, tamaño de título, desplazamiento de acordes, gaps, umbrales y dimensiones están repartidos entre ambos pasos (por ejemplo líneas 146–152 y 286–295). Cambiar uno sin el otro rompe la correspondencia entre preview y PDF.

### 5. Estilos y configuración

- Hay estilos en línea en [HymnCard.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/HymnCard.tsx:367) y [PdfPagePreview.tsx](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/src/components/PdfPagePreview.tsx:38). En ambos casos el tamaño es dinámico por estado/configuración, un uso razonable de `style`; no es redundante con Tailwind por definición. El resto de diseño usa mayormente clases utilitarias.

- [index.html](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/index.html:6) desactiva zoom con `maximum-scale=1.0, user-scalable=no`, lo que restringe zoom accesible en móviles. Debe retirarse salvo que exista una necesidad funcional bien justificada.

- [index.html](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/index.html:8) y [manifest.json](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/public/manifest.json:4) muestran cadenas con caracteres dañados en la lectura: “cǭnticos”, “adoraci��n”, “bǧsqueda”. Si los bytes almacenados realmente contienen esos caracteres, los metadatos visibles y accesibles están corruptos. Conviene confirmar encoding UTF-8 antes de corregirlos.

- [package.json](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/package.json:6) ofrece `build` y `test`; `vite.config.ts` únicamente fija el puerto 3000 en [vite.config.ts](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/vite.config.ts:7). [tsconfig.json](/C:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/web/tsconfig.json:14) activa `strict`, `noUnusedLocals` y `noUnusedParameters`, lo cual es positivo. No observé reglas de lint o formateo configuradas en el paquete.

## Alcance y verificación

La inspección fue estática. Las conclusiones sobre audio móvil, encoding de archivo y apariencia final del PDF necesitan reproducirse en los navegadores/dispositivos de destino para confirmar el impacto. No se ejecutaron pruebas, según las instrucciones de la sesión. No pude crear el archivo solicitado por el bloqueo de escritura del entorno.