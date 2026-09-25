---
name: add-hymn
description: Transforma texto plano de una alabanza, valida duplicados y la inserta estructurada en HymnDataProvider.kt con un ID consecutivo.
---

# Habilidad: add-hymn (Agregar Alabanzas sin Duplicados)

Esta habilidad permite a cualquier agente de IA o desarrollador importar cantos o himnos religiosos desde un formato de texto plano no estructurado (letras crudas) hacia la base de datos de código fuente del proyecto (`HymnDataProvider.kt`), garantizando la integridad de los datos y la ausencia de duplicados.

---

## 1. Entrada de Datos

El usuario proporcionará el canto en texto plano. Este texto típicamente contiene:
- Un título en la primera línea o indicado explícitamente.
- Opcionalmente, un autor (ej. "Tito Abarca", "Conjunto Villanueva").
- Opcionalmente, un enlace de YouTube.
- Las estrofas y coros, separados por una o más líneas vacías.

---

## 2. Flujo de Trabajo Automatizado (Workflow)

El workflow está totalmente automatizado mediante el script Python ubicado en:
`[.agents/skills/add-hymn/scripts/add_hymn.py](file:///c:/Users/HuGOD777/proyectos%20prime/ANDROID/Cancionero-Digital/.agents/skills/add-hymn/scripts/add_hymn.py)`

### Pasos que realiza el agente:
1. **Preparar el Texto Plano**: Si el usuario proporciona un bloque de texto libre en el chat, guárdalo temporalmente en un archivo de texto en el directorio scratch del agente, por ejemplo: `<appDataDir>/scratch/nuevo_himno.txt`.
2. **Ejecutar el Script**: Corre el comando de Python pasando los argumentos necesarios o la ruta del archivo.
3. **Analizar la Respuesta**:
   - **Caso de Éxito**: El script informará que la alabanza se ha añadido con éxito, indicando el nuevo ID asignado y el título.
   - **Caso de Duplicado Detectado**: El script abortará la operación sin modificar ningún archivo y te indicará qué alabanza existente coincide (por título o por fragmentos de estrofas) junto con su ID. Informa al usuario sobre este duplicado.

---

## 3. Comandos de Ejecución

### Opción A: Mediante archivo de texto (Recomendado para letras largas)
Crea un archivo temporal con la estructura:
```text
Título: Título del Canto
Autor: Nombre del Autor (Opcional)
Enlace: https://youtube.com/... (Opcional)

Estrofa I
Línea 1 de la estrofa
Línea 2 de la estrofa...

Estrofa II o Coro
Línea 1...
```
Y ejecuta:
```bash
python ".agents/skills/add-hymn/scripts/add_hymn.py" --file "ruta/del/archivo_temporal.txt"
```

### Opción B: Mediante argumentos directos en línea de comandos
```bash
python ".agents/skills/add-hymn/scripts/add_hymn.py" --title "Título del Canto" --content "Estrofa 1\nLínea 2\n\nCoro\nLínea 1" --author "Nombre" --link "https://youtube.com/..."
```

---

## 4. Criterios de Duplicación (Algoritmo de Verificación)

Para evitar duplicados sutiles (diferencias en puntuación, mayúsculas o acentos), el script de verificación realiza una normalización estricta:
1. Elimina todos los acentos y diacríticos (`á` -> `a`, `ñ` -> `n` si es necesario para búsquedas difusas, aunque en español se conserva la `ñ` pero se quita la tilde).
2. Convierte todo el texto a minúsculas y elimina signos de puntuación no esenciales.
3. Compara el título normalizado.
4. Compara fragmentos de las estrofas. Si más del 70% de las palabras de cualquier estrofa coinciden con una estrofa de una alabanza existente, se considera un duplicado potencial y se detiene la importación para revisión humana.
