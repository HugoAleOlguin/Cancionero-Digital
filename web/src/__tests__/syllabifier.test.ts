import { describe, it, expect } from 'vitest';
import { syllabifyWord, splitLineIntoSyllables } from '../util/syllabifier';

describe('Spanish Syllabifier Engine', () => {
  it('correctly syllabifies standard Spanish words', () => {
    expect(syllabifyWord('gracia')).toEqual(['gra', 'cia']);
    expect(syllabifyWord('sublime')).toEqual(['su', 'bli', 'me']);
    expect(syllabifyWord('cancionero')).toEqual(['can', 'cio', 'ne', 'ro']);
    expect(syllabifyWord('cristiano')).toEqual(['cris', 'tia', 'no']);
    expect(syllabifyWord('pastor')).toEqual(['pas', 'tor']);
    expect(syllabifyWord('alabanza')).toEqual(['a', 'la', 'ban', 'za']);
  });

  it('handles diphthongs and hiatus correctly', () => {
    // Diphthongs
    expect(syllabifyWord('cielo')).toEqual(['cie', 'lo']);
    expect(syllabifyWord('rey')).toEqual(['rey']);
    expect(syllabifyWord('bueno')).toEqual(['bue', 'no']);

    // Hiatus (strong-strong or accented weak)
    expect(syllabifyWord('dia')).toEqual(['dia']); // sin tilde es diptongo
    expect(syllabifyWord('día')).toEqual(['dí', 'a']);
    expect(syllabifyWord('león')).toEqual(['le', 'ón']);
    expect(syllabifyWord('creo')).toEqual(['cre', 'o']);
  });

  it('handles monosyllables and punctuation intact', () => {
    expect(syllabifyWord('es')).toEqual(['es']);
    expect(syllabifyWord('Dios')).toEqual(['Dios']);
    expect(syllabifyWord('fe')).toEqual(['fe']);
  });

  it('splits an entire hymn line into syllable tokens with word indices', () => {
    const line = 'Gracia sublime es';
    const tokens = splitLineIntoSyllables(line);
    // Debe tener tokens correspondientes a "Gra", "cia", "su", "bli", "me", "es"
    expect(tokens.length).toBe(6);
    expect(tokens.map(t => t.text).join('')).toBe('Graciasublimees');
    expect(tokens[0].text).toBe('Gra');
    expect(tokens[1].text).toBe('cia');
    expect(tokens[1].isWordEnd).toBe(true);
    expect(tokens[2].text).toBe('su');
    expect(tokens[4].text).toBe('me');
    expect(tokens[4].isWordEnd).toBe(true);
    expect(tokens[5].text).toBe('es');
    expect(tokens[5].isWordEnd).toBe(true);
  });
});
