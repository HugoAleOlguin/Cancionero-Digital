export interface UkuleleChord {
  name: string;        // ej: "DO", "SOL", "REm"
  symbol: string;      // ej: "C", "G", "Dm"
  type: 'major' | 'minor' | '7th' | 'm7' | 'maj7' | 'sus4' | 'dim';
  typeName: string;    // ej: "Mayor", "Menor", "Séptima"
  // Trastes para [Cuerda 4 (G), Cuerda 3 (C), Cuerda 2 (E), Cuerda 1 (A)]
  // 0 = al aire, -1 = silenciada, >0 = número de traste
  frets: [number, number, number, number];
  // Dedos sugeridos: [1=índice, 2=medio, 3=anular, 4=meñique], 0 = sin dedo
  fingers?: [number, number, number, number];
  baseFret?: number; // traste inicial en el mástil (1 por defecto)
  barre?: number;    // traste donde va la cejilla completa
}

export interface HarmonicFamily {
  keyName: string;    // ej: "DO"
  keySymbol: string;  // ej: "C"
  tonic: UkuleleChord;        // I
  subdominant: UkuleleChord;  // IV
  dominant: UkuleleChord;     // V
  dominant7: UkuleleChord;    // V7
  relativeMinor: UkuleleChord;// vi
  secondary1: UkuleleChord;   // ii
  secondary2: UkuleleChord;   // iii
}

