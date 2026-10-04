import { HarmonicFamily, UkuleleChord } from './ukuleleChords';

export type GuitarChord = UkuleleChord; // Estructura compartida (frets tiene 6 elementos)

// -------------------------------------------------------------
// Biblioteca Completa de Acordes de Guitarra (Afinación E-A-D-G-B-E)
// cuerdas: [6ª(E), 5ª(A), 4ª(D), 3ª(G), 2ª(B), 1ª(E)]
// -------------------------------------------------------------
export const GUITAR_CHORDS_DB: Record<string, GuitarChord> = {
  // --- DO / C ---
  'C': {
    name: 'DO', symbol: 'C', type: 'major', typeName: 'Mayor',
    frets: [-1, 3, 2, 0, 1, 0], fingers: [0, 3, 2, 0, 1, 0],
    notes: ['Do', 'Mi', 'Sol'],
    stringNotes: ['X', 'Do', 'Mi', 'Sol', 'Do', 'Mi'],
    description: 'Dedo 1 en 2ª cuerda traste 1, dedo 2 en 4ª cuerda traste 2 y dedo 3 en 5ª cuerda traste 3. La 6ª cuerda no se toca.',
    resolvesTo: ['FA', 'SOL', 'SOL7', 'LAm'],
    difficulty: 'Fácil'
  },
  'Cm': {
    name: 'DOm', symbol: 'Cm', type: 'minor', typeName: 'Menor',
    frets: [-1, 3, 5, 5, 4, 3], fingers: [0, 1, 3, 4, 2, 1], barre: 3, baseFret: 3,
    notes: ['Do', 'Mib', 'Sol'],
    stringNotes: ['X', 'Do', 'Sol', 'Do', 'Mib', 'Sol'],
    description: 'Cejilla con el dedo 1 en el traste 3 desde la 5ª cuerda. Dedos 3 y 4 en cuerdas 4 y 3 traste 5; dedo 2 en 2ª cuerda traste 4.',
    resolvesTo: ['FAm', 'SOL7', 'MIb'],
    difficulty: 'Media'
  },
  'C7': {
    name: 'DO7', symbol: 'C7', type: '7th', typeName: 'Séptima',
    frets: [-1, 3, 2, 3, 1, 0], fingers: [0, 3, 2, 4, 1, 0],
    notes: ['Do', 'Mi', 'Sol', 'Sib'],
    stringNotes: ['X', 'Do', 'Mi', 'Sib', 'Do', 'Mi'],
    description: 'Como el acorde de DO mayor, pero añadiendo el dedo meñique (4) en la 3ª cuerda traste 3.',
    resolvesTo: ['FA', 'FAm'],
    difficulty: 'Media'
  },
  'Cm7': {
    name: 'DOm7', symbol: 'Cm7', type: 'm7', typeName: 'Menor 7',
    frets: [-1, 3, 5, 3, 4, 3], fingers: [0, 1, 3, 1, 2, 1], barre: 3, baseFret: 3,
    notes: ['Do', 'Mib', 'Sol', 'Sib'],
    description: 'Cejilla con dedo 1 en el traste 3 (cuerdas 5 a 1). Dedo 3 en 4ª cuerda traste 5 y dedo 2 en 2ª cuerda traste 4.',
    resolvesTo: ['FAm', 'FA7'],
    difficulty: 'Media'
  },
  'Cmaj7': {
    name: 'DOmaj7', symbol: 'Cmaj7', type: 'maj7', typeName: 'Mayor 7',
    frets: [-1, 3, 2, 0, 0, 0], fingers: [0, 3, 2, 0, 0, 0],
    notes: ['Do', 'Mi', 'Sol', 'Si'],
    description: 'Dedo 2 en 4ª cuerda traste 2 y dedo 3 en 5ª cuerda traste 3. Cuerdas 1, 2 y 3 al aire.',
    resolvesTo: ['FAmaj7', 'LAm'],
    difficulty: 'Fácil'
  },
  'Csus4': {
    name: 'DOsus4', symbol: 'Csus4', type: 'sus4', typeName: 'Sus 4',
    frets: [-1, 3, 3, 0, 1, 1], fingers: [0, 3, 4, 0, 1, 1], barre: 1,
    notes: ['Do', 'Fa', 'Sol'],
    description: 'Dedo 1 cubre cuerdas 1 y 2 en traste 1, dedo 3 en 5ª cuerda traste 3 y dedo 4 en 4ª cuerda traste 3.',
    resolvesTo: ['DO', 'SOL'],
    difficulty: 'Media'
  },

  // --- DO# / REb (C# / Db) ---
  'C#': {
    name: 'DO#', symbol: 'C#', type: 'major', typeName: 'Mayor',
    frets: [-1, 4, 6, 6, 6, 4], fingers: [0, 1, 2, 3, 4, 1], barre: 4, baseFret: 4,
    notes: ['Do#', 'Fa', 'Sol#'],
    description: 'Cejilla con dedo 1 en traste 4 desde la 5ª cuerda, y dedos 2, 3 y 4 en cuerdas 4, 3 y 2 traste 6.',
    resolvesTo: ['FA#', 'SOL#', 'SOL#7'],
    difficulty: 'Media'
  },
  'C#m': {
    name: 'DO#m', symbol: 'C#m', type: 'minor', typeName: 'Menor',
    frets: [-1, 4, 6, 6, 5, 4], fingers: [0, 1, 3, 4, 2, 1], barre: 4, baseFret: 4,
    notes: ['Do#', 'Mi', 'Sol#'],
    description: 'Cejilla con dedo 1 en el traste 4, dedo 2 en 2ª cuerda traste 5, y dedos 3 y 4 en cuerdas 4 y 3 traste 6.',
    resolvesTo: ['FA#m', 'SOL#7', 'LA'],
    difficulty: 'Media'
  },
  'C#7': {
    name: 'DO#7', symbol: 'C#7', type: '7th', typeName: 'Séptima',
    frets: [-1, 4, 6, 4, 6, 4], fingers: [0, 1, 3, 1, 4, 1], barre: 4, baseFret: 4,
    notes: ['Do#', 'Fa', 'Sol#', 'Si'],
    description: 'Cejilla en traste 4; dedo 3 en 4ª cuerda traste 6 y dedo 4 en 2ª cuerda traste 6.',
    resolvesTo: ['FA#', 'FA#m'],
    difficulty: 'Media'
  },
  'C#m7': {
    name: 'DO#m7', symbol: 'C#m7', type: 'm7', typeName: 'Menor 7',
    frets: [-1, 4, 6, 4, 5, 4], fingers: [0, 1, 3, 1, 2, 1], barre: 4, baseFret: 4,
    notes: ['Do#', 'Mi', 'Sol#', 'Si'],
    description: 'Cejilla en traste 4 desde la 5ª cuerda, dedo 3 en 4ª cuerda traste 6, dedo 2 en 2ª cuerda traste 5.',
    resolvesTo: ['FA#m'],
    difficulty: 'Media'
  },

  // --- RE / D ---
  'D': {
    name: 'RE', symbol: 'D', type: 'major', typeName: 'Mayor',
    frets: [-1, -1, 0, 2, 3, 2], fingers: [0, 0, 0, 1, 3, 2],
    notes: ['Re', 'Fa#', 'La'],
    stringNotes: ['X', 'X', 'Re', 'La', 'Re', 'Fa#'],
    description: 'Dedo 1 en 3ª cuerda traste 2, dedo 2 en 1ª cuerda traste 2 y dedo 3 en 2ª cuerda traste 3. Cuerda 4 al aire.',
    resolvesTo: ['SOL', 'LA', 'LA7', 'SIm'],
    difficulty: 'Fácil'
  },
  'Dm': {
    name: 'REm', symbol: 'Dm', type: 'minor', typeName: 'Menor',
    frets: [-1, -1, 0, 2, 3, 1], fingers: [0, 0, 0, 2, 3, 1],
    notes: ['Re', 'Fa', 'La'],
    stringNotes: ['X', 'X', 'Re', 'La', 'Re', 'Fa'],
    description: 'Dedo 1 en la 1ª cuerda traste 1, dedo 2 en 3ª cuerda traste 2 y dedo 3 en 2ª cuerda traste 3.',
    resolvesTo: ['SOLm', 'LA7', 'FA'],
    difficulty: 'Fácil'
  },
  'D7': {
    name: 'RE7', symbol: 'D7', type: '7th', typeName: 'Séptima',
    frets: [-1, -1, 0, 2, 1, 2], fingers: [0, 0, 0, 2, 1, 3],
    notes: ['Re', 'Fa#', 'La', 'Do'],
    description: 'Dedo 1 en 2ª cuerda traste 1, dedo 2 en 3ª cuerda traste 2 y dedo 3 en 1ª cuerda traste 2.',
    resolvesTo: ['SOL', 'SOLm'],
    difficulty: 'Fácil'
  },
  'Dm7': {
    name: 'REm7', symbol: 'Dm7', type: 'm7', typeName: 'Menor 7',
    frets: [-1, -1, 0, 2, 1, 1], fingers: [0, 0, 0, 2, 1, 1], barre: 1,
    notes: ['Re', 'Fa', 'La', 'Do'],
    description: 'Media cejilla con el dedo 1 pisando cuerdas 1 y 2 en traste 1, y dedo 2 en 3ª cuerda traste 2.',
    resolvesTo: ['SOLm', 'SOL7'],
    difficulty: 'Fácil'
  },
  'Dmaj7': {
    name: 'REmaj7', symbol: 'Dmaj7', type: 'maj7', typeName: 'Mayor 7',
    frets: [-1, -1, 0, 2, 2, 2], fingers: [0, 0, 0, 1, 1, 1], barre: 2,
    notes: ['Re', 'Fa#', 'La', 'Do#'],
    description: 'Media cejilla con dedo 1 en el traste 2 pisando las cuerdas 1, 2 y 3. 4ª cuerda al aire.',
    resolvesTo: ['SOLmaj7'],
    difficulty: 'Fácil'
  },
  'Dsus4': {
    name: 'REsus4', symbol: 'Dsus4', type: 'sus4', typeName: 'Sus 4',
    frets: [-1, -1, 0, 2, 3, 3], fingers: [0, 0, 0, 1, 2, 3],
    notes: ['Re', 'Sol', 'La'],
    description: 'Dedo 1 en 3ª cuerda traste 2, dedo 2 en 2ª cuerda traste 3 y dedo 3 en 1ª cuerda traste 3.',
    resolvesTo: ['RE', 'LA'],
    difficulty: 'Fácil'
  },

  // --- RE# / MIb (Eb) ---
  'Eb': {
    name: 'MIb', symbol: 'Eb', type: 'major', typeName: 'Mayor',
    frets: [-1, 6, 8, 8, 8, 6], fingers: [0, 1, 2, 3, 4, 1], barre: 6, baseFret: 6,
    notes: ['Mib', 'Sol', 'Sib'],
    description: 'Cejilla con dedo 1 en traste 6 desde 5ª cuerda; dedos 2, 3 y 4 en cuerdas 4, 3 y 2 traste 8.',
    resolvesTo: ['LAb', 'SIb', 'SIb7', 'DOm'],
    difficulty: 'Media'
  },
  'Ebm': {
    name: 'MIbm', symbol: 'Ebm', type: 'minor', typeName: 'Menor',
    frets: [-1, 6, 8, 8, 7, 6], fingers: [0, 1, 3, 4, 2, 1], barre: 6, baseFret: 6,
    notes: ['Mib', 'Solb', 'Sib'],
    description: 'Cejilla con dedo 1 en traste 6, dedo 2 en 2ª cuerda traste 7, dedos 3 y 4 en cuerdas 4 y 3 traste 8.',
    resolvesTo: ['LAbm', 'SIb7'],
    difficulty: 'Media'
  },
  'Eb7': {
    name: 'MIb7', symbol: 'Eb7', type: '7th', typeName: 'Séptima',
    frets: [-1, 6, 8, 6, 8, 6], fingers: [0, 1, 3, 1, 4, 1], barre: 6, baseFret: 6,
    notes: ['Mib', 'Sol', 'Sib', 'Reb'],
    description: 'Cejilla en traste 6; dedo 3 en 4ª cuerda traste 8 y dedo 4 en 2ª cuerda traste 8.',
    resolvesTo: ['LAb', 'LAbm'],
    difficulty: 'Media'
  },

  // --- MI / E ---
  'E': {
    name: 'MI', symbol: 'E', type: 'major', typeName: 'Mayor',
    frets: [0, 2, 2, 1, 0, 0], fingers: [0, 2, 3, 1, 0, 0],
    notes: ['Mi', 'Sol#', 'Si'],
    stringNotes: ['Mi', 'Si', 'Mi', 'Sol#', 'Si', 'Mi'],
    description: 'Dedo 1 en 3ª cuerda traste 1, dedo 2 en 5ª cuerda traste 2 y dedo 3 en 4ª cuerda traste 2. Las demás al aire.',
    resolvesTo: ['LA', 'SI', 'SI7', 'DO#m'],
    difficulty: 'Fácil'
  },
  'Em': {
    name: 'MIm', symbol: 'Em', type: 'minor', typeName: 'Menor',
    frets: [0, 2, 2, 0, 0, 0], fingers: [0, 2, 3, 0, 0, 0],
    notes: ['Mi', 'Sol', 'Si'],
    stringNotes: ['Mi', 'Si', 'Mi', 'Sol', 'Si', 'Mi'],
    description: 'Dedo 2 en la 5ª cuerda traste 2 y dedo 3 en la 4ª cuerda traste 2. Todas las demás cuerdas al aire.',
    resolvesTo: ['LAm', 'SI7', 'SOL'],
    difficulty: 'Fácil'
  },
  'E7': {
    name: 'MI7', symbol: 'E7', type: '7th', typeName: 'Séptima',
    frets: [0, 2, 0, 1, 0, 0], fingers: [0, 2, 0, 1, 0, 0],
    notes: ['Mi', 'Sol#', 'Si', 'Re'],
    description: 'Dedo 1 en 3ª cuerda traste 1 y dedo 2 en 5ª cuerda traste 2. Cuerdas 6, 4, 2 y 1 al aire.',
    resolvesTo: ['LA', 'LAm'],
    difficulty: 'Fácil'
  },
  'Em7': {
    name: 'MIm7', symbol: 'Em7', type: 'm7', typeName: 'Menor 7',
    frets: [0, 2, 2, 0, 3, 0], fingers: [0, 2, 3, 0, 4, 0],
    notes: ['Mi', 'Sol', 'Si', 'Re'],
    description: 'Dedos 2 y 3 en cuerdas 5 y 4 traste 2, y dedo 4 en 2ª cuerda traste 3. Cuerdas 1, 3 y 6 al aire.',
    resolvesTo: ['LAm', 'SOL'],
    difficulty: 'Fácil'
  },
  'Emaj7': {
    name: 'MImaj7', symbol: 'Emaj7', type: 'maj7', typeName: 'Mayor 7',
    frets: [0, 2, 1, 1, 0, 0], fingers: [0, 3, 1, 2, 0, 0],
    notes: ['Mi', 'Sol#', 'Si', 'Re#'],
    description: 'Dedo 1 en 4ª cuerda traste 1, dedo 2 en 3ª cuerda traste 1 y dedo 3 en 5ª cuerda traste 2.',
    resolvesTo: ['LAmaj7'],
    difficulty: 'Media'
  },
  'Esus4': {
    name: 'MIsus4', symbol: 'Esus4', type: 'sus4', typeName: 'Sus 4',
    frets: [0, 2, 2, 2, 0, 0], fingers: [0, 2, 3, 4, 0, 0],
    notes: ['Mi', 'La', 'Si'],
    description: 'Dedos 2, 3 y 4 en el traste 2 de las cuerdas 5, 4 y 3. Cuerdas 1, 2 y 6 al aire.',
    resolvesTo: ['MI', 'SI'],
    difficulty: 'Fácil'
  },

  // --- FA / F ---
  'F': {
    name: 'FA', symbol: 'F', type: 'major', typeName: 'Mayor',
    frets: [1, 3, 3, 2, 1, 1], fingers: [1, 3, 4, 2, 1, 1], barre: 1,
    notes: ['Fa', 'La', 'Do'],
    stringNotes: ['Fa', 'Do', 'Fa', 'La', 'Do', 'Fa'],
    description: 'Cejilla completa con el dedo 1 en el traste 1. Dedo 2 en 3ª cuerda traste 2, dedos 3 y 4 en cuerdas 5 y 4 traste 3.',
    resolvesTo: ['SIb', 'DO', 'DO7', 'REm'],
    difficulty: 'Avanzada'
  },
  'Fm': {
    name: 'FAm', symbol: 'Fm', type: 'minor', typeName: 'Menor',
    frets: [1, 3, 3, 1, 1, 1], fingers: [1, 3, 4, 1, 1, 1], barre: 1,
    notes: ['Fa', 'Lab', 'Do'],
    description: 'Cejilla con el dedo 1 en el traste 1. Dedos 3 y 4 en las cuerdas 5 y 4 en el traste 3.',
    resolvesTo: ['SIbm', 'DO7'],
    difficulty: 'Avanzada'
  },
  'F7': {
    name: 'FA7', symbol: 'F7', type: '7th', typeName: 'Séptima',
    frets: [1, 3, 1, 2, 1, 1], fingers: [1, 3, 1, 2, 1, 1], barre: 1,
    notes: ['Fa', 'La', 'Do', 'Mib'],
    description: 'Cejilla con dedo 1 en traste 1; dedo 3 en 5ª cuerda traste 3 y dedo 2 en 3ª cuerda traste 2.',
    resolvesTo: ['SIb', 'SIbm'],
    difficulty: 'Avanzada'
  },
  'Fm7': {
    name: 'FAm7', symbol: 'Fm7', type: 'm7', typeName: 'Menor 7',
    frets: [1, 3, 1, 1, 1, 1], fingers: [1, 3, 1, 1, 1, 1], barre: 1,
    notes: ['Fa', 'Lab', 'Do', 'Mib'],
    description: 'Cejilla con dedo 1 en traste 1 y dedo 3 en 5ª cuerda traste 3.',
    resolvesTo: ['SIbm'],
    difficulty: 'Avanzada'
  },
  'Fmaj7': {
    name: 'FAmaj7', symbol: 'Fmaj7', type: 'maj7', typeName: 'Mayor 7',
    frets: [-1, -1, 3, 2, 1, 0], fingers: [0, 0, 3, 2, 1, 0],
    notes: ['Fa', 'La', 'Do', 'Mi'],
    description: 'Dedo 1 en 2ª cuerda traste 1, dedo 2 en 3ª cuerda traste 2, dedo 3 en 4ª cuerda traste 3. 1ª cuerda al aire.',
    resolvesTo: ['SIbmaj7', 'REm'],
    difficulty: 'Fácil'
  },
  'Fsus4': {
    name: 'FAsus4', symbol: 'Fsus4', type: 'sus4', typeName: 'Sus 4',
    frets: [1, 3, 3, 3, 1, 1], fingers: [1, 2, 3, 4, 1, 1], barre: 1,
    notes: ['Fa', 'Sib', 'Do'],
    description: 'Cejilla en traste 1 con dedo 1; dedos 2, 3 y 4 en el traste 3 de las cuerdas 5, 4 y 3.',
    resolvesTo: ['FA', 'DO'],
    difficulty: 'Avanzada'
  },

  // --- FA# / SOLb (F# / Gb) ---
  'F#': {
    name: 'FA#', symbol: 'F#', type: 'major', typeName: 'Mayor',
    frets: [2, 4, 4, 3, 2, 2], fingers: [1, 3, 4, 2, 1, 1], barre: 2,
    notes: ['Fa#', 'La#', 'Do#'],
    description: 'Cejilla completa en el traste 2; dedo 2 en 3ª cuerda traste 3, dedos 3 y 4 en cuerdas 5 y 4 traste 4.',
    resolvesTo: ['SI', 'DO#', 'DO#7', 'RE#m'],
    difficulty: 'Avanzada'
  },
  'F#m': {
    name: 'FA#m', symbol: 'F#m', type: 'minor', typeName: 'Menor',
    frets: [2, 4, 4, 2, 2, 2], fingers: [1, 3, 4, 1, 1, 1], barre: 2,
    notes: ['Fa#', 'La', 'Do#'],
    stringNotes: ['Fa#', 'Do#', 'Fa#', 'La', 'Do#', 'Fa#'],
    description: 'Cejilla en traste 2 con dedo 1; dedos 3 y 4 en las cuerdas 5 y 4 en el traste 4.',
    resolvesTo: ['SIm', 'DO#7', 'LA'],
    difficulty: 'Avanzada'
  },
  'F#7': {
    name: 'FA#7', symbol: 'F#7', type: '7th', typeName: 'Séptima',
    frets: [2, 4, 2, 3, 2, 2], fingers: [1, 3, 1, 2, 1, 1], barre: 2,
    notes: ['Fa#', 'La#', 'Do#', 'Mi'],
    description: 'Cejilla en traste 2; dedo 3 en 5ª cuerda traste 4 y dedo 2 en 3ª cuerda traste 3.',
    resolvesTo: ['SI', 'SIm'],
    difficulty: 'Avanzada'
  },
  'F#m7': {
    name: 'FA#m7', symbol: 'F#m7', type: 'm7', typeName: 'Menor 7',
    frets: [2, 4, 2, 2, 2, 2], fingers: [1, 3, 1, 1, 1, 1], barre: 2,
    notes: ['Fa#', 'La', 'Do#', 'Mi'],
    description: 'Cejilla en traste 2 y dedo 3 en 5ª cuerda traste 4.',
    resolvesTo: ['SIm'],
    difficulty: 'Avanzada'
  },

  // --- SOL / G ---
  'G': {
    name: 'SOL', symbol: 'G', type: 'major', typeName: 'Mayor',
    frets: [3, 2, 0, 0, 0, 3], fingers: [2, 1, 0, 0, 0, 3],
    notes: ['Sol', 'Si', 'Re'],
    stringNotes: ['Sol', 'Si', 'Re', 'Sol', 'Si', 'Sol'],
    description: 'Dedo 1 en 5ª cuerda traste 2, dedo 2 en 6ª cuerda traste 3 y dedo 3 en 1ª cuerda traste 3.',
    resolvesTo: ['DO', 'RE', 'RE7', 'MIm'],
    difficulty: 'Fácil'
  },
  'Gm': {
    name: 'SOLm', symbol: 'Gm', type: 'minor', typeName: 'Menor',
    frets: [3, 5, 5, 3, 3, 3], fingers: [1, 3, 4, 1, 1, 1], barre: 3, baseFret: 3,
    notes: ['Sol', 'Sib', 'Re'],
    description: 'Cejilla en el traste 3 con dedo 1; dedos 3 y 4 en cuerdas 5 y 4 traste 5.',
    resolvesTo: ['DOm', 'RE7', 'SIb'],
    difficulty: 'Avanzada'
  },
  'G7': {
    name: 'SOL7', symbol: 'G7', type: '7th', typeName: 'Séptima',
    frets: [3, 2, 0, 0, 0, 1], fingers: [3, 2, 0, 0, 0, 1],
    notes: ['Sol', 'Si', 'Re', 'Fa'],
    stringNotes: ['Sol', 'Si', 'Re', 'Sol', 'Si', 'Fa'],
    description: 'Dedo 1 en 1ª cuerda traste 1, dedo 2 en 5ª cuerda traste 2 y dedo 3 en 6ª cuerda traste 3.',
    resolvesTo: ['DO', 'DOm'],
    difficulty: 'Fácil'
  },
  'Gm7': {
    name: 'SOLm7', symbol: 'Gm7', type: 'm7', typeName: 'Menor 7',
    frets: [3, 5, 3, 3, 3, 3], fingers: [1, 3, 1, 1, 1, 1], barre: 3, baseFret: 3,
    notes: ['Sol', 'Sib', 'Re', 'Fa'],
    description: 'Cejilla en traste 3 con dedo 1 y dedo 3 en 5ª cuerda traste 5.',
    resolvesTo: ['DOm', 'DO7'],
    difficulty: 'Avanzada'
  },
  'Gmaj7': {
    name: 'SOLmaj7', symbol: 'Gmaj7', type: 'maj7', typeName: 'Mayor 7',
    frets: [3, 2, 0, 0, 0, 2], fingers: [2, 1, 0, 0, 0, 3],
    notes: ['Sol', 'Si', 'Re', 'Fa#'],
    description: 'Dedo 1 en 5ª cuerda traste 2, dedo 2 en 6ª cuerda traste 3, dedo 3 en 1ª cuerda traste 2.',
    resolvesTo: ['DOmaj7', 'MIm'],
    difficulty: 'Fácil'
  },
  'Gsus4': {
    name: 'SOLsus4', symbol: 'Gsus4', type: 'sus4', typeName: 'Sus 4',
    frets: [3, 2, 0, 0, 1, 3], fingers: [3, 2, 0, 0, 1, 4],
    notes: ['Sol', 'Do', 'Re'],
    description: 'Dedo 1 en 2ª cuerda traste 1, dedo 2 en 5ª cuerda traste 2, dedo 3 en 6ª cuerda traste 3 y dedo 4 en 1ª cuerda traste 3.',
    resolvesTo: ['SOL', 'RE'],
    difficulty: 'Media'
  },

  // --- SOL# / LAb (Ab) ---
  'Ab': {
    name: 'LAb', symbol: 'Ab', type: 'major', typeName: 'Mayor',
    frets: [4, 6, 6, 5, 4, 4], fingers: [1, 3, 4, 2, 1, 1], barre: 4, baseFret: 4,
    notes: ['Lab', 'Do', 'Mib'],
    description: 'Cejilla completa en traste 4; dedo 2 en 3ª cuerda traste 5, dedos 3 y 4 en cuerdas 5 y 4 traste 6.',
    resolvesTo: ['REb', 'MIb', 'MIb7', 'FAm'],
    difficulty: 'Avanzada'
  },
  'G#m': {
    name: 'SOL#m', symbol: 'G#m', type: 'minor', typeName: 'Menor',
    frets: [4, 6, 6, 4, 4, 4], fingers: [1, 3, 4, 1, 1, 1], barre: 4, baseFret: 4,
    notes: ['Sol#', 'Si', 'Re#'],
    description: 'Cejilla en traste 4 con dedo 1; dedos 3 y 4 en cuerdas 5 y 4 traste 6.',
    resolvesTo: ['DO#m', 'RE#7'],
    difficulty: 'Avanzada'
  },
  'Abm': {
    name: 'LAbm', symbol: 'Abm', type: 'minor', typeName: 'Menor',
    frets: [4, 6, 6, 4, 4, 4], fingers: [1, 3, 4, 1, 1, 1], barre: 4, baseFret: 4,
    notes: ['Lab', 'Si', 'Mib'],
    description: 'Igual a SOL#m: cejilla en traste 4; dedos 3 y 4 en cuerdas 5 y 4 traste 6.',
    resolvesTo: ['REbm', 'MIb7'],
    difficulty: 'Avanzada'
  },
  'G#7': {
    name: 'SOL#7', symbol: 'G#7', type: '7th', typeName: 'Séptima',
    frets: [4, 6, 4, 5, 4, 4], fingers: [1, 3, 1, 2, 1, 1], barre: 4, baseFret: 4,
    notes: ['Sol#', 'Do', 'Re#', 'Fa#'],
    description: 'Cejilla en traste 4; dedo 3 en 5ª cuerda traste 6 y dedo 2 en 3ª cuerda traste 5.',
    resolvesTo: ['DO#', 'DO#m'],
    difficulty: 'Avanzada'
  },
  'Ab7': {
    name: 'LAb7', symbol: 'Ab7', type: '7th', typeName: 'Séptima',
    frets: [4, 6, 4, 5, 4, 4], fingers: [1, 3, 1, 2, 1, 1], barre: 4, baseFret: 4,
    notes: ['Lab', 'Do', 'Mib', 'Solb'],
    description: 'Cejilla en traste 4; dedo 3 en 5ª cuerda traste 6 y dedo 2 en 3ª cuerda traste 5.',
    resolvesTo: ['REb', 'REbm'],
    difficulty: 'Avanzada'
  },

  // --- LA / A ---
  'A': {
    name: 'LA', symbol: 'A', type: 'major', typeName: 'Mayor',
    frets: [-1, 0, 2, 2, 2, 0], fingers: [0, 0, 1, 2, 3, 0],
    notes: ['La', 'Do#', 'Mi'],
    stringNotes: ['X', 'La', 'Mi', 'La', 'Do#', 'Mi'],
    description: 'Dedos 1, 2 y 3 juntos en el traste 2 de las cuerdas 4, 3 y 2. Cuerdas 5 y 1 al aire.',
    resolvesTo: ['RE', 'MI', 'MI7', 'FA#m'],
    difficulty: 'Fácil'
  },
  'Am': {
    name: 'LAm', symbol: 'Am', type: 'minor', typeName: 'Menor',
    frets: [-1, 0, 2, 2, 1, 0], fingers: [0, 0, 2, 3, 1, 0],
    notes: ['La', 'Do', 'Mi'],
    stringNotes: ['X', 'La', 'Mi', 'La', 'Do', 'Mi'],
    description: 'Dedo 1 en 2ª cuerda traste 1, dedos 2 y 3 en cuerdas 4 y 3 traste 2. Cuerda 5 y 1 al aire.',
    resolvesTo: ['REm', 'MI7', 'DO'],
    difficulty: 'Fácil'
  },
  'A7': {
    name: 'LA7', symbol: 'A7', type: '7th', typeName: 'Séptima',
    frets: [-1, 0, 2, 0, 2, 0], fingers: [0, 0, 1, 0, 2, 0],
    notes: ['La', 'Do#', 'Mi', 'Sol'],
    stringNotes: ['X', 'La', 'Mi', 'Sol', 'Do#', 'Mi'],
    description: 'Dedo 1 en 4ª cuerda traste 2 y dedo 2 en 2ª cuerda traste 2. Cuerdas 5, 3 y 1 al aire.',
    resolvesTo: ['RE', 'REm'],
    difficulty: 'Fácil'
  },
  'Am7': {
    name: 'LAm7', symbol: 'Am7', type: 'm7', typeName: 'Menor 7',
    frets: [-1, 0, 2, 0, 1, 0], fingers: [0, 0, 2, 0, 1, 0],
    notes: ['La', 'Do', 'Mi', 'Sol'],
    description: 'Dedo 1 en 2ª cuerda traste 1 y dedo 2 en 4ª cuerda traste 2. Cuerdas 5, 3 y 1 al aire.',
    resolvesTo: ['REm', 'DO'],
    difficulty: 'Fácil'
  },
  'Amaj7': {
    name: 'LAmaj7', symbol: 'Amaj7', type: 'maj7', typeName: 'Mayor 7',
    frets: [-1, 0, 2, 1, 2, 0], fingers: [0, 0, 2, 1, 3, 0],
    notes: ['La', 'Do#', 'Mi', 'Sol#'],
    description: 'Dedo 1 en 3ª cuerda traste 1, dedo 2 en 4ª cuerda traste 2 y dedo 3 en 2ª cuerda traste 2.',
    resolvesTo: ['REmaj7', 'FA#m'],
    difficulty: 'Fácil'
  },
  'Asus4': {
    name: 'LAsus4', symbol: 'Asus4', type: 'sus4', typeName: 'Sus 4',
    frets: [-1, 0, 2, 2, 3, 0], fingers: [0, 0, 1, 2, 3, 0],
    notes: ['La', 'Re', 'Mi'],
    description: 'Dedos 1 y 2 en cuerdas 4 y 3 traste 2, y dedo 3 en 2ª cuerda traste 3. Cuerda 5 y 1 al aire.',
    resolvesTo: ['LA', 'MI'],
    difficulty: 'Fácil'
  },

  // --- LA# / SIb (Bb) ---
  'Bb': {
    name: 'SIb', symbol: 'Bb', type: 'major', typeName: 'Mayor',
    frets: [-1, 1, 3, 3, 3, 1], fingers: [0, 1, 2, 3, 4, 1], barre: 1,
    notes: ['Sib', 'Re', 'Fa'],
    stringNotes: ['X', 'Sib', 'Fa', 'Sib', 'Re', 'Fa'],
    description: 'Cejilla con el dedo 1 en el traste 1 desde la 5ª cuerda. Dedos 2, 3 y 4 en cuerdas 4, 3 y 2 traste 3.',
    resolvesTo: ['MIb', 'FA', 'FA7', 'SOLm'],
    difficulty: 'Avanzada'
  },
  'Bbm': {
    name: 'SIbm', symbol: 'Bbm', type: 'minor', typeName: 'Menor',
    frets: [-1, 1, 3, 3, 2, 1], fingers: [0, 1, 3, 4, 2, 1], barre: 1,
    notes: ['Sib', 'Reb', 'Fa'],
    description: 'Cejilla con dedo 1 en traste 1 desde la 5ª cuerda, dedo 2 en 2ª cuerda traste 2, dedos 3 y 4 en traste 3.',
    resolvesTo: ['MIbm', 'FA7'],
    difficulty: 'Avanzada'
  },
  'Bb7': {
    name: 'SIb7', symbol: 'Bb7', type: '7th', typeName: 'Séptima',
    frets: [-1, 1, 3, 1, 3, 1], fingers: [0, 1, 3, 1, 4, 1], barre: 1,
    notes: ['Sib', 'Re', 'Fa', 'Lab'],
    description: 'Cejilla en traste 1 desde 5ª cuerda; dedo 3 en 4ª cuerda traste 3 y dedo 4 en 2ª cuerda traste 3.',
    resolvesTo: ['MIb', 'MIbm'],
    difficulty: 'Avanzada'
  },
  'Bbm7': {
    name: 'SIbm7', symbol: 'Bbm7', type: 'm7', typeName: 'Menor 7',
    frets: [-1, 1, 3, 1, 2, 1], fingers: [0, 1, 3, 1, 2, 1], barre: 1,
    notes: ['Sib', 'Reb', 'Fa', 'Lab'],
    description: 'Cejilla en traste 1 desde 5ª cuerda; dedo 3 en 4ª cuerda traste 3 y dedo 2 en 2ª cuerda traste 2.',
    resolvesTo: ['MIbm'],
    difficulty: 'Avanzada'
  },

  // --- SI / B ---
  'B': {
    name: 'SI', symbol: 'B', type: 'major', typeName: 'Mayor',
    frets: [-1, 2, 4, 4, 4, 2], fingers: [0, 1, 2, 3, 4, 1], barre: 2,
    notes: ['Si', 'Re#', 'Fa#'],
    stringNotes: ['X', 'Si', 'Fa#', 'Si', 'Re#', 'Fa#'],
    description: 'Cejilla con dedo 1 en traste 2 desde 5ª cuerda; dedos 2, 3 y 4 en cuerdas 4, 3 y 2 traste 4.',
    resolvesTo: ['MI', 'FA#', 'FA#7', 'SOL#m'],
    difficulty: 'Avanzada'
  },
  'Bm': {
    name: 'SIm', symbol: 'Bm', type: 'minor', typeName: 'Menor',
    frets: [-1, 2, 4, 4, 3, 2], fingers: [0, 1, 3, 4, 2, 1], barre: 2,
    notes: ['Si', 'Re', 'Fa#'],
    stringNotes: ['X', 'Si', 'Fa#', 'Si', 'Re', 'Fa#'],
    description: 'Cejilla en el traste 2 desde la 5ª cuerda, dedo 2 en 2ª cuerda traste 3, dedos 3 y 4 en cuerdas 4 y 3 traste 4.',
    resolvesTo: ['MIm', 'FA#7', 'RE'],
    difficulty: 'Avanzada'
  },
  'B7': {
    name: 'SI7', symbol: 'B7', type: '7th', typeName: 'Séptima',
    frets: [-1, 2, 1, 2, 0, 2], fingers: [0, 2, 1, 3, 0, 4],
    notes: ['Si', 'Re#', 'Fa#', 'La'],
    stringNotes: ['X', 'Si', 'Re#', 'La', 'Si', 'Fa#'],
    description: 'Dedo 1 en 4ª cuerda traste 1, dedo 2 en 5ª cuerda traste 2, dedo 3 en 3ª cuerda traste 2 y dedo 4 en 1ª cuerda traste 2.',
    resolvesTo: ['MI', 'MIm'],
    difficulty: 'Media'
  },
  'Bm7': {
    name: 'SIm7', symbol: 'Bm7', type: 'm7', typeName: 'Menor 7',
    frets: [-1, 2, 4, 2, 3, 2], fingers: [0, 1, 3, 1, 2, 1], barre: 2,
    notes: ['Si', 'Re', 'Fa#', 'La'],
    description: 'Cejilla en traste 2 desde 5ª cuerda; dedo 3 en 4ª cuerda traste 4 y dedo 2 en 2ª cuerda traste 3.',
    resolvesTo: ['MIm'],
    difficulty: 'Avanzada'
  },
  'Bsus4': {
    name: 'SIsus4', symbol: 'Bsus4', type: 'sus4', typeName: 'Sus 4',
    frets: [-1, 2, 4, 4, 5, 2], fingers: [0, 1, 2, 3, 4, 1], barre: 2,
    notes: ['Si', 'Mi', 'Fa#'],
    description: 'Cejilla en traste 2 desde 5ª cuerda; dedos 2, 3 y 4 en traste 4 (cuerdas 4 y 3) y traste 5 (cuerda 2).',
    resolvesTo: ['SI', 'FA#'],
    difficulty: 'Avanzada'
  },
};

