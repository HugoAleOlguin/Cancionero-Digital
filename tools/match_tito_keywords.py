import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
tito = [h for h in cat if 'tito' in h.get('author', '').lower() or 'abarca' in h.get('author', '').lower()]

for h in tito:
    c = h['content'].lower()
    t = h['title'].lower()
    hid = h['id']
    title = h['title']
    has_link = bool(h.get('link', '').strip())
    link = h.get('link', '')

    if 'cadena' in c or 'cerrojo' in c:
        print(f"Cadenas/Cerrojos -> #{hid} '{title}' (Has link: {has_link})")
    if 'leproso' in c or 'limpio' in c or 'leproso' in t:
        print(f"Leproso/Limpio -> #{hid} '{title}' (Has link: {has_link})")
    if 'tiz' in c or 'incendio' in c:
        print(f"Tizón/Incendio -> #{hid} '{title}' (Has link: {has_link})")
    if 'ponte de pie' in c or 'ponte de pie' in t:
        print(f"Ponte de pie -> #{hid} '{title}' (Has link: {has_link})")
    if 'caleb' in c or 'caleb' in t:
        print(f"Caleb -> #{hid} '{title}' (Has link: {has_link})")
    if 'mefi' in c or 'mefi' in t:
        print(f"Mefiboset -> #{hid} '{title}' (Has link: {has_link})")
    if 'albanil' in c or 'albañil' in c:
        print(f"Albañil -> #{hid} '{title}' (Has link: {has_link})")
    if 'josue' in c or 'josué' in c or 'josue' in t:
        print(f"Josué -> #{hid} '{title}' (Has link: {has_link})")
    if 'job' in c or 'job' in t:
        print(f"Job -> #{hid} '{title}' (Has link: {has_link})")
    if 'jose' in c or 'josé' in c:
        print(f"José -> #{hid} '{title}' (Has link: {has_link})")
    if 'barca' in c or 'barca' in t:
        print(f"Barca -> #{hid} '{title}' (Has link: {has_link})")
    if 'samaritano' in c:
        print(f"Samaritano -> #{hid} '{title}' (Has link: {has_link})")
    if 'huesos secos' in c or 'huesos' in c:
        print(f"Huesos -> #{hid} '{title}' (Has link: {has_link})")
