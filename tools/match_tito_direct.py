import json
import unicodedata
import re

def norm(s):
    if not s: return ""
    s = s.lower()
    s = "".join(c for c in unicodedata.normalize("NFD", s) if unicodedata.category(c) != "Mn")
    return re.sub(r"[^a-z0-9\s]", " ", s).strip()

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']

tito_channel_vids = [
    ("fkoG31ATcJk", "Yo quiero estar"),
    ("jKfs8A5X8r0", "Llena Hoy Mi Vida"),
    ("e_HEQ-9sF3Q", "Quemame con Fuego Santo"),
    ("A4jk658pYAI", "Renuncio a la Miseria"),
    ("UZF2pC_xhkI", "Señor Yo Te Vi"),
    ("BCHr3QJGOaQ", "Siete veces"),
    ("S1nWwWVmnJQ", "Sigo de Pie"),
    ("ixR4Y1leDiA", "Trayendo Consuelo"),
    ("ExKi7ES_7ek", "Caleb"),
    ("Z2scHOqUzek", "Consolada Fui"),
    ("ZxKnQLDjBUU", "Eres Libre"),
    ("GjPQjU0YYo0", "La reina de Sabá"),
    ("XD_27RjNprI", "Esperalo"),
    ("uHNhe_qD1hU", "Todo Cambio"),
    ("58pJyGiIjeM", "Te lo Debo a Ti"),
    ("F8B2GYLraYw", "Te lo debo a ti"),
    ("KnOBBzSgYiE", "Nehemias"),
    ("k_w2ouuUSOk", "No hay cadenas ni cerrojos"),
    ("djVYJpxj96M", "Eutico"),
    ("jpbYB3DAaug", "Tizon Arrebatado"),
    ("yFRuFBkzs8c", "Esfuérzate y sé Valiente"),
    ("fwyQG7gQyMo", "Despierta mi Primer Amor"),
    ("s2fmtH7vARQ", "Quiero; sé Limpio"),
    ("Y-1DZqpTboA", "El Jordan"),
    ("_mQKytCyTAY", "Sopla"),
    ("KyNum9IuS1w", "Yo Conozco"),
    ("VnCUWMiUBJo", "Bendice Alma mía a Jehová"),
    ("vX8zIqeJq0k", "Tu corazón en Horeb"),
]

print("=== COTEJANDO VIDEOS DIRECTOS DE TITO ABARCA ===")
for vid, title in tito_channel_vids:
    tn = norm(title)
    found = []
    for h in cat:
        htn = norm(h['title'])
        if tn == htn or (len(tn) > 4 and (tn in htn or htn in tn)):
            found.append(h)
    
    if found:
        print(f"\n>> Video: {title} ({vid})")
        for h in found:
            curr_link = h.get('link', '').strip()
            print(f"   Match: #{h['id']} '{h['title']}' [{h.get('author')}] | Link: {repr(curr_link)}")
            print(f"          Letra: {' '.join(h['content'].splitlines()[:2])[:70]}")
