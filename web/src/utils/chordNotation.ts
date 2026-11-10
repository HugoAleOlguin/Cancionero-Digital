export type ChordNotationType = 'latin' | 'anglo';

const ANGLO_TO_LATIN_ROOT: Record<string, string> = {
  'c': 'DO',
  'c#': 'DO#',
  'db': 'REb',
  'd': 'RE',
  'd#': 'RE#',
  'eb': 'MIb',
  'e': 'MI',
  'f': 'FA',
  'f#': 'FA#',
  'gb': 'SOLb',
  'g': 'SOL',
  'g#': 'SOL#',
  'ab': 'LAb',
  'a': 'LA',
  'a#': 'LA#',
  'bb': 'SIb',
  'b': 'SI',
  'cb': 'SI',
  'b#': 'DO'
};

/**
 * Formatea un acorde según la notación deseada.
 * Por defecto se utiliza 'latin' (DO, RE, MI, FA, SOL, LA, SI).
 */
export function formatChordNotation(symbol: string, notation: ChordNotationType = 'latin'): string {
  if (!symbol) return '';

  if (notation === 'anglo') {
    return symbol;
  }

  // Notación Latina (Solfeo)
  const match = symbol.trim().match(/^([A-Ga-g][#b]?)(.*)$/);
  if (!match) return symbol;

  const rawRoot = match[1].toLowerCase();
  const suffix = match[2];

  const latinRoot = ANGLO_TO_LATIN_ROOT[rawRoot];
  if (!latinRoot) return symbol;

  return `${latinRoot}${suffix}`;
}

/**
 * Obtiene el nombre visual limpio del acorde para la cabecera o botones.
 */
export function getChordDisplayName(symbol: string, notation: ChordNotationType = 'latin'): string {
  return formatChordNotation(symbol, notation);
}
