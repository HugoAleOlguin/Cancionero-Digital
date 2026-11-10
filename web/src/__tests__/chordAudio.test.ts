import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest';
import {
  getChordFrequencies,
  getAudioContext,
  playAcousticChord,
  resetAudioContextForTesting
} from '../utils/chordAudio';
import { UKULELE_CHORDS_DB, UkuleleChord } from '../data/ukuleleChords';
import { GUITAR_CHORDS_DB } from '../data/guitarChords';

describe('Chord Audio Synthesizer Frequency Engine', () => {
  it('calculates correct frequencies for Ukulele C major [0, 0, 0, 3]', () => {
    const cUke = UKULELE_CHORDS_DB['C'];
    const freqs = getChordFrequencies(cUke, 'ukulele');
    expect(freqs.length).toBe(4);
    // G4 ~ 392Hz, C4 ~ 261.6Hz, E4 ~ 329.6Hz, C5 (A4 + 3 semitones) ~ 523.25Hz
    expect(freqs[0]).toBeCloseTo(392.0, 0);
    expect(freqs[1]).toBeCloseTo(261.63, 0);
    expect(freqs[2]).toBeCloseTo(329.63, 0);
    expect(freqs[3]).toBeCloseTo(523.25, 0);
  });

  it('calculates correct frequencies for Guitar E major [0, 2, 2, 1, 0, 0]', () => {
    const eGuitar = GUITAR_CHORDS_DB['E'];
    const freqs = getChordFrequencies(eGuitar, 'guitar');
    expect(freqs.length).toBe(6);
    // E2 ~ 82.4Hz
    expect(freqs[0]).toBeCloseTo(82.41, 0);
    // B2 (A2 + 2 frets) ~ 123.47Hz
    expect(freqs[1]).toBeCloseTo(123.47, 0);
    // E3 (D3 + 2 frets) ~ 164.81Hz
    expect(freqs[2]).toBeCloseTo(164.81, 0);
    // G#3 (G3 + 1 fret) ~ 207.65Hz
    expect(freqs[3]).toBeCloseTo(207.65, 0);
    // B3 ~ 246.94Hz
    expect(freqs[4]).toBeCloseTo(246.94, 0);
    // E4 ~ 329.63Hz
    expect(freqs[5]).toBeCloseTo(329.63, 0);
  });

  it('omits muted strings (frets === -1) from audio frequencies', () => {
    const dGuitar = GUITAR_CHORDS_DB['D']; // [-1, -1, 0, 2, 3, 2]
    const freqs = getChordFrequencies(dGuitar, 'guitar');
    // Only 4 active sounding strings (D3, A3, D4, F#4)
    expect(freqs.length).toBe(4);
  });

  it('validates that frets must be valid non-negative integers', () => {
    const invalidChord = {
      name: 'Custom',
      symbol: 'X',
      frets: [0, 2.5, -2, NaN, null as unknown as number],
      fingers: [0, 0, 0, 0, 0],
      barres: []
    } as unknown as UkuleleChord;

    const freqs = getChordFrequencies(invalidChord, 'ukulele');
    // Solo el traste 0 (índice 0) debe ser procesado.
    // 2.5 no es entero, -2 es negativo, NaN no es entero, null no es número.
    expect(freqs.length).toBe(1);
    expect(freqs[0]).toBeCloseTo(392.0, 0);

    // Con guitarra
    const invalidGuitarChord = {
      name: 'Custom Guitar',
      symbol: 'X',
      frets: [0, 2, -1, 3.14, '5' as unknown as number, -5],
      fingers: [0, 0, 0, 0, 0, 0],
      barres: []
    } as unknown as UkuleleChord;

    const guitarFreqs = getChordFrequencies(invalidGuitarChord, 'guitar');
    // Solo los trastes 0 y 2 (índices 0 y 1) son enteros válidos >= 0
    expect(guitarFreqs.length).toBe(2);
    expect(guitarFreqs[0]).toBeCloseTo(82.41, 0);
  });

  it('returns empty array if chord or frets are missing', () => {
    expect(getChordFrequencies(null as unknown as UkuleleChord, 'ukulele')).toEqual([]);
    expect(getChordFrequencies({} as unknown as UkuleleChord, 'ukulele')).toEqual([]);
  });
});

