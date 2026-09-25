#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
tools/test_manage_hymns.py
Suite de pruebas unitarias para tools/manage_hymns.py
"""

import sys
import unittest
import json
import tempfile
from pathlib import Path

# Add project root to sys.path
PROJECT_ROOT = Path(__file__).resolve().parent.parent
if str(PROJECT_ROOT) not in sys.path:
    sys.path.insert(0, str(PROJECT_ROOT))

try:
    from tools.manage_hymns import (
        normalize_text,
        stanza_similarity,
        check_duplicate,
        add_hymn,
        edit_hymn,
        delete_hymn,
        validate_catalog,
        load_catalog,
        save_catalog,
        parse_plain_text
    )
except ImportError:
    from manage_hymns import (
        normalize_text,
        stanza_similarity,
        check_duplicate,
        add_hymn,
        edit_hymn,
        delete_hymn,
        validate_catalog,
        load_catalog,
        save_catalog,
        parse_plain_text
    )

class TestManageHymns(unittest.TestCase):

    def setUp(self):
        self.sample_catalog = {
            "version": 1,
            "updatedAt": "2026-09-25T00:00:00Z",
            "totalHymns": 2,
            "hymns": [
                {
                    "id": 1,
                    "title": "Cuan Grande Es Él",
                    "author": "Carl Boberg",
                    "link": "https://youtube.com/watch?v=1",
                    "content": "Señor mi Dios al contemplar los cielos\nEl firmamento y las mil estrellas.\n\nCoro:\nMi corazón entona la canción\n¡Cuán grande es Él!",
                    "updatedAt": "2026-09-25T00:00:00Z",
                    "isDeleted": False
                },
                {
                    "id": 2,
                    "title": "En Una Nube Blanca",
                    "author": "Desconocido",
                    "link": "",
                    "content": "En una nube blanca Cristo volverá\nCon poder y gloria nos levantará.\n\nCoro:\nCristo viene ya, prepárate hermano.",
                    "updatedAt": "2026-09-25T00:00:00Z",
                    "isDeleted": False
                }
            ]
        }

    def test_normalize_text(self):
        self.assertEqual(normalize_text("¡Cúán Gránde Es Él, Señor!"), "cuan grande es el senor")
        self.assertEqual(normalize_text("  Espacios   múltiples  "), "espacios multiples")
        self.assertEqual(normalize_text(""), "")

    def test_stanza_similarity(self):
        s1 = "Señor mi Dios al contemplar los cielos"
        s2 = "Señor mi Dios al contemplar los cielos y estrellas"
        sim = stanza_similarity(s1, s2)
        self.assertGreater(sim, 0.7)

        s3 = "Completamente diferente sin nada igual"
        self.assertEqual(stanza_similarity(s1, s3), 0.0)

    def test_duplicate_detection_by_title(self):
        is_dup, reason, dup_id = check_duplicate(
            title="cuan grande es el",
            content="Cualquier letra diferente",
            hymns=self.sample_catalog["hymns"]
        )
        self.assertTrue(is_dup)
        self.assertEqual(dup_id, 1)

    def test_duplicate_detection_by_stanza(self):
        is_dup, reason, dup_id = check_duplicate(
            title="Otro Título Nuevo",
            content="En una nube blanca Cristo volverá con poder y gloria nos levantará",
            hymns=self.sample_catalog["hymns"]
        )
        self.assertTrue(is_dup)
        self.assertEqual(dup_id, 2)

    def test_add_hymn_success(self):
        ok, msg, new_id = add_hymn(
            self.sample_catalog,
            title="Alabanza Inédita",
            content="Primera estrofa con palabras únicas.\n\nSegunda estrofa.",
            author="Autor de Prueba"
        )
        self.assertTrue(ok)
        self.assertEqual(new_id, 3)
        self.assertEqual(len(self.sample_catalog["hymns"]), 3)
        self.assertEqual(self.sample_catalog["hymns"][-1]["title"], "Alabanza Inédita")

    def test_add_hymn_duplicate_rejected(self):
        ok, msg, dup_id = add_hymn(
            self.sample_catalog,
            title="Cuan Grande Es Él",
            content="Letra idéntica o no"
        )
        self.assertFalse(ok)
        self.assertEqual(dup_id, 1)

    def test_add_hymn_force_bypass(self):
        ok, msg, new_id = add_hymn(
            self.sample_catalog,
            title="Cuan Grande Es Él",
            content="Versión alternativa forzada",
            force=True
        )
        self.assertTrue(ok)
        self.assertEqual(new_id, 3)

    def test_edit_hymn(self):
        ok, msg = edit_hymn(
            self.sample_catalog,
            hymn_id=1,
            title="Cuán Grande Es Dios (Corregido)",
            author="Nuevo Autor"
        )
        self.assertTrue(ok)
        h1 = self.sample_catalog["hymns"][0]
        self.assertEqual(h1["title"], "Cuán Grande Es Dios (Corregido)")
        self.assertEqual(h1["author"], "Nuevo Autor")

    def test_edit_nonexistent_hymn(self):
        ok, msg = edit_hymn(self.sample_catalog, hymn_id=999, title="Inexistente")
        self.assertFalse(ok)

    def test_delete_hymn_soft(self):
        ok, msg = delete_hymn(self.sample_catalog, hymn_id=2, soft_delete=True)
        self.assertTrue(ok)
        h2 = self.sample_catalog["hymns"][1]
        self.assertTrue(h2["isDeleted"])

    def test_delete_hymn_physical(self):
        ok, msg = delete_hymn(self.sample_catalog, hymn_id=2, soft_delete=False)
        self.assertTrue(ok)
        self.assertEqual(len(self.sample_catalog["hymns"]), 1)

    def test_validate_catalog(self):
        ok, issues = validate_catalog(self.sample_catalog)
        self.assertTrue(ok)
        self.assertEqual(len(issues), 0)

        # Inyectar error: ID duplicado
        bad_catalog = {
            "hymns": [
                {"id": 1, "title": "A", "content": "Letra", "isDeleted": False},
                {"id": 1, "title": "B", "content": "Letra", "isDeleted": False}
            ]
        }
        ok_bad, issues_bad = validate_catalog(bad_catalog)
        self.assertFalse(ok_bad)
        self.assertIn("ID duplicado", issues_bad[0])

    def test_save_and_load_catalog(self):
        with tempfile.TemporaryDirectory() as tmpdir:
            tmp_cat = Path(tmpdir) / "catalog.json"
            tmp_ver = Path(tmpdir) / "version.json"
            tmp_ast = Path(tmpdir) / "assets.json"

            save_catalog(self.sample_catalog, catalog_path=tmp_cat, version_path=tmp_ver, assets_path=tmp_ast)
            self.assertTrue(tmp_cat.exists())
            self.assertTrue(tmp_ver.exists())
            self.assertTrue(tmp_ast.exists())

            loaded = load_catalog(tmp_cat)
            self.assertEqual(loaded["totalHymns"], 2)
            self.assertEqual(len(loaded["hymns"]), 2)

    def test_parse_plain_text(self):
        raw = """Título: Canto de Esperanza
Autor: Tito Abarca
Enlace: https://youtube.com/test

Estrofa 1
Palabras de la estrofa 1

Estrofa 2
Palabras de la estrofa 2"""
        t, a, l, c = parse_plain_text(raw)
        self.assertEqual(t, "Canto de Esperanza")
        self.assertEqual(a, "Tito Abarca")
        self.assertEqual(l, "https://youtube.com/test")
        self.assertTrue("Estrofa 1" in c)
        self.assertTrue("Estrofa 2" in c)

if __name__ == "__main__":
    unittest.main()
