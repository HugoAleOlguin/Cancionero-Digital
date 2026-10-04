import { jsPDF } from 'jspdf';
import { Hymn, TypographyType } from '../types/hymn';
import { ChordNotationType, formatChordNotation } from '../util/chordNotation';
import { splitLineIntoSyllables } from '../util/syllabifier';

export interface PdfStudioConfig {
  fontSize: number;          // en pt (ej: 8 a 18, default: 12)
  typography: TypographyType; // 'serif' | 'sans' | 'mono'
  showHeader: boolean;       // Encabezado superior ornamental
  showFooter: boolean;       // Pie de página con numeración
  showChords: boolean;       // Mostrar notas musicales sobre sílabas
  chordNotation: ChordNotationType; // 'latin' | 'anglo'
  customTitle?: string;
  customAuthor?: string;
  content: string;           // Contenido editable
}

export interface PdfLineToken {
  id: string;
  text: string;
  leadingPunct?: string;
  trailingPunct?: string;
  chord?: string;
}

export interface PdfLineLayout {
  text: string;
  lineIndex: number;
  hasChords: boolean;
  syllables: PdfLineToken[];
}

export interface PdfStanzaLayout {
  stanzaIndex: number;
  lines: PdfLineLayout[];
}

export interface PdfPageLayout {
  pageNumber: number;
  totalPages: number;
  title?: string;
  author?: string;
  stanzas: PdfStanzaLayout[];
  hasHeader: boolean;
  hasFooter: boolean;
}

/**
 * Mapeo de tipografías canónicas a familias de fuentes soportadas nativamente por jsPDF.
 */
export function getJsPdfFont(typography: TypographyType): string {
  switch (typography) {
    case 'sans':
      return 'helvetica';
    case 'mono':
      return 'courier';
    case 'serif':
    default:
      return 'times';
  }
}

/**
 * Dibuja un divisor estilizado con remate ornamental en el centro:
 * ─────── • ◆ • ───────
 */
export function drawShapedDivider(doc: jsPDF, y: number, pageWidth: number, margin: number) {
  const cx = pageWidth / 2;
  const gap = 14;

  // Líneas laterales delgadas en gris pergamino suave
  doc.setDrawColor(215, 208, 195);
  doc.setLineWidth(0.3);
  doc.line(margin, y, cx - gap, y);
  doc.line(cx + gap, y, pageWidth - margin, y);

  // Acentos geométricos dorados en el centro
  doc.setFillColor(197, 160, 58); // GoldenMain

  // Puntos laterales
  doc.circle(cx - 7, y, 0.6, 'F');
  doc.circle(cx + 7, y, 0.6, 'F');

  // Rombo / Diamante central (4mm x 4mm)
  doc.lines(
    [
      [2, 2],
      [-2, 2],
      [-2, -2],
      [2, -2]
    ],
    cx,
    y - 2,
    [1, 1],
    'F',
    true
  );
}

/**
 * Paginador inteligente de alabanzas para A4 (210 x 297 mm).
 * Descompone el contenido en páginas virtuales respetando márgenes,
 * encabezado, pie de página, tamaño de letra y presencia de acordes.
 */
