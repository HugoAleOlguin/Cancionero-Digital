import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
by_id = {h['id']: h for h in cat}

test_matches = [
    (481, "Carta a Timoteo", "Q_Ul-JLAmDI", "Iglesia Central de Tucumán - Carta a Timoteo"),
    (482, "Mi vida está en Tus manos", "1UEHrrHiY1Q", "Conjunto Cantando para Cristo Vol.1 (Mi Vida esta en Tus Manos)"),
    (485, "Ahora Todo es con mi Jesús", "1UEHrrHiY1Q", "Ahora todo es con Jesús - Conjunto Cantando para Cristo"),
    (501, "Nunca te abandonó", "_9elZ3Ehf54", "Dios nunca me abandonó (Conjunto de Tucumán)"),
    (519, "Solo Tú Señor", "JFFIAWSafP0", "Solo Tú Señor - Conjunto de Perico Jujuy"),
    (523, "Todo Pasará", "TR8_WLUGmRQ", "Todo Pasará - Conjunto de Perico Jujuy"),
    (530, "La Trompeta Sonará", "Dh1EtFBZ87Y", "Las Trompetas Sonarán - Conjunto de Perico Jujuy"),
]

for hid, title, vid, yt_title in test_matches:
    h = by_id[hid]
    print(f"=== #{hid} '{h['title']}' ===")
    print(h['content'][:140])
    print(f"Pista: https://www.youtube.com/watch?v={vid} -> {yt_title}")
    print()
