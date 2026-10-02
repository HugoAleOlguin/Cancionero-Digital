import { Hymn, MatchOccurrence, SearchableHymn } from '../types/hymn';

/**
 * Normaliza cadenas de texto eliminando marcas diacríticas (acentos) y convirtiendo a minúsculas,
 * idéntico al motor FuzzyHymnSearchEngine.kt de la app Android.
 */
export function normalizeText(text: string): string {
  if (!text) return '';
  return text
    .normalize('NFD')
    .replace(/[\u0300-\u036f]/g, '')
    .toLowerCase();
}

/**
 * Pre-calcula una representación en memoria optimizada para búsqueda instantánea con latencia cero.
 * Excluye himnos marcados como isDeleted.
 */
export function createSearchableHymns(hymns: Hymn[]): SearchableHymn[] {
  return hymns
    .filter(h => !h.isDeleted)
    .sort((a, b) => a.id - b.id)
    .map(h => {
      const displayTitle = `${h.id} - ${h.title}`;
      const allVersions = [h.content, ...(h.extraVersions || [])];
      const combinedContent = allVersions.join('\n\n');

      return {
        hymn: h,
        normalizedTitle: normalizeText(displayTitle),
        normalizedAuthor: normalizeText(h.author || ''),
        normalizedContent: normalizeText(combinedContent),
        splitStanzas: h.content.split(/\n\s*\n/).filter(s => s.trim().length > 0)
      };
    });
}

/**
 * Busca himnos por coincidencia exacta de subcadena en número, título, autor o letra.
 * Preserva estrictamente el orden numérico de los himnos sin distorsiones.
 */
export function searchHymns(searchables: SearchableHymn[], query: string): SearchableHymn[] {
  const trimmed = query.trim();
  if (!trimmed) return searchables;

  const normalizedQuery = normalizeText(trimmed);

  return searchables.filter(item => {
    // 1. Coincidencia por número directo
    if (String(item.hymn.id) === trimmed) {
      return true;
    }
    // 2. Coincidencia en título con número (ej: "12 - Grande")
    if (item.normalizedTitle.includes(normalizedQuery)) {
      return true;
    }
    // 3. Coincidencia en autor
    if (item.normalizedAuthor.includes(normalizedQuery)) {
      return true;
    }
    // 4. Coincidencia en letra / estrofas
    if (item.normalizedContent.includes(normalizedQuery)) {
      return true;
    }
    return false;
  });
}

/**
 * Encuentra todas las ocurrencias para navegación (< 1/N >) y scroll exacto.
 */
export function extractMatches(searchables: SearchableHymn[], query: string): MatchOccurrence[] {
  const trimmed = query.trim();
  if (!trimmed) return [];

  const normalizedQuery = normalizeText(trimmed);
  const matches: MatchOccurrence[] = [];

  for (const item of searchables) {
    // Buscar en título
    const titleNorm = item.normalizedTitle;
    let titleIdx = titleNorm.indexOf(normalizedQuery);
    while (titleIdx !== -1) {
      matches.push({
        hymnId: item.hymn.id,
        isTitle: true,
        stanzaIndex: -1,
        charRange: [titleIdx, titleIdx + normalizedQuery.length]
      });
      titleIdx = titleNorm.indexOf(normalizedQuery, titleIdx + 1);
    }

    // Buscar en estrofas
    item.splitStanzas.forEach((stanza, sIdx) => {
      const stanzaNorm = normalizeText(stanza);
      let sIdxMatch = stanzaNorm.indexOf(normalizedQuery);
      while (sIdxMatch !== -1) {
        matches.push({
          hymnId: item.hymn.id,
          isTitle: false,
          stanzaIndex: sIdx,
          charRange: [sIdxMatch, sIdxMatch + normalizedQuery.length]
        });
        sIdxMatch = stanzaNorm.indexOf(normalizedQuery, sIdxMatch + 1);
      }
    });
  }

  return matches;
}
