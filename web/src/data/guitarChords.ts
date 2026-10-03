import { HarmonicFamily, UkuleleChord } from './ukuleleChords';

export type GuitarChord = UkuleleChord; // Estructura unificada (frets tiene 6 elementos)

// -------------------------------------------------------------
// Biblioteca Completa de Acordes de Guitarra (Afinación E-A-D-G-B-E)
// cuerdas: [6ta(E), 5ta(A), 4ta(D), 3ra(G), 2da(B), 1ra(E)]
// -------------------------------------------------------------
export const GUITAR_CHORDS_DB: Record<string, GuitarChord> = {
  // --- DO / C ---
  'C': { name: 'DO', symbol: 'C', type: 'major', typeName: 'Mayor', frets: [-1, 3, 2, 0, 1, 0], fingers: [0, 3, 2, 0, 1, 0] },
  'Cm': { name: 'DOm', symbol: 'Cm', type: 'minor', typeName: 'Menor', frets: [-1, 3, 5, 5, 4, 3], fingers: [0, 1, 3, 4, 2, 1], barre: 3, baseFret: 3 },
  'C7': { name: 'DO7', symbol: 'C7', type: '7th', typeName: 'Séptima', frets: [-1, 3, 2, 3, 1, 0], fingers: [0, 3, 2, 4, 1, 0] },
  'Cm7': { name: 'DOm7', symbol: 'Cm7', type: 'm7', typeName: 'Menor 7', frets: [-1, 3, 5, 3, 4, 3], fingers: [0, 1, 3, 1, 2, 1], barre: 3, baseFret: 3 },
  'Cmaj7': { name: 'DOmaj7', symbol: 'Cmaj7', type: 'maj7', typeName: 'Mayor 7', frets: [-1, 3, 2, 0, 0, 0], fingers: [0, 3, 2, 0, 0, 0] },
  'Csus4': { name: 'DOsus4', symbol: 'Csus4', type: 'sus4', typeName: 'Sus 4', frets: [-1, 3, 3, 0, 1, 1], fingers: [0, 3, 4, 0, 1, 1], barre: 1 },

  // --- DO# / REb (C# / Db) ---
  'C#': { name: 'DO#', symbol: 'C#', type: 'major', typeName: 'Mayor', frets: [-1, 4, 6, 6, 6, 4], fingers: [0, 1, 2, 3, 4, 1], barre: 4, baseFret: 4 },
  'C#m': { name: 'DO#m', symbol: 'C#m', type: 'minor', typeName: 'Menor', frets: [-1, 4, 6, 6, 5, 4], fingers: [0, 1, 3, 4, 2, 1], barre: 4, baseFret: 4 },
  'C#7': { name: 'DO#7', symbol: 'C#7', type: '7th', typeName: 'Séptima', frets: [-1, 4, 6, 4, 6, 4], fingers: [0, 1, 3, 1, 4, 1], barre: 4, baseFret: 4 },

  // --- RE / D ---
  'D': { name: 'RE', symbol: 'D', type: 'major', typeName: 'Mayor', frets: [-1, -1, 0, 2, 3, 2], fingers: [0, 0, 0, 1, 3, 2] },
  'Dm': { name: 'REm', symbol: 'Dm', type: 'minor', typeName: 'Menor', frets: [-1, -1, 0, 2, 3, 1], fingers: [0, 0, 0, 2, 3, 1] },
  'D7': { name: 'RE7', symbol: 'D7', type: '7th', typeName: 'Séptima', frets: [-1, -1, 0, 2, 1, 2], fingers: [0, 0, 0, 2, 1, 3] },
  'Dm7': { name: 'REm7', symbol: 'Dm7', type: 'm7', typeName: 'Menor 7', frets: [-1, -1, 0, 2, 1, 1], fingers: [0, 0, 0, 2, 1, 1], barre: 1 },
  'Dmaj7': { name: 'REmaj7', symbol: 'Dmaj7', type: 'maj7', typeName: 'Mayor 7', frets: [-1, -1, 0, 2, 2, 2], fingers: [0, 0, 0, 1, 1, 1], barre: 2 },
  'Dsus4': { name: 'REsus4', symbol: 'Dsus4', type: 'sus4', typeName: 'Sus 4', frets: [-1, -1, 0, 2, 3, 3], fingers: [0, 0, 0, 1, 2, 3] },

  // --- RE# / MIb (Eb) ---
  'Eb': { name: 'MIb', symbol: 'Eb', type: 'major', typeName: 'Mayor', frets: [-1, 6, 8, 8, 8, 6], fingers: [0, 1, 2, 3, 4, 1], barre: 6, baseFret: 6 },
  'Ebm': { name: 'MIbm', symbol: 'Ebm', type: 'minor', typeName: 'Menor', frets: [-1, 6, 8, 8, 7, 6], fingers: [0, 1, 3, 4, 2, 1], barre: 6, baseFret: 6 },
  'Eb7': { name: 'MIb7', symbol: 'Eb7', type: '7th', typeName: 'Séptima', frets: [-1, 6, 8, 6, 8, 6], fingers: [0, 1, 3, 1, 4, 1], barre: 6, baseFret: 6 },

  // --- MI / E ---
  'E': { name: 'MI', symbol: 'E', type: 'major', typeName: 'Mayor', frets: [0, 2, 2, 1, 0, 0], fingers: [0, 2, 3, 1, 0, 0] },
  'Em': { name: 'MIm', symbol: 'Em', type: 'minor', typeName: 'Menor', frets: [0, 2, 2, 0, 0, 0], fingers: [0, 2, 3, 0, 0, 0] },
  'E7': { name: 'MI7', symbol: 'E7', type: '7th', typeName: 'Séptima', frets: [0, 2, 0, 1, 0, 0], fingers: [0, 2, 0, 1, 0, 0] },
  'Em7': { name: 'MIm7', symbol: 'Em7', type: 'm7', typeName: 'Menor 7', frets: [0, 2, 2, 0, 3, 0], fingers: [0, 2, 3, 0, 4, 0] },
  'Emaj7': { name: 'MImaj7', symbol: 'Emaj7', type: 'maj7', typeName: 'Mayor 7', frets: [0, 2, 1, 1, 0, 0], fingers: [0, 3, 1, 2, 0, 0] },
  'Esus4': { name: 'MIsus4', symbol: 'Esus4', type: 'sus4', typeName: 'Sus 4', frets: [0, 2, 2, 2, 0, 0], fingers: [0, 2, 3, 4, 0, 0] },

  // --- FA / F ---
  'F': { name: 'FA', symbol: 'F', type: 'major', typeName: 'Mayor', frets: [1, 3, 3, 2, 1, 1], fingers: [1, 3, 4, 2, 1, 1], barre: 1 },
  'Fm': { name: 'FAm', symbol: 'Fm', type: 'minor', typeName: 'Menor', frets: [1, 3, 3, 1, 1, 1], fingers: [1, 3, 4, 1, 1, 1], barre: 1 },
  'F7': { name: 'FA7', symbol: 'F7', type: '7th', typeName: 'Séptima', frets: [1, 3, 1, 2, 1, 1], fingers: [1, 3, 1, 2, 1, 1], barre: 1 },
  'Fm7': { name: 'FAm7', symbol: 'Fm7', type: 'm7', typeName: 'Menor 7', frets: [1, 3, 1, 1, 1, 1], fingers: [1, 3, 1, 1, 1, 1], barre: 1 },
  'Fmaj7': { name: 'FAmaj7', symbol: 'Fmaj7', type: 'maj7', typeName: 'Mayor 7', frets: [-1, -1, 3, 2, 1, 0], fingers: [0, 0, 3, 2, 1, 0] },
  'Fsus4': { name: 'FAsus4', symbol: 'Fsus4', type: 'sus4', typeName: 'Sus 4', frets: [1, 3, 3, 3, 1, 1], fingers: [1, 2, 3, 4, 1, 1], barre: 1 },

  // --- FA# / SOLb (F# / Gb) ---
  'F#': { name: 'FA#', symbol: 'F#', type: 'major', typeName: 'Mayor', frets: [2, 4, 4, 3, 2, 2], fingers: [1, 3, 4, 2, 1, 1], barre: 2 },
  'F#m': { name: 'FA#m', symbol: 'F#m', type: 'minor', typeName: 'Menor', frets: [2, 4, 4, 2, 2, 2], fingers: [1, 3, 4, 1, 1, 1], barre: 2 },
  'F#7': { name: 'FA#7', symbol: 'F#7', type: '7th', typeName: 'Séptima', frets: [2, 4, 2, 3, 2, 2], fingers: [1, 3, 1, 2, 1, 1], barre: 2 },
  'F#m7': { name: 'FA#m7', symbol: 'F#m7', type: 'm7', typeName: 'Menor 7', frets: [2, 4, 2, 2, 2, 2], fingers: [1, 3, 1, 1, 1, 1], barre: 2 },

  // --- SOL / G ---
  'G': { name: 'SOL', symbol: 'G', type: 'major', typeName: 'Mayor', frets: [3, 2, 0, 0, 0, 3], fingers: [2, 1, 0, 0, 0, 3] },
  'Gm': { name: 'SOLm', symbol: 'Gm', type: 'minor', typeName: 'Menor', frets: [3, 5, 5, 3, 3, 3], fingers: [1, 3, 4, 1, 1, 1], barre: 3, baseFret: 3 },
  'G7': { name: 'SOL7', symbol: 'G7', type: '7th', typeName: 'Séptima', frets: [3, 2, 0, 0, 0, 1], fingers: [3, 2, 0, 0, 0, 1] },
  'Gm7': { name: 'SOLm7', symbol: 'Gm7', type: 'm7', typeName: 'Menor 7', frets: [3, 5, 3, 3, 3, 3], fingers: [1, 3, 1, 1, 1, 1], barre: 3, baseFret: 3 },
  'Gmaj7': { name: 'SOLmaj7', symbol: 'Gmaj7', type: 'maj7', typeName: 'Mayor 7', frets: [3, 2, 0, 0, 0, 2], fingers: [2, 1, 0, 0, 0, 3] },
  'Gsus4': { name: 'SOLsus4', symbol: 'Gsus4', type: 'sus4', typeName: 'Sus 4', frets: [3, 2, 0, 0, 1, 3], fingers: [3, 2, 0, 0, 1, 4] },

  // --- SOL# / LAb (Ab) ---
  'Ab': { name: 'LAb', symbol: 'Ab', type: 'major', typeName: 'Mayor', frets: [4, 6, 6, 5, 4, 4], fingers: [1, 3, 4, 2, 1, 1], barre: 4, baseFret: 4 },
  'G#m': { name: 'SOL#m', symbol: 'G#m', type: 'minor', typeName: 'Menor', frets: [4, 6, 6, 4, 4, 4], fingers: [1, 3, 4, 1, 1, 1], barre: 4, baseFret: 4 },
  'G#7': { name: 'SOL#7', symbol: 'G#7', type: '7th', typeName: 'Séptima', frets: [4, 6, 4, 5, 4, 4], fingers: [1, 3, 1, 2, 1, 1], barre: 4, baseFret: 4 },

  // --- LA / A ---
  'A': { name: 'LA', symbol: 'A', type: 'major', typeName: 'Mayor', frets: [-1, 0, 2, 2, 2, 0], fingers: [0, 0, 1, 2, 3, 0] },
  'Am': { name: 'LAm', symbol: 'Am', type: 'minor', typeName: 'Menor', frets: [-1, 0, 2, 2, 1, 0], fingers: [0, 0, 2, 3, 1, 0] },
  'A7': { name: 'LA7', symbol: 'A7', type: '7th', typeName: 'Séptima', frets: [-1, 0, 2, 0, 2, 0], fingers: [0, 0, 1, 0, 2, 0] },
  'Am7': { name: 'LAm7', symbol: 'Am7', type: 'm7', typeName: 'Menor 7', frets: [-1, 0, 2, 0, 1, 0], fingers: [0, 0, 2, 0, 1, 0] },
  'Amaj7': { name: 'LAmaj7', symbol: 'Amaj7', type: 'maj7', typeName: 'Mayor 7', frets: [-1, 0, 2, 1, 2, 0], fingers: [0, 0, 2, 1, 3, 0] },
  'Asus4': { name: 'LAsus4', symbol: 'Asus4', type: 'sus4', typeName: 'Sus 4', frets: [-1, 0, 2, 2, 3, 0], fingers: [0, 0, 1, 2, 3, 0] },

  // --- LA# / SIb (Bb) ---
  'Bb': { name: 'SIb', symbol: 'Bb', type: 'major', typeName: 'Mayor', frets: [-1, 1, 3, 3, 3, 1], fingers: [0, 1, 2, 3, 4, 1], barre: 1 },
  'Bbm': { name: 'SIbm', symbol: 'Bbm', type: 'minor', typeName: 'Menor', frets: [-1, 1, 3, 3, 2, 1], fingers: [0, 1, 3, 4, 2, 1], barre: 1 },
  'Bb7': { name: 'SIb7', symbol: 'Bb7', type: '7th', typeName: 'Séptima', frets: [-1, 1, 3, 1, 3, 1], fingers: [0, 1, 3, 1, 4, 1], barre: 1 },

  // --- SI / B ---
  'B': { name: 'SI', symbol: 'B', type: 'major', typeName: 'Mayor', frets: [-1, 2, 4, 4, 4, 2], fingers: [0, 1, 2, 3, 4, 1], barre: 2 },
  'Bm': { name: 'SIm', symbol: 'Bm', type: 'minor', typeName: 'Menor', frets: [-1, 2, 4, 4, 3, 2], fingers: [0, 1, 3, 4, 2, 1], barre: 2 },
  'B7': { name: 'SI7', symbol: 'B7', type: '7th', typeName: 'Séptima', frets: [-1, 2, 1, 2, 0, 2], fingers: [0, 2, 1, 3, 0, 4] },
  'Bm7': { name: 'SIm7', symbol: 'Bm7', type: 'm7', typeName: 'Menor 7', frets: [-1, 2, 4, 2, 3, 2], fingers: [0, 1, 3, 1, 2, 1], barre: 2 },
  'Bsus4': { name: 'SIsus4', symbol: 'Bsus4', type: 'sus4', typeName: 'Sus 4', frets: [-1, 2, 4, 4, 5, 2], fingers: [0, 1, 2, 3, 4, 1], barre: 2 },
};