// -------------------------------------------------------------
// Biblioteca Completa de Acordes de Ukelele (Afinación G-C-E-A)
// -------------------------------------------------------------
export const UKULELE_CHORDS_DB: Record<string, UkuleleChord> = {
  // --- DO / C ---
  'C': { name: 'DO', symbol: 'C', type: 'major', typeName: 'Mayor', frets: [0, 0, 0, 3], fingers: [0, 0, 0, 3] },
  'Cm': { name: 'DOm', symbol: 'Cm', type: 'minor', typeName: 'Menor', frets: [0, 3, 3, 3], fingers: [0, 1, 2, 3], barre: 3 },
  'C7': { name: 'DO7', symbol: 'C7', type: '7th', typeName: 'Séptima', frets: [0, 0, 0, 1], fingers: [0, 0, 0, 1] },
  'Cm7': { name: 'DOm7', symbol: 'Cm7', type: 'm7', typeName: 'Menor 7', frets: [3, 3, 3, 3], fingers: [1, 1, 1, 1], barre: 3 },
  'Cmaj7': { name: 'DOmaj7', symbol: 'Cmaj7', type: 'maj7', typeName: 'Mayor 7', frets: [0, 0, 0, 2], fingers: [0, 0, 0, 2] },
  'Csus4': { name: 'DOsus4', symbol: 'Csus4', type: 'sus4', typeName: 'Sus 4', frets: [0, 0, 1, 3], fingers: [0, 0, 1, 3] },
  'Cdim': { name: 'DOdim', symbol: 'Cdim', type: 'dim', typeName: 'Disminuido', frets: [2, 3, 2, 3], fingers: [1, 3, 2, 4] },

  // --- DO# / REb (C# / Db) ---
  'C#': { name: 'DO#', symbol: 'C#', type: 'major', typeName: 'Mayor', frets: [1, 1, 1, 4], fingers: [1, 1, 1, 4], barre: 1 },
  'C#m': { name: 'DO#m', symbol: 'C#m', type: 'minor', typeName: 'Menor', frets: [1, 1, 0, 4], fingers: [1, 1, 0, 4] },
  'C#7': { name: 'DO#7', symbol: 'C#7', type: '7th', typeName: 'Séptima', frets: [1, 1, 1, 2], fingers: [1, 1, 1, 2], barre: 1 },
  'C#m7': { name: 'DO#m7', symbol: 'C#m7', type: 'm7', typeName: 'Menor 7', frets: [4, 4, 4, 4], fingers: [1, 1, 1, 1], barre: 4, baseFret: 4 },

  // --- RE / D ---
  'D': { name: 'RE', symbol: 'D', type: 'major', typeName: 'Mayor', frets: [2, 2, 2, 0], fingers: [1, 2, 3, 0] },
  'Dm': { name: 'REm', symbol: 'Dm', type: 'minor', typeName: 'Menor', frets: [2, 2, 1, 0], fingers: [2, 3, 1, 0] },
  'D7': { name: 'RE7', symbol: 'D7', type: '7th', typeName: 'Séptima', frets: [2, 0, 2, 0], fingers: [1, 0, 2, 0] },
  'Dm7': { name: 'REm7', symbol: 'Dm7', type: 'm7', typeName: 'Menor 7', frets: [2, 2, 1, 3], fingers: [2, 3, 1, 4] },
  'Dmaj7': { name: 'REmaj7', symbol: 'Dmaj7', type: 'maj7', typeName: 'Mayor 7', frets: [2, 2, 2, 4], fingers: [1, 1, 1, 3], barre: 2 },
  'Dsus4': { name: 'REsus4', symbol: 'Dsus4', type: 'sus4', typeName: 'Sus 4', frets: [2, 2, 3, 0], fingers: [1, 2, 3, 0] },
  'Ddim': { name: 'REdim', symbol: 'Ddim', type: 'dim', typeName: 'Disminuido', frets: [1, 2, 1, 2], fingers: [1, 3, 2, 4] },

  // --- RE# / MIb (D# / Eb) ---
  'Eb': { name: 'MIb', symbol: 'Eb', type: 'major', typeName: 'Mayor', frets: [3, 3, 3, 1], fingers: [2, 3, 4, 1] },
  'Ebm': { name: 'MIbm', symbol: 'Ebm', type: 'minor', typeName: 'Menor', frets: [3, 3, 2, 1], fingers: [3, 4, 2, 1] },
  'Eb7': { name: 'MIb7', symbol: 'Eb7', type: '7th', typeName: 'Séptima', frets: [3, 3, 3, 4], fingers: [1, 1, 1, 2], barre: 3 },

  // --- MI / E ---
  'E': { name: 'MI', symbol: 'E', type: 'major', typeName: 'Mayor', frets: [4, 4, 4, 2], fingers: [2, 3, 4, 1] },
  'Em': { name: 'MIm', symbol: 'Em', type: 'minor', typeName: 'Menor', frets: [0, 4, 3, 2], fingers: [0, 3, 2, 1] },
  'E7': { name: 'MI7', symbol: 'E7', type: '7th', typeName: 'Séptima', frets: [1, 2, 0, 2], fingers: [1, 2, 0, 3] },
  'Em7': { name: 'MIm7', symbol: 'Em7', type: 'm7', typeName: 'Menor 7', frets: [0, 2, 0, 2], fingers: [0, 1, 0, 2] },
  'Emaj7': { name: 'MImaj7', symbol: 'Emaj7', type: 'maj7', typeName: 'Mayor 7', frets: [1, 3, 0, 2], fingers: [1, 3, 0, 2] },
  'Esus4': { name: 'MIsus4', symbol: 'Esus4', type: 'sus4', typeName: 'Sus 4', frets: [2, 4, 0, 2], fingers: [1, 3, 0, 2] },
  'Edim': { name: 'MIdim', symbol: 'Edim', type: 'dim', typeName: 'Disminuido', frets: [0, 1, 0, 1], fingers: [0, 1, 0, 2] },

  // --- FA / F ---
  'F': { name: 'FA', symbol: 'F', type: 'major', typeName: 'Mayor', frets: [2, 0, 1, 0], fingers: [2, 0, 1, 0] },
  'Fm': { name: 'FAm', symbol: 'Fm', type: 'minor', typeName: 'Menor', frets: [1, 0, 1, 3], fingers: [1, 0, 2, 4] },
  'F7': { name: 'FA7', symbol: 'F7', type: '7th', typeName: 'Séptima', frets: [2, 3, 1, 0], fingers: [2, 3, 1, 0] },
  'Fm7': { name: 'FAm7', symbol: 'Fm7', type: 'm7', typeName: 'Menor 7', frets: [1, 3, 1, 3], fingers: [1, 3, 2, 4] },
  'Fmaj7': { name: 'FAmaj7', symbol: 'Fmaj7', type: 'maj7', typeName: 'Mayor 7', frets: [2, 4, 1, 3], fingers: [2, 4, 1, 3] },
  'Fsus4': { name: 'FAsus4', symbol: 'Fsus4', type: 'sus4', typeName: 'Sus 4', frets: [3, 0, 1, 1], fingers: [3, 0, 1, 1], barre: 1 },
  'Fdim': { name: 'FAdim', symbol: 'Fdim', type: 'dim', typeName: 'Disminuido', frets: [1, 2, 1, 2], fingers: [1, 3, 2, 4] },

  // --- FA# / SOLb (F# / Gb) ---
  'F#': { name: 'FA#', symbol: 'F#', type: 'major', typeName: 'Mayor', frets: [3, 2, 2, 1], fingers: [4, 2, 3, 1] },
  'F#m': { name: 'FA#m', symbol: 'F#m', type: 'minor', typeName: 'Menor', frets: [2, 1, 2, 0], fingers: [2, 1, 3, 0] },
  'F#7': { name: 'FA#7', symbol: 'F#7', type: '7th', typeName: 'Séptima', frets: [3, 4, 2, 4], fingers: [2, 3, 1, 4] },
  'F#m7': { name: 'FA#m7', symbol: 'F#m7', type: 'm7', typeName: 'Menor 7', frets: [2, 4, 2, 4], fingers: [1, 3, 2, 4] },

  // --- SOL / G ---
  'G': { name: 'SOL', symbol: 'G', type: 'major', typeName: 'Mayor', frets: [0, 2, 3, 2], fingers: [0, 1, 3, 2] },
  'Gm': { name: 'SOLm', symbol: 'Gm', type: 'minor', typeName: 'Menor', frets: [0, 2, 3, 1], fingers: [0, 2, 3, 1] },
  'G7': { name: 'SOL7', symbol: 'G7', type: '7th', typeName: 'Séptima', frets: [0, 2, 1, 2], fingers: [0, 2, 1, 3] },
  'Gm7': { name: 'SOLm7', symbol: 'Gm7', type: 'm7', typeName: 'Menor 7', frets: [0, 2, 1, 1], fingers: [0, 2, 1, 1], barre: 1 },
  'Gmaj7': { name: 'SOLmaj7', symbol: 'Gmaj7', type: 'maj7', typeName: 'Mayor 7', frets: [0, 2, 2, 2], fingers: [0, 1, 2, 3] },
  'Gsus4': { name: 'SOLsus4', symbol: 'Gsus4', type: 'sus4', typeName: 'Sus 4', frets: [0, 2, 3, 3], fingers: [0, 1, 2, 3] },
  'Gdim': { name: 'SOLdim', symbol: 'Gdim', type: 'dim', typeName: 'Disminuido', frets: [0, 1, 0, 1], fingers: [0, 1, 0, 2] },

  // --- SOL# / LAb (G# / Ab) ---
  'Ab': { name: 'LAb', symbol: 'Ab', type: 'major', typeName: 'Mayor', frets: [5, 3, 4, 3], fingers: [4, 1, 3, 2], baseFret: 3 },
  'G#m': { name: 'SOL#m', symbol: 'G#m', type: 'minor', typeName: 'Menor', frets: [4, 3, 4, 2], fingers: [3, 2, 4, 1] },
  'G#7': { name: 'SOL#7', symbol: 'G#7', type: '7th', typeName: 'Séptima', frets: [1, 3, 2, 3], fingers: [1, 3, 2, 4] },

  // --- LA / A ---
  'A': { name: 'LA', symbol: 'A', type: 'major', typeName: 'Mayor', frets: [2, 1, 0, 0], fingers: [2, 1, 0, 0] },
  'Am': { name: 'LAm', symbol: 'Am', type: 'minor', typeName: 'Menor', frets: [2, 0, 0, 0], fingers: [2, 0, 0, 0] },
  'A7': { name: 'LA7', symbol: 'A7', type: '7th', typeName: 'Séptima', frets: [0, 1, 0, 0], fingers: [0, 1, 0, 0] },
  'Am7': { name: 'LAm7', symbol: 'Am7', type: 'm7', typeName: 'Menor 7', frets: [0, 0, 0, 0], fingers: [0, 0, 0, 0] },
  'Amaj7': { name: 'LAmaj7', symbol: 'Amaj7', type: 'maj7', typeName: 'Mayor 7', frets: [1, 1, 0, 0], fingers: [1, 2, 0, 0] },
  'Asus4': { name: 'LAsus4', symbol: 'Asus4', type: 'sus4', typeName: 'Sus 4', frets: [2, 2, 0, 0], fingers: [1, 2, 0, 0] },
  'Adim': { name: 'LAdim', symbol: 'Adim', type: 'dim', typeName: 'Disminuido', frets: [2, 3, 2, 3], fingers: [1, 3, 2, 4] },

  // --- LA# / SIb (Bb) ---
  'Bb': { name: 'SIb', symbol: 'Bb', type: 'major', typeName: 'Mayor', frets: [3, 2, 1, 1], fingers: [3, 2, 1, 1], barre: 1 },
  'Bbm': { name: 'SIbm', symbol: 'Bbm', type: 'minor', typeName: 'Menor', frets: [3, 1, 1, 1], fingers: [3, 1, 1, 1], barre: 1 },
  'Bb7': { name: 'SIb7', symbol: 'Bb7', type: '7th', typeName: 'Séptima', frets: [1, 2, 1, 1], fingers: [1, 2, 1, 1], barre: 1 },
  'Bbm7': { name: 'SIbm7', symbol: 'Bbm7', type: 'm7', typeName: 'Menor 7', frets: [1, 1, 1, 1], fingers: [1, 1, 1, 1], barre: 1 },

  // --- SI / B ---
  'B': { name: 'SI', symbol: 'B', type: 'major', typeName: 'Mayor', frets: [4, 3, 2, 2], fingers: [4, 3, 1, 1], barre: 2 },
  'Bm': { name: 'SIm', symbol: 'Bm', type: 'minor', typeName: 'Menor', frets: [4, 2, 2, 2], fingers: [3, 1, 1, 1], barre: 2 },
  'B7': { name: 'SI7', symbol: 'B7', type: '7th', typeName: 'Séptima', frets: [2, 3, 2, 2], fingers: [1, 2, 1, 1], barre: 2 },
  'Bm7': { name: 'SIm7', symbol: 'Bm7', type: 'm7', typeName: 'Menor 7', frets: [2, 2, 2, 2], fingers: [1, 1, 1, 1], barre: 2 },
  'Bmaj7': { name: 'SImaj7', symbol: 'Bmaj7', type: 'maj7', typeName: 'Mayor 7', frets: [3, 3, 2, 2], fingers: [3, 4, 1, 2] },
  'Bsus4': { name: 'SIsus4', symbol: 'Bsus4', type: 'sus4', typeName: 'Sus 4', frets: [4, 4, 2, 2], fingers: [3, 4, 1, 1], barre: 2 },
  'Bdim': { name: 'SIdim', symbol: 'Bdim', type: 'dim', typeName: 'Disminuido', frets: [1, 2, 1, 2], fingers: [1, 3, 2, 4] },
};

