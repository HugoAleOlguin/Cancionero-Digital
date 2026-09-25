import json
import collections

analysis = json.load(open('data/cuadernillo_analysis.json', encoding='utf-8'))
candidates = analysis['new_hymns'][:380]

authors = collections.Counter(h.get('author', 'Desconocido') for h in candidates)
print("Distribución de autores en las 380 alabanzas:")
for a, count in authors.most_common():
    print(f"  - {a}: {count} alabanzas")

# Verificamos si hay alguna alabanza con content vacío o muy corto
short_content = [h for h in candidates if len(h['content'].strip()) < 100]
print(f"\nAlabanzas con letra corta (<100 caracteres): {len(short_content)}")

# Verificamos que ninguna tenga títulos vacíos
empty_titles = [h for h in candidates if not h['title'].strip()]
print(f"Alabanzas sin título: {len(empty_titles)}")
