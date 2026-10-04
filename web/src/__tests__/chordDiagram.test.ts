import { describe, it, expect } from 'vitest';
import { getBarreSpan } from '../components/ChordDiagram';
import { UKULELE_CHORDS_DB } from '../data/ukuleleChords';
import { GUITAR_CHORDS_DB } from '../data/guitarChords';

describe('ChordDiagram Barre Span Detection', () => {
  it('detects partial barre for ukulele Fsus4 [3, 0, 1, 1] covering only strings 2 and 1', () => {
    const fsus4 = UKULELE_CHORDS_DB['Fsus4'];
    expect(fsus4).toBeDefined();
    // frets: [3, 0, 1, 1], barre: 1
    const span = getBarreSpan(fsus4);
    expect(span).not.toBeNull();
    // Indices 2 and 3 correspond to string 2 (E) and string 1 (A)
    // String 1 (C, index 1) is open (0), so barre must NOT cross it
    expect(span?.startIdx).toBe(2);
    expect(span?.endIdx).toBe(3);
  });

  it('detects partial barre for ukulele Gm7 [0, 2, 1, 1] covering only strings 2 and 1', () => {
    const gm7 = UKULELE_CHORDS_DB['Gm7'];
    expect(gm7).toBeDefined();
    const span = getBarreSpan(gm7);
    expect(span).not.toBeNull();
    expect(span?.startIdx).toBe(2);
    expect(span?.endIdx).toBe(3);
  });

  it('detects full 6-string barre for guitar F major [1, 3, 3, 2, 1, 1]', () => {
    const fMajor = GUITAR_CHORDS_DB['F'];
    expect(fMajor).toBeDefined();
    const span = getBarreSpan(fMajor);
    expect(span).not.toBeNull();
    // Spans from string 6 (index 0) to string 1 (index 5)
    expect(span?.startIdx).toBe(0);
    expect(span?.endIdx).toBe(5);
  });

  it('detects 5-string barre for guitar Bm [-1, 2, 4, 4, 3, 2] omitting muted 6th string', () => {
    const bm = GUITAR_CHORDS_DB['Bm'];
    expect(bm).toBeDefined();
    const span = getBarreSpan(bm);
    expect(span).not.toBeNull();
    // Spans from string 5 (index 1) to string 1 (index 5)
    expect(span?.startIdx).toBe(1);
    expect(span?.endIdx).toBe(5);
  });

  it('returns null for chords without barre or with single-string notes', () => {
    const cMajor = UKULELE_CHORDS_DB['C'];
    const span = getBarreSpan(cMajor);
    expect(span).toBeNull();
  });
});
