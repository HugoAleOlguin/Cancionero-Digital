import { describe, it, expect } from 'vitest';
import { buildHymnPdfDocument } from '../utils/pdfGenerator';
import { Hymn } from '../types/hymn';

describe('PDF Generator (Centered Layout & Shaped Dividers)', () => {
  const sampleHymn: Hymn = {
    id: 27,
    title: 'El Camino a Emaús',
    content: 'Dos de los suyos caminan con gran tristeza,\nvan camino a la aldea que está en Emaús.\n\nQuédate, no te vayas, en esta noche a posar,\nquédate forastero la noche ya viene.',
    author: 'Tito Abarca',
    isDeleted: false
  };

  it('builds a valid jsPDF document instance', () => {
    const doc = buildHymnPdfDocument(sampleHymn, 0);
    expect(doc).toBeDefined();
    expect((doc as any).internal.getNumberOfPages()).toBeGreaterThanOrEqual(1);
  });
});
