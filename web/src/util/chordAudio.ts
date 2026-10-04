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
 * Omite las cuerdas silenciadas (-1).
 */
export function getChordFrequencies(chord: UkuleleChord, instrument: 'ukulele' | 'guitar'): number[] {
  const isGuitar = instrument === 'guitar' || chord.frets.length === 6;
  const baseFreqs = isGuitar ? GUITAR_BASE_FREQS : UKULELE_BASE_FREQS;
  const freqs: number[] = [];

  chord.frets.forEach((fret, idx) => {
    if (fret >= 0 && idx < baseFreqs.length) {
      // f = f0 * 2^(traste / 12)
      const freq = baseFreqs[idx] * Math.pow(2, fret / 12);
      freqs.push(freq);
    }
  });

  return freqs;
}

let sharedAudioCtx: AudioContext | null = null;

function getAudioContext(): AudioContext | null {
  if (typeof window === 'undefined') return null;
  const AudioCtxClass = window.AudioContext || (window as unknown as { webkitAudioContext: typeof AudioContext }).webkitAudioContext;
  if (!AudioCtxClass) return null;

  if (!sharedAudioCtx) {
    sharedAudioCtx = new AudioCtxClass();
  }
  if (sharedAudioCtx.state === 'suspended') {
    sharedAudioCtx.resume().catch(() => {});
  }
  return sharedAudioCtx;
}

/**
 * Sintetiza un rasgueo acústico cálido del acorde usando Web Audio API nativo.
 * Cero dependencias externas, latencia ultra-baja y sonido orgánico.
 */
export async function playAcousticChord(chord: UkuleleChord, instrument: 'ukulele' | 'guitar'): Promise<void> {
  const ctx = getAudioContext();
  if (!ctx) return;

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

  freqs.forEach((freq, i) => {
    const stringTime = now + i * strumSpeed;
    const osc = ctx.createOscillator();
    const stringGain = ctx.createGain();

    // Timbre tipo cuerda pulsada (triangular rica en armónicos)
    osc.type = 'triangle';
    osc.frequency.setValueAtTime(freq, stringTime);

    // Envolvente de volumen (ataque instantáneo y decaimiento suave)
    stringGain.gain.setValueAtTime(0.0001, stringTime);
    stringGain.gain.exponentialRampToValueAtTime(0.4, stringTime + 0.004);
    stringGain.gain.exponentialRampToValueAtTime(0.0001, stringTime + 1.4);

    osc.connect(stringGain);
    stringGain.connect(filter);

    osc.start(stringTime);
    osc.stop(stringTime + 1.45);
  });
}
