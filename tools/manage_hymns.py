#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
tools/manage_hymns.py
Herramienta de administración para el catálogo de Cancionero Digital.
Permite agregar, editar, eliminar, validar y publicar alabanzas con sincronización a GitHub.
"""

import os
import sys
import json
import re
import shutil
import argparse
import subprocess
import unicodedata
from datetime import datetime, timezone
from pathlib import Path

DEFAULT_CATALOG_PATH = Path("data/catalog.json")
DEFAULT_VERSION_PATH = Path("data/catalog_version.json")
DEFAULT_ASSETS_PATH = Path("app/src/main/assets/catalog.json")

def normalize_text(text: str) -> str:
    """Normaliza texto eliminando acentos, caracteres diacríticos y puntuación para comparaciones."""
    if not text:
        return ""
    text = text.lower()
    text = "".join(
        c for c in unicodedata.normalize("NFD", text)
        if unicodedata.category(c) != "Mn"
    )
    text = re.sub(r"[^a-z0-9ñ\s]", " ", text)
    return re.sub(r"\s+", " ", text).strip()

def get_words(text: str) -> set:
    """Obtiene el conjunto de palabras normalizadas."""
    return set(normalize_text(text).split())

def stanza_similarity(s1: str, s2: str) -> float:
    """Calcula similitud de Jaccard entre dos estrofas."""
    w1 = get_words(s1)
    w2 = get_words(s2)
    if not w1 or not w2:
        return 0.0
    intersection = w1.intersection(w2)
    union = w1.union(w2)
    return len(intersection) / len(union)

def load_catalog(catalog_path: Path = DEFAULT_CATALOG_PATH) -> dict:
    """Carga el catálogo desde disco."""
    if not catalog_path.exists():
        raise FileNotFoundError(f"No se encontró el catálogo en {catalog_path}")
    with open(catalog_path, "r", encoding="utf-8") as f:
        return json.load(f)

def save_catalog(catalog: dict,
                 catalog_path: Path = DEFAULT_CATALOG_PATH,
                 version_path: Path = DEFAULT_VERSION_PATH,
                 assets_path: Path = DEFAULT_ASSETS_PATH) -> None:
    """Guarda el catálogo actualizado en disco y actualiza los metadatos."""
    now_iso = datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")
    catalog["updatedAt"] = now_iso
    catalog["totalHymns"] = len([h for h in catalog.get("hymns", []) if not h.get("isDeleted", False)])

    catalog_dir = catalog_path.parent
    catalog_dir.mkdir(parents=True, exist_ok=True)

    catalog_str = json.dumps(catalog, indent=2, ensure_ascii=False)
    with open(catalog_path, "w", encoding="utf-8") as f:
        f.write(catalog_str)

    if version_path:
        version_dir = version_path.parent
        version_dir.mkdir(parents=True, exist_ok=True)
        version_info = {
            "version": catalog.get("version", 1),
            "updatedAt": now_iso,
            "totalHymns": catalog["totalHymns"]
        }
        with open(version_path, "w", encoding="utf-8") as f:
            json.dump(version_info, f, indent=2, ensure_ascii=False)

    if assets_path:
        assets_dir = assets_path.parent
        assets_dir.mkdir(parents=True, exist_ok=True)
        with open(assets_path, "w", encoding="utf-8") as f:
            f.write(catalog_str)

def check_duplicate(title: str, content: str, hymns: list, threshold: float = 0.7) -> tuple:
    """
    Verifica si una alabanza ya existe por título idéntico o similitud de estrofas (>threshold).
    Retorna (is_duplicate: bool, reason: str, matching_id: int | None)
    """
    norm_new_title = normalize_text(title)
    new_stanzas = [s.strip() for s in content.split("\n\n") if s.strip()]

    for h in hymns:
        if h.get("isDeleted", False):
            continue
        h_id = h.get("id")
        h_title = h.get("title", "")
        h_content = h.get("content", "")

        # 1. Chequeo de título
        if normalize_text(h_title) == norm_new_title:
            return True, f"El título coincide exactamente con la alabanza #{h_id}: '{h_title}'", h_id

        # 2. Chequeo de estrofas
        existing_stanzas = [s.strip() for s in h_content.split("\n\n") if s.strip()]
        for ns in new_stanzas:
            if len(get_words(ns)) < 4:
                continue
            for es in existing_stanzas:
                sim = stanza_similarity(ns, es)
                if sim >= threshold:
                    return True, f"Estrofa duplicada ({int(sim*100)}% de coincidencia) con alabanza #{h_id} '{h_title}'", h_id

    return False, "Sin duplicados", None

def add_hymn(catalog: dict, title: str, content: str, author: str = "", link: str = "", force: bool = False) -> tuple:
    """
    Agrega una alabanza validando duplicados y asignando ID secuencial.
    Retorna (success: bool, message: str, hymn_id: int | None)
    """
    title = title.strip()
    content = content.strip()
    author = author.strip()
    link = link.strip()

    if not title:
        return False, "El título no puede estar vacío.", None
    if not content:
        return False, "El contenido/letra no puede estar vacío.", None

    hymns = catalog.get("hymns", [])
    if not force:
        is_dup, reason, dup_id = check_duplicate(title, content, hymns)
        if is_dup:
            return False, f"Alabanza rechazada por duplicado: {reason}", dup_id

    # Asignar próximo ID
    existing_ids = [h.get("id", 0) for h in hymns]
    next_id = max(existing_ids, default=0) + 1

    now_iso = datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")
    new_hymn = {
        "id": next_id,
        "title": title,
        "link": link,
        "author": author,
        "content": content,
        "updatedAt": now_iso,
        "isDeleted": False
    }
    hymns.append(new_hymn)
    catalog["hymns"] = hymns
    return True, f"Alabanza #{next_id} '{title}' agregada exitosamente.", next_id

def edit_hymn(catalog: dict, hymn_id: int, title: str = None, content: str = None, author: str = None, link: str = None) -> tuple:
    """Edita una alabanza existente por su ID."""
    hymns = catalog.get("hymns", [])
    target = None
    for h in hymns:
        if h.get("id") == hymn_id:
            target = h
            break

    if not target:
        return False, f"No se encontró ninguna alabanza con ID #{hymn_id}."

    if title is not None and title.strip():
        target["title"] = title.strip()
    if content is not None and content.strip():
        target["content"] = content.strip()
    if author is not None:
        target["author"] = author.strip()
    if link is not None:
        target["link"] = link.strip()

    target["updatedAt"] = datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")
    return True, f"Alabanza #{hymn_id} '{target['title']}' actualizada correctamente."

def delete_hymn(catalog: dict, hymn_id: int, soft_delete: bool = True) -> tuple:
    """Elimina una alabanza (lógica por defecto para preservar historial de IDs, o física)."""
    hymns = catalog.get("hymns", [])
    for idx, h in enumerate(hymns):
        if h.get("id") == hymn_id:
            title = h.get("title", "")
            if soft_delete:
                h["isDeleted"] = True
                h["updatedAt"] = datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")
                return True, f"Alabanza #{hymn_id} '{title}' marcada como eliminada."
            else:
                hymns.pop(idx)
                return True, f"Alabanza #{hymn_id} '{title}' eliminada físicamente del catálogo."
    return False, f"No se encontró alabanza con ID #{hymn_id}."

def validate_catalog(catalog: dict) -> tuple:
    """
    Valida la consistencia e integridad del catálogo.
    Retorna (is_valid: bool, issues: list)
    """
    issues = []
    hymns = catalog.get("hymns", [])
    if not isinstance(hymns, list):
        return False, ["El campo 'hymns' debe ser una lista."]

    seen_ids = set()
    for h in hymns:
        h_id = h.get("id")
        if h_id is None:
            issues.append(f"Alabanza sin ID: {h.get('title', 'Sin título')}")
        elif h_id in seen_ids:
            issues.append(f"ID duplicado detectado: #{h_id}")
        else:
            seen_ids.add(h_id)

        if not h.get("title", "").strip():
            issues.append(f"Alabanza #{h_id} tiene título vacío.")
        if not h.get("content", "").strip() and not h.get("isDeleted", False):
            issues.append(f"Alabanza activa #{h_id} tiene contenido vacío.")

    return len(issues) == 0, issues

def publish_catalog(repo_dir: str = ".", auto_push: bool = True) -> tuple:
    """
    Incrementa la versión del catálogo, guarda copias y ejecuta git add, commit y push.
    """
    catalog = load_catalog()
    is_valid, issues = validate_catalog(catalog)
    if not is_valid:
        return False, f"Error: El catálogo contiene errores de validación:\n" + "\n".join(issues)

    current_version = catalog.get("version", 1)
    new_version = current_version + 1
    catalog["version"] = new_version
    save_catalog(catalog)

    print(f"Versión incrementada de v{current_version} -> v{new_version}")

    if not auto_push:
        return True, f"Catálogo guardado localmente en versión v{new_version}. (Push omitido)."

    try:
        # Git operations
        subprocess.run(["git", "add", "data/catalog.json", "data/catalog_version.json", "app/src/main/assets/catalog.json"],
                       check=True, cwd=repo_dir, capture_output=True, text=True)
        commit_msg = f"Actualizar catálogo de alabanzas (v{new_version})"
        subprocess.run(["git", "commit", "-m", commit_msg],
                       check=True, cwd=repo_dir, capture_output=True, text=True)
        print("Git commit creado exitosamente.")
        
        push_res = subprocess.run(["git", "push", "origin", "main"],
                                  cwd=repo_dir, capture_output=True, text=True)
        if push_res.returncode == 0:
            return True, f"¡Éxito! Catálogo v{new_version} publicado y sincronizado con GitHub."
        else:
            return False, f"Commit realizado, pero falló git push: {push_res.stderr.strip()}"
    except subprocess.CalledProcessError as e:
        return False, f"Error ejecutando git: {e.stderr if hasattr(e, 'stderr') else str(e)}"

def parse_plain_text(text: str) -> tuple:
    """Parsea un bloque de texto plano extrayendo título, autor, link y estrofas."""
    lines = [line.strip() for line in text.split("\n")]
    while lines and not lines[0]:
        lines.pop(0)

    title, author, link = "", "", ""
    content_start_idx = 0
    explicit = False

    for i, line in enumerate(lines[:5]):
        lower = line.lower()
        if lower.startswith(("titulo:", "title:", "título:")):
            title = line.split(":", 1)[1].strip()
            explicit = True
            content_start_idx = max(content_start_idx, i + 1)
        elif lower.startswith(("autor:", "author:")):
            author = line.split(":", 1)[1].strip()
            explicit = True
            content_start_idx = max(content_start_idx, i + 1)
        elif lower.startswith(("enlace:", "link:", "youtube:", "url:")):
            link = line.split(":", 1)[1].strip()
            explicit = True
            content_start_idx = max(content_start_idx, i + 1)

    if not explicit and lines:
        title = lines[0]
        if len(lines) > 2 and lines[1] and not lines[2] and len(lines[1]) < 45:
            author = lines[1]
            content_start_idx = 3
        else:
            content_start_idx = 1

    content_lines = lines[content_start_idx:]
    content = "\n".join(content_lines).strip()
    return title, author, link, content

def run_interactive_menu():
    """Menú interactivo en terminal para el administrador."""
    print("=" * 60)
    print("   📖 CANCIONERO DIGITAL - ASISTENTE DE ADMINISTRACIÓN")
    print("=" * 60)

    try:
        catalog = load_catalog()
    except Exception as e:
        print(f"Error cargando catálogo: {e}")
        return

    while True:
        v = catalog.get("version", 1)
        total = len([h for h in catalog.get("hymns", []) if not h.get("isDeleted", False)])
        print(f"\n[Catálogo actual: Versión v{v} | {total} alabanzas activas]")
        print("1. ➕ Agregar nueva alabanza")
        print("2. ✏️  Editar / Corregir alabanza existente")
        print("3. 🗑️  Eliminar alabanza")
        print("4. 🔍 Buscar alabanza")
        print("5. 🛡️  Validar integridad del catálogo")
        print("6. 🚀 Publicar cambios a GitHub (commit + push)")
        print("0. 🚪 Salir")

        choice = input("\nSelecciona una opción (0-6): ").strip()

        if choice == "1":
            print("\n--- AGREGAR NUEVA ALABANZA ---")
            mode = input("¿Importar desde archivo de texto? (s/n): ").strip().lower()
            if mode == "s":
                file_path = input("Ingresa la ruta del archivo (.txt): ").strip()
                if not os.path.exists(file_path):
                    print("❌ El archivo no existe.")
                    continue
                with open(file_path, "r", encoding="utf-8") as f:
                    raw = f.read()
                t, a, l, c = parse_plain_text(raw)
            else:
                t = input("Título: ").strip()
                a = input("Autor (opcional): ").strip()
                l = input("Enlace de YouTube (opcional): ").strip()
                print("Escribe o pega la letra (estrofas separadas por línea en blanco).")
                print("Para finalizar, escribe 'FIN' en una línea vacía:")
                content_lines = []
                while True:
                    line = input()
                    if line.strip() == "FIN":
                        break
                    content_lines.append(line)
                c = "\n".join(content_lines).strip()

            ok, msg, h_id = add_hymn(catalog, t, c, a, l)
            if ok:
                save_catalog(catalog)
                print(f"✅ {msg}")
            else:
                print(f"❌ {msg}")

        elif choice == "2":
            print("\n--- EDITAR ALABANZA ---")
            h_id_str = input("Ingresa el ID de la alabanza a editar: ").strip()
            if not h_id_str.isdigit():
                print("❌ ID inválido.")
                continue
            h_id = int(h_id_str)
            target = next((h for h in catalog.get("hymns", []) if h.get("id") == h_id), None)
            if not target:
                print(f"❌ No existe alabanza con ID #{h_id}")
                continue

            print(f"Alabanza encontrada: #{target['id']} - {target['title']} ({target.get('author','')})")
            new_title = input(f"Nuevo título [{target['title']}]: ").strip()
            new_author = input(f"Nuevo autor [{target.get('author','')}]: ").strip()
            new_link = input(f"Nuevo enlace [{target.get('link','')}]: ").strip()
            edit_content = input("¿Deseas reemplazar la letra? (s/n): ").strip().lower()
            new_content = None
            if edit_content == "s":
                print("Pega la nueva letra. Escribe 'FIN' para terminar:")
                content_lines = []
                while True:
                    line = input()
                    if line.strip() == "FIN":
                        break
                    content_lines.append(line)
                new_content = "\n".join(content_lines).strip()

            ok, msg = edit_hymn(catalog, h_id,
                                title=new_title or None,
                                content=new_content,
                                author=new_author or None,
                                link=new_link or None)
            if ok:
                save_catalog(catalog)
                print(f"✅ {msg}")
            else:
                print(f"❌ {msg}")

        elif choice == "3":
            print("\n--- ELIMINAR ALABANZA ---")
            h_id_str = input("Ingresa el ID de la alabanza a eliminar: ").strip()
            if not h_id_str.isdigit():
                print("❌ ID inválido.")
                continue
            h_id = int(h_id_str)
            confirm = input(f"¿Estás seguro de eliminar la alabanza #{h_id}? (s/n): ").strip().lower()
            if confirm == "s":
                ok, msg = delete_hymn(catalog, h_id, soft_delete=True)
                if ok:
                    save_catalog(catalog)
                    print(f"✅ {msg}")
                else:
                    print(f"❌ {msg}")

        elif choice == "4":
            print("\n--- BUSCAR ALABANZA ---")
            query = input("Buscar por título o fragmento: ").strip()
            norm_q = normalize_text(query)
            matches = []
            for h in catalog.get("hymns", []):
                if h.get("isDeleted", False):
                    continue
                if norm_q in normalize_text(h.get("title", "")) or norm_q in normalize_text(h.get("content", "")):
                    matches.append(h)
            print(f"\nResultados encontrados ({len(matches)}):")
            for m in matches[:10]:
                print(f"  #{m['id']} - {m['title']} | {m.get('author', 'Sin autor')}")
            if len(matches) > 10:
                print(f"  ... y {len(matches)-10} más.")

        elif choice == "5":
            print("\n--- VALIDAR CATÁLOGO ---")
            ok, issues = validate_catalog(catalog)
            if ok:
                print("✅ El catálogo está 100% íntegro y sin errores.")
            else:
                print("❌ Se encontraron los siguientes problemas:")
                for issue in issues:
                    print(f"   - {issue}")

        elif choice == "6":
            print("\n--- PUBLICAR CAMBIOS A GITHUB ---")
            confirm = input("¿Confirmas incrementar versión y subir cambios a GitHub? (s/n): ").strip().lower()
            if confirm == "s":
                ok, msg = publish_catalog(auto_push=True)
                if ok:
                    print(f"🎉 {msg}")
                    catalog = load_catalog() # Recargar versión
                else:
                    print(f"❌ {msg}")

        elif choice == "0":
            print("Hasta luego.")
            break
        else:
            print("Opción no válida. Intenta de nuevo.")

def main():
    parser = argparse.ArgumentParser(description="Asistente de administración de alabanzas")
    parser.add_argument("--interactive", action="store_true", help="Iniciar menú interactivo")
    parser.add_argument("--add", action="store_true", help="Agregar alabanza")
    parser.add_argument("--edit", type=int, help="ID de la alabanza a editar")
    parser.add_argument("--delete", type=int, help="ID de la alabanza a eliminar")
    parser.add_argument("--validate", action="store_true", help="Validar integridad del catálogo")
    parser.add_argument("--publish", action="store_true", help="Publicar nueva versión a GitHub")
    parser.add_argument("--no-push", action="store_true", help="Omitir git push al publicar")

    parser.add_argument("--title", help="Título de la alabanza")
    parser.add_argument("--content", help="Contenido de la alabanza")
    parser.add_argument("--author", default="", help="Autor")
    parser.add_argument("--link", default="", help="Enlace de YouTube")
    parser.add_argument("--file", help="Archivo de texto plano con la alabanza")
    parser.add_argument("--force", action="store_true", help="Forzar adición ignorando duplicados")

    args = parser.parse_args()

    if len(sys.argv) == 1 or args.interactive:
        run_interactive_menu()
        return

    catalog = load_catalog()

    if args.add:
        if args.file:
            with open(args.file, "r", encoding="utf-8") as f:
                t, a, l, c = parse_plain_text(f.read())
        else:
            t = args.title or ""
            c = args.content or ""
            a = args.author or ""
            l = args.link or ""

        ok, msg, h_id = add_hymn(catalog, t, c, a, l, force=args.force)
        if ok:
            save_catalog(catalog)
            print(f"SUCCESS: {msg}")
            sys.exit(0)
        else:
            print(f"ERROR: {msg}")
            sys.exit(1)

    elif args.edit is not None:
        ok, msg = edit_hymn(catalog, args.edit, title=args.title, content=args.content, author=args.author, link=args.link)
        if ok:
            save_catalog(catalog)
            print(f"SUCCESS: {msg}")
            sys.exit(0)
        else:
            print(f"ERROR: {msg}")
            sys.exit(1)

    elif args.delete is not None:
        ok, msg = delete_hymn(catalog, args.delete, soft_delete=True)
        if ok:
            save_catalog(catalog)
            print(f"SUCCESS: {msg}")
            sys.exit(0)
        else:
            print(f"ERROR: {msg}")
            sys.exit(1)

    elif args.validate:
        ok, issues = validate_catalog(catalog)
        if ok:
            print("SUCCESS: Catálogo válido e íntegro.")
            sys.exit(0)
        else:
            print("ERROR: Problemas encontrados:\n" + "\n".join(issues))
            sys.exit(1)

    elif args.publish:
        ok, msg = publish_catalog(auto_push=not args.no_push)
        if ok:
            print(f"SUCCESS: {msg}")
            sys.exit(0)
        else:
            print(f"ERROR: {msg}")
            sys.exit(1)

if __name__ == "__main__":
    main()
