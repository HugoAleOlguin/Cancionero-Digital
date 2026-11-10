export interface SyllableToken {
  id: string;          // Identificador único (ej: "l0_s2")
  text: string;        // Texto de la sílaba (ej: "Gra")
  trailingPunct?: string; // Puntuación adjunta al final (ej: ",", ".")
  leadingPunct?: string;  // Puntuación adjunta al inicio (ej: "¿", "¡")
  isWordEnd: boolean;  // Indica si es la última sílaba de la palabra (para spacing)
  wordIndex: number;   // Índice de la palabra en la línea
  syllableIndex: number; // Índice de la sílaba en la línea
}

const STRONG_VOWELS = new Set(['a', 'e', 'o', 'á', 'é', 'ó', 'A', 'E', 'O', 'Á', 'É', 'Ó']);
const WEAK_VOWELS = new Set(['i', 'u', 'ü', 'I', 'U', 'Ü']);
const ACCENTED_WEAK_VOWELS = new Set(['í', 'ú', 'Í', 'Ú']);

function isVowel(c: string): boolean {
  return STRONG_VOWELS.has(c) || WEAK_VOWELS.has(c) || ACCENTED_WEAK_VOWELS.has(c);
}

function isHiatus(c1: string, c2: string): boolean {
  // Fuerte + Fuerte
  if (STRONG_VOWELS.has(c1) && STRONG_VOWELS.has(c2)) return true;
  // Débil con tilde + Fuerte
  if (ACCENTED_WEAK_VOWELS.has(c1) && (STRONG_VOWELS.has(c2) || WEAK_VOWELS.has(c2))) return true;
  // Fuerte + Débil con tilde
  if (STRONG_VOWELS.has(c1) && ACCENTED_WEAK_VOWELS.has(c2)) return true;
  // Dos débiles iguales (ej: ti-i-ta)
  if (c1.toLowerCase() === c2.toLowerCase() && WEAK_VOWELS.has(c1)) return true;
  return false;
}

// Grupos consonánticos inseparables al inicio de sílaba
const INSEPARABLE_ONSET_REGEX = /^(pl|pr|bl|br|fl|fr|cl|cr|gl|gr|tl|tr|dr|ch|ll|rr)$/i;

/**
 * Divide una palabra en español en sus sílabas componentes.
 */
export function syllabifyWord(rawWord: string): string[] {
  if (!rawWord || rawWord.length <= 1) return [rawWord];

  // Separar puntuación externa si la hubiera
  const word = rawWord;
  const chars = Array.from(word);
  const n = chars.length;

  // Localizar centros vocálicos (núcleos de sílaba)
  // Agrupamos vocales contiguas respetando diptongos e hiatos
  const cuts: boolean[] = new Array(n).fill(false);

  // Paso 1: Cortes entre vocales (Hiatos)
  for (let i = 0; i < n - 1; i++) {
    const c1 = chars[i];
    const c2 = chars[i + 1];
    if (isVowel(c1) && isVowel(c2)) {
      if (isHiatus(c1, c2)) {
        cuts[i + 1] = true;
      }
    }
  }

  // Paso 2: Cortes entre consonantes y vocales
  // Buscamos secuencias de consonantes entre dos vocales: V (C...C) V
  let lastVowelIdx = -1;
  for (let i = 0; i < n; i++) {
    if (isVowel(chars[i])) {
      if (lastVowelIdx !== -1) {
        const consCount = i - lastVowelIdx - 1;
        const startCons = lastVowelIdx + 1;

        if (consCount === 1) {
          // Una sola consonante entre vocales siempre va con la segunda vocal: V-CV
          cuts[startCons] = true;
        } else if (consCount === 2) {
          const pair = chars.slice(startCons, startCons + 2).join('');
          if (INSEPARABLE_ONSET_REGEX.test(pair)) {
            // Inseparable (ej: br, cl, tr): V-CCV
            cuts[startCons] = true;
          } else {
            // Se separan (ej: can-to, al-to): VC-CV
            cuts[startCons + 1] = true;
          }
        } else if (consCount === 3) {
          const lastPair = chars.slice(startCons + 1, startCons + 3).join('');
          if (INSEPARABLE_ONSET_REGEX.test(lastPair)) {
            // VC-CCV (ej: en-trar, com-prar)
            cuts[startCons + 1] = true;
          } else {
            // VCC-CV (ej: ins-tar)
            cuts[startCons + 2] = true;
          }
        } else if (consCount >= 4) {
          // VCC-CCV (ej: cons-truir)
          cuts[startCons + 2] = true;
        }
      }
      lastVowelIdx = i;
    }
  }

  // Reconstruir sílabas según los cortes
  const syllables: string[] = [];
  let current = '';
  for (let i = 0; i < n; i++) {
    if (cuts[i] && current.length > 0) {
      syllables.push(current);
      current = '';
    }
    current += chars[i];
  }
  if (current.length > 0) {
    syllables.push(current);
  }

  return syllables.length > 0 ? syllables : [word];
}

/**
 * Divide una línea lírica completa en tokens de sílabas listos para interacción.
 */
export function splitLineIntoSyllables(line: string, lineIndex: number = 0): SyllableToken[] {
  if (!line || !line.trim()) return [];

  // Dividir por palabras preservando signos
  const words = line.trim().split(/\s+/);
  const tokens: SyllableToken[] = [];
  let syllableCounter = 0;

  words.forEach((wordRaw, wordIndex) => {
    // Extraer signos de puntuación iniciales y finales
    const leadingMatch = wordRaw.match(/^([¿¡"'(]+)/);
    const leadingPunct = leadingMatch ? leadingMatch[1] : '';
    const withoutLeading = leadingMatch ? wordRaw.slice(leadingPunct.length) : wordRaw;

    const trailingMatch = withoutLeading.match(/([.,;:!?"')]+)$/);
    const trailingPunct = trailingMatch ? trailingMatch[1] : '';
    const cleanWord = trailingMatch
      ? withoutLeading.slice(0, withoutLeading.length - trailingPunct.length)
      : withoutLeading;

    if (!cleanWord) {
      // Solo puntuación o espacio
      return;
    }

    const syls = syllabifyWord(cleanWord);

    syls.forEach((syl, sIdx) => {
      const isWordEnd = sIdx === syls.length - 1;
      const isWordStart = sIdx === 0;

      tokens.push({
        id: `l${lineIndex}_s${syllableCounter}`,
        text: syl,
        leadingPunct: isWordStart && leadingPunct ? leadingPunct : undefined,
        trailingPunct: isWordEnd && trailingPunct ? trailingPunct : undefined,
        isWordEnd,
        wordIndex,
        syllableIndex: syllableCounter
      });

      syllableCounter++;
    });
  });

  return tokens;
}
