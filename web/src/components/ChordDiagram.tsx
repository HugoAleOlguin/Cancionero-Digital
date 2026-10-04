import React from 'react';
import { UkuleleChord } from '../data/ukuleleChords';

export interface ChordDiagramProps {
  chord: UkuleleChord; // Estructura compartida (4 o 6 cuerdas)
  size?: 'sm' | 'md' | 'lg';
  showTitle?: boolean;
  className?: string;
  onClick?: () => void;
  isSelected?: boolean;
  interactive?: boolean;
}

/**
 * Calcula con precisión el tramo contiguo de cuerdas que abarca la cejilla (barre).
 * Regla física estricta: Una cejilla NUNCA puede cruzar cuerdas al aire (0) ni silenciadas (-1).
 * Por ejemplo, en Fsus4 [3, 0, 1, 1], la cejilla solo cubre las cuerdas 2 y 1 (índices 2 y 3).
 */
export function getBarreSpan(chord: UkuleleChord): { startIdx: number; endIdx: number } | null {
  const targetFret = chord.barre;
  if (!targetFret || targetFret <= 0) return null;

  let bestSpan: { startIdx: number; endIdx: number } | null = null;
  let maxMatched = 0;
  let currentStart = -1;

  for (let i = 0; i <= chord.frets.length; i++) {
    const fret = i < chord.frets.length ? chord.frets[i] : -1;
    // Un traste solo puede pertenecer al segmento si es >= targetFret y mayor a 0
    const isValid = fret >= targetFret && fret > 0;

    if (isValid) {
      if (currentStart === -1) currentStart = i;
    } else {
      if (currentStart !== -1) {
        const segStart = currentStart;
        const segEnd = i - 1;

        // Buscar las cuerdas dentro del segmento contiguo que tengan exactamente targetFret
        const matchingIndices: number[] = [];
        for (let j = segStart; j <= segEnd; j++) {
          if (chord.frets[j] === targetFret) {
            matchingIndices.push(j);
          }
        }

        if (matchingIndices.length >= 2) {
          const first = matchingIndices[0];
          const last = matchingIndices[matchingIndices.length - 1];
          const count = last - first + 1;
          if (count > maxMatched) {
            maxMatched = count;
            bestSpan = { startIdx: first, endIdx: last };
          }
        }
        currentStart = -1;
      }
    }
  }

  return bestSpan;
}

