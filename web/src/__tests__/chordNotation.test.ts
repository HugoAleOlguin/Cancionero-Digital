import { describe, it, expect } from 'vitest';
import { formatChordNotation, getChordDisplayName } from '../util/chordNotation';

describe('Chord Notation Engine (DO-RE-MI Default vs C-D-E)', () => {
  it('converts Anglo symbols to Latin solfege (DO-RE-MI) by default', () => {
    expect(formatChordNotation('C', 'latin')).toBe('DO');
    expect(formatChordNotation('G', 'latin')).toBe('SOL');
    expect(formatChordNotation('D', 'latin')).toBe('RE');
    expect(formatChordNotation('A', 'latin')).toBe('LA');
    expect(formatChordNotation('E', 'latin')).toBe('MI');
    expect(formatChordNotation('F', 'latin')).toBe('FA');
    expect(formatChordNotation('B', 'latin')).toBe('SI');
  });

  it('correctly handles chord alterations and qualities in Latin notation', () => {
    expect(formatChordNotation('Am', 'latin')).toBe('LAm');
    expect(formatChordNotation('G7', 'latin')).toBe('SOL7');
    expect(formatChordNotation('F#m', 'latin')).toBe('FA#m');
    expect(formatChordNotation('Bb', 'latin')).toBe('SIb');
    expect(formatChordNotation('C#m7', 'latin')).toBe('DO#m7');
    expect(formatChordNotation('Dsus4', 'latin')).toBe('REsus4');
    expect(formatChordNotation('Fmaj7', 'latin')).toBe('FAmaj7');
    expect(formatChordNotation('Cdim', 'latin')).toBe('DOdim');
  });

  it('preserves Anglo symbols when anglo notation is explicitly requested', () => {
    expect(formatChordNotation('C', 'anglo')).toBe('C');
    expect(formatChordNotation('Am', 'anglo')).toBe('Am');
    expect(formatChordNotation('F#m7', 'anglo')).toBe('F#m7');
    expect(formatChordNotation('Bb', 'anglo')).toBe('Bb');
  });

  it('provides full display name with both or single notation', () => {
    expect(getChordDisplayName('C', 'latin')).toBe('DO');
    expect(getChordDisplayName('Am', 'latin')).toBe('LAm');
  });
});
