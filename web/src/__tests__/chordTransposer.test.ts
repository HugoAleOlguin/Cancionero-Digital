import { describe, it, expect } from 'vitest';
import { transposeChord, transposeKey, transposeHymnChords } from '../util/chordTransposer';

describe('Chord Transposer Engine', () => {
  it('transposes major and minor chords correctly by semitones', () => {
    // C + 1 -> C# / Db
    expect(transposeChord('C', 1)).toBe('C#');
    // C + 2 -> D
    expect(transposeChord('C', 2)).toBe('D');
    // C - 1 -> B
    expect(transposeChord('C', -1)).toBe('B');

    // Am + 2 -> Bm
    expect(transposeChord('Am', 2)).toBe('Bm');
    // Am - 1 -> Abm / G#m
    expect(['Abm', 'G#m']).toContain(transposeChord('Am', -1));

    // G7 + 2 -> A7
    expect(transposeChord('G7', 2)).toBe('A7');

    // F#m + 1 -> Gm
    expect(transposeChord('F#m', 1)).toBe('Gm');
  });

  it('transposes entire hymn chord maps cleanly', () => {
    const originalChords = {
      'l0_s0': 'C',
      'l0_s4': 'G',
      'l1_s2': 'Am',
      'l1_s6': 'F'
    };

    // Subir 2 semitonos (DO a RE)
    const transposed = transposeHymnChords(originalChords, 2);
    expect(transposed['l0_s0']).toBe('D');
    expect(transposed['l0_s4']).toBe('A');
    expect(transposed['l1_s2']).toBe('Bm');
    expect(transposed['l1_s6']).toBe('G');
  });

  it('transposes root keys cleanly', () => {
    expect(transposeKey('C', 2)).toBe('D');
    expect(transposeKey('G', -2)).toBe('F');
    expect(transposeKey('Am', 2)).toBe('Bm');
  });
});