// -------------------------------------------------------------
// Familias Armónicas de Acompañamiento para Guitarra
// Contiene las 12 tonalidades mayores cromáticas y las menores clave
// -------------------------------------------------------------
export const GUITAR_HARMONIC_FAMILIES: Record<string, HarmonicFamily> = {
  // === TONALIDADES MAYORES (12 CROMÁTICAS) ===
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
  'C#': {
    keyName: 'DO#',
    keySymbol: 'C#',
    tonic: GUITAR_CHORDS_DB['C#'],
    subdominant: GUITAR_CHORDS_DB['F#'],
    dominant: GUITAR_CHORDS_DB['Ab'] || GUITAR_CHORDS_DB['G#7'],
    dominant7: GUITAR_CHORDS_DB['Ab7'] || GUITAR_CHORDS_DB['G#7'],
    relativeMinor: GUITAR_CHORDS_DB['Bbm'],
    secondary1: GUITAR_CHORDS_DB['Ebm'],
    secondary2: GUITAR_CHORDS_DB['Fm'],
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
  'Eb': {
    keyName: 'MIb',
    keySymbol: 'Eb',
    tonic: GUITAR_CHORDS_DB['Eb'],
    subdominant: GUITAR_CHORDS_DB['Ab'],
    dominant: GUITAR_CHORDS_DB['Bb'],
    dominant7: GUITAR_CHORDS_DB['Bb7'],
    relativeMinor: GUITAR_CHORDS_DB['Cm'],
    secondary1: GUITAR_CHORDS_DB['Fm'],
    secondary2: GUITAR_CHORDS_DB['Gm'],
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
  'F#': {
    keyName: 'FA#',
    keySymbol: 'F#',
    tonic: GUITAR_CHORDS_DB['F#'],
    subdominant: GUITAR_CHORDS_DB['B'],
    dominant: GUITAR_CHORDS_DB['C#'],
    dominant7: GUITAR_CHORDS_DB['C#7'],
    relativeMinor: GUITAR_CHORDS_DB['Ebm'],
    secondary1: GUITAR_CHORDS_DB['G#m'],
    secondary2: GUITAR_CHORDS_DB['Bbm'],
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
  'Ab': {
    keyName: 'LAb',
    keySymbol: 'Ab',
    tonic: GUITAR_CHORDS_DB['Ab'],
    subdominant: GUITAR_CHORDS_DB['C#'],
    dominant: GUITAR_CHORDS_DB['Eb'],
    dominant7: GUITAR_CHORDS_DB['Eb7'],
    relativeMinor: GUITAR_CHORDS_DB['Fm'],
    secondary1: GUITAR_CHORDS_DB['Bbm'],
    secondary2: GUITAR_CHORDS_DB['Cm'],
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
    secondary2: GUITAR_CHORDS_DB['Ebm'],
  },

  // === TONALIDADES MENORES CLÁSICAS ===
  'Am': {
    keyName: 'LAm',
    keySymbol: 'Am',
    isMinor: true,
    tonic: GUITAR_CHORDS_DB['Am'],
    subdominant: GUITAR_CHORDS_DB['Dm'],
    dominant: GUITAR_CHORDS_DB['Em'],
    dominant7: GUITAR_CHORDS_DB['E7'],
    relativeMinor: GUITAR_CHORDS_DB['C'],
    secondary1: GUITAR_CHORDS_DB['F'],
    secondary2: GUITAR_CHORDS_DB['G'],
  },
  'Em': {
    keyName: 'MIm',
    keySymbol: 'Em',
    isMinor: true,
    tonic: GUITAR_CHORDS_DB['Em'],
    subdominant: GUITAR_CHORDS_DB['Am'],
    dominant: GUITAR_CHORDS_DB['Bm'],
    dominant7: GUITAR_CHORDS_DB['B7'],
    relativeMinor: GUITAR_CHORDS_DB['G'],
    secondary1: GUITAR_CHORDS_DB['C'],
    secondary2: GUITAR_CHORDS_DB['D'],
  },
  'Dm': {
    keyName: 'REm',
    keySymbol: 'Dm',
    isMinor: true,
    tonic: GUITAR_CHORDS_DB['Dm'],
    subdominant: GUITAR_CHORDS_DB['Gm'],
    dominant: GUITAR_CHORDS_DB['Am'],
    dominant7: GUITAR_CHORDS_DB['A7'],
    relativeMinor: GUITAR_CHORDS_DB['F'],
    secondary1: GUITAR_CHORDS_DB['Bb'],
    secondary2: GUITAR_CHORDS_DB['C'],
  },
  'Bm': {
    keyName: 'SIm',
    keySymbol: 'Bm',
    isMinor: true,
    tonic: GUITAR_CHORDS_DB['Bm'],
    subdominant: GUITAR_CHORDS_DB['Em'],
    dominant: GUITAR_CHORDS_DB['F#m'],
    dominant7: GUITAR_CHORDS_DB['F#7'],
    relativeMinor: GUITAR_CHORDS_DB['D'],
    secondary1: GUITAR_CHORDS_DB['G'],
    secondary2: GUITAR_CHORDS_DB['A'],
  },
  'F#m': {
    keyName: 'FA#m',
    keySymbol: 'F#m',
    isMinor: true,
    tonic: GUITAR_CHORDS_DB['F#m'],
    subdominant: GUITAR_CHORDS_DB['Bm'],
    dominant: GUITAR_CHORDS_DB['C#m'],
    dominant7: GUITAR_CHORDS_DB['C#7'],
    relativeMinor: GUITAR_CHORDS_DB['A'],
    secondary1: GUITAR_CHORDS_DB['D'],
    secondary2: GUITAR_CHORDS_DB['E'],
  },
  'Cm': {
    keyName: 'DOm',
    keySymbol: 'Cm',
    isMinor: true,
    tonic: GUITAR_CHORDS_DB['Cm'],
    subdominant: GUITAR_CHORDS_DB['Fm'],
    dominant: GUITAR_CHORDS_DB['Gm'],
    dominant7: GUITAR_CHORDS_DB['G7'],
    relativeMinor: GUITAR_CHORDS_DB['Eb'],
    secondary1: GUITAR_CHORDS_DB['Ab'],
    secondary2: GUITAR_CHORDS_DB['Bb'],
  },
  'Gm': {
    keyName: 'SOLm',
    keySymbol: 'Gm',
    isMinor: true,
    tonic: GUITAR_CHORDS_DB['Gm'],
    subdominant: GUITAR_CHORDS_DB['Cm'],
    dominant: GUITAR_CHORDS_DB['Dm'],
    dominant7: GUITAR_CHORDS_DB['D7'],
    relativeMinor: GUITAR_CHORDS_DB['Bb'],
    secondary1: GUITAR_CHORDS_DB['Eb'],
    secondary2: GUITAR_CHORDS_DB['F'],
  },
};
