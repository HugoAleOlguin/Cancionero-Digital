import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
by_id = {h['id']: h for h in cat}

perico_cantando_matches = [
    (481, "Carta a Timoteo", "Q_Ul-JLAmDI", "Iglesia Central de Tucumán - Carta a Timoteo"),
    (484, "Mi Rey Jesús", "r6OXCF_xIvw", "Mi Rey Jesús - Conjunto Cantando para Cristo"),
    (485, "Ahora Todo es con mi Jesús", "1UEHrrHiY1Q", "Ahora todo es con Jesús - Conjunto Cantando para Cristo"),
    (489, "Testimonio", "j7sJ28Gla4g", "Mi Testimonio - Conjunto Cantando para Cristo"),
    (494, "Perdóname Señor", "w1rn9vReu8A", "SEÑOR PERDÓNAME (Conjunto de Perico)"),
    (496, "Yo Soy tu Dios", "9hJ6T-K33JA", "Yo soy Tú Dios - Conjunto Cantando para Cristo"),
    (501, "Nunca te abandonó", "_9elZ3Ehf54", "Dios nunca me abandonó (Conjunto de Tucumán)"),
    (511, "Algo va a Pasar", "qVS594HkkO4", "Algo va a Pasar - Conjunto Cantando para Cristo"),
    (512, "El Hijo Prodigo", "zv6fushcxx8", "El Amor por Tí Siempre Perdurará (Hijo Pródigo)"),
    (514, "Jesús el Gran Capitán", "wriDOxrGY-8", "Jesús el Gran Capitán - Conjunto Cantando para Cristo"),
    (515, "Tengo miedo que te Olvides", "IuNgH0KSWNM", "Tengo Miedo que te Olvides - Conjunto Cantando para Cristo"),
    (518, "La Voz de Jehová", "SJVtuMtFtA8", "La Voz de Jehová - Conjunto Cantando para Cristo"),
    (519, "Solo Tú Señor", "JFFIAWSafP0", "Solo Tú Señor - Conjunto de Perico Jujuy"),
    (522, "El Dulce Hogar", "1j7aSJa_tXU", "Esperaré - Conjunto de Perico Jujuy"),
    (523, "Todo Pasará", "TR8_WLUGmRQ", "Todo Pasará - Conjunto de Perico Jujuy"),
    (526, "Tu Presencia aquí", "l4xfNZibSsI", "Cuando tú Presencia pasa por Aquí - Conjunto de Perico Jujuy"),
    (530, "La Trompeta Sonará", "Dh1EtFBZ87Y", "Las Trompetas Sonarán - Conjunto de Perico Jujuy"),
]

print("=== VERIFICACIÓN DETALLADA LÍRICA ===")
for hid, title, vid, yt_title in perico_cantando_matches:
    h = by_id[hid]
    curr_link = h.get('link', '').strip()
    first_lines = ' '.join(h['content'].splitlines()[:3])
    print(f"#{hid} '{h['title']}' [CurrLink: {bool(curr_link)}]")
    print(f"  Letra: {first_lines[:75]}")
    print(f"  Video: https://www.youtube.com/watch?v={vid} -> {yt_title}")
    print()
