import { describe, it, expect } from 'vitest';
import {
  GUITAR_CHORDS_DB,
  GUITAR_HARMONIC_FAMILIES
} from '../data/guitarChords';
import { PREDOMINANT_KEYS } from '../data/ukuleleChords';

describe('Guitar Chords Database & Harmonic Engine', () => {
  it('contains valid 6-string fretings for all defined guitar chords', () => {
    const chordEntries = Object.entries(GUITAR_CHORDS_DB);
    expect(chordEntries.length).toBeGreaterThanOrEqual(40);

    for (const [symbol, chord] of chordEntries) {
      expect(chord.frets).toBeDefined();
      expect(chord.frets.length).toBe(6);
      expect(chord.name).toBeDefined();
      expect(chord.symbol).toBe(symbol);
      chord.frets.forEach(f => {
        expect(f).toBeGreaterThanOrEqual(-1);
      });
    }
  });

  it('correctly matches standard Spanish/Acoustic guitar tuning (E-A-D-G-B-E) for primary chords', () => {
    // C (DO): x32010
    expect(GUITAR_CHORDS_DB['C'].frets).toEqual([-1, 3, 2, 0, 1, 0]);
    // G (SOL): 320003
    expect(GUITAR_CHORDS_DB['G'].frets).toEqual([3, 2, 0, 0, 0, 3]);
    // D (RE): xx0232
    expect(GUITAR_CHORDS_DB['D'].frets).toEqual([-1, -1, 0, 2, 3, 2]);
    // A (LA): x02220
    expect(GUITAR_CHORDS_DB['A'].frets).toEqual([-1, 0, 2, 2, 2, 0]);
    // E (MI): 022100
    expect(GUITAR_CHORDS_DB['E'].frets).toEqual([0, 2, 2, 1, 0, 0]);
    // F (FA): 133211 con cejilla
    expect(GUITAR_CHORDS_DB['F'].frets).toEqual([1, 3, 3, 2, 1, 1]);
    expect(GUITAR_CHORDS_DB['F'].barre).toBe(1);
    // Am (LAm): x02210
    expect(GUITAR_CHORDS_DB['Am'].frets).toEqual([-1, 0, 2, 2, 1, 0]);
    // Em (MIm): 022000
    expect(GUITAR_CHORDS_DB['Em'].frets).toEqual([0, 2, 2, 0, 0, 0]);
    // Dm (REm): xx0231
    expect(GUITAR_CHORDS_DB['Dm'].frets).toEqual([-1, -1, 0, 2, 3, 1]);
  });

  it('provides complete guitar harmonic families for all predominant keys', () => {
    for (const k of PREDOMINANT_KEYS) {
      const family = GUITAR_HARMONIC_FAMILIES[k.symbol];
      expect(family).toBeDefined();
      expect(family.tonic.frets.length).toBe(6);
      expect(family.subdominant.frets.length).toBe(6);
      expect(family.dominant.frets.length).toBe(6);
      expect(family.dominant7.frets.length).toBe(6);
      expect(family.relativeMinor.frets.length).toBe(6);
      expect(family.secondary1.frets.length).toBe(6);
      expect(family.secondary2.frets.length).toBe(6);
    }
  });

  it('verifies guitar DO (C) progression matches C, F, G, G7, Am, Dm, Em', () => {
    const cFamily = GUITAR_HARMONIC_FAMILIES['C'];
    expect(cFamily.tonic.symbol).toBe('C');
    expect(cFamily.subdominant.symbol).toBe('F');
    expect(cFamily.dominant.symbol).toBe('G');
    expect(cFamily.dominant7.symbol).toBe('G7');
    expect(cFamily.relativeMinor.symbol).toBe('Am');
    expect(cFamily.secondary1.symbol).toBe('Dm');
    expect(cFamily.secondary2.symbol).toBe('Em');
  });
});
