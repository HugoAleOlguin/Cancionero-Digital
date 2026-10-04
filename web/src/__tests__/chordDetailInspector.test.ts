import { describe, it, expect } from 'vitest';
import { findBelongingFamilies } from '../components/ChordDetailInspector';
import { HARMONIC_FAMILIES } from '../data/ukuleleChords';
import { GUITAR_HARMONIC_FAMILIES } from '../data/guitarChords';

describe('findBelongingFamilies Engine', () => {
  it('correctly identifies families that Am (LAm) belongs to in Ukulele', () => {
    const families = findBelongingFamilies('Am', HARMONIC_FAMILIES);
    expect(families.length).toBeGreaterThanOrEqual(2);

    const keySymbols = families.map(f => f.keySymbol);
    // Am is relative minor of C, tonic of Am, secondary in F or G
    expect(keySymbols).toContain('C');
    expect(keySymbols).toContain('Am');
  });

  it('correctly identifies families that F (FA) belongs to in Guitar', () => {
    const families = findBelongingFamilies('F', GUITAR_HARMONIC_FAMILIES);
    expect(families.length).toBeGreaterThanOrEqual(2);

    const keySymbols = families.map(f => f.keySymbol);
    // F is subdominant in C, tonic in F, dominant in Bb, etc.
    expect(keySymbols).toContain('C');
    expect(keySymbols).toContain('F');
  });

  it('handles chords with no harmonic family gracefully', () => {
    const families = findBelongingFamilies('NON_EXISTENT', HARMONIC_FAMILIES);
    expect(families).toEqual([]);
  });
});
