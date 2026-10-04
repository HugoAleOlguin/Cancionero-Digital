const CANONICAL_ROOTS = [
  'C', 'C#', 'D', 'Eb', 'E', 'F', 'F#', 'G', 'Ab', 'A', 'Bb', 'B'
];

const ROOT_TO_INDEX: Record<string, number> = {
  'c': 0, 'b#': 0,
  'c#': 1, 'db': 1,
  'd': 2,
  'd#': 3, 'eb': 3,
  'e': 4,
  'f': 5, 'e#': 5,
  'f#': 6, 'gb': 6,
  'g': 7,
  'g#': 8, 'ab': 8,
  'a': 9,
  'a#': 10, 'bb': 10,
  'b': 11, 'cb': 11
};

/**
 * Transporta un acorde individual por un número de semitonos (positivo o negativo).
 * Preserva sufijos como m, 7, m7, maj7, sus4, dim.
 */
export function transposeChord(symbol: string, semitones: number): string {
  if (!symbol) return '';

  const match = symbol.trim().match(/^([A-Ga-g][#b]?)(.*)$/);
  if (!match) return symbol;

  const rawRoot = match[1].toLowerCase();
  const suffix = match[2];

  const rootIdx = ROOT_TO_INDEX[rawRoot];
  if (rootIdx === undefined) return symbol;

  const newIdx = (rootIdx + (semitones % 12) + 12) % 12;
  const newRoot = CANONICAL_ROOTS[newIdx];

  return `${newRoot}${suffix}`;
}

/**
 * Transporta la clave armónica de base (ej: "C" -> "D", "Am" -> "Bm").
 */
export function transposeKey(key: string, semitones: number): string {
  return transposeChord(key, semitones);
}

/**
 * Transporta todo el mapa de acordes de una alabanza por un diferencial de semitonos.
 */
export function transposeHymnChords(
  chords: Record<string, string>,
  semitones: number
): Record<string, string> {
  const result: Record<string, string> = {};
  for (const [sylId, chord] of Object.entries(chords)) {
    result[sylId] = transposeChord(chord, semitones);
  }
  return result;
}
