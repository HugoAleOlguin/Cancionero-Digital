import json

batch_updates = [
    # Perico / Cantando para Cristo (17)
    (481, "https://www.youtube.com/watch?v=Q_Ul-JLAmDI", "Carta a Timoteo"),
    (484, "https://www.youtube.com/watch?v=r6OXCF_xIvw", "Mi Rey Jesús"),
    (485, "https://www.youtube.com/watch?v=1UEHrrHiY1Q", "Ahora Todo es con mi Jesús"),
    (489, "https://www.youtube.com/watch?v=j7sJ28Gla4g", "Testimonio"),
    (494, "https://www.youtube.com/watch?v=w1rn9vReu8A", "Perdóname Señor"),
    (496, "https://www.youtube.com/watch?v=9hJ6T-K33JA", "Yo Soy tu Dios"),
    (501, "https://www.youtube.com/watch?v=_9elZ3Ehf54", "Nunca te abandonó"),
    (511, "https://www.youtube.com/watch?v=qVS594HkkO4", "Algo va a Pasar"),
    (512, "https://www.youtube.com/watch?v=zv6fushcxx8", "El Hijo Prodigo"),
    (514, "https://www.youtube.com/watch?v=wriDOxrGY-8", "Jesús el Gran Capitán"),
    (515, "https://www.youtube.com/watch?v=IuNgH0KSWNM", "Tengo miedo que te Olvides"),
    (518, "https://www.youtube.com/watch?v=SJVtuMtFtA8", "La Voz de Jehová"),
    (519, "https://www.youtube.com/watch?v=JFFIAWSafP0", "Solo Tú Señor"),
    (522, "https://www.youtube.com/watch?v=1j7aSJa_tXU", "El Dulce Hogar"),
    (523, "https://www.youtube.com/watch?v=TR8_WLUGmRQ", "Todo Pasará"),
    (526, "https://www.youtube.com/watch?v=l4xfNZibSsI", "Tu Presencia aquí"),
    (530, "https://www.youtube.com/watch?v=Dh1EtFBZ87Y", "La Trompeta Sonará"),

    # Conjunto Luján (2)
    (932, "https://www.youtube.com/watch?v=2dGr2y8oEmU", "Mirame Señor"),
    (934, "https://www.youtube.com/watch?v=f6NTrz1p_9o", "Yo Soy de Jesús"),

    # Conjunto Central de Tucumán (9)
    (384, "https://www.youtube.com/watch?v=wTxqLc_zFSs", "La Visión de Daniel"),
    (388, "https://www.youtube.com/watch?v=3kPj9z6rc3Y", "Veremos al Rey"),
    (389, "https://www.youtube.com/watch?v=KmhzrwIk1xc", "El Ensueño de la Iglesia"),
    (392, "https://www.youtube.com/watch?v=KnsIyWVM37I", "Sembrador"),
    (403, "https://www.youtube.com/watch?v=z0NgG0bfqyE", "Carta al Hijo Prodigo"),
    (408, "https://www.youtube.com/watch?v=3lp4rdGYqm8", "Más que Vencedores"),
    (409, "https://www.youtube.com/watch?v=OXbDat9yYgY", "Pedro"),
    (411, "https://www.youtube.com/watch?v=jBDFWjkK-iY", "Zorobabel"),
    (413, "https://www.youtube.com/watch?v=U2qiOifs67U", "El Leproso"),
]

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
by_id = {h['id']: h for h in cat}

print(f"Total elementos a auditar: {len(batch_updates)}")
safe = True
for hid, link, title in batch_updates:
    if hid not in by_id:
        print(f"ERROR: ID #{hid} no existe en catálogo!")
        safe = False
        continue
    h = by_id[hid]
    curr_link = h.get('link', '').strip()
    if curr_link:
        print(f"ALERTA VIOLACIÓN REGLA: #{hid} '{h['title']}' ya tiene link: {curr_link}")
        safe = False
    else:
        print(f"OK #{hid} '{h['title']}' [Autor: {h.get('author')}] -> Link actual vacío")

if safe:
    print("\n>>> AUDITORÍA PERFECTA: 28 de 28 himnos están 100% libres y listos para inyección sin sobreescribir nada.")
else:
    print("\n>>> PELIGRO: Existen violaciones de seguridad.")