describe('Web Audio API Lifecycle & Memory Management', () => {
  let mockAudioContext: any;
  let createdOscillators: any[] = [];
  let createdGainNodes: any[] = [];
  let createdFilters: any[] = [];

  beforeEach(() => {
    resetAudioContextForTesting();
    createdOscillators = [];
    createdGainNodes = [];
    createdFilters = [];

    mockAudioContext = {
      state: 'suspended',
      currentTime: 0,
      destination: {},
      resume: vi.fn().mockImplementation(async () => {
        mockAudioContext.state = 'running';
      }),
      createBiquadFilter: vi.fn().mockImplementation(() => {
        const filter = {
          type: 'lowpass',
          frequency: { setValueAtTime: vi.fn() },
          Q: { setValueAtTime: vi.fn() },
          connect: vi.fn(),
          disconnect: vi.fn()
        };
        createdFilters.push(filter);
        return filter;
      }),
      createGain: vi.fn().mockImplementation(() => {
        const gain = {
          gain: {
            setValueAtTime: vi.fn(),
            exponentialRampToValueAtTime: vi.fn()
          },
          connect: vi.fn(),
          disconnect: vi.fn()
        };
        createdGainNodes.push(gain);
        return gain;
      }),
      createOscillator: vi.fn().mockImplementation(() => {
        const osc = {
          type: 'sine',
          frequency: { setValueAtTime: vi.fn() },
          connect: vi.fn(),
          disconnect: vi.fn(),
          start: vi.fn(),
          stop: vi.fn(),
          onended: null as (() => void) | null
        };
        createdOscillators.push(osc);
        return osc;
      })
    };

    // Mock global window and AudioContext using globalThis
    (globalThis as any).window = {
      AudioContext: vi.fn().mockImplementation(() => mockAudioContext)
    };
  });

  afterEach(() => {
    resetAudioContextForTesting();
    delete (globalThis as any).window;
  });

  it('resumes suspended AudioContext on getAudioContext', async () => {
    const ctx = await getAudioContext();
    expect(ctx).toBe(mockAudioContext);
    expect(mockAudioContext.resume).toHaveBeenCalled();
    expect(mockAudioContext.state).toBe('running');
  });

  it('awaits resume before scheduling notes and disconnects nodes on onended', async () => {
    mockAudioContext.state = 'suspended';
    const cUke = UKULELE_CHORDS_DB['C']; // 4 cuerdas

    await playAcousticChord(cUke, 'ukulele');

    // Debe haber llamado a resume
    expect(mockAudioContext.resume).toHaveBeenCalled();

    // 4 osciladores creados
    expect(createdOscillators.length).toBe(4);
    // 4 gains individuales + 1 masterGain = 5 gain nodes
    expect(createdGainNodes.length).toBe(5);
    // 1 biquad filter
    expect(createdFilters.length).toBe(1);

    // Antes de onended, los nodos no deben estar desconectados
    expect(createdOscillators[0].disconnect).not.toHaveBeenCalled();
    expect(createdFilters[0].disconnect).not.toHaveBeenCalled();

    // Simular término de los 4 osciladores
    createdOscillators.forEach(osc => {
      expect(typeof osc.onended).toBe('function');
      osc.onended();
    });

    // Tras finalizar todos los osciladores, todos los osciladores, gains y filter deben desconectarse
    createdOscillators.forEach(osc => {
      expect(osc.disconnect).toHaveBeenCalled();
    });
    createdGainNodes.forEach(gain => {
      expect(gain.disconnect).toHaveBeenCalled();
    });
    createdFilters.forEach(filter => {
      expect(filter.disconnect).toHaveBeenCalled();
    });
  });
});
