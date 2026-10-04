export interface UkuleleChord {
  name: string;        // ej: "DO", "SOL", "REm"
  symbol: string;      // ej: "C", "G", "Dm"
  type: 'major' | 'minor' | '7th' | 'm7' | 'maj7' | 'sus4' | 'dim';
  typeName: string;    // ej: "Mayor", "Menor", "Séptima", "Sus 4", "Disminuido"
  // Trastes para las cuerdas del instrumento (4 para ukelele, 6 para guitarra)
  // 0 = al aire, -1 = silenciada, >0 = número de traste
  frets: number[];
  // Dedos sugeridos: [1=índice, 2=medio, 3=anular, 4=meñique], 0 = sin dedo
  fingers?: number[];
  baseFret?: number; // traste inicial en el mástil (1 por defecto)
  barre?: number;    // traste donde va la cejilla
  notes?: string[];  // Notas que componen el acorde, ej: ['Do', 'Mi', 'Sol']
  stringNotes?: string[]; // Nota en cada cuerda (de 4ª a 1ª en uke: G-C-E-A)
  description?: string; // Guía explicativa para colocar los dedos
  resolvesTo?: string[]; // Acordes complementarios habituales
  difficulty?: 'Fácil' | 'Media' | 'Avanzada';
}

export interface HarmonicFamily {
  keyName: string;    // ej: "DO" o "LA menor"
  keySymbol: string;  // ej: "C" o "Am"
  isMinor?: boolean;
  tonic: UkuleleChord;        // I (o i)
  subdominant: UkuleleChord;  // IV (o iv)
  dominant: UkuleleChord;     // V
  dominant7: UkuleleChord;    // V7
  relativeMinor: UkuleleChord;// vi (o Relativa Mayor III)
  secondary1: UkuleleChord;   // ii (o subdominante secundaria)
  secondary2: UkuleleChord;   // iii (o séptima menor)
}

