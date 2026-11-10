import { UkuleleChord } from '../data/ukuleleChords';

// Frecuencias fundamentales de cuerdas al aire (Hz)
export const UKULELE_BASE_FREQS = [
  392.00, // 4ª Cuerda: G4
  261.63, // 3ª Cuerda: C4
  329.63, // 2ª Cuerda: E4
  440.00  // 1ª Cuerda: A4
];

export const GUITAR_BASE_FREQS = [
  82.41,  // 6ª Cuerda: E2
  110.00, // 5ª Cuerda: A2
  146.83, // 4ª Cuerda: D3
  196.00, // 3ª Cuerda: G3
  246.94, // 2ª Cuerda: B3
  329.63  // 1ª Cuerda: E4
];

/**
 * Calcula las frecuencias sonoras (Hz) de cada cuerda pisada en el acorde.
 * Omite las cuerdas silenciadas (-1) y valida que los trastes sean enteros válidos >= 0.
 */
export function getChordFrequencies(chord: UkuleleChord, instrument: 'ukulele' | 'guitar'): number[] {
  if (!chord || !Array.isArray(chord.frets)) return [];
  const isGuitar = instrument === 'guitar' || chord.frets.length === 6;
  const baseFreqs = isGuitar ? GUITAR_BASE_FREQS : UKULELE_BASE_FREQS;
  const freqs: number[] = [];

  chord.frets.forEach((fret, idx) => {
    if (typeof fret === 'number' && Number.isInteger(fret) && fret >= 0 && idx < baseFreqs.length) {
      // f = f0 * 2^(traste / 12)
      const freq = baseFreqs[idx] * Math.pow(2, fret / 12);
      freqs.push(freq);
    }
  });

  return freqs;
}

let sharedAudioCtx: AudioContext | null = null;

/**
 * Obtiene o inicializa la instancia singleton de AudioContext.
 * Si el contexto se encuentra en estado 'suspended' (política de autoplay en navegadores móviles),
 * espera de forma asíncrona su reanudación antes de retornar.
 */
export async function getAudioContext(): Promise<AudioContext | null> {
  if (typeof window === 'undefined') return null;
  const AudioCtxClass = window.AudioContext || (window as unknown as { webkitAudioContext: typeof AudioContext }).webkitAudioContext;
  if (!AudioCtxClass) return null;

  if (!sharedAudioCtx || sharedAudioCtx.state === 'closed') {
    sharedAudioCtx = new AudioCtxClass();
  }
  if (sharedAudioCtx.state === 'suspended') {
    await sharedAudioCtx.resume();
  }
  return sharedAudioCtx;
}

/**
 * Resetea el singleton de AudioContext (útil para pruebas unitarias).
 */
export function resetAudioContextForTesting(): void {
  sharedAudioCtx = null;
}

/**
 * Sintetiza un rasgueo acústico cálido del acorde usando Web Audio API nativo.
 * Cero dependencias externas, latencia ultra-baja y sonido orgánico.
 * Maneja asíncronamente el estado suspended y desconecta explícitamente los nodos
 * al finalizar la reproducción para evitar fugas de memoria en el grafo de Web Audio.
 */
export async function playAcousticChord(chord: UkuleleChord, instrument: 'ukulele' | 'guitar'): Promise<void> {
  const ctx = await getAudioContext();
  if (!ctx) return;

  if (ctx.state === 'suspended') {
    await ctx.resume();
  }

  const freqs = getChordFrequencies(chord, instrument);
  if (freqs.length === 0) return;

  const now = ctx.currentTime;
  const strumSpeed = 0.038; // Retraso orgánico entre cuerdas (rasgueo hacia abajo)

  // Filtro cálido de caja de resonancia de madera
  const filter = ctx.createBiquadFilter();
  filter.type = 'lowpass';
  filter.frequency.setValueAtTime(2600, now);
  filter.Q.setValueAtTime(1.2, now);

  const masterGain = ctx.createGain();
  masterGain.gain.setValueAtTime(0.35, now);
  filter.connect(masterGain);
  masterGain.connect(ctx.destination);

  let endedCount = 0;
  const totalStrings = freqs.length;
  const totalDuration = (totalStrings - 1) * strumSpeed + 1.45;

  const oscillators: { osc: OscillatorNode; stringGain: GainNode }[] = [];
  let isCleanedUp = false;

  const cleanupAll = () => {
    if (isCleanedUp) return;
    isCleanedUp = true;
    clearTimeout(fallbackTimer);

    oscillators.forEach(({ osc, stringGain }) => {
      try {
        osc.disconnect();
      } catch {
        // Ignorar si ya fue desconectado
      }
      try {
        stringGain.disconnect();
      } catch {
        // Ignorar si ya fue desconectado
      }
    });

    try {
      filter.disconnect();
    } catch {
      // Ignorar si ya fue desconectado
    }
    try {
      masterGain.disconnect();
    } catch {
      // Ignorar si ya fue desconectado
    }
  };

  // Temporizador de respaldo por si onended no se dispara (ej. pestañas en segundo plano)
  const fallbackTimer = setTimeout(() => {
    cleanupAll();
  }, Math.ceil((totalDuration + 0.2) * 1000));

  freqs.forEach((freq, i) => {
    const stringTime = now + i * strumSpeed;
    const osc = ctx.createOscillator();
    const stringGain = ctx.createGain();

    oscillators.push({ osc, stringGain });

    // Timbre tipo cuerda pulsada (triangular rica en armónicos)
    osc.type = 'triangle';
    osc.frequency.setValueAtTime(freq, stringTime);

    // Envolvente de volumen (ataque instantáneo y decaimiento suave)
    stringGain.gain.setValueAtTime(0.0001, stringTime);
    stringGain.gain.exponentialRampToValueAtTime(0.4, stringTime + 0.004);
    stringGain.gain.exponentialRampToValueAtTime(0.0001, stringTime + 1.4);

    osc.connect(stringGain);
    stringGain.connect(filter);

    // Desconexión individual al terminar la reproducción del oscilador
    osc.onended = () => {
      try {
        osc.disconnect();
      } catch {
        // Ignorar si ya fue desconectado
      }
      try {
        stringGain.disconnect();
      } catch {
        // Ignorar si ya fue desconectado
      }
      endedCount++;
      if (endedCount >= totalStrings) {
        cleanupAll();
      }
    };

    osc.start(stringTime);
    osc.stop(stringTime + 1.45);
  });
}
