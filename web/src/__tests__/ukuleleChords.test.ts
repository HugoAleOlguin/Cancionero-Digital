import { describe, it, expect } from 'vitest';
import {
  UKULELE_CHORDS_DB,
  HARMONIC_FAMILIES,
  PREDOMINANT_KEYS
} from '../data/ukuleleChords';

describe('Ukulele Chords Database & Harmonic Engine', () => {
  it('contains valid 4-string fretings for all defined chords', () => {
    const chordEntries = Object.entries(UKULELE_CHORDS_DB);
    expect(chordEntries.length).toBeGreaterThanOrEqual(40);

    for (const [symbol, chord] of chordEntries) {
      expect(chord.frets).toBeDefined();
      expect(chord.frets.length).toBe(4);
      expect(chord.name).toBeDefined();
      expect(chord.symbol).toBe(symbol);
      // Frets must be >= -1 (-1 is muted, 0 is open, >0 is fret)
      chord.frets.forEach(f => {
        expect(f).toBeGreaterThanOrEqual(-1);
      });
    }
  });

  it('correctly matches standard ukulele tuning (G-C-E-A) for key chords', () => {
    // C (DO): [0, 0, 0, 3]
    expect(UKULELE_CHORDS_DB['C'].frets).toEqual([0, 0, 0, 3]);
    // G (SOL): [0, 2, 3, 2]
    expect(UKULELE_CHORDS_DB['G'].frets).toEqual([0, 2, 3, 2]);
    // D (RE): [2, 2, 2, 0]
    expect(UKULELE_CHORDS_DB['D'].frets).toEqual([2, 2, 2, 0]);
    // F (FA): [2, 0, 1, 0]
    expect(UKULELE_CHORDS_DB['F'].frets).toEqual([2, 0, 1, 0]);
    // Am (LAm): [2, 0, 0, 0]
    expect(UKULELE_CHORDS_DB['Am'].frets).toEqual([2, 0, 0, 0]);
  });

  it('provides complete harmonic accompaniment families for predominant keys', () => {
    for (const k of PREDOMINANT_KEYS) {
      const family = HARMONIC_FAMILIES[k.symbol];
      expect(family).toBeDefined();
      expect(family.tonic).toBeDefined();
      expect(family.subdominant).toBeDefined();
      expect(family.dominant).toBeDefined();
      expect(family.dominant7).toBeDefined();
      expect(family.relativeMinor).toBeDefined();
      expect(family.secondary1).toBeDefined();
      expect(family.secondary2).toBeDefined();
    }
  });

  it('verifies DO (C) family chords correspond to C, F, G, G7, Am, Dm, Em', () => {
    const cFamily = HARMONIC_FAMILIES['C'];
    expect(cFamily.tonic.symbol).toBe('C');
    expect(cFamily.subdominant.symbol).toBe('F');
    expect(cFamily.dominant.symbol).toBe('G');
    expect(cFamily.dominant7.symbol).toBe('G7');
    expect(cFamily.relativeMinor.symbol).toBe('Am');
    expect(cFamily.secondary1.symbol).toBe('Dm');
    expect(cFamily.secondary2.symbol).toBe('Em');
  });
});
