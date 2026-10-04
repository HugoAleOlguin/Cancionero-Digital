import { describe, it, expect } from 'vitest';
import {
  buildHymnPdfDocument,
  paginateHymnContent,
  PdfStudioConfig
} from '../utils/pdfGenerator';
import { Hymn } from '../types/hymn';

describe('PDF Studio & Generator Engine', () => {
  const sampleHymn: Hymn = {
    id: 27,
    title: 'El Camino a Emaús',
    content: 'Dos de los suyos caminan con gran tristeza,\nvan camino a la aldea que está en Emaús.\n\nQuédate, no te vayas, en esta noche a posar,\nquédate forastero la noche ya viene.',
    author: 'Tito Abarca',
    isDeleted: false
  };

  const sampleChords: Record<string, string> = {
    'l0_s0': 'C',
    'l0_s4': 'G',
    'l2_s0': 'Am'
  };

  it('builds a valid jsPDF document with default parameters', () => {
    const doc = buildHymnPdfDocument(sampleHymn, 0);
    expect(doc).toBeDefined();
    expect((doc as any).internal.getNumberOfPages()).toBeGreaterThanOrEqual(1);
  });

  it('paginates short hymn content into a single page', () => {
    const config: PdfStudioConfig = {
      fontSize: 12,
      typography: 'serif',
      showHeader: true,
      showFooter: true,
      showChords: false,
      chordNotation: 'latin',
      content: sampleHymn.content
    };

    const pages = paginateHymnContent(sampleHymn, config);
    expect(pages.length).toBe(1);
    expect(pages[0].pageNumber).toBe(1);
    expect(pages[0].totalPages).toBe(1);
    expect(pages[0].stanzas.length).toBe(2);
  });

  it('automatically creates multiple pages when font size is large or content is long', () => {
    const longContent = Array(12)
      .fill('Esta es una estrofa larga de prueba para el cancionero cristiano.\nCon varias líneas de texto que llenan el espacio vertical.')
      .join('\n\n');

    const config: PdfStudioConfig = {
      fontSize: 16,
      typography: 'sans',
      showHeader: true,
      showFooter: true,
      showChords: false,
      chordNotation: 'latin',
      content: longContent
    };

    const pages = paginateHymnContent(sampleHymn, config);
    expect(pages.length).toBeGreaterThan(1);
    expect(pages[0].totalPages).toBe(pages.length);
    expect(pages[1].pageNumber).toBe(2);
  });

  it('correctly associates chords with syllables when showChords is enabled', () => {
    const config: PdfStudioConfig = {
      fontSize: 12,
      typography: 'serif',
      showHeader: true,
      showFooter: true,
      showChords: true,
      chordNotation: 'latin',
      content: sampleHymn.content
    };

    const pages = paginateHymnContent(sampleHymn, config, sampleChords);
    expect(pages.length).toBe(1);
    const firstLine = pages[0].stanzas[0].lines[0];
    expect(firstLine.hasChords).toBe(true);
    expect(firstLine.syllables.some(s => s.chord === 'DO')).toBe(true);
  });

  it('generates a multi-page jsPDF document matching the paginated layout', () => {
    const longContent = Array(15)
      .fill('Estrofa de alabanza para probar el desbordamiento de página en el PDF.')
      .join('\n\n');

    const config: PdfStudioConfig = {
      fontSize: 16,
      typography: 'serif',
      showHeader: true,
      showFooter: true,
      showChords: false,
      chordNotation: 'latin',
      content: longContent
    };

    const doc = buildHymnPdfDocument(sampleHymn, 0, config);
    const totalPages = (doc as any).internal.getNumberOfPages();
    expect(totalPages).toBeGreaterThan(1);
  });

  it('respects showHeader and showFooter flags', () => {
    const config: PdfStudioConfig = {
      fontSize: 12,
      typography: 'mono',
      showHeader: false,
      showFooter: false,
      showChords: false,
      chordNotation: 'latin',
      content: sampleHymn.content
    };

    const pages = paginateHymnContent(sampleHymn, config);
    expect(pages.length).toBe(1);
    const doc = buildHymnPdfDocument(sampleHymn, 0, config);
    expect(doc).toBeDefined();
  });
});
