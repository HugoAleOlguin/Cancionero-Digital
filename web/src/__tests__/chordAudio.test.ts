import { describe, it, expect } from 'vitest';
import { getChordFrequencies } from '../util/chordAudio';
import { UKULELE_CHORDS_DB } from '../data/ukuleleChords';
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
});