// -------------------------------------------------------------
// Familias Armónicas de Acompañamiento para Guitarra
// -------------------------------------------------------------
export const GUITAR_HARMONIC_FAMILIES: Record<string, HarmonicFamily> = {
  'C': {
    keyName: 'DO',
    keySymbol: 'C',
    tonic: GUITAR_CHORDS_DB['C'],
    subdominant: GUITAR_CHORDS_DB['F'],
    dominant: GUITAR_CHORDS_DB['G'],
    dominant7: GUITAR_CHORDS_DB['G7'],
    relativeMinor: GUITAR_CHORDS_DB['Am'],
    secondary1: GUITAR_CHORDS_DB['Dm'],
    secondary2: GUITAR_CHORDS_DB['Em'],
  },
  'G': {
    keyName: 'SOL',
    keySymbol: 'G',
    tonic: GUITAR_CHORDS_DB['G'],
    subdominant: GUITAR_CHORDS_DB['C'],
    dominant: GUITAR_CHORDS_DB['D'],
    dominant7: GUITAR_CHORDS_DB['D7'],
    relativeMinor: GUITAR_CHORDS_DB['Em'],
    secondary1: GUITAR_CHORDS_DB['Am'],
    secondary2: GUITAR_CHORDS_DB['Bm'],
  },
  'D': {
    keyName: 'RE',
    keySymbol: 'D',
    tonic: GUITAR_CHORDS_DB['D'],
    subdominant: GUITAR_CHORDS_DB['G'],
    dominant: GUITAR_CHORDS_DB['A'],
    dominant7: GUITAR_CHORDS_DB['A7'],
    relativeMinor: GUITAR_CHORDS_DB['Bm'],
    secondary1: GUITAR_CHORDS_DB['Em'],
    secondary2: GUITAR_CHORDS_DB['F#m'],
  },
  'A': {
    keyName: 'LA',
    keySymbol: 'A',
    tonic: GUITAR_CHORDS_DB['A'],
    subdominant: GUITAR_CHORDS_DB['D'],
    dominant: GUITAR_CHORDS_DB['E'],
    dominant7: GUITAR_CHORDS_DB['E7'],
    relativeMinor: GUITAR_CHORDS_DB['F#m'],
    secondary1: GUITAR_CHORDS_DB['Bm'],
    secondary2: GUITAR_CHORDS_DB['C#m'],
  },
  'E': {
    keyName: 'MI',
    keySymbol: 'E',
    tonic: GUITAR_CHORDS_DB['E'],
    subdominant: GUITAR_CHORDS_DB['A'],
    dominant: GUITAR_CHORDS_DB['B'],
    dominant7: GUITAR_CHORDS_DB['B7'],
    relativeMinor: GUITAR_CHORDS_DB['C#m'],
    secondary1: GUITAR_CHORDS_DB['F#m'],
    secondary2: GUITAR_CHORDS_DB['G#m'],
  },
  'F': {
    keyName: 'FA',
    keySymbol: 'F',
    tonic: GUITAR_CHORDS_DB['F'],
    subdominant: GUITAR_CHORDS_DB['Bb'],
    dominant: GUITAR_CHORDS_DB['C'],
    dominant7: GUITAR_CHORDS_DB['C7'],
    relativeMinor: GUITAR_CHORDS_DB['Dm'],
    secondary1: GUITAR_CHORDS_DB['Gm'],
    secondary2: GUITAR_CHORDS_DB['Am'],
  },
  'Bb': {
    keyName: 'SIb',
    keySymbol: 'Bb',
    tonic: GUITAR_CHORDS_DB['Bb'],
    subdominant: GUITAR_CHORDS_DB['Eb'],
    dominant: GUITAR_CHORDS_DB['F'],
    dominant7: GUITAR_CHORDS_DB['F7'],
    relativeMinor: GUITAR_CHORDS_DB['Gm'],
    secondary1: GUITAR_CHORDS_DB['Cm'],
    secondary2: GUITAR_CHORDS_DB['Dm'],
  },
  'B': {
    keyName: 'SI',
    keySymbol: 'B',
    tonic: GUITAR_CHORDS_DB['B'],
    subdominant: GUITAR_CHORDS_DB['E'],
    dominant: GUITAR_CHORDS_DB['F#'],
    dominant7: GUITAR_CHORDS_DB['F#7'],
    relativeMinor: GUITAR_CHORDS_DB['G#m'],
    secondary1: GUITAR_CHORDS_DB['C#m'],
    secondary2: GUITAR_CHORDS_DB['Ebm'] || GUITAR_CHORDS_DB['D#m'],
  },
};