export function paginateHymnContent(
  hymn: Hymn,
  config: PdfStudioConfig,
  chords?: Record<string, string>
): PdfPageLayout[] {
  const activeContent = config.content || hymn.content || '';
  const rawStanzas = activeContent
    .split(/\n\s*\n/)
    .map(s => s.trim())
    .filter(s => s.length > 0);

  // Dimensiones en mm (A4 vertical)
  const headerTopY = config.showHeader ? 24 : 16;
  const footerBottomY = config.showFooter ? 276 : 286;
  const titleBlockHeight = 26; // Título + autor en página 1

  const lineHeight = config.fontSize * 0.46;
  const chordExtraHeight = config.showChords ? config.fontSize * 0.32 : 0;
  const stanzaGap = config.fontSize * 0.52;

  let globalLineCounter = 0;

  // Procesar todas las estrofas y líneas con tokens
  const processedStanzas: PdfStanzaLayout[] = rawStanzas.map((rawStanza, sIdx) => {
    const rawLines = rawStanza.split('\n');
    const lines: PdfLineLayout[] = rawLines.map(rawLine => {
      const lineIdx = globalLineCounter++;
      const cleanLine = rawLine.trim();
      const tokens = splitLineIntoSyllables(cleanLine, lineIdx);

      let lineHasChords = false;
      const syllables: PdfLineToken[] = tokens.map(t => {
        let chordSymbol: string | undefined;
        if (config.showChords && chords && chords[t.id]) {
          chordSymbol = formatChordNotation(chords[t.id], config.chordNotation);
          lineHasChords = true;
        }
        return {
          id: t.id,
          text: t.text,
          leadingPunct: t.leadingPunct,
          trailingPunct: t.trailingPunct,
          chord: chordSymbol
        };
      });

      return {
        text: cleanLine,
        lineIndex: lineIdx,
        hasChords: lineHasChords,
        syllables
      };
    });

    return {
      stanzaIndex: sIdx,
      lines
    };
  });

  // Distribuir estrofas en páginas
  const pages: { title?: string; author?: string; stanzas: PdfStanzaLayout[] }[] = [];

  let currentPageStanzas: PdfStanzaLayout[] = [];
  let isFirstPage = true;
  let currentY = isFirstPage ? (headerTopY + titleBlockHeight) : (headerTopY + 12);
  let maxY = footerBottomY - 6;

  for (const stanza of processedStanzas) {
    // Calcular altura de esta estrofa
    let stanzaHeight = 0;
    for (const line of stanza.lines) {
      stanzaHeight += lineHeight + (line.hasChords ? chordExtraHeight : 0);
    }

    // Verificar si cabe en la página actual
    const willFit = (currentY + stanzaHeight) <= maxY;

    if (willFit || currentPageStanzas.length === 0) {
      currentPageStanzas.push(stanza);
      currentY += stanzaHeight + stanzaGap;
    } else {
      // Cerrar página actual y abrir una nueva
      pages.push({
        title: isFirstPage ? `${hymn.id}. ${config.customTitle || hymn.title}` : undefined,
        author: isFirstPage ? (config.customAuthor || hymn.author) : undefined,
        stanzas: currentPageStanzas
      });

      isFirstPage = false;
      currentPageStanzas = [stanza];
      currentY = headerTopY + 12 + stanzaHeight + stanzaGap;
      maxY = footerBottomY - 6;
    }
  }

  // Guardar última página
  if (currentPageStanzas.length > 0 || pages.length === 0) {
    pages.push({
      title: isFirstPage ? `${hymn.id}. ${config.customTitle || hymn.title}` : undefined,
      author: isFirstPage ? (config.customAuthor || hymn.author) : undefined,
      stanzas: currentPageStanzas
    });
  }

  const totalPages = pages.length;

  return pages.map((p, idx) => ({
    pageNumber: idx + 1,
    totalPages,
    title: p.title,
    author: p.author,
    stanzas: p.stanzas,
    hasHeader: config.showHeader,
    hasFooter: config.showFooter
  }));
}

/**
 * Genera el documento jsPDF con soporte completo para PdfStudioConfig
 * y renderizado de acordes sobre sílabas.
 */