// -------------------------------------------------------------
// Biblioteca Completa de Acordes de Ukelele (Afinación G-C-E-A)
// Cuerdas ordenadas: [4ª(G), 3ª(C), 2ª(E), 1ª(A)]
// -------------------------------------------------------------
export const UKULELE_CHORDS_DB: Record<string, UkuleleChord> = {
  // --- DO / C ---
  'C': {
    name: 'DO', symbol: 'C', type: 'major', typeName: 'Mayor',
    frets: [0, 0, 0, 3], fingers: [0, 0, 0, 3],
    notes: ['Do', 'Mi', 'Sol'],
    stringNotes: ['Sol', 'Do', 'Mi', 'Do'],
    description: 'Coloca el dedo anular (3) en la 1ª cuerda en el traste 3. Deja sonar las cuerdas 2, 3 y 4 al aire.',
    resolvesTo: ['FA', 'SOL', 'SOL7', 'LAm'],
    difficulty: 'Fácil'
  },
  'Cm': {
    name: 'DOm', symbol: 'Cm', type: 'minor', typeName: 'Menor',
    frets: [0, 3, 3, 3], fingers: [0, 1, 2, 3],
    notes: ['Do', 'Mib', 'Sol'],
    stringNotes: ['Sol', 'Mib', 'Sol', 'Do'],
    description: 'Dedos 1, 2 y 3 en las cuerdas 3, 2 y 1 en el traste 3 (o media cejilla con dedo 1). La 4ª cuerda va al aire.',
    resolvesTo: ['FAm', 'SOL7', 'MIb'],
    difficulty: 'Media'
  },
  'C7': {
    name: 'DO7', symbol: 'C7', type: '7th', typeName: 'Séptima',
    frets: [0, 0, 0, 1], fingers: [0, 0, 0, 1],
    notes: ['Do', 'Mi', 'Sol', 'Sib'],
    stringNotes: ['Sol', 'Do', 'Mi', 'Sib'],
    description: 'Coloca el dedo índice (1) en la 1ª cuerda en el traste 1. Cuerdas 2, 3 y 4 al aire.',
    resolvesTo: ['FA', 'FAm'],
    difficulty: 'Fácil'
  },
  'Cm7': {
    name: 'DOm7', symbol: 'Cm7', type: 'm7', typeName: 'Menor 7',
    frets: [3, 3, 3, 3], fingers: [1, 1, 1, 1], barre: 3,
    notes: ['Do', 'Mib', 'Sol', 'Sib'],
    description: 'Cejilla con el dedo índice (1) presionando las 4 cuerdas en el traste 3.',
    resolvesTo: ['FAm', 'FA7'],
    difficulty: 'Media'
  },
  'Cmaj7': {
    name: 'DOmaj7', symbol: 'Cmaj7', type: 'maj7', typeName: 'Mayor 7',
    frets: [0, 0, 0, 2], fingers: [0, 0, 0, 2],
    notes: ['Do', 'Mi', 'Sol', 'Si'],
    description: 'Coloca el dedo medio (2) en la 1ª cuerda en el traste 2. Cuerdas 2, 3 y 4 al aire.',
    resolvesTo: ['FAmaj7', 'LAm'],
    difficulty: 'Fácil'
  },
  'Csus4': {
    name: 'DOsus4', symbol: 'Csus4', type: 'sus4', typeName: 'Sus 4',
    frets: [0, 0, 1, 3], fingers: [0, 0, 1, 3],
    notes: ['Do', 'Fa', 'Sol'],
    description: 'Dedo 1 en la 2ª cuerda traste 1 y dedo 3 en la 1ª cuerda traste 3.',
    resolvesTo: ['DO', 'SOL'],
    difficulty: 'Fácil'
  },
  'Cdim': {
    name: 'DOdim', symbol: 'Cdim', type: 'dim', typeName: 'Disminuido',
    frets: [2, 3, 2, 3], fingers: [1, 3, 2, 4],
    notes: ['Do', 'Mib', 'Solb', 'La'],
    description: 'Forma en rombo: dedo 1 en 4ª y dedo 2 en 2ª cuerda traste 2; dedos 3 y 4 en traste 3.',
    resolvesTo: ['DO#', 'REm'],
    difficulty: 'Media'
  },

  // --- DO# / REb (C# / Db) ---
  'C#': {
    name: 'DO#', symbol: 'C#', type: 'major', typeName: 'Mayor',
    frets: [1, 1, 1, 4], fingers: [1, 1, 1, 4], barre: 1,
    notes: ['Do#', 'Fa', 'Sol#'],
    description: 'Media cejilla con dedo 1 en traste 1 en cuerdas 4, 3 y 2, y dedo meñique (4) en 1ª cuerda traste 4.',
    resolvesTo: ['FA#', 'SOL#', 'SOL#7'],
    difficulty: 'Media'
  },
  'C#m': {
    name: 'DO#m', symbol: 'C#m', type: 'minor', typeName: 'Menor',
    frets: [1, 1, 0, 4], fingers: [1, 1, 0, 4],
    notes: ['Do#', 'Mi', 'Sol#'],
    description: 'Dedo 1 presiona cuerdas 4 y 3 en traste 1, 2ª cuerda al aire, dedo 4 en 1ª cuerda traste 4.',
    resolvesTo: ['FA#m', 'SOL#7'],
    difficulty: 'Media'
  },
  'C#7': {
    name: 'DO#7', symbol: 'C#7', type: '7th', typeName: 'Séptima',
    frets: [1, 1, 1, 2], fingers: [1, 1, 1, 2], barre: 1,
    notes: ['Do#', 'Fa', 'Sol#', 'Si'],
    description: 'Cejilla con dedo 1 en traste 1 en cuerdas 4, 3 y 2, y dedo 2 en 1ª cuerda traste 2.',
    resolvesTo: ['FA#', 'FA#m'],
    difficulty: 'Media'
  },
  'C#m7': {
    name: 'DO#m7', symbol: 'C#m7', type: 'm7', typeName: 'Menor 7',
    frets: [4, 4, 4, 4], fingers: [1, 1, 1, 1], barre: 4, baseFret: 4,
    notes: ['Do#', 'Mi', 'Sol#', 'Si'],
    description: 'Cejilla completa con dedo 1 en el traste 4 cubriendo todas las cuerdas.',
    resolvesTo: ['FA#m'],
    difficulty: 'Media'
  },

  // --- RE / D ---
  'D': {
    name: 'RE', symbol: 'D', type: 'major', typeName: 'Mayor',
    frets: [2, 2, 2, 0], fingers: [1, 2, 3, 0],
    notes: ['Re', 'Fa#', 'La'],
    stringNotes: ['La', 'Mi', 'Fa#', 'La'],
    description: 'Dedos 1, 2 y 3 apretados en el traste 2 de las cuerdas 4, 3 y 2. La 1ª cuerda va al aire.',
    resolvesTo: ['SOL', 'LA', 'LA7', 'SIm'],
    difficulty: 'Fácil'
  },
  'Dm': {
    name: 'REm', symbol: 'Dm', type: 'minor', typeName: 'Menor',
    frets: [2, 2, 1, 0], fingers: [2, 3, 1, 0],
    notes: ['Re', 'Fa', 'La'],
    stringNotes: ['La', 'Re', 'Fa', 'La'],
    description: 'Dedo 1 en la 2ª cuerda traste 1, dedos 2 y 3 en las cuerdas 4 y 3 traste 2. 1ª cuerda al aire.',
    resolvesTo: ['SOLm', 'LA7', 'FA'],
    difficulty: 'Fácil'
  },
  'D7': {
    name: 'RE7', symbol: 'D7', type: '7th', typeName: 'Séptima',
    frets: [2, 0, 2, 0], fingers: [1, 0, 2, 0],
    notes: ['Re', 'Fa#', 'La', 'Do'],
    description: 'Dedo 1 en 4ª cuerda traste 2 y dedo 2 en 2ª cuerda traste 2. Cuerdas 1 y 3 al aire.',
    resolvesTo: ['SOL', 'SOLm'],
    difficulty: 'Fácil'
  },
  'Dm7': {
    name: 'REm7', symbol: 'Dm7', type: 'm7', typeName: 'Menor 7',
    frets: [2, 2, 1, 3], fingers: [2, 3, 1, 4],
    notes: ['Re', 'Fa', 'La', 'Do'],
    description: 'Dedo 1 en 2ª cuerda traste 1, dedos 2 y 3 en traste 2 cuerdas 4 y 3, dedo 4 en 1ª cuerda traste 3.',
    resolvesTo: ['SOLm', 'SOL7'],
    difficulty: 'Media'
  },
  'Dmaj7': {
    name: 'REmaj7', symbol: 'Dmaj7', type: 'maj7', typeName: 'Mayor 7',
    frets: [2, 2, 2, 4], fingers: [1, 1, 1, 3], barre: 2,
    notes: ['Re', 'Fa#', 'La', 'Do#'],
    description: 'Cejilla con dedo 1 en traste 2 (cuerdas 4, 3 y 2) y dedo 3 en 1ª cuerda traste 4.',
    resolvesTo: ['SOLmaj7'],
    difficulty: 'Media'
  },
  'Dsus4': {
    name: 'REsus4', symbol: 'Dsus4', type: 'sus4', typeName: 'Sus 4',
    frets: [2, 2, 3, 0], fingers: [1, 2, 3, 0],
    notes: ['Re', 'Sol', 'La'],
    description: 'Dedos 1 y 2 en cuerdas 4 y 3 traste 2, y dedo 3 en 2ª cuerda traste 3.',
    resolvesTo: ['RE', 'LA'],
    difficulty: 'Fácil'
  },
  'Ddim': {
    name: 'REdim', symbol: 'Ddim', type: 'dim', typeName: 'Disminuido',
    frets: [1, 2, 1, 2], fingers: [1, 3, 2, 4],
    notes: ['Re', 'Fa', 'Lab', 'Si'],
    description: 'Dedo 1 en 4ª y dedo 2 en 2ª cuerda traste 1; dedos 3 y 4 en traste 2.',
    resolvesTo: ['MIb', 'MIm'],
    difficulty: 'Media'
  },

  // --- RE# / MIb (D# / Eb) ---
  'Eb': {
    name: 'MIb', symbol: 'Eb', type: 'major', typeName: 'Mayor',
    frets: [3, 3, 3, 1], fingers: [2, 3, 4, 1],
    notes: ['Mib', 'Sol', 'Sib'],
    description: 'Dedo 1 en la 1ª cuerda traste 1; dedos 2, 3 y 4 en cuerdas 4, 3 y 2 traste 3.',
    resolvesTo: ['LAb', 'SIb', 'SIb7', 'DOm'],
    difficulty: 'Media'
  },
  'Ebm': {
    name: 'MIbm', symbol: 'Ebm', type: 'minor', typeName: 'Menor',
    frets: [3, 3, 2, 1], fingers: [3, 4, 2, 1],
    notes: ['Mib', 'Solb', 'Sib'],
    description: 'Dedo 1 en 1ª cuerda traste 1, dedo 2 en 2ª cuerda traste 2, dedos 3 y 4 en cuerdas 4 y 3 traste 3.',
    resolvesTo: ['LAbm', 'SIb7'],
    difficulty: 'Media'
  },
  'Eb7': {
    name: 'MIb7', symbol: 'Eb7', type: '7th', typeName: 'Séptima',
    frets: [3, 3, 3, 4], fingers: [1, 1, 1, 2], barre: 3,
    notes: ['Mib', 'Sol', 'Sib', 'Reb'],
    description: 'Cejilla con dedo 1 en traste 3 (cuerdas 4, 3 y 2) y dedo 2 en 1ª cuerda traste 4.',
    resolvesTo: ['LAb', 'LAbm'],
    difficulty: 'Media'
  },
  'Ebmaj7': {
    name: 'MIbmaj7', symbol: 'Ebmaj7', type: 'maj7', typeName: 'Mayor 7',
    frets: [3, 3, 3, 5], fingers: [1, 1, 1, 3], barre: 3,
    notes: ['Mib', 'Sol', 'Sib', 'Re'],
    description: 'Cejilla en traste 3 con dedo 1 y dedo 3 en 1ª cuerda traste 5.',
    resolvesTo: ['LAbmaj7'],
    difficulty: 'Media'
  },

  // --- MI / E ---
  'E': {
    name: 'MI', symbol: 'E', type: 'major', typeName: 'Mayor',
    frets: [4, 4, 4, 2], fingers: [2, 3, 4, 1],
    notes: ['Mi', 'Sol#', 'Si'],
    description: 'Dedo 1 en 1ª cuerda traste 2; dedos 2, 3 y 4 en traste 4 de las cuerdas 4, 3 y 2.',
    resolvesTo: ['LA', 'SI', 'SI7', 'DO#m'],
    difficulty: 'Media'
  },
  'Em': {
    name: 'MIm', symbol: 'Em', type: 'minor', typeName: 'Menor',
    frets: [0, 4, 3, 2], fingers: [0, 3, 2, 1],
    notes: ['Mi', 'Sol', 'Si'],
    stringNotes: ['Sol', 'Mi', 'Sol', 'Si'],
    description: 'Escalera: dedo 1 en 1ª cuerda traste 2, dedo 2 en 2ª cuerda traste 3, dedo 3 en 3ª cuerda traste 4. Cuerda 4 al aire.',
    resolvesTo: ['LAm', 'SI7', 'SOL'],
    difficulty: 'Fácil'
  },
  'E7': {
    name: 'MI7', symbol: 'E7', type: '7th', typeName: 'Séptima',
    frets: [1, 2, 0, 2], fingers: [1, 2, 0, 3],
    notes: ['Mi', 'Sol#', 'Si', 'Re'],
    description: 'Dedo 1 en 4ª cuerda traste 1, dedo 2 en 3ª cuerda traste 2 y dedo 3 en 1ª cuerda traste 2.',
    resolvesTo: ['LA', 'LAm'],
    difficulty: 'Fácil'
  },
  'Em7': {
    name: 'MIm7', symbol: 'Em7', type: 'm7', typeName: 'Menor 7',
    frets: [0, 2, 0, 2], fingers: [0, 1, 0, 2],
    notes: ['Mi', 'Sol', 'Si', 'Re'],
    description: 'Dedo 1 en 3ª cuerda traste 2 y dedo 2 en 1ª cuerda traste 2. Cuerdas 2 y 4 al aire.',
    resolvesTo: ['LAm', 'SOL'],
    difficulty: 'Fácil'
  },
  'Emaj7': {
    name: 'MImaj7', symbol: 'Emaj7', type: 'maj7', typeName: 'Mayor 7',
    frets: [1, 3, 0, 2], fingers: [1, 3, 0, 2],
    notes: ['Mi', 'Sol#', 'Si', 'Re#'],
    description: 'Dedo 1 en 4ª cuerda traste 1, dedo 3 en 3ª cuerda traste 3, dedo 2 en 1ª cuerda traste 2.',
    resolvesTo: ['LAmaj7'],
    difficulty: 'Media'
  },
  'Esus4': {
    name: 'MIsus4', symbol: 'Esus4', type: 'sus4', typeName: 'Sus 4',
    frets: [2, 4, 0, 2], fingers: [1, 3, 0, 2],
    notes: ['Mi', 'La', 'Si'],
    description: 'Dedo 1 en 4ª cuerda traste 2, dedo 3 en 3ª cuerda traste 4 y dedo 2 en 1ª cuerda traste 2.',
    resolvesTo: ['MI', 'SI'],
    difficulty: 'Media'
  },
  'Edim': {
    name: 'MIdim', symbol: 'Edim', type: 'dim', typeName: 'Disminuido',
    frets: [0, 1, 0, 1], fingers: [0, 1, 0, 2],
    notes: ['Mi', 'Sol', 'Sib', 'Reb'],
    description: 'Dedo 1 en 3ª cuerda traste 1 y dedo 2 en 1ª cuerda traste 1. Cuerdas 2 y 4 al aire.',
    resolvesTo: ['FA', 'FAm'],
    difficulty: 'Fácil'
  },

  // --- FA / F ---
  'F': {
    name: 'FA', symbol: 'F', type: 'major', typeName: 'Mayor',
    frets: [2, 0, 1, 0], fingers: [2, 0, 1, 0],
    notes: ['Fa', 'La', 'Do'],
    stringNotes: ['La', 'Do', 'Fa', 'La'],
    description: 'Dedo 1 en la 2ª cuerda traste 1 y dedo 2 en la 4ª cuerda traste 2. Cuerdas 1 y 3 al aire.',
    resolvesTo: ['SIb', 'DO', 'DO7', 'REm'],
    difficulty: 'Fácil'
  },
  'Fm': {
    name: 'FAm', symbol: 'Fm', type: 'minor', typeName: 'Menor',
    frets: [1, 0, 1, 3], fingers: [1, 0, 2, 4],
    notes: ['Fa', 'Lab', 'Do'],
    description: 'Dedo 1 en 4ª cuerda traste 1, dedo 2 en 2ª cuerda traste 1 y dedo 4 en 1ª cuerda traste 3. 3ª cuerda al aire.',
    resolvesTo: ['SIbm', 'DO7'],
    difficulty: 'Media'
  },
  'F7': {
    name: 'FA7', symbol: 'F7', type: '7th', typeName: 'Séptima',
    frets: [2, 3, 1, 0], fingers: [2, 3, 1, 0],
    notes: ['Fa', 'La', 'Do', 'Mib'],
    description: 'Dedo 1 en 2ª cuerda traste 1, dedo 2 en 4ª cuerda traste 2 y dedo 3 en 3ª cuerda traste 3. 1ª cuerda al aire.',
    resolvesTo: ['SIb', 'SIbm'],
    difficulty: 'Fácil'
  },
  'Fm7': {
    name: 'FAm7', symbol: 'Fm7', type: 'm7', typeName: 'Menor 7',
    frets: [1, 3, 1, 3], fingers: [1, 3, 2, 4],
    notes: ['Fa', 'Lab', 'Do', 'Mib'],
    description: 'Dedo 1 en 4ª cuerda traste 1, dedo 2 en 2ª cuerda traste 1, dedo 3 en 3ª cuerda traste 3, dedo 4 en 1ª cuerda traste 3.',
    resolvesTo: ['SIbm'],
    difficulty: 'Media'
  },
  'Fmaj7': {
    name: 'FAmaj7', symbol: 'Fmaj7', type: 'maj7', typeName: 'Mayor 7',
    frets: [2, 4, 1, 3], fingers: [2, 4, 1, 3],
    notes: ['Fa', 'La', 'Do', 'Mi'],
    description: 'Dedo 1 en 2ª cuerda traste 1, dedo 2 en 4ª cuerda traste 2, dedo 4 en 3ª cuerda traste 4, dedo 3 en 1ª cuerda traste 3.',
    resolvesTo: ['SIbmaj7', 'REm'],
    difficulty: 'Media'
  },
  'Fsus4': {
    name: 'FAsus4', symbol: 'Fsus4', type: 'sus4', typeName: 'Sus 4',
    frets: [3, 0, 1, 1], fingers: [3, 0, 1, 1], barre: 1,
    notes: ['Fa', 'Sib', 'Do'],
    stringNotes: ['Do', 'Do', 'Fa', 'Sib'],
    description: 'Media cejilla con el dedo 1 pisando las cuerdas 1 y 2 en traste 1; dedo 3 en la 4ª cuerda traste 3. Cuerda 3 al aire.',
    resolvesTo: ['FA', 'DO'],
    difficulty: 'Media'
  },
  'Fdim': {
    name: 'FAdim', symbol: 'Fdim', type: 'dim', typeName: 'Disminuido',
    frets: [1, 2, 1, 2], fingers: [1, 3, 2, 4],
    notes: ['Fa', 'Lab', 'Si', 'Re'],
    description: 'Dedo 1 en 4ª y dedo 2 en 2ª cuerda traste 1; dedos 3 y 4 en cuerdas 3 y 1 traste 2.',
    resolvesTo: ['FA#', 'SOLm'],
    difficulty: 'Media'
  },

  // --- FA# / SOLb (F# / Gb) ---
  'F#': {
    name: 'FA#', symbol: 'F#', type: 'major', typeName: 'Mayor',
    frets: [3, 2, 2, 1], fingers: [4, 2, 3, 1],
    notes: ['Fa#', 'La#', 'Do#'],
    description: 'Dedo 1 en 1ª cuerda traste 1, dedos 2 y 3 en cuerdas 3 y 2 traste 2, y dedo 4 en 4ª cuerda traste 3.',
    resolvesTo: ['SI', 'DO#', 'DO#7', 'RE#m'],
    difficulty: 'Media'
  },
  'F#m': {
    name: 'FA#m', symbol: 'F#m', type: 'minor', typeName: 'Menor',
    frets: [2, 1, 2, 0], fingers: [2, 1, 3, 0],
    notes: ['Fa#', 'La', 'Do#'],
    stringNotes: ['La', 'Do#', 'Fa#', 'La'],
    description: 'Dedo 1 en 3ª cuerda traste 1, dedos 2 y 3 en cuerdas 4 y 2 traste 2. 1ª cuerda al aire.',
    resolvesTo: ['SIm', 'DO#7', 'LA'],
    difficulty: 'Fácil'
  },
  'F#7': {
    name: 'FA#7', symbol: 'F#7', type: '7th', typeName: 'Séptima',
    frets: [3, 4, 2, 4], fingers: [2, 3, 1, 4],
    notes: ['Fa#', 'La#', 'Do#', 'Mi'],
    description: 'Dedo 1 en 2ª cuerda traste 2, dedo 2 en 4ª cuerda traste 3, dedos 3 y 4 en traste 4.',
    resolvesTo: ['SI', 'SIm'],
    difficulty: 'Media'
  },
  'F#m7': {
    name: 'FA#m7', symbol: 'F#m7', type: 'm7', typeName: 'Menor 7',
    frets: [2, 4, 2, 4], fingers: [1, 3, 2, 4],
    notes: ['Fa#', 'La', 'Do#', 'Mi'],
    description: 'Dedo 1 en 4ª y dedo 2 en 2ª cuerda traste 2; dedos 3 y 4 en cuerdas 3 y 1 traste 4.',
    resolvesTo: ['SIm'],
    difficulty: 'Media'
  },

  // --- SOL / G ---
  'G': {
    name: 'SOL', symbol: 'G', type: 'major', typeName: 'Mayor',
    frets: [0, 2, 3, 2], fingers: [0, 1, 3, 2],
    notes: ['Sol', 'Si', 'Re'],
    stringNotes: ['Sol', 'Re', 'Sol', 'Si'],
    description: 'Forma de triángulo: dedo 1 en 3ª cuerda traste 2, dedo 2 en 1ª cuerda traste 2, dedo 3 en 2ª cuerda traste 3.',
    resolvesTo: ['DO', 'RE', 'RE7', 'MIm'],
    difficulty: 'Fácil'
  },
  'Gm': {
    name: 'SOLm', symbol: 'Gm', type: 'minor', typeName: 'Menor',
    frets: [0, 2, 3, 1], fingers: [0, 2, 3, 1],
    notes: ['Sol', 'Sib', 'Re'],
    stringNotes: ['Sol', 'Re', 'Sol', 'Sib'],
    description: 'Dedo 1 en 1ª cuerda traste 1, dedo 2 en 3ª cuerda traste 2, dedo 3 en 2ª cuerda traste 3. 4ª cuerda al aire.',
    resolvesTo: ['DOm', 'RE7', 'SIb'],
    difficulty: 'Fácil'
  },
  'G7': {
    name: 'SOL7', symbol: 'G7', type: '7th', typeName: 'Séptima',
    frets: [0, 2, 1, 2], fingers: [0, 2, 1, 3],
    notes: ['Sol', 'Si', 'Re', 'Fa'],
    stringNotes: ['Sol', 'Re', 'Fa', 'Si'],
    description: 'Dedo 1 en 2ª cuerda traste 1, dedo 2 en 3ª cuerda traste 2, dedo 3 en 1ª cuerda traste 2. 4ª cuerda al aire.',
    resolvesTo: ['DO', 'DOm'],
    difficulty: 'Fácil'
  },
  'Gm7': {
    name: 'SOLm7', symbol: 'Gm7', type: 'm7', typeName: 'Menor 7',
    frets: [0, 2, 1, 1], fingers: [0, 2, 1, 1], barre: 1,
    notes: ['Sol', 'Sib', 'Re', 'Fa'],
    stringNotes: ['Sol', 'Re', 'Fa', 'Sib'],
    description: 'Media cejilla con el dedo 1 cubriendo cuerdas 1 y 2 en traste 1; dedo 2 en 3ª cuerda traste 2. 4ª cuerda al aire.',
    resolvesTo: ['DOm', 'DO7'],
    difficulty: 'Fácil'
  },
  'Gmaj7': {
    name: 'SOLmaj7', symbol: 'Gmaj7', type: 'maj7', typeName: 'Mayor 7',
    frets: [0, 2, 2, 2], fingers: [0, 1, 2, 3],
    notes: ['Sol', 'Si', 'Re', 'Fa#'],
    description: 'Dedos 1, 2 y 3 en traste 2 de las cuerdas 3, 2 y 1. 4ª cuerda al aire.',
    resolvesTo: ['DOmaj7', 'MIm'],
    difficulty: 'Fácil'
  },
  'Gsus4': {
    name: 'SOLsus4', symbol: 'Gsus4', type: 'sus4', typeName: 'Sus 4',
    frets: [0, 2, 3, 3], fingers: [0, 1, 2, 3],
    notes: ['Sol', 'Do', 'Re'],
    description: 'Dedo 1 en 3ª cuerda traste 2, dedos 2 y 3 en cuerdas 2 y 1 traste 3. 4ª cuerda al aire.',
    resolvesTo: ['SOL', 'RE'],
    difficulty: 'Fácil'
  },
  'Gdim': {
    name: 'SOLdim', symbol: 'Gdim', type: 'dim', typeName: 'Disminuido',
    frets: [0, 1, 0, 1], fingers: [0, 1, 0, 2],
    notes: ['Sol', 'Sib', 'Reb', 'Mi'],
    description: 'Dedo 1 en 3ª cuerda traste 1 y dedo 2 en 1ª cuerda traste 1. Cuerdas 2 y 4 al aire.',
    resolvesTo: ['LAb', 'LAm'],
    difficulty: 'Fácil'
  },

  // --- SOL# / LAb (G# / Ab) ---
  'Ab': {
    name: 'LAb', symbol: 'Ab', type: 'major', typeName: 'Mayor',
    frets: [5, 3, 4, 3], fingers: [4, 1, 3, 2], baseFret: 3,
    notes: ['Lab', 'Do', 'Mib'],
    description: 'Dedo 1 en 3ª cuerda traste 3, dedo 2 en 1ª cuerda traste 3, dedo 3 en 2ª cuerda traste 4 y dedo 4 en 4ª cuerda traste 5.',
    resolvesTo: ['REb', 'MIb', 'MIb7', 'FAm'],
    difficulty: 'Media'
  },
  'G#m': {
    name: 'SOL#m', symbol: 'G#m', type: 'minor', typeName: 'Menor',
    frets: [4, 3, 4, 2], fingers: [3, 2, 4, 1],
    notes: ['Sol#', 'Si', 'Re#'],
    description: 'Dedo 1 en 1ª cuerda traste 2, dedo 2 en 3ª cuerda traste 3, dedos 3 y 4 en cuerdas 4 y 2 traste 4.',
    resolvesTo: ['DO#m', 'RE#7'],
    difficulty: 'Media'
  },
  'Abm': {
    name: 'LAbm', symbol: 'Abm', type: 'minor', typeName: 'Menor',
    frets: [4, 3, 4, 2], fingers: [3, 2, 4, 1],
    notes: ['Lab', 'Si', 'Mib'],
    description: 'Igual a SOL#m: dedo 1 en 1ª cuerda traste 2, dedo 2 en 3ª cuerda traste 3, dedos 3 y 4 en traste 4.',
    resolvesTo: ['REbm', 'MIb7'],
    difficulty: 'Media'
  },
  'Ab7': {
    name: 'LAb7', symbol: 'Ab7', type: '7th', typeName: 'Séptima',
    frets: [1, 3, 2, 3], fingers: [1, 3, 2, 4],
    notes: ['Lab', 'Do', 'Mib', 'Solb'],
    description: 'Dedo 1 en 4ª cuerda traste 1, dedo 2 en 2ª cuerda traste 2, dedos 3 y 4 en cuerdas 3 y 1 traste 3.',
    resolvesTo: ['REb', 'REbm'],
    difficulty: 'Media'
  },
  'G#7': {
    name: 'SOL#7', symbol: 'G#7', type: '7th', typeName: 'Séptima',
    frets: [1, 3, 2, 3], fingers: [1, 3, 2, 4],
    notes: ['Sol#', 'Do', 'Re#', 'Fa#'],
    description: 'Igual a LAb7: dedo 1 en 4ª traste 1, dedo 2 en 2ª traste 2, dedos 3 y 4 en cuerdas 3 y 1 traste 3.',
    resolvesTo: ['DO#', 'DO#m'],
    difficulty: 'Media'
  },

  // --- LA / A ---
  'A': {
    name: 'LA', symbol: 'A', type: 'major', typeName: 'Mayor',
    frets: [2, 1, 0, 0], fingers: [2, 1, 0, 0],
    notes: ['La', 'Do#', 'Mi'],
    stringNotes: ['La', 'Do#', 'Mi', 'La'],
    description: 'Dedo 1 en la 3ª cuerda traste 1 y dedo 2 en la 4ª cuerda traste 2. Cuerdas 1 y 2 al aire.',
    resolvesTo: ['RE', 'MI', 'MI7', 'FA#m'],
    difficulty: 'Fácil'
  },
  'Am': {
    name: 'LAm', symbol: 'Am', type: 'minor', typeName: 'Menor',
    frets: [2, 0, 0, 0], fingers: [2, 0, 0, 0],
    notes: ['La', 'Do', 'Mi'],
    stringNotes: ['La', 'Do', 'Mi', 'La'],
    description: 'Dedo medio (2) en la 4ª cuerda traste 2. Las demás cuerdas van al aire.',
    resolvesTo: ['REm', 'MI7', 'DO'],
    difficulty: 'Fácil'
  },
  'A7': {
    name: 'LA7', symbol: 'A7', type: '7th', typeName: 'Séptima',
    frets: [0, 1, 0, 0], fingers: [0, 1, 0, 0],
    notes: ['La', 'Do#', 'Mi', 'Sol'],
    stringNotes: ['Sol', 'Do#', 'Mi', 'La'],
    description: 'Dedo índice (1) en la 3ª cuerda traste 1. Todas las demás cuerdas al aire.',
    resolvesTo: ['RE', 'REm'],
    difficulty: 'Fácil'
  },
  'Am7': {
    name: 'LAm7', symbol: 'Am7', type: 'm7', typeName: 'Menor 7',
    frets: [0, 0, 0, 0], fingers: [0, 0, 0, 0],
    notes: ['La', 'Do', 'Mi', 'Sol'],
    stringNotes: ['Sol', 'Do', 'Mi', 'La'],
    description: '¡Acorde al aire! Todas las cuerdas se tocan abiertas sin poner ningún dedo.',
    resolvesTo: ['REm', 'DO'],
    difficulty: 'Fácil'
  },
  'Amaj7': {
    name: 'LAmaj7', symbol: 'Amaj7', type: 'maj7', typeName: 'Mayor 7',
    frets: [1, 1, 0, 0], fingers: [1, 2, 0, 0],
    notes: ['La', 'Do#', 'Mi', 'Sol#'],
    description: 'Dedo 1 en 4ª cuerda traste 1 y dedo 2 en 3ª cuerda traste 1. Cuerdas 1 y 2 al aire.',
    resolvesTo: ['REmaj7', 'FA#m'],
    difficulty: 'Fácil'
  },
  'Asus4': {
    name: 'LAsus4', symbol: 'Asus4', type: 'sus4', typeName: 'Sus 4',
    frets: [2, 2, 0, 0], fingers: [1, 2, 0, 0],
    notes: ['La', 'Re', 'Mi'],
    description: 'Dedos 1 y 2 en traste 2 de las cuerdas 4 y 3. Cuerdas 1 y 2 al aire.',
    resolvesTo: ['LA', 'MI'],
    difficulty: 'Fácil'
  },
  'Adim': {
    name: 'LAdim', symbol: 'Adim', type: 'dim', typeName: 'Disminuido',
    frets: [2, 3, 2, 3], fingers: [1, 3, 2, 4],
    notes: ['La', 'Do', 'Mib', 'Fa#'],
    description: 'Dedo 1 en 4ª y dedo 2 en 2ª cuerda traste 2; dedos 3 y 4 en cuerdas 3 y 1 traste 3.',
    resolvesTo: ['SIb', 'SIbm'],
    difficulty: 'Media'
  },

  // --- LA# / SIb (Bb) ---
  'Bb': {
    name: 'SIb', symbol: 'Bb', type: 'major', typeName: 'Mayor',
    frets: [3, 2, 1, 1], fingers: [3, 2, 1, 1], barre: 1,
    notes: ['Sib', 'Re', 'Fa'],
    stringNotes: ['Re', 'Re', 'Fa', 'Sib'],
    description: 'Media cejilla con el dedo 1 pisando cuerdas 1 y 2 en traste 1; dedo 2 en 3ª cuerda traste 2 y dedo 3 en 4ª cuerda traste 3.',
    resolvesTo: ['MIb', 'FA', 'FA7', 'SOLm'],
    difficulty: 'Media'
  },
  'Bbm': {
    name: 'SIbm', symbol: 'Bbm', type: 'minor', typeName: 'Menor',
    frets: [3, 1, 1, 1], fingers: [3, 1, 1, 1], barre: 1,
    notes: ['Sib', 'Reb', 'Fa'],
    description: 'Media cejilla con dedo 1 en traste 1 cubriendo cuerdas 3, 2 y 1; dedo 3 en 4ª cuerda traste 3.',
    resolvesTo: ['MIbm', 'FA7'],
    difficulty: 'Media'
  },
  'Bb7': {
    name: 'SIb7', symbol: 'Bb7', type: '7th', typeName: 'Séptima',
    frets: [1, 2, 1, 1], fingers: [1, 2, 1, 1], barre: 1,
    notes: ['Sib', 'Re', 'Fa', 'Lab'],
    description: 'Cejilla con dedo 1 en traste 1 y dedo 2 en 3ª cuerda traste 2.',
    resolvesTo: ['MIb', 'MIbm'],
    difficulty: 'Media'
  },
  'Bbm7': {
    name: 'SIbm7', symbol: 'Bbm7', type: 'm7', typeName: 'Menor 7',
    frets: [1, 1, 1, 1], fingers: [1, 1, 1, 1], barre: 1,
    notes: ['Sib', 'Reb', 'Fa', 'Lab'],
    description: 'Cejilla completa con el dedo 1 pisando las 4 cuerdas en el traste 1.',
    resolvesTo: ['MIbm'],
    difficulty: 'Media'
  },
  'Bbmaj7': {
    name: 'SIbmaj7', symbol: 'Bbmaj7', type: 'maj7', typeName: 'Mayor 7',
    frets: [3, 2, 1, 0], fingers: [3, 2, 1, 0],
    notes: ['Sib', 'Re', 'Fa', 'La'],
    description: 'Dedo 1 en 2ª cuerda traste 1, dedo 2 en 3ª cuerda traste 2, dedo 3 en 4ª cuerda traste 3. 1ª cuerda al aire.',
    resolvesTo: ['MIbmaj7'],
    difficulty: 'Fácil'
  },

  // --- SI / B ---
  'B': {
    name: 'SI', symbol: 'B', type: 'major', typeName: 'Mayor',
    frets: [4, 3, 2, 2], fingers: [4, 3, 1, 1], barre: 2,
    notes: ['Si', 'Re#', 'Fa#'],
    description: 'Media cejilla con el dedo 1 en cuerdas 1 y 2 traste 2; dedo 2 en 3ª cuerda traste 3 y dedo 3 en 4ª cuerda traste 4.',
    resolvesTo: ['MI', 'FA#', 'FA#7', 'SOL#m'],
    difficulty: 'Media'
  },
  'Bm': {
    name: 'SIm', symbol: 'Bm', type: 'minor', typeName: 'Menor',
    frets: [4, 2, 2, 2], fingers: [3, 1, 1, 1], barre: 2,
    notes: ['Si', 'Re', 'Fa#'],
    description: 'Media cejilla con dedo 1 en traste 2 cubriendo cuerdas 3, 2 y 1; dedo 3 en 4ª cuerda traste 4.',
    resolvesTo: ['MIm', 'FA#7', 'RE'],
    difficulty: 'Media'
  },
  'B7': {
    name: 'SI7', symbol: 'B7', type: '7th', typeName: 'Séptima',
    frets: [2, 3, 2, 2], fingers: [1, 2, 1, 1], barre: 2,
    notes: ['Si', 'Re#', 'Fa#', 'La'],
    description: 'Cejilla con dedo 1 en traste 2 y dedo 2 en 3ª cuerda traste 3.',
    resolvesTo: ['MI', 'MIm'],
    difficulty: 'Media'
  },
  'Bm7': {
    name: 'SIm7', symbol: 'Bm7', type: 'm7', typeName: 'Menor 7',
    frets: [2, 2, 2, 2], fingers: [1, 1, 1, 1], barre: 2,
    notes: ['Si', 'Re', 'Fa#', 'La'],
    description: 'Cejilla completa con el dedo 1 pisando las 4 cuerdas en el traste 2.',
    resolvesTo: ['MIm'],
    difficulty: 'Media'
  },
  'Bmaj7': {
    name: 'SImaj7', symbol: 'Bmaj7', type: 'maj7', typeName: 'Mayor 7',
    frets: [3, 3, 2, 2], fingers: [3, 4, 1, 2],
    notes: ['Si', 'Re#', 'Fa#', 'La#'],
    description: 'Dedos 1 y 2 en traste 2 (cuerdas 2 y 1), dedos 3 y 4 en traste 3 (cuerdas 4 y 3).',
    resolvesTo: ['MImaj7'],
    difficulty: 'Media'
  },
  'Bsus4': {
    name: 'SIsus4', symbol: 'Bsus4', type: 'sus4', typeName: 'Sus 4',
    frets: [4, 4, 2, 2], fingers: [3, 4, 1, 1], barre: 2,
    notes: ['Si', 'Mi', 'Fa#'],
    description: 'Media cejilla con dedo 1 en cuerdas 1 y 2 traste 2; dedos 3 y 4 en cuerdas 4 y 3 traste 4.',
    resolvesTo: ['SI', 'FA#'],
    difficulty: 'Media'
  },
  'Bdim': {
    name: 'SIdim', symbol: 'Bdim', type: 'dim', typeName: 'Disminuido',
    frets: [1, 2, 1, 2], fingers: [1, 3, 2, 4],
    notes: ['Si', 'Re', 'Fa', 'Lab'],
    description: 'Dedo 1 en 4ª y dedo 2 en 2ª cuerda traste 1; dedos 3 y 4 en cuerdas 3 y 1 traste 2.',
    resolvesTo: ['DO', 'DOm'],
    difficulty: 'Media'
  },
};

