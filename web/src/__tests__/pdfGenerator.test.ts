import { describe, it, expect } from 'vitest';
import {
  buildHymnPdfDocument,
  paginateHymnContent,
  estimateTitleLineCount,
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

  it('preserves isWordEnd property on syllables to prevent mashed words', () => {
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
    const firstLine = pages[0].stanzas[0].lines[0];
    const wordEndSyllables = firstLine.syllables.filter(s => s.isWordEnd);
    expect(wordEndSyllables.length).toBeGreaterThan(0);
  });

  it('subdivides a huge stanza that exceeds page space into multiple pages without dropping lines', () => {
    // 50 líneas dentro de una única estrofa (sin doble salto de línea)
    const giantStanzaLines = Array.from({ length: 50 }, (_, i) => `Línea número ${i + 1} de la estrofa gigante de prueba.`);
    const singleGiantStanza = giantStanzaLines.join('\n');

    const config: PdfStudioConfig = {
      fontSize: 14,
      typography: 'serif',
      showHeader: true,
      showFooter: true,
      showChords: false,
      chordNotation: 'latin',
      content: singleGiantStanza
    };

    const pages = paginateHymnContent(sampleHymn, config);
    expect(pages.length).toBeGreaterThanOrEqual(2);

    // Comprobar que en la página 2 la primera estrofa está marcada como continuación
    expect(pages[1].stanzas[0].isContinuation).toBe(true);

    // Verificar que todas las 50 líneas fueron preservadas en su orden original
    const totalLinesAcrossPages = pages.flatMap(p => p.stanzas.flatMap(s => s.lines));
    expect(totalLinesAcrossPages.length).toBe(50);
    expect(totalLinesAcrossPages[0].text).toBe('Línea número 1 de la estrofa gigante de prueba.');
    expect(totalLinesAcrossPages[49].text).toBe('Línea número 50 de la estrofa gigante de prueba.');

    // Verificar que el documento PDF se genera correctamente
    const doc = buildHymnPdfDocument(sampleHymn, 0, config);
    expect((doc as any).internal.getNumberOfPages()).toBe(pages.length);
  });

  it('dynamically estimates titleBlockHeight based on character length', () => {
    // Título corto cabe en 1 línea
    expect(estimateTitleLineCount('27. El Camino a Emaús')).toBe(1);

    // Título muy largo se divide en 2 o más líneas
    const longTitle = '150. Alabanza solemne con un título sumamente extenso que excede con creces el ancho imprimible de una sola línea';
    const estimatedLines = estimateTitleLineCount(longTitle, 38);
    expect(estimatedLines).toBeGreaterThanOrEqual(2);

    const config: PdfStudioConfig = {
      fontSize: 12,
      typography: 'sans',
      showHeader: true,
      showFooter: true,
      showChords: false,
      chordNotation: 'latin',
      customTitle: longTitle,
      content: sampleHymn.content
    };

    const pages = paginateHymnContent(sampleHymn, config);
    expect(pages.length).toBe(1);
    expect(pages[0].title).toContain(longTitle);
  });

  it('safely handles lines with chords that exceed printable width without error', () => {
    const longLineWithChords = 'Esta es una frase musical sumamente larga con muchas sílabas y acordes que llenan todo el ancho imprimible útil';
    const sampleLongChords: Record<string, string> = {
      'l0_s0': 'Cmaj7',
      'l0_s5': 'F#m7b5',
      'l0_s10': 'B7alt',
      'l0_s15': 'Em9'
    };

    const config: PdfStudioConfig = {
      fontSize: 16,
      typography: 'mono',
      showHeader: true,
      showFooter: true,
      showChords: true,
      chordNotation: 'latin',
      content: longLineWithChords
    };

    const pages = paginateHymnContent(sampleHymn, config, sampleLongChords);
    expect(pages.length).toBe(1);
    expect(pages[0].stanzas[0].lines[0].hasChords).toBe(true);

    // buildHymnPdfDocument debe escalar y acotar el ancho sin lanzar errores
    const doc = buildHymnPdfDocument(sampleHymn, 0, config, sampleLongChords);
    expect(doc).toBeDefined();
    expect((doc as any).internal.getNumberOfPages()).toBe(1);
  });
});