// -------------------------------------------------------------
// Familias Armónicas (Progresiones Principales de Acompañamiento)
// Con predominancia de las tonalidades clave de alabanza: DO, SOL, RE, etc.
// -------------------------------------------------------------
export const HARMONIC_FAMILIES: Record<string, HarmonicFamily> = {
  'C': {
    keyName: 'DO',
    keySymbol: 'C',
    tonic: UKULELE_CHORDS_DB['C'],
    subdominant: UKULELE_CHORDS_DB['F'],
    dominant: UKULELE_CHORDS_DB['G'],
    dominant7: UKULELE_CHORDS_DB['G7'],
    relativeMinor: UKULELE_CHORDS_DB['Am'],
    secondary1: UKULELE_CHORDS_DB['Dm'],
    secondary2: UKULELE_CHORDS_DB['Em'],
  },
  'G': {
    keyName: 'SOL',
    keySymbol: 'G',
    tonic: UKULELE_CHORDS_DB['G'],
    subdominant: UKULELE_CHORDS_DB['C'],
    dominant: UKULELE_CHORDS_DB['D'],
    dominant7: UKULELE_CHORDS_DB['D7'],
    relativeMinor: UKULELE_CHORDS_DB['Em'],
    secondary1: UKULELE_CHORDS_DB['Am'],
    secondary2: UKULELE_CHORDS_DB['Bm'],
  },
  'D': {
    keyName: 'RE',
    keySymbol: 'D',
    tonic: UKULELE_CHORDS_DB['D'],
    subdominant: UKULELE_CHORDS_DB['G'],
    dominant: UKULELE_CHORDS_DB['A'],
    dominant7: UKULELE_CHORDS_DB['A7'],
    relativeMinor: UKULELE_CHORDS_DB['Bm'],
    secondary1: UKULELE_CHORDS_DB['Em'],
    secondary2: UKULELE_CHORDS_DB['F#m'],
  },
  'A': {
    keyName: 'LA',
    keySymbol: 'A',
    tonic: UKULELE_CHORDS_DB['A'],
    subdominant: UKULELE_CHORDS_DB['D'],
    dominant: UKULELE_CHORDS_DB['E'],
    dominant7: UKULELE_CHORDS_DB['E7'],
    relativeMinor: UKULELE_CHORDS_DB['F#m'],
    secondary1: UKULELE_CHORDS_DB['Bm'],
    secondary2: UKULELE_CHORDS_DB['C#m'],
  },
  'E': {
    keyName: 'MI',
    keySymbol: 'E',
    tonic: UKULELE_CHORDS_DB['E'],
    subdominant: UKULELE_CHORDS_DB['A'],
    dominant: UKULELE_CHORDS_DB['B'],
    dominant7: UKULELE_CHORDS_DB['B7'],
    relativeMinor: UKULELE_CHORDS_DB['C#m'],
    secondary1: UKULELE_CHORDS_DB['F#m'],
    secondary2: UKULELE_CHORDS_DB['G#m'],
  },
  'F': {
    keyName: 'FA',
    keySymbol: 'F',
    tonic: UKULELE_CHORDS_DB['F'],
    subdominant: UKULELE_CHORDS_DB['Bb'],
    dominant: UKULELE_CHORDS_DB['C'],
    dominant7: UKULELE_CHORDS_DB['C7'],
    relativeMinor: UKULELE_CHORDS_DB['Dm'],
    secondary1: UKULELE_CHORDS_DB['Gm'],
    secondary2: UKULELE_CHORDS_DB['Am'],
  },
  'Bb': {
    keyName: 'SIb',
    keySymbol: 'Bb',
    tonic: UKULELE_CHORDS_DB['Bb'],
    subdominant: UKULELE_CHORDS_DB['Eb'],
    dominant: UKULELE_CHORDS_DB['F'],
    dominant7: UKULELE_CHORDS_DB['F7'],
    relativeMinor: UKULELE_CHORDS_DB['Gm'],
    secondary1: UKULELE_CHORDS_DB['Cm'],
    secondary2: UKULELE_CHORDS_DB['Dm'],
  },
  'B': {
    keyName: 'SI',
    keySymbol: 'B',
    tonic: UKULELE_CHORDS_DB['B'],
    subdominant: UKULELE_CHORDS_DB['E'],
    dominant: UKULELE_CHORDS_DB['F#'],
    dominant7: UKULELE_CHORDS_DB['F#7'],
    relativeMinor: UKULELE_CHORDS_DB['G#m'],
    secondary1: UKULELE_CHORDS_DB['C#m'],
    secondary2: UKULELE_CHORDS_DB['D#m'] || UKULELE_CHORDS_DB['Ebm'],
  },
};

// Claves predominantes recomendadas para alabanzas cristianas
export const PREDOMINANT_KEYS = [
  { symbol: 'C', name: 'DO' },
  { symbol: 'G', name: 'SOL' },
  { symbol: 'D', name: 'RE' },
  { symbol: 'A', name: 'LA' },
  { symbol: 'F', name: 'FA' },
  { symbol: 'E', name: 'MI' },
  { symbol: 'Bb', name: 'SIb' },
  { symbol: 'B', name: 'SI' },
];
