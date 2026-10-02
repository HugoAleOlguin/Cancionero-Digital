import { describe, it, expect } from 'vitest';
import { normalizeText, createSearchableHymns, searchHymns, extractMatches } from '../utils/searchEngine';
import { Hymn } from '../types/hymn';

describe('Search Engine (Fuzzy/Exact parity with Android)', () => {
  it('normalizes diacritics and converts to lowercase', () => {
    expect(normalizeText('Cántico de Adoración')).toBe('cantico de adoracion');
    expect(normalizeText('Jesús es Señor')).toBe('jesus es senor');
    expect(normalizeText('ÁÉÍÓÚ ñ áéíóú')).toBe('aeiou n aeiou');
  });

  const mockHymns: Hymn[] = [
    {
      id: 1,
      title: 'A Cristo coronad',
      content: 'I\nA Cristo coronad,\nDivino Salvador;\nSentado en alta majestad\nEs digno de loor.',
      author: 'George J. Elvey',
      link: 'https://youtube.com',
      isDeleted: false
    },
    {
      id: 2,
      title: 'Señor Tú Eres',
      content: 'Señor tú eres la persona\nmás importante de este lugar',
      author: 'Desconocido',
      isDeleted: false
    },
    {
      id: 3,
      title: 'Himno Antiguo Eliminado',
      content: 'Contenido antiguo borrado',
      author: 'Anónimo',
      isDeleted: true
    },
    {
      id: 12,
      title: 'Grande es Tu Fidelidad',
      content: 'Oh Dios eterno, tu misericordia\nNi una sombra de duda tendrá',
      author: 'Thomas Chisholm',
      isDeleted: false
    }
  ];

  it('filters out deleted hymns when building searchable cache', () => {
    const searchable = createSearchableHymns(mockHymns);
    expect(searchable.length).toBe(3);
    expect(searchable.some(s => s.hymn.id === 3)).toBe(false);
  });

  it('searches by exact number and preserves numerical order', () => {
    const searchable = createSearchableHymns(mockHymns);
    const results = searchHymns(searchable, '2');
    expect(results.length).toBeGreaterThan(0);
    expect(results[0].hymn.id).toBe(2);
  });

  it('searches by title without accents', () => {
    const searchable = createSearchableHymns(mockHymns);
    const results = searchHymns(searchable, 'senor tu eres');
    expect(results.length).toBe(1);
    expect(results[0].hymn.title).toBe('Señor Tú Eres');
  });

  it('searches by lyric content inside stanzas', () => {
    const searchable = createSearchableHymns(mockHymns);
    const results = searchHymns(searchable, 'alta majestad');
    expect(results.length).toBe(1);
    expect(results[0].hymn.id).toBe(1);
  });

  it('extracts match occurrences for highlighting and counter', () => {
    const searchable = createSearchableHymns(mockHymns);
    const matches = extractMatches(searchable, 'Cristo');
    expect(matches.length).toBeGreaterThan(0);
    expect(matches[0].hymnId).toBe(1);
  });
});
