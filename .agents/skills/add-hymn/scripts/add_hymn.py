#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import os
import re
import sys
import argparse
import unicodedata

def normalize_text(text):
    if not text:
        return ""
    # Convertir a minúsculas
    text = text.lower()
    # Separar caracteres combinados (acentos)
    text = "".join(
        c for c in unicodedata.normalize('NFD', text)
        if unicodedata.category(c) != 'Mn'
    )
    # Reemplazar puntuación no esencial por espacios
    text = re.sub(r'[^a-z0-9ñ\s]', ' ', text)
    # Colapsar espacios
    text = re.sub(r'\s+', ' ', text).strip()
    return text

def get_words(text):
    return set(normalize_text(text).split())

def stanza_similarity(s1, s2):
    w1 = get_words(s1)
    w2 = get_words(s2)
    if not w1 or not w2:
        return 0.0
    intersection = w1.intersection(w2)
    union = w1.union(w2)
    return len(intersection) / len(union)

def parse_plain_text(text):
    lines = [line.strip() for line in text.split('\n')]
    # Limpiar líneas vacías iniciales
    while lines and not lines[0]:
        lines.pop(0)

    title = ""
    author = ""
    link = ""
    content_start_idx = 0

    # Analizar si hay prefijos explícitos
    explicit = False
    for i, line in enumerate(lines[:5]):
        lower_line = line.lower()
        if lower_line.startswith(("titulo:", "title:", "título:")):
            title = line.split(":", 1)[1].strip()
            explicit = True
            content_start_idx = max(content_start_idx, i + 1)
        elif lower_line.startswith(("autor:", "author:")):
            author = line.split(":", 1)[1].strip()
            explicit = True
            content_start_idx = max(content_start_idx, i + 1)
        elif lower_line.startswith(("enlace:", "link:", "youtube:", "url:")):
            link = line.split(":", 1)[1].strip()
            explicit = True
            content_start_idx = max(content_start_idx, i + 1)

    if not explicit and lines:
        # Heurística: Primera línea no vacía es el título
        title = lines[0]
        # Si la segunda es corta y la tercera es vacía, asumimos autor
        if len(lines) > 2 and lines[1] and not lines[2] and len(lines[1]) < 45:
            author = lines[1]
            content_start_idx = 3
        else:
            content_start_idx = 1

    content_lines = lines[content_start_idx:]
    content = "\n".join(content_lines).strip()
    return title, author, link, content

def find_hymn_data_provider():
    # Buscar en rutas típicas
    possible_paths = [
        os.path.join("app", "src", "main", "java", "com", "example", "ui", "HymnDataProvider.kt"),
        os.path.join("..", "..", "app", "src", "main", "java", "com", "example", "ui", "HymnDataProvider.kt"),
        os.path.join("..", "..", "..", "app", "src", "main", "java", "com", "example", "ui", "HymnDataProvider.kt")
    ]
    # Buscar desde el directorio de este script
    script_dir = os.path.dirname(os.path.abspath(__file__))
    for path in possible_paths:
        abs_path = os.path.abspath(os.path.join(script_dir, path))
        if os.path.exists(abs_path):
            return abs_path
        # También buscar relativo al directorio de ejecución actual
        abs_path_cwd = os.path.abspath(path)
        if os.path.exists(abs_path_cwd):
            return abs_path_cwd
    return None

