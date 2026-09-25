import json

cat = json.load(open('data/catalog.json', encoding='utf-8'))['hymns']
acuna = [h for h in cat if ('acuña' in h.get('author', '').lower() or 'acuna' in h.get('author', '').lower()) and not h.get('isDeleted', False)]

keywords = ["huerto", "oración", "oracion", "galileo", "samaritana", "apocalipsis", "salmo", "121", "vencedores", "vencedor", "gracias", "jehová", "jehova", "sentido", "poder"]

for h in acuna:
    content_lower = h['content'].lower()
    matches = [kw for kw in keywords if kw in content_lower]
    print(f"#{h['id']} '{h['title']}' -> Palabras clave encontradas: {matches}")
