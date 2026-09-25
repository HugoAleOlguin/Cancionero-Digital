import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']

official_vids = [
    ("fkoG31ATcJk", "Yo quiero estar"),
    ("BCHr3QJGOaQ", "Siete veces"),
    ("GjPQjU0YYo0", "La reina de Saba"),
    ("bkD8IpacGY8", "Te Espere"),
    ("UZF2pC_xhkI", "Senor Yo Te Vi"),
    ("Z2scHOqUzek", "Consolada Fui"),
    ("A4jk658pYAI", "Renuncio a la Miseria"),
    ("jKfs8A5X8r0", "Llena Hoy Mi Vida"),
    ("S1nWwWVmnJQ", "Sigo de Pie"),
    ("ixR4Y1leDiA", "Trayendo Consuelo"),
    ("ExKi7ES_7ek", "Caleb"),
    ("XD_27RjNprI", "Esperalo"),
    ("ZxKnQLDjBUU", "Eres Libre"),
    ("djVYJpxj96M", "Eutico"),
    ("e_HEQ-9sF3Q", "Quemame con Fuego Santo"),
    ("58pJyGiIjeM", "Te lo Debo a Ti"),
]

def norm(s):
    import unicodedata
    return ''.join(c for c in unicodedata.normalize('NFD', s.lower()) if unicodedata.category(c) != 'Mn')

for vid, title in official_vids:
    nt = norm(title)
    print(f"\n=== Testing official video: [{vid}] '{title}' ===")
    matches = []
    for h in cat:
        nh = norm(h['title'])
        nc = norm(h['content'])
        if nt in nh or nh in nt or nt in nc:
            matches.append((h['id'], h['title'], h.get('author'), h.get('link', '')))
    for hid, htitle, hauth, hlink in matches:
        print(f"  -> #{hid} '{htitle}' ({hauth}) | Has link: {bool(hlink.strip())} | Link: {hlink}")
