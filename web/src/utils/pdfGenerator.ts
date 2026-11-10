import { jsPDF } from 'jspdf';
import { Hymn, TypographyType } from '../types/hymn';
import { ChordNotationType, formatChordNotation } from './chordNotation';
import { splitLineIntoSyllables } from './syllabifier';

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
  isWordEnd: boolean;
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
  isContinuation?: boolean;
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
 * Dibuja un icono vectorial de nota musical (♫) en el encabezado.
 * Evita caracteres Unicode directos que se corrompen en fuentes PostScript estándar de jsPDF.
 */
export function drawMusicalNoteIcon(
  doc: jsPDF,
  x: number,
  y: number,
  color: [number, number, number] = [197, 160, 58]
) {
  doc.setFillColor(color[0], color[1], color[2]);
  doc.setDrawColor(color[0], color[1], color[2]);

  // Cabeza 1 (izquierda)
  doc.circle(x, y, 0.75, 'F');
  // Cabeza 2 (derecha, ligeramente más alta)
  doc.circle(x + 2.8, y - 0.4, 0.75, 'F');

  // Plicas verticales
  doc.setLineWidth(0.35);
  doc.line(x + 0.65, y, x + 0.65, y - 3.4);
  doc.line(x + 3.45, y - 0.4, x + 3.45, y - 3.8);

  // Barra de unión superior (beam)
  doc.setLineWidth(0.7);
  doc.line(x + 0.65, y - 3.4, x + 3.45, y - 3.8);
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
 * Estima dinámicamente cuántas líneas ocupará el título en el encabezado (fuente 18pt bold)
 * considerando un ancho imprimible de ~156 mm (aprox. 38 caracteres por línea).
 */
export function estimateTitleLineCount(title: string, maxCharsPerLine: number = 38): number {
  const cleanTitle = title.trim();
  if (!cleanTitle) return 1;
  if (cleanTitle.length <= maxCharsPerLine) return 1;

  const words = cleanTitle.split(/\s+/);
  let lines = 1;
  let currentLen = 0;

  for (const word of words) {
    if (currentLen === 0) {
      currentLen = word.length;
    } else if (currentLen + 1 + word.length <= maxCharsPerLine) {
      currentLen += 1 + word.length;
    } else {
      lines++;
      currentLen = word.length;
    }
  }

  return Math.max(1, lines);
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

  // Dimensiones y márgenes en mm (A4 vertical: 210 x 297 mm, márgenes 22 mm)
  // El encabezado ubica su divisor en Y=22. Los textos comienzan en Y=38 si showHeader es true, o Y=20 si es false.
  const startYHeader = config.showHeader ? 38 : 20;
  const footerBottomY = config.showFooter ? 276 : 286;
  const maxY = footerBottomY - 6; // 270mm con footer, 280mm sin footer

  // Estimación dinámica de la altura del bloque de título en la primera página
  const displayTitle = `${hymn.id}. ${config.customTitle || hymn.title || ''}`;
  const titleLinesCount = estimateTitleLineCount(displayTitle, 38);
  const authorText = config.customAuthor || hymn.author;
  const hasAuthor = Boolean(authorText && authorText.trim());
  // Cada línea de título suma 8mm; autor suma 9mm (1+8) si existe o 6mm si no
  const titleBlockHeight = (titleLinesCount * 8) + (hasAuthor ? 9 : 6);

  const startYFirstPage = startYHeader + titleBlockHeight;
  const startYSubsequent = startYHeader;
  const emptyPageAvailableHeight = maxY - startYSubsequent;

  const baseLineHeight = config.fontSize * 0.46;
  const chordOffset = config.showChords ? config.fontSize * 0.30 : 0;
  const stanzaGap = config.fontSize * 0.52;

  const getLineHeight = (line: PdfLineLayout) =>
    baseLineHeight + (line.hasChords ? chordOffset : 0);

  const getStanzaHeight = (lines: PdfLineLayout[]) =>
    lines.reduce((acc, l) => acc + getLineHeight(l), 0);

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
          chord: chordSymbol,
          isWordEnd: t.isWordEnd
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
  let currentY = startYFirstPage;

  const flushPage = () => {
    pages.push({
      title: isFirstPage ? `${hymn.id}. ${config.customTitle || hymn.title}` : undefined,
      author: isFirstPage ? (config.customAuthor || hymn.author) : undefined,
      stanzas: currentPageStanzas
    });
    isFirstPage = false;
    currentPageStanzas = [];
    currentY = startYSubsequent;
  };

  for (const stanza of processedStanzas) {
    const stanzaHeight = getStanzaHeight(stanza.lines);
    const gapNeeded = currentPageStanzas.length > 0 ? stanzaGap : 0;

    // Caso 1: La estrofa cabe completa en la página actual
    if (currentY + gapNeeded + stanzaHeight <= maxY) {
      currentPageStanzas.push(stanza);
      currentY += gapNeeded + stanzaHeight;
      continue;
    }

    // Caso 2: No cabe completa en la página actual, pero sí cabe entera en una página limpia posterior.
    // Si la página actual ya tiene estrofas, la cerramos limpiamente para no romper la estrofa innecesariamente.
    if (currentPageStanzas.length > 0 && stanzaHeight <= emptyPageAvailableHeight) {
      flushPage();
      currentPageStanzas.push(stanza);
      currentY = startYSubsequent + stanzaHeight;
      continue;
    }

    // Caso 3: La estrofa supera el espacio restante de la página o la altura total disponible de una página vacía.
    // Subdividimos por líneas de manera que quepan las que entren en la página actual y el resto continúe en la siguiente.
    let remainingLines = [...stanza.lines];
    let isContinuation = false;

    while (remainingLines.length > 0) {
      const currentGap = currentPageStanzas.length > 0 ? stanzaGap : 0;
      const availableSpace = maxY - (currentY + currentGap);

      // Calcular cuántas líneas caben en el espacio restante de la página actual
      let fitCount = 0;
      let usedHeight = 0;

      for (let i = 0; i < remainingLines.length; i++) {
        const lineH = getLineHeight(remainingLines[i]);
        if (usedHeight + lineH <= availableSpace) {
          usedHeight += lineH;
          fitCount++;
        } else {
          break;
        }
      }

      // Si no cabe ninguna línea y la página ya tiene contenido, cerramos la página y abrimos una nueva
      if (fitCount === 0) {
        if (currentPageStanzas.length > 0) {
          flushPage();
          continue;
        } else {
          // Si la página ya está vacía pero ni 1 sola línea cabe (por tamaño extremo), forzamos al menos 1 línea
          fitCount = 1;
          usedHeight = getLineHeight(remainingLines[0]);
        }
      }

      const chunkLines = remainingLines.slice(0, fitCount);
      remainingLines = remainingLines.slice(fitCount);

      if (currentPageStanzas.length > 0) {
        currentY += stanzaGap;
      }

      currentPageStanzas.push({
        stanzaIndex: stanza.stanzaIndex,
        lines: chunkLines,
        isContinuation
      });

      currentY += usedHeight;
      isContinuation = true;

      // Si aún quedan líneas por ubicar, cerramos esta página para continuar en la siguiente
      if (remainingLines.length > 0) {
        flushPage();
      }
    }
  }

  // Guardar última página si contiene estrofas o si el documento quedó vacío
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

      drawMusicalNoteIcon(doc, pageWidth - margin - 5, headerY - 3.5);

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
          let spaceWidth = doc.getTextWidth(' ');

          // Medir el ancho total considerando texto y espacios de palabras
          let totalLineWidth = 0;
          line.syllables.forEach((syl, sIdx) => {
            const sylText = `${syl.leadingPunct || ''}${syl.text}${syl.trailingPunct || ''}`;
            totalLineWidth += doc.getTextWidth(sylText);
            if (syl.isWordEnd && sIdx < line.syllables.length - 1) {
              totalLineWidth += spaceWidth;
            }
          });

          // Verificar si excede el ancho imprimible útil (contentWidth)
          let effectiveFontSize = config.fontSize;
          if (totalLineWidth > contentWidth && totalLineWidth > 0) {
            const scale = contentWidth / totalLineWidth;
            effectiveFontSize = Math.max(7.5, config.fontSize * scale);
            doc.setFontSize(effectiveFontSize);
            spaceWidth = doc.getTextWidth(' ');

            totalLineWidth = 0;
            line.syllables.forEach((syl, sIdx) => {
              const sylText = `${syl.leadingPunct || ''}${syl.text}${syl.trailingPunct || ''}`;
              totalLineWidth += doc.getTextWidth(sylText);
              if (syl.isWordEnd && sIdx < line.syllables.length - 1) {
                totalLineWidth += spaceWidth;
              }
            });
          }

          const chordFontSize = Math.max(7.0, effectiveFontSize * 0.72);

          // Verificar si el acorde de la primera sílaba sobresale a la izquierda
          let leftOverhang = 0;
          const firstSyl = line.syllables[0];
          if (firstSyl?.chord) {
            doc.setFont(fontName, 'bold');
            doc.setFontSize(chordFontSize);
            const firstChordWidth = doc.getTextWidth(firstSyl.chord);
            doc.setFont(fontName, 'normal');
            doc.setFontSize(effectiveFontSize);
            const firstSylText = `${firstSyl.leadingPunct || ''}${firstSyl.text}${firstSyl.trailingPunct || ''}`;
            const firstSylWidth = doc.getTextWidth(firstSylText);
            if (firstChordWidth > firstSylWidth) {
              leftOverhang = (firstChordWidth - firstSylWidth) / 2;
            }
          }

          let currentX = Math.max(margin + leftOverhang, (pageWidth - totalLineWidth) / 2);
          if (currentX + totalLineWidth > pageWidth - margin) {
            currentX = Math.max(margin + leftOverhang, pageWidth - margin - totalLineWidth);
          }

          line.syllables.forEach((syl, sIdx) => {
            const sylText = `${syl.leadingPunct || ''}${syl.text}${syl.trailingPunct || ''}`;
            doc.setFont(fontName, 'normal');
            doc.setFontSize(effectiveFontSize);
            const sylWidth = doc.getTextWidth(sylText);

            // Si la sílaba tiene acorde, dibujarlo arriba centrado en dorado
            if (syl.chord) {
              doc.setFont(fontName, 'bold');
              doc.setFontSize(chordFontSize);
              doc.setTextColor(165, 120, 20); // GoldenMain imprimible
              doc.text(syl.chord, currentX + sylWidth / 2, currentY - 3.8, { align: 'center' });
            }

            // Dibujar texto de la sílaba
            doc.setFont(fontName, 'normal');
            doc.setFontSize(effectiveFontSize);
            doc.setTextColor(45, 52, 60);
            doc.text(sylText, currentX, currentY);

            currentX += sylWidth;

            // Si es fin de palabra, añadir espacio
            if (syl.isWordEnd && sIdx < line.syllables.length - 1) {
              currentX += spaceWidth;
            }
          });

          currentY += baseLineHeight;
        } else {
          // Línea regular sin acordes: texto centrado nítido, ajustando escala si excede contentWidth
          doc.setFont(fontName, 'normal');
          doc.setFontSize(config.fontSize);
          const textW = doc.getTextWidth(line.text);
          if (textW > contentWidth && textW > 0) {
            const scale = contentWidth / textW;
            doc.setFontSize(Math.max(7.5, config.fontSize * scale));
          }
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
