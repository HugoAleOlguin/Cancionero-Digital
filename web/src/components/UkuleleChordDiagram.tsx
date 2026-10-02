import React from 'react';
import { UkuleleChord } from '../data/ukuleleChords';

interface UkuleleChordDiagramProps {
  chord: UkuleleChord;
  size?: 'sm' | 'md' | 'lg';
  showTitle?: boolean;
  className?: string;
}

export const UkuleleChordDiagram: React.FC<UkuleleChordDiagramProps> = ({
  chord,
  size = 'md',
  showTitle = true,
  className = ''
}) => {
  // Dimensiones del mástil
  const stringCount = 4; // G, C, E, A
  const fretCount = 4;   // 4 trastes visibles
  
  const width = size === 'sm' ? 84 : size === 'lg' ? 120 : 100;
  const height = size === 'sm' ? 104 : size === 'lg' ? 150 : 124;

  const marginX = size === 'sm' ? 16 : 18;
  const marginTop = size === 'sm' ? 24 : 28;
  const marginBottom = size === 'sm' ? 14 : 16;

  const fretboardWidth = width - marginX * 2;
  const fretboardHeight = height - marginTop - marginBottom;

  const stringSpacing = fretboardWidth / (stringCount - 1);
  const fretSpacing = fretboardHeight / fretCount;

  const baseFret = chord.baseFret || 1;
  const isFirstPosition = baseFret === 1;

  const stringNames = ['G', 'C', 'E', 'A'];

  return (
    <div className={`flex flex-col items-center bg-white dark:bg-darkcard border border-parchment-border dark:border-jetcarbon-border rounded-xl p-2.5 shadow-2xs hover:shadow-xs transition-all ${className}`}>
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

      <svg width={width} height={height} viewBox={`0 0 ${width} ${height}`} className="overflow-visible select-none">
        {/* Indicador de traste si no es la primera posición (ej. 3fr) */}
        {!isFirstPosition && (
          <text
            x={marginX - 4}
            y={marginTop + fretSpacing * 0.7}
            fontSize="9"
            fontWeight="bold"
            fill="#C5A03A"
            textAnchor="end"
          >
            {baseFret}fr
          </text>
        )}

        {/* Cejuela (Nut) gruesa en la posición 1, o línea normal si es traste alto */}
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

        {/* Cuerdas verticales (4 cuerdas: G, C, E, A) */}
        {Array.from({ length: stringCount }).map((_, idx) => (
          <line
            key={`string-${idx}`}
            x1={marginX + idx * stringSpacing}
            y1={marginTop}
            x2={marginX + idx * stringSpacing}
            y2={marginTop + fretboardHeight}
            stroke="#A39B8B"
            className="dark:stroke-gray-600"
            strokeWidth={idx === 1 ? '1.8' : idx === 0 ? '1.4' : '1.1'} // cuerda C más gruesa
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

        {/* Indicadores de cuerda al aire (O) o silenciada (X) sobre la cejuela */}
        {chord.frets.map((fret, sIdx) => {
          const cx = marginX + sIdx * stringSpacing;
          const cy = marginTop - 9;

          if (fret === 0) {
            // Cuerda al aire: círculo sin relleno
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
            // Cuerda silenciada: X
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

        {/* Cejilla completa (Barre) si aplica */}
        {chord.barre && (
          <rect
            x={marginX}
            y={marginTop + (chord.barre - (baseFret - 1) - 0.75) * fretSpacing}
            width={fretboardWidth}
            height={fretSpacing * 0.5}
            rx={fretSpacing * 0.25}
            fill="#C5A03A"
            opacity="0.9"
          />
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
              {/* Punto circular en oro cálido */}
              <circle
                cx={cx}
                cy={cy}
                r={size === 'sm' ? 5 : 6}
                fill="#C5A03A"
                stroke="#FFFFFF"
                className="dark:stroke-darkcard"
                strokeWidth="1.2"
              />
              {/* Número de dedo (1=índice, 2=medio, 3=anular, 4=meñique) */}
              {finger > 0 && (
                <text
                  x={cx}
                  y={cy + 2.5}
                  fontSize={size === 'sm' ? '7' : '8'}
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
