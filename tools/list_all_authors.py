import json
import collections

data = json.load(open('data/catalog.json', encoding='utf-8'))
hymns = data['hymns']

# Contar por autor
author_counts = collections.Counter()
author_samples = collections.defaultdict(list)

for h in hymns:
    if h.get('isDeleted', False):
        continue
    author = h.get('author', '').strip()
    if not author:
        author = "Sin autor especificado"
    author_counts[author] += 1
    if len(author_samples[author]) < 3:
        author_samples[author].append(f"#{h['id']} {h['title']}")

print(f"Total alabanzas analizadas: {len(hymns)}")
print(f"Total autores/conjuntos distintos: {len(author_counts)}")
print("\n--- LISTA DE AUTORES ORDENADOS POR CANTIDAD DE ALABANZAS ---")

for author, count in author_counts.most_common():
    samples_str = ", ".join(author_samples[author])
    print(f"[{count:3d} canciones] {author}  -->  (Ej: {samples_str})")
