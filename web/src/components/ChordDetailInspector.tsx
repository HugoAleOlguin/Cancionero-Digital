import React, { useState } from 'react';
import { UkuleleChord, HarmonicFamily } from '../data/ukuleleChords';
import { ChordDiagram } from './ChordDiagram';
import { playAcousticChord } from '../util/chordAudio';
import { Volume2, ArrowLeft, Music, Sparkles, BookMarked } from 'lucide-react';

interface ChordDetailInspectorProps {
  chord: UkuleleChord;
  instrument: 'ukulele' | 'guitar';
  families: Record<string, HarmonicFamily>;
  onBack: () => void;
  onSelectChord: (symbol: string) => void;
  onSelectFamily?: (keySymbol: string) => void;
}

/**
 * Encuentra a qué familias armónicas pertenece un acorde dado.
 */
export function findBelongingFamilies(
  chordSymbol: string,
  families: Record<string, HarmonicFamily>
): Array<{ keySymbol: string; keyName: string; role: string }> {
  const results: Array<{ keySymbol: string; keyName: string; role: string }> = [];

  for (const [key, fam] of Object.entries(families)) {
    if (fam.tonic.symbol === chordSymbol) {
      results.push({ keySymbol: key, keyName: fam.keyName, role: 'Tónica (I)' });
    } else if (fam.subdominant.symbol === chordSymbol) {
      results.push({ keySymbol: key, keyName: fam.keyName, role: 'Subdominante (IV)' });
    } else if (fam.dominant.symbol === chordSymbol) {
      results.push({ keySymbol: key, keyName: fam.keyName, role: 'Dominante (V)' });
    } else if (fam.dominant7.symbol === chordSymbol) {
      results.push({ keySymbol: key, keyName: fam.keyName, role: 'Séptima (V7)' });
    } else if (fam.relativeMinor.symbol === chordSymbol) {
      results.push({ keySymbol: key, keyName: fam.keyName, role: fam.isMinor ? 'Relativa Mayor' : 'Relativa Menor (vi)' });
    } else if (fam.secondary1.symbol === chordSymbol || fam.secondary2.symbol === chordSymbol) {
      results.push({ keySymbol: key, keyName: fam.keyName, role: 'Secundario' });
    }
  }

  return results;
}