// -------------------------------------------------------------
// Familias Armónicas (Progresiones Principales de Acompañamiento)
// Contiene las 12 tonalidades mayores cromáticas y las menores clave
// -------------------------------------------------------------
export const HARMONIC_FAMILIES: Record<string, HarmonicFamily> = {
  // === TONALIDADES MAYORES (12 CROMÁTICAS) ===
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
  'C#': {
    keyName: 'DO#',
    keySymbol: 'C#',
    tonic: UKULELE_CHORDS_DB['C#'],
    subdominant: UKULELE_CHORDS_DB['F#'],
    dominant: UKULELE_CHORDS_DB['Ab'] || UKULELE_CHORDS_DB['G#7'],
    dominant7: UKULELE_CHORDS_DB['Ab7'] || UKULELE_CHORDS_DB['G#7'],
    relativeMinor: UKULELE_CHORDS_DB['Bbm'],
    secondary1: UKULELE_CHORDS_DB['Ebm'],
    secondary2: UKULELE_CHORDS_DB['Fm'],
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
  'Eb': {
    keyName: 'MIb',
    keySymbol: 'Eb',
    tonic: UKULELE_CHORDS_DB['Eb'],
    subdominant: UKULELE_CHORDS_DB['Ab'],
    dominant: UKULELE_CHORDS_DB['Bb'],
    dominant7: UKULELE_CHORDS_DB['Bb7'],
    relativeMinor: UKULELE_CHORDS_DB['Cm'],
    secondary1: UKULELE_CHORDS_DB['Fm'],
    secondary2: UKULELE_CHORDS_DB['Gm'],
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
  'F#': {
    keyName: 'FA#',
    keySymbol: 'F#',
    tonic: UKULELE_CHORDS_DB['F#'],
    subdominant: UKULELE_CHORDS_DB['B'],
    dominant: UKULELE_CHORDS_DB['C#'],
    dominant7: UKULELE_CHORDS_DB['C#7'],
    relativeMinor: UKULELE_CHORDS_DB['Ebm'],
    secondary1: UKULELE_CHORDS_DB['G#m'],
    secondary2: UKULELE_CHORDS_DB['Bbm'],
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
  'Ab': {
    keyName: 'LAb',
    keySymbol: 'Ab',
    tonic: UKULELE_CHORDS_DB['Ab'],
    subdominant: UKULELE_CHORDS_DB['C#'],
    dominant: UKULELE_CHORDS_DB['Eb'],
    dominant7: UKULELE_CHORDS_DB['Eb7'],
    relativeMinor: UKULELE_CHORDS_DB['Fm'],
    secondary1: UKULELE_CHORDS_DB['Bbm'],
    secondary2: UKULELE_CHORDS_DB['Cm'],
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
    secondary2: UKULELE_CHORDS_DB['Ebm'],
  },

  // === TONALIDADES MENORES CLÁSICAS ===
  'Am': {
    keyName: 'LAm',
    keySymbol: 'Am',
    isMinor: true,
    tonic: UKULELE_CHORDS_DB['Am'],
    subdominant: UKULELE_CHORDS_DB['Dm'],
    dominant: UKULELE_CHORDS_DB['Em'],
    dominant7: UKULELE_CHORDS_DB['E7'],
    relativeMinor: UKULELE_CHORDS_DB['C'], // Relativa Mayor
    secondary1: UKULELE_CHORDS_DB['F'],
    secondary2: UKULELE_CHORDS_DB['G'],
  },
  'Em': {
    keyName: 'MIm',
    keySymbol: 'Em',
    isMinor: true,
    tonic: UKULELE_CHORDS_DB['Em'],
    subdominant: UKULELE_CHORDS_DB['Am'],
    dominant: UKULELE_CHORDS_DB['Bm'],
    dominant7: UKULELE_CHORDS_DB['B7'],
    relativeMinor: UKULELE_CHORDS_DB['G'],
    secondary1: UKULELE_CHORDS_DB['C'],
    secondary2: UKULELE_CHORDS_DB['D'],
  },
  'Dm': {
    keyName: 'REm',
    keySymbol: 'Dm',
    isMinor: true,
    tonic: UKULELE_CHORDS_DB['Dm'],
    subdominant: UKULELE_CHORDS_DB['Gm'],
    dominant: UKULELE_CHORDS_DB['Am'],
    dominant7: UKULELE_CHORDS_DB['A7'],
    relativeMinor: UKULELE_CHORDS_DB['F'],
    secondary1: UKULELE_CHORDS_DB['Bb'],
    secondary2: UKULELE_CHORDS_DB['C'],
  },
  'Bm': {
    keyName: 'SIm',
    keySymbol: 'Bm',
    isMinor: true,
    tonic: UKULELE_CHORDS_DB['Bm'],
    subdominant: UKULELE_CHORDS_DB['Em'],
    dominant: UKULELE_CHORDS_DB['F#m'],
    dominant7: UKULELE_CHORDS_DB['F#7'],
    relativeMinor: UKULELE_CHORDS_DB['D'],
    secondary1: UKULELE_CHORDS_DB['G'],
    secondary2: UKULELE_CHORDS_DB['A'],
  },
  'F#m': {
    keyName: 'FA#m',
    keySymbol: 'F#m',
    isMinor: true,
    tonic: UKULELE_CHORDS_DB['F#m'],
    subdominant: UKULELE_CHORDS_DB['Bm'],
    dominant: UKULELE_CHORDS_DB['C#m'],
    dominant7: UKULELE_CHORDS_DB['C#7'],
    relativeMinor: UKULELE_CHORDS_DB['A'],
    secondary1: UKULELE_CHORDS_DB['D'],
    secondary2: UKULELE_CHORDS_DB['E'],
  },
  'Cm': {
    keyName: 'DOm',
    keySymbol: 'Cm',
    isMinor: true,
    tonic: UKULELE_CHORDS_DB['Cm'],
    subdominant: UKULELE_CHORDS_DB['Fm'],
    dominant: UKULELE_CHORDS_DB['Gm'],
    dominant7: UKULELE_CHORDS_DB['G7'],
    relativeMinor: UKULELE_CHORDS_DB['Eb'],
    secondary1: UKULELE_CHORDS_DB['Ab'],
    secondary2: UKULELE_CHORDS_DB['Bb'],
  },
  'Gm': {
    keyName: 'SOLm',
    keySymbol: 'Gm',
    isMinor: true,
    tonic: UKULELE_CHORDS_DB['Gm'],
    subdominant: UKULELE_CHORDS_DB['Cm'],
    dominant: UKULELE_CHORDS_DB['Dm'],
    dominant7: UKULELE_CHORDS_DB['D7'],
    relativeMinor: UKULELE_CHORDS_DB['Bb'],
    secondary1: UKULELE_CHORDS_DB['Eb'],
    secondary2: UKULELE_CHORDS_DB['F'],
  },
};

// Claves cromáticas mayores completas (12)
export const ALL_MAJOR_KEYS = [
  { symbol: 'C', name: 'DO' },
  { symbol: 'C#', name: 'DO#' },
  { symbol: 'D', name: 'RE' },
  { symbol: 'Eb', name: 'MIb' },
  { symbol: 'E', name: 'MI' },
  { symbol: 'F', name: 'FA' },
  { symbol: 'F#', name: 'FA#' },
  { symbol: 'G', name: 'SOL' },
  { symbol: 'Ab', name: 'LAb' },
  { symbol: 'A', name: 'LA' },
  { symbol: 'Bb', name: 'SIb' },
  { symbol: 'B', name: 'SI' },
];

// Claves menores clave de alabanza
export const ALL_MINOR_KEYS = [
  { symbol: 'Am', name: 'LAm' },
  { symbol: 'Em', name: 'MIm' },
  { symbol: 'Dm', name: 'REm' },
  { symbol: 'Bm', name: 'SIm' },
  { symbol: 'F#m', name: 'FA#m' },
  { symbol: 'Cm', name: 'DOm' },
  { symbol: 'Gm', name: 'SOLm' },
];

// Mantenemos compatibilidad con el array anterior
export const PREDOMINANT_KEYS = ALL_MAJOR_KEYS;
