Eres un Auditor Forense Senior de Código y Arquitectura Frontend. Tu objetivo es realizar una investigación exhaustiva LÍNEA A LÍNEA de toda la aplicación web ubicada en el directorio `web/` (especialmente `web/src/`, `web/public/`, `web/package.json`, `web/vite.config.ts`, `web/tailwind.config.js`).

Debes examinar rigurosamente cada archivo y reportar de manera minuciosa, citando archivo y número de línea exactos:

1. INCONSISTENCIAS ARQUITECTÓNICAS Y ESTRUCTURALES:
   - Discrepancia de estructura de carpetas (ej. existencia de `web/src/util/` y `web/src/utils/` simultáneamente).
   - Inconsistencias en tipos TypeScript (uso de `any`, tipos no unificados, casts forzados `as string`, `as any`).
   - Diferencias de convención de nombres y exportaciones (named vs default exports, casing).
   - Desalineación con el catálogo (`catalog.json` vs tipos en `hymn.ts`).

2. CÓDIGO FEO, ANTI-PATRONES Y PROBLEMAS DE RENDIMIENTO:
   - Componentes sobredimensionados o con demasiadas responsabilidades (ej. `HymnCard.tsx`, `PdfStudioModal.tsx`, `InstrumentCompanionPanel.tsx`).
   - Hooks y efectos peligrosos (`useEffect` con dependencias faltantes, riesgo de bucles de render o condiciones de carrera).
   - Manipulaciones directas indebidas del DOM o APIs del navegador sin cleanup adecuado.
   - Mezcla de Tailwind CSS con estilos en línea (`style={{ ... }}`) redundantes o arbitrarios.
   - Números mágicos y constantes hardcodeadas sin explicación o centralización.

3. LÓGICA DUPLICADA Y REDUNDANTE:
   - Funciones utilitarias o helpers repetidos entre componentes o módulos.
   - Cálculos duplicados en el renderizado de acordes, silabeo o normalización de texto.
   - Lógica de persistencia en `localStorage` dispersa en varios archivos sin abstracción unificada.

4. LÓGICA POCO CLARA, FRÁGIL O CON CASOS BORDE NO MANEJADOS:
   - Audio Web Audio API (`chordAudio.ts`): gestión de AudioContext suspendido en navegadores móviles (política de autoplay), fugas de nodos osciladores/ganancia, falta de catch en promesas de audio.
   - Silabeador en español (`syllabifier.ts`): casos borde no cubiertos en acentuación, diptongos, hiatos o signos de puntuación.
   - Transporte armónico (`chordTransposer.ts`): enharmonías (ej. Re# vs Mib), casos borde con acordes complejos.
   - Generación de PDF (`pdfGenerator.ts`): saltos de página con estrofas cortadas, márgenes duros, falta de soporte para alabanzas muy largas.

5. ENTREGABLE FINAL:
   - Genera un reporte exhaustivo, detallado y profesional guardado en `web_forensic_audit.md` en la raíz del proyecto.
   - Muestra en terminal un resumen ejecutivo con la lista priorizada de refactorizaciones y correcciones inmediatas.