export const ChordDiagram: React.FC<ChordDiagramProps> = ({
  chord,
  size = 'md',
  showTitle = true,
  className = '',
  onClick,
  isSelected = false,
  interactive = false
}) => {
  const stringCount = chord.frets.length; // 4 para Ukelele, 6 para Guitarra
  const fretCount = 4; // 4 trastes visibles
  const isGuitar = stringCount === 6;

  // Dimensiones según tamaño e instrumento
  const baseWidth = isGuitar ? 116 : 100;
  const width = size === 'sm' ? (isGuitar ? 96 : 84) : size === 'lg' ? (isGuitar ? 144 : 124) : baseWidth;
  const height = size === 'sm' ? 108 : size === 'lg' ? 154 : 128;

  const marginX = size === 'sm' ? 14 : 18;
  const marginTop = size === 'sm' ? 24 : 28;
  const marginBottom = size === 'sm' ? 14 : 16;

  const fretboardWidth = width - marginX * 2;
  const fretboardHeight = height - marginTop - marginBottom;

  const stringSpacing = fretboardWidth / (stringCount - 1);
  const fretSpacing = fretboardHeight / fretCount;

  const baseFret = chord.baseFret || 1;
  const isFirstPosition = baseFret === 1;

  const stringNames = isGuitar
    ? ['E', 'A', 'D', 'G', 'B', 'E']
    : ['G', 'C', 'E', 'A'];

  // Grosor de cuerdas según instrumento
  const getStringStroke = (idx: number) => {
    if (isGuitar) {
      return [2.2, 1.9, 1.6, 1.3, 1.1, 0.9][idx] || 1.2;
    }
    return idx === 1 ? 1.8 : idx === 0 ? 1.4 : 1.1;
  };

  const dotRadius = isGuitar ? (size === 'sm' ? 4.5 : size === 'lg' ? 6.5 : 5.5) : (size === 'sm' ? 5 : size === 'lg' ? 7 : 6);
  const barreSpan = getBarreSpan(chord);

  const isClickable = interactive || !!onClick;

  return (
    <div
      onClick={onClick}
      role={isClickable ? 'button' : undefined}
      tabIndex={isClickable ? 0 : undefined}
      onKeyDown={e => {
        if (isClickable && (e.key === 'Enter' || e.key === ' ')) {
          e.preventDefault();
          onClick?.();
        }
      }}
      className={`flex flex-col items-center bg-white dark:bg-darkcard border rounded-xl p-2.5 transition-all select-none ${
        isSelected
          ? 'border-golden ring-2 ring-golden/40 bg-golden/5 dark:bg-golden/10 shadow-sm'
          : isClickable
          ? 'border-parchment-border dark:border-jetcarbon-border hover:border-golden/60 hover:shadow-xs cursor-pointer'
          : 'border-parchment-border dark:border-jetcarbon-border shadow-2xs'
      } ${className}`}
    >
      {showTitle && (
        <div className="text-center mb-1 w-full">
          <div className="flex items-baseline justify-center gap-1">
            <span className="font-bold text-sm sm:text-base text-jetcarbon dark:text-gray-100">
              {chord.name}
            </span>
            <span className="text-xs font-semibold text-golden">
              ({chord.symbol})
            </span>
          </div>
          <span className="text-[10px] uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 block -mt-0.5">
            {chord.typeName}
          </span>
        </div>
      )}

      <svg width={width} height={height} viewBox={`0 0 ${width} ${height}`} className="overflow-visible">
        {/* Indicador de traste si no es la primera posición (ej. 3fr) */}
        {!isFirstPosition && (
          <text
            x={marginX - 3}
            y={marginTop + fretSpacing * 0.7}
            fontSize="9"
            fontWeight="bold"
            fill="#C5A03A"
            textAnchor="end"
          >
            {baseFret}fr
          </text>
        )}

        {/* Cejuela (Nut) en traste 1, o línea normal en traste alto */}
        {isFirstPosition ? (
          <rect
            x={marginX - 1}
            y={marginTop - 3}
            width={fretboardWidth + 2}
            height={3}
            fill="#C5A03A"
            rx="1"
          />
        ) : (
          <line
            x1={marginX}
            y1={marginTop}
            x2={marginX + fretboardWidth}
            y2={marginTop}
            stroke="#9E7E24"
            strokeWidth="1.5"
          />
        )}

        {/* Líneas horizontales de trastes */}
        {Array.from({ length: fretCount + 1 }).map((_, idx) => (
          <line
            key={`fret-${idx}`}
            x1={marginX}
            y1={marginTop + idx * fretSpacing}
            x2={marginX + fretboardWidth}
            y2={marginTop + idx * fretSpacing}
            stroke="#D0C9BA"
            className="dark:stroke-jetcarbon-border"
            strokeWidth="1"
          />
        ))}

        {/* Cuerdas verticales */}
        {Array.from({ length: stringCount }).map((_, idx) => (
          <line
            key={`string-${idx}`}
            x1={marginX + idx * stringSpacing}
            y1={marginTop}
            x2={marginX + idx * stringSpacing}
            y2={marginTop + fretboardHeight}
            stroke="#A39B8B"
            className="dark:stroke-gray-600"
            strokeWidth={getStringStroke(idx)}
          />
        ))}

        {/* Nombres de las cuerdas en la parte inferior */}
        {stringNames.map((sName, idx) => (
          <text
            key={`sname-${idx}`}
            x={marginX + idx * stringSpacing}
            y={marginTop + fretboardHeight + 11}
            fontSize="8"
            fill="#7A8699"
            textAnchor="middle"
            fontWeight="600"
          >
            {sName}
          </text>
        ))}

        {/* Indicadores de cuerda al aire (O) o silenciada (X) */}
        {chord.frets.map((fret, sIdx) => {
          const cx = marginX + sIdx * stringSpacing;
          const cy = marginTop - 9;

          if (fret === 0) {
            // Cuerda al aire
            return (
              <circle
                key={`open-${sIdx}`}
                cx={cx}
                cy={cy}
                r="3"
                fill="none"
                stroke="#C5A03A"
                strokeWidth="1.2"
              />
            );
          } else if (fret === -1) {
            // Cuerda silenciada (X)
            return (
              <text
                key={`mute-${sIdx}`}
                x={cx}
                y={cy + 3}
                fontSize="9"
                fontWeight="bold"
                fill="#94A3B8"
                textAnchor="middle"
              >
                ×
              </text>
            );
          }
          return null;
        })}

        {/* Cejilla (Barre) contigua y precisa: Solo conecta el rango de cuerdas pisadas */}
        {barreSpan && chord.barre && (
          (() => {
            const relativeFret = chord.barre - (baseFret - 1);
            if (relativeFret >= 1 && relativeFret <= fretCount) {
              const xStart = marginX + barreSpan.startIdx * stringSpacing;
              const xEnd = marginX + barreSpan.endIdx * stringSpacing;
              const cy = marginTop + (relativeFret - 0.5) * fretSpacing;
              const barHeight = dotRadius * 2;
              const barWidth = (xEnd - xStart) + dotRadius * 2;
              const barX = xStart - dotRadius;
              const barY = cy - barHeight / 2;

              return (
                <rect
                  x={barX}
                  y={barY}
                  width={barWidth}
                  height={barHeight}
                  rx={barHeight / 2}
                  fill="#C5A03A"
                  opacity="0.95"
                />
              );
            }
            return null;
          })()
        )}

        {/* Puntos dorados de digitación con número de dedo */}
        {chord.frets.map((fret, sIdx) => {
          if (fret <= 0) return null;
          const relativeFret = fret - (baseFret - 1);
          if (relativeFret < 1 || relativeFret > fretCount) return null;

          const cx = marginX + sIdx * stringSpacing;
          const cy = marginTop + (relativeFret - 0.5) * fretSpacing;
          const finger = chord.fingers ? chord.fingers[sIdx] : 0;

          return (
            <g key={`dot-${sIdx}-${fret}`}>
              <circle
                cx={cx}
                cy={cy}
                r={dotRadius}
                fill="#C5A03A"
                stroke="#FFFFFF"
                className="dark:stroke-darkcard"
                strokeWidth="1.2"
              />
              {finger > 0 && (
                <text
                  x={cx}
                  y={cy + (size === 'lg' ? 3 : 2.5)}
                  fontSize={size === 'sm' ? '7' : size === 'lg' ? '9' : '8'}
                  fontWeight="bold"
                  fill="#FFFFFF"
                  textAnchor="middle"
                >
                  {finger}
                </text>
              )}
            </g>
          );
        })}
      </svg>
    </div>
  );
};