def main():
    parser = argparse.ArgumentParser(description="Transforma e inyecta alabanzas sin duplicados en HymnDataProvider.kt")
    parser.add_argument("--file", help="Ruta de un archivo de texto plano con la alabanza")
    parser.add_argument("--title", help="Título de la alabanza")
    parser.add_argument("--content", help="Contenido completo de la alabanza (estrofas)")
    parser.add_argument("--author", default="", help="Autor de la alabanza")
    parser.add_argument("--link", default="", help="Enlace de YouTube de la alabanza")
    
    args = parser.parse_args()

    # Obtener valores de entrada
    title, author, link, content = "", "", "", ""
    if args.file:
        if not os.path.exists(args.file):
            print(f"Error: El archivo especificado no existe: {args.file}")
            sys.exit(1)
        with open(args.file, "r", encoding="utf-8") as f:
            file_text = f.read()
        title, author, link, content = parse_plain_text(file_text)
    else:
        title = args.title or ""
        content = args.content or ""
        author = args.author or ""
        link = args.link or ""

    # Validar campos requeridos
    if not title:
        print("Error: Se requiere especificar un título (mediante --title o en el archivo)")
        sys.exit(1)
    if not content:
        print("Error: Se requiere especificar el contenido/letra (mediante --content o en el archivo)")
        sys.exit(1)

    # Buscar HymnDataProvider.kt
    provider_path = find_hymn_data_provider()
    if not provider_path:
        print("Error: No se pudo encontrar el archivo HymnDataProvider.kt en las rutas estándar.")
        sys.exit(1)

    print(f"Leyendo base de datos de alabanzas desde: {provider_path}")
    with open(provider_path, "r", encoding="utf-8") as f:
        file_content = f.read()

    # Regex para extraer todos los himnos existentes
    # Match: Hymn( id = X, title = "...", link = "...", author = "...", content = """...""" )
    hymn_pattern = re.compile(
        r'Hymn\(\s*id\s*=\s*(\d+)\s*,\s*title\s*=\s*"([^"]*)"\s*,\s*link\s*=\s*"([^"]*)"\s*,\s*author\s*=\s*"([^"]*)"\s*,\s*content\s*=\s*"""(.*?)"""\s*\)',
        re.DOTALL
    )

    hymns = []
    max_id = 0
    matches = list(hymn_pattern.finditer(file_content))

    for match in matches:
        h_id = int(match.group(1))
        h_title = match.group(2)
        h_link = match.group(3)
        h_author = match.group(4)
        h_content = match.group(5)
        
        hymns.append({
            "id": h_id,
            "title": h_title,
            "link": h_link,
            "author": h_author,
            "content": h_content,
            "match": match
        })
        if h_id > max_id:
            max_id = h_id

    # 1. Validar Duplicados por Título
    norm_new_title = normalize_text(title)
    for hymn in hymns:
        if normalize_text(hymn["title"]) == norm_new_title:
            print(f"\n[!] DUPLICADO DETECTADO: El título '{title}' ya existe en el himno ID {hymn['id']}.")
            print(f"    Título existente: \"{hymn['title']}\"")
            print("    Operación abortada para prevenir duplicados.")
            sys.exit(1)

    # 2. Validar Duplicados por Contenido (Estrofas)
    new_stanzas = [s.strip() for s in content.split("\n\n") if s.strip()]
    for hymn in hymns:
        exist_stanzas = [s.strip() for s in hymn["content"].split("\n\n") if s.strip()]
        for ns in new_stanzas:
            for es in exist_stanzas:
                sim = stanza_similarity(ns, es)
                if sim > 0.75:
                    print(f"\n[!] DUPLICADO DETECTADO: Se encontró alta similitud ({sim:.1%}) en una estrofa con el himno ID {hymn['id']}.")
                    print(f"    Himno existente: \"{hymn['title']}\" ({hymn['author'] or 'Sin autor'})")
                    print(f"    Estrofa nueva:\n    ---\n{ns}\n    ---")
                    print(f"    Estrofa existente:\n    ---\n{es}\n    ---")
                    print("    Operación abortada para prevenir duplicados.")
                    sys.exit(1)

    # No hay duplicados. Generar el nuevo ID.
    new_id = max_id + 1
    print(f"No se encontraron duplicados. Asignando nuevo ID: {new_id}")

    # Escapar caracteres en Kotlin
    # En Kotlin, el símbolo '$' se usa para interpolación de cadenas. Debemos escaparlo como '\$' en el string.
    escaped_content = content.replace('$', '\\$')
    escaped_title = title.replace('"', '\\"')
    escaped_author = author.replace('"', '\\"')
    escaped_link = link.replace('"', '\\"')

    # Generar bloque Kotlin para el nuevo himno
    new_hymn_code = (
        f"        Hymn(\n"
        f"            id = {new_id},\n"
        f"            title = \"{escaped_title}\",\n"
        f"            link = \"{escaped_link}\",\n"
        f"            author = \"{escaped_author}\",\n"
        f"            content = \"\"\"{escaped_content}\"\"\"\n"
        f"        )"
    )

    # Encontrar la última ocurrencia para insertar
    if not matches:
        print("Error: No se encontraron bloques 'Hymn(...)' en HymnDataProvider.kt. ¿El archivo está vacío o tiene otro formato?")
        sys.exit(1)

    last_match = matches[-1]
    last_match_text = last_match.group(0)
    
    # Reemplazar la última ocurrencia agregándole una coma y el nuevo bloque
    replacement = last_match_text + ",\n" + new_hymn_code
    
    start_pos = last_match.start()
    end_pos = last_match.end()
    
    new_file_content = file_content[:start_pos] + replacement + file_content[end_pos:]

    # Escribir de vuelta a HymnDataProvider.kt
    with open(provider_path, "w", encoding="utf-8") as f:
        f.write(new_file_content)

    print(f"\n[+] ÉXITO: Alabanza añadida correctamente.")
    print(f"    ID: {new_id}")
    print(f"    Título: {title}")
    print(f"    Autor: {author if author else 'No especificado'}")
    print(f"    Enlace: {link if link else 'No especificado'}")

if __name__ == "__main__":
    main()
