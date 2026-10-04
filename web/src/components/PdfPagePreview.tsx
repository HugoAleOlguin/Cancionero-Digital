import React from 'react';
import { PdfPageLayout, PdfStudioConfig } from '../utils/pdfGenerator';

interface PdfPagePreviewProps {
  page: PdfPageLayout;
  config: PdfStudioConfig;
}

// Divisor ornamental idéntico al PDF real (líneas, puntos y rombo central dorado)
const OrnamentalDivider: React.FC = () => (
  <div className="flex items-center justify-center gap-2 my-2 w-full text-parchment-border dark:text-jetcarbon-border">
    <div className="flex-1 h-[0.5px] bg-neutral-300" />
    <span className="w-1 h-1 rounded-full bg-golden" />
    <span className="w-1.5 h-1.5 bg-golden rotate-45" />
    <span className="w-1 h-1 rounded-full bg-golden" />
    <div className="flex-1 h-[0.5px] bg-neutral-300" />
  </div>
);

export const PdfPagePreview: React.FC<PdfPagePreviewProps> = ({ page, config }) => {
  const fontClass = {
    serif: 'font-serif',
    sans: 'font-sans',
    mono: 'font-mono'
  }[config.typography];

  return (
    <div className="relative w-full max-w-[560px] mx-auto mb-8 transition-all">
      {/* Etiqueta superior de número de hoja */}
      <div className="flex items-center justify-between text-[11px] font-semibold text-jetcarbon-muted dark:text-gray-400 mb-1.5 px-1">
        <span>Hoja {page.pageNumber} de {page.totalPages}</span>
        <span className="text-[10px] uppercase tracking-wider text-golden font-bold">Formato A4</span>
      </div>

      {/* Hoja A4 con proporción exacta (210mm x 297mm = 1 : 1.414) */}
      <div
        className={`w-full aspect-[210/297] bg-white text-[#2D343C] rounded-sm shadow-xl border border-neutral-200/80 p-[8%] flex flex-col justify-between select-text overflow-hidden ${fontClass}`}
        style={{ fontSize: `${config.fontSize * 0.9}px` }}
      >
        {/* Parte Superior: Encabezado */}
        <div>
          {page.hasHeader && (
            <div className="mb-4">
              <div className="flex items-center justify-between text-[10px] tracking-widest text-neutral-500 font-sans uppercase">
                <span>Cancionero Cristiano</span>
                <span className="text-golden font-bold text-xs">♪</span>
              </div>
              <OrnamentalDivider />
            </div>
          )}

          {/* Título y Autor en Página 1 */}
          {page.pageNumber === 1 && (
            <div className="text-center mb-5">
              <h1 className="text-lg sm:text-xl font-bold tracking-tight text-neutral-900 leading-snug">
                {page.title}
              </h1>
              {page.author && (
                <p className="text-xs italic text-neutral-600 mt-1">
                  {page.author}
                </p>
              )}
            </div>
          )}

          {/* Estrofas de la página actual */}
          <div className="space-y-4">
            {page.stanzas.map((stanza, sIdx) => (
              <div key={`page-${page.pageNumber}-s-${sIdx}`} className="text-center space-y-1">
                {stanza.lines.map((line, lIdx) => {
                  if (!line.text) {
                    return <div key={`empty-${lIdx}`} className="h-2" />;
                  }

                  if (line.hasChords) {
                    return (
                      <div
                        key={`line-${lIdx}`}
                        className="inline-flex flex-wrap justify-center items-end leading-none relative pt-4"
                      >
                        {line.syllables.map((syl, sylIdx) => (
                          <span
                            key={`syl-${sylIdx}-${syl.id}`}
                            className="inline-block relative px-[1px] leading-tight"
                          >
                            {/* Acorde flotante arriba */}
                            {syl.chord && (
                              <span className="absolute -top-3.5 left-1/2 -translate-x-1/2 text-[10px] font-bold text-golden-dark dark:text-golden font-sans whitespace-nowrap">
                                {syl.chord}
                              </span>
                            )}
                            <span className="text-neutral-800">
                              {syl.leadingPunct}
                              {syl.text}
                              {syl.trailingPunct}
                            </span>
                          </span>
                        ))}
                      </div>
                    );
                  }

                  return (
                    <div
                      key={`line-${lIdx}`}
                      className="leading-snug text-neutral-800 whitespace-pre-wrap"
                    >
                      {line.text}
                    </div>
                  );
                })}
              </div>
            ))}
          </div>
        </div>

        {/* Parte Inferior: Pie de página */}
        {page.hasFooter && (
          <div className="mt-4 pt-2">
            <OrnamentalDivider />
            <div className="flex items-center justify-between text-[10px] text-neutral-500 font-sans tracking-wide">
              <span>Cancionero Cristiano</span>
              <span>{page.totalPages > 1 ? `${page.pageNumber} / ${page.totalPages}` : '1'}</span>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};
