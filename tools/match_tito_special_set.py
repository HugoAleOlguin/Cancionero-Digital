import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']

specials = [
    ("8g_RrJarFuU", "Eres Libre"),
    ("Nfr_mu5fehM", "Mujer Virtuosa"),
    ("ORWW1G7aMDo", "Aunque todos te nieguen"),
    ("KnOBBzSgYiE", "Nehemias"),
    ("s8w-q8pfIE8", "La Oracion de Ana"),
    ("uKS1kQnIdbw", "Alla en el Olvido"),
    ("vHTuIrT-F6Q", "Quiero Senor"),
    ("ZVvv_cyvw6c", "Como el ciervo"),
    ("C3087InnZ-8", "Aviva el don"),
]

def norm(s):
    import unicodedata
    return ''.join(c for c in unicodedata.normalize('NFD', s.lower()) if unicodedata.category(c) != 'Mn')

for vid, title in specials:
    nt = norm(title)
    print(f"\n=== Special: [{vid}] '{title}' ===")
    matches = []
    for h in cat:
        nh = norm(h['title'])
        nc = norm(h['content'])
        if nt in nh or nh in nt or nt in nc:
            matches.append((h['id'], h['title'], h.get('author'), h.get('link', '')))
    for hid, htitle, hauth, hlink in matches:
        print(f"  -> #{hid} '{htitle}' ({hauth}) | Has link: {bool(hlink.strip())} | Link: {hlink}")