export const ChordDetailInspector: React.FC<ChordDetailInspectorProps> = ({
  chord,
  instrument,
  families,
  onBack,
  onSelectChord,
  onSelectFamily
}) => {
  const [isPlaying, setIsPlaying] = useState(false);

  const handlePlay = async () => {
    if (isPlaying) return;
    setIsPlaying(true);
    try {
      await playAcousticChord(chord, instrument);
    } catch {
      // Ignorar bloqueos de audio
    } finally {
      setTimeout(() => setIsPlaying(false), 700);
    }
  };

  const belongingFamilies = findBelongingFamilies(chord.symbol, families);
  const isGuitar = instrument === 'guitar' || chord.frets.length === 6;

  return (
    <div className="flex flex-col space-y-3.5 animate-in fade-in duration-150">
      {/* Barra superior con Botón Volver prominente */}
      <div className="flex items-center justify-between pb-2 border-b border-parchment-border dark:border-jetcarbon-border">
        <button
          onClick={onBack}
          className="flex items-center gap-1.5 py-1 px-2.5 rounded-lg text-xs font-bold bg-parchment dark:bg-darkbg text-jetcarbon dark:text-gray-200 border border-parchment-border dark:border-jetcarbon-border hover:border-golden hover:text-golden-dark dark:hover:text-golden transition-all"
        >
          <ArrowLeft size={15} />
          <span>Volver a la lista</span>
        </button>

        <span className="text-[11px] font-semibold text-golden uppercase tracking-wider">
          {isGuitar ? 'Guitarra' : 'Ukelele'}
        </span>
      </div>

      {/* Tarjeta Central Compacta del Acorde */}
      <div className="flex flex-col items-center bg-parchment/60 dark:bg-darkbg/60 p-3 rounded-2xl border border-parchment-border dark:border-jetcarbon-border">
        <div className="flex items-baseline gap-2 mb-1">
          <span className="text-xl font-bold text-jetcarbon dark:text-gray-100">
            {chord.name}
          </span>
          <span className="text-base font-semibold text-golden">
            ({chord.symbol})
          </span>
          <span className="text-[10px] uppercase font-bold text-jetcarbon-muted dark:text-gray-400">
            • {chord.typeName}
          </span>
        </div>

        {/* Diagrama con tamaño adecuado sin sobredimensionar */}
        <ChordDiagram
          chord={chord}
          size="md"
          showTitle={false}
          className="border-0 shadow-none bg-transparent dark:bg-transparent p-0"
        />

        {/* Botón Escuchar directo y limpio */}
        <button
          onClick={handlePlay}
          disabled={isPlaying}
          className={`mt-2 flex items-center justify-center gap-2 py-1.5 px-4 rounded-xl text-xs font-bold transition-all shadow-2xs ${
            isPlaying
              ? 'bg-golden-dark text-white ring-2 ring-golden scale-95'
              : 'bg-golden hover:bg-golden-dark text-white hover:shadow-xs active:scale-98'
          }`}
        >
          <Volume2 size={15} className={isPlaying ? 'animate-bounce' : ''} />
          <span>{isPlaying ? 'Sonando...' : 'Escuchar Acorde'}</span>
        </button>
      </div>

      {/* Notas que lo componen (Horizontal compacto) */}
      {chord.notes && chord.notes.length > 0 && (
        <div className="bg-white dark:bg-darkcard p-2.5 rounded-xl border border-parchment-border dark:border-jetcarbon-border">
          <div className="flex items-center gap-1.5 mb-1.5 text-jetcarbon-muted dark:text-gray-400">
            <Music size={12} className="text-golden" />
            <span className="text-[10px] font-bold uppercase tracking-wider">
              Notas del acorde
            </span>
          </div>
          <div className="flex flex-wrap gap-1.5">
            {chord.notes.map((note, idx) => (
              <span
                key={`note-${idx}`}
                className="px-2 py-0.5 text-xs font-bold rounded-md bg-golden/10 text-golden-dark dark:text-golden border border-golden/20"
              >
                {note}
              </span>
            ))}
          </div>
        </div>
      )}

      {/* Familias a las que pertenece (Remarcado especial) */}
      <div className="bg-white dark:bg-darkcard p-2.5 rounded-xl border border-parchment-border dark:border-jetcarbon-border">
        <div className="flex items-center gap-1.5 mb-1.5 text-jetcarbon-muted dark:text-gray-400">
          <BookMarked size={12} className="text-golden" />
          <span className="text-[10px] font-bold uppercase tracking-wider">
            ¿En qué familias armónicas se usa?
          </span>
        </div>
        {belongingFamilies.length > 0 ? (
          <div className="flex flex-wrap gap-1.5">
            {belongingFamilies.map((fam, idx) => (
              <button
                key={`fam-${idx}`}
                onClick={() => onSelectFamily?.(fam.keySymbol)}
                title={`Ir a la familia de ${fam.keyName} (${fam.role})`}
                className="px-2 py-1 rounded-lg text-[11px] font-bold bg-golden/5 hover:bg-golden/15 text-jetcarbon dark:text-gray-200 border border-golden/30 hover:border-golden transition-all text-left flex items-center gap-1"
              >
                <span className="text-golden-dark dark:text-golden">{fam.keyName}</span>
                <span className="text-[9px] opacity-75 font-normal">({fam.role})</span>
              </button>
            ))}
          </div>
        ) : (
          <p className="text-[11px] text-jetcarbon-muted dark:text-gray-400 italic">
            Acorde cromático o de adorno armónico directo.
          </p>
        )}
      </div>

      {/* Acordes complementarios habituales */}
      {chord.resolvesTo && chord.resolvesTo.length > 0 && (
        <div className="bg-white dark:bg-darkcard p-2.5 rounded-xl border border-parchment-border dark:border-jetcarbon-border">
          <div className="flex items-center gap-1.5 mb-1.5 text-jetcarbon-muted dark:text-gray-400">
            <Sparkles size={12} className="text-golden" />
            <span className="text-[10px] font-bold uppercase tracking-wider">
              Acordes que combinan bien
            </span>
          </div>
          <div className="flex flex-wrap gap-1.5">
            {chord.resolvesTo.map((target, idx) => (
              <button
                key={`target-${idx}`}
                onClick={() => onSelectChord(target)}
                className="px-2 py-0.5 text-xs font-bold rounded-md bg-parchment dark:bg-darkbg text-jetcarbon dark:text-gray-300 border border-parchment-border dark:border-jetcarbon-border hover:border-golden hover:text-golden-dark dark:hover:text-golden transition-colors"
              >
                {target}
              </button>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};
