import { jsPDF } from 'jspdf';
import { Hymn } from '../types/hymn';

/**
 * Dibuja un divisor estilizado con remate ornamental en el centro:
 * ─────── • ◆ • ───────
 */
function drawShapedDivider(doc: jsPDF, y: number, pageWidth: number, margin: number) {
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
 * Genera y descarga un documento PDF de alta calidad editorial del canto seleccionado.
 * Cumple estrictamente con:
 * - Todo centrado armónicamente (título, autor y estrofas).
 * - Título como elemento primordial: "XX. Título".
 * - Divisores con formas ornamentales elegantes en header y footer.
 * - Cero enlaces, cero URLs y cero cruces.
 * - Ajuste inteligente de escala para encajar preferentemente en 1 página A4.
 */
export function buildHymnPdfDocument(hymn: Hymn, versionIndex: number = 0): jsPDF {
  const doc = new jsPDF({
    orientation: 'portrait',
    unit: 'mm',
    format: 'a4'
  });

  const pageWidth = 210;
  const margin = 24;
  const contentWidth = pageWidth - margin * 2;
  const headerY = 22;
  const footerY = 278;

  // Seleccionar contenido activo (original o versión alterna)
  const versions = [hymn.content, ...(hymn.extraVersions || [])];
  const activeContent = versions[versionIndex] || hymn.content;
  const stanzas = activeContent
    .split(/\n\s*\n/)
    .map(s => s.trim())
    .filter(s => s.length > 0);

  // 1. Algoritmo de cálculo de altura y tamaño de fuente
  // Probamos fuentes desde 13pt hasta 9.5pt para que encaje en 1 sola hoja si es posible
  const availableHeight = footerY - headerY - 45; // Espacio libre para título + letra

  let selectedFontSize = 13;
  let lineHeightMm = selectedFontSize * 0.48;
  let stanzaGapMm = selectedFontSize * 0.55;

  for (let testSize = 13; testSize >= 9.5; testSize -= 0.5) {
    const testLineHeight = testSize * 0.48;
    const testStanzaGap = testSize * 0.55;
    
    // Contar líneas totales
    let totalLinesCount = 0;
    stanzas.forEach(st => {
      const lines = st.split('\n');
      totalLinesCount += lines.length;
    });

    const calculatedHeight = (totalLinesCount * testLineHeight) + ((stanzas.length - 1) * testStanzaGap) + 30;
    if (calculatedHeight <= availableHeight) {
      selectedFontSize = testSize;
      lineHeightMm = testLineHeight;
      stanzaGapMm = testStanzaGap;
      break;
    }
  }

  // Helper para dibujar Header y Footer
  const renderHeaderFooter = (pageNumber: number, totalPages: number) => {
    // Encabezado
    doc.setFont('helvetica', 'normal');
    doc.setFontSize(8.5);
    doc.setTextColor(110, 115, 125);
    doc.text('CANCIONERO CRISTIANO', margin, headerY - 5);

    // Nota musical sutil a la derecha
    doc.setFont('times', 'normal');
    doc.setFontSize(11);
    doc.setTextColor(197, 160, 58);
    doc.text('♪', pageWidth - margin - 2, headerY - 5);

    // Divisor ornamental superior
    drawShapedDivider(doc, headerY, pageWidth, margin);

    // Divisor ornamental inferior
    drawShapedDivider(doc, footerY, pageWidth, margin);

    // Pie de página (Nombre a la izquierda, página a la derecha)
    doc.setFont('helvetica', 'normal');
    doc.setFontSize(8.5);
    doc.setTextColor(130, 135, 145);
    doc.text('Cancionero Cristiano', margin, footerY + 8);
    doc.text(
      totalPages > 1 ? `${pageNumber} / ${totalPages}` : '1',
      pageWidth - margin,
      footerY + 8,
      { align: 'right' }
    );
  };

  // Dibujar encabezado en página 1
  renderHeaderFooter(1, 1);

  // 2. Título Protagonista Centrado ("XX. Título de la Alabanza")
  let currentY = headerY + 20;

  const displayTitle = `${hymn.id}. ${hymn.title}`;
  doc.setFont('times', 'bold');
  doc.setFontSize(18);
  doc.setTextColor(30, 36, 43); // JetCarbon elegante
  
  // Dividir título si es muy largo
  const titleLines = doc.splitTextToSize(displayTitle, contentWidth - 10);
  titleLines.forEach((tLine: string) => {
    doc.text(tLine, pageWidth / 2, currentY, { align: 'center' });
    currentY += 8;
  });

  // Autor Centrado (si existe)
  if (hymn.author && hymn.author.trim()) {
    currentY += 1;
    doc.setFont('times', 'italic');
    doc.setFontSize(9.5);
    doc.setTextColor(100, 110, 120);
    doc.text(hymn.author.trim(), pageWidth / 2, currentY, { align: 'center' });
    currentY += 7;
  } else {
    currentY += 5;
  }

  // 3. Estrofas Centradas
  doc.setFont('times', 'normal');
  doc.setFontSize(selectedFontSize);
  doc.setTextColor(45, 52, 60);

  let currentPage = 1;

  stanzas.forEach((stanza, sIdx) => {
    const lines = stanza.split('\n');
    const stanzaHeight = lines.length * lineHeightMm;

    // Si la estrofa no cabe en la página actual, pasar a una nueva
    if (currentY + stanzaHeight > footerY - 5) {
      doc.addPage();
      currentPage++;
      currentY = headerY + 16;
      renderHeaderFooter(currentPage, currentPage);
    }

    lines.forEach(line => {
      const cleanLine = line.trim();
      if (cleanLine) {
        doc.text(cleanLine, pageWidth / 2, currentY, { align: 'center' });
      }
      currentY += lineHeightMm;
    });

    if (sIdx < stanzas.length - 1) {
      currentY += stanzaGapMm;
    }
  });

  // Si hubo múltiples páginas, actualizar la numeración total
  const pageCount = (doc as any).internal.getNumberOfPages();
  if (pageCount > 1) {
    for (let p = 1; p <= pageCount; p++) {
      doc.setPage(p);
      renderHeaderFooter(p, pageCount);
    }
  }

  return doc;
}

/**
 * Construye el documento y activa la descarga en el navegador.
 */
export function generateHymnPdf(hymn: Hymn, versionIndex: number = 0) {
  const doc = buildHymnPdfDocument(hymn, versionIndex);

  const safeFilename = `${hymn.id}_${hymn.title}`
    .normalize('NFD')
    .replace(/[\u0300-\u036f]/g, '')
    .replace(/[^a-zA-Z0-9_-]/g, '_')
    .substring(0, 40);

  doc.save(`${safeFilename}.pdf`);
}