export function buildHymnPdfDocument(
  hymn: Hymn,
  versionIndex: number = 0,
  customConfig?: Partial<PdfStudioConfig>,
  chords?: Record<string, string>
): jsPDF {
  const versions = [hymn.content, ...(hymn.extraVersions || [])];
  const initialContent = customConfig?.content ?? versions[versionIndex] ?? hymn.content;

  const config: PdfStudioConfig = {
    fontSize: customConfig?.fontSize ?? 12,
    typography: customConfig?.typography ?? 'serif',
    showHeader: customConfig?.showHeader ?? true,
    showFooter: customConfig?.showFooter ?? true,
    showChords: customConfig?.showChords ?? false,
    chordNotation: customConfig?.chordNotation ?? 'latin',
    customTitle: customConfig?.customTitle,
    customAuthor: customConfig?.customAuthor,
    content: initialContent
  };

  const pages = paginateHymnContent(hymn, config, chords);

  const doc = new jsPDF({
    orientation: 'portrait',
    unit: 'mm',
    format: 'a4'
  });

  const pageWidth = 210;
  const margin = 22;
  const contentWidth = pageWidth - margin * 2;
  const headerY = 22;
  const footerY = 278;

  const fontName = getJsPdfFont(config.typography);
  const baseLineHeight = config.fontSize * 0.46;
  const chordOffset = config.fontSize * 0.30;
  const stanzaGap = config.fontSize * 0.52;

  pages.forEach((page, pIdx) => {
    if (pIdx > 0) {
      doc.addPage();
    }

    // 1. Encabezado superior
    if (config.showHeader) {
      doc.setFont('helvetica', 'normal');
      doc.setFontSize(8.5);
      doc.setTextColor(110, 115, 125);
      doc.text('CANCIONERO CRISTIANO', margin, headerY - 5);

      doc.setFont('times', 'normal');
      doc.setFontSize(11);
      doc.setTextColor(197, 160, 58);
      doc.text('♪', pageWidth - margin - 2, headerY - 5);

      drawShapedDivider(doc, headerY, pageWidth, margin);
    }

    // 2. Pie de página inferior
    if (config.showFooter) {
      drawShapedDivider(doc, footerY, pageWidth, margin);

      doc.setFont('helvetica', 'normal');
      doc.setFontSize(8.5);
      doc.setTextColor(130, 135, 145);
      doc.text('Cancionero Cristiano', margin, footerY + 8);
      doc.text(
        page.totalPages > 1 ? `${page.pageNumber} / ${page.totalPages}` : '1',
        pageWidth - margin,
        footerY + 8,
        { align: 'right' }
      );
    }

    let currentY = config.showHeader ? (headerY + 16) : 20;

    // 3. Título y Autor en Página 1
    if (page.pageNumber === 1) {
      const displayTitle = `${hymn.id}. ${config.customTitle || hymn.title}`;
      doc.setFont(fontName, 'bold');
      doc.setFontSize(18);
      doc.setTextColor(30, 36, 43);

      const titleLines = doc.splitTextToSize(displayTitle, contentWidth - 10);
      titleLines.forEach((tLine: string) => {
        doc.text(tLine, pageWidth / 2, currentY, { align: 'center' });
        currentY += 8;
      });

      const authorText = config.customAuthor || hymn.author;
      if (authorText && authorText.trim()) {
        currentY += 1;
        doc.setFont(fontName, 'italic');
        doc.setFontSize(9.5);
        doc.setTextColor(100, 110, 120);
        doc.text(authorText.trim(), pageWidth / 2, currentY, { align: 'center' });
        currentY += 8;
      } else {
        currentY += 6;
      }
    }

    // 4. Estrofas
    doc.setFont(fontName, 'normal');
    doc.setFontSize(config.fontSize);

    page.stanzas.forEach((stanza, sIdx) => {
      stanza.lines.forEach(line => {
        if (!line.text) {
          currentY += baseLineHeight;
          return;
        }

        if (line.hasChords) {
          // Si tiene acordes, dejamos espacio vertical superior para las notas
          currentY += chordOffset;

          // Renderizar sílabas y acordes centrados armónicamente
          doc.setFont(fontName, 'normal');
          doc.setFontSize(config.fontSize);
          const fullLineWidth = doc.getTextWidth(line.text);
          let currentX = (pageWidth - fullLineWidth) / 2;

          line.syllables.forEach(syl => {
            const sylText = `${syl.leadingPunct || ''}${syl.text}${syl.trailingPunct || ''}`;
            const sylWidth = doc.getTextWidth(sylText);

            // Si la sílaba tiene acorde, dibujarlo arriba centrado en dorado
            if (syl.chord) {
              doc.setFont(fontName, 'bold');
              doc.setFontSize(Math.max(7.5, config.fontSize * 0.72));
              doc.setTextColor(165, 120, 20); // GoldenMain imprimible
              doc.text(syl.chord, currentX + sylWidth / 2, currentY - 3.8, { align: 'center' });
            }

            // Dibujar texto de la sílaba
            doc.setFont(fontName, 'normal');
            doc.setFontSize(config.fontSize);
            doc.setTextColor(45, 52, 60);
            doc.text(sylText, currentX, currentY);

            currentX += sylWidth;
          });

          currentY += baseLineHeight;
        } else {
          // Línea regular sin acordes: texto centrado nítido
          doc.setFont(fontName, 'normal');
          doc.setFontSize(config.fontSize);
          doc.setTextColor(45, 52, 60);
          doc.text(line.text, pageWidth / 2, currentY, { align: 'center' });
          currentY += baseLineHeight;
        }
      });

      if (sIdx < page.stanzas.length - 1) {
        currentY += stanzaGap;
      }
    });
  });

  return doc;
}

/**
 * Construye el documento y activa la descarga en el navegador.
 */
export function generateHymnPdf(
  hymn: Hymn,
  versionIndex: number = 0,
  customConfig?: Partial<PdfStudioConfig>,
  chords?: Record<string, string>
) {
  const doc = buildHymnPdfDocument(hymn, versionIndex, customConfig, chords);

  const titleForFilename = customConfig?.customTitle || hymn.title;
  const safeFilename = `${hymn.id}_${titleForFilename}`
    .normalize('NFD')
    .replace(/[\u0300-\u036f]/g, '')
    .replace(/[^a-zA-Z0-9_-]/g, '_')
    .substring(0, 40);

  doc.save(`${safeFilename}.pdf`);
}
