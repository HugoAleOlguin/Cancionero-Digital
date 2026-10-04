import React, { useState } from 'react';
import { UkuleleChord } from '../data/ukuleleChords';
import { ChordDiagram } from './ChordDiagram';
import { playAcousticChord } from '../util/chordAudio';
import { Volume2, Pin, Maximize2, X, Music, Sparkles } from 'lucide-react';

interface ChordDetailInspectorProps {
  chord: UkuleleChord;
  instrument: 'ukulele' | 'guitar';
  isModal?: boolean;
  onClose: () => void;
  onSelectChord?: (symbol: string) => void;
  onToggleDock?: () => void;
}

export const ChordDetailInspector: React.FC<ChordDetailInspectorProps> = ({
  chord,
  instrument,
  isModal = false,
  onClose,
  onSelectChord,
  onToggleDock
}) => {
  const [isPlaying, setIsPlaying] = useState(false);

  const handlePlaySound = async () => {
    if (isPlaying) return;
    setIsPlaying(true);
    try {
      await playAcousticChord(chord, instrument);
    } catch {
      // Ignorar si el navegador bloquea audio
    } finally {
      setTimeout(() => setIsPlaying(false), 900);
    }
  };

  const isGuitar = instrument === 'guitar' || chord.frets.length === 6;
  const stringNames = isGuitar
    ? ['6ª (E)', '5ª (A)', '4ª (D)', '3ª (G)', '2ª (B)', '1ª (E)']
    : ['4ª (G)', '3ª (C)', '2ª (E)', '1ª (A)'];

  const content = (
    <div className="flex flex-col space-y-4">
      {/* Cabecera del Inspector */}
      <div className="flex items-start justify-between pb-3 border-b border-parchment-border dark:border-jetcarbon-border">
        <div>
          <div className="flex items-baseline gap-2">
            <h2 className="text-xl font-bold text-jetcarbon dark:text-gray-100">
              {chord.name}
            </h2>
            <span className="text-base font-semibold text-golden">
              {chord.symbol}
            </span>
            <span className="text-[11px] font-bold uppercase tracking-wider px-2 py-0.5 rounded-full bg-golden/10 text-golden-dark dark:text-golden border border-golden/20">
              {chord.typeName}
            </span>
          </div>
          <p className="text-xs text-jetcarbon-muted dark:text-gray-400 mt-0.5">
            {isGuitar ? 'Guitarra (6 cuerdas)' : 'Ukelele (4 cuerdas)'} • {chord.difficulty || 'Fácil'}
          </p>
        </div>

        <div className="flex items-center gap-1">
          {onToggleDock && (
            <button
              onClick={onToggleDock}
              title={isModal ? 'Fijar en barra lateral' : 'Expandir a pantalla completa'}
              className="p-1.5 rounded-lg text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400 dark:hover:text-white hover:bg-black/5 dark:hover:bg-white/10 transition-colors"
            >
              {isModal ? <Pin size={16} /> : <Maximize2 size={16} />}
            </button>
          )}
          <button
            onClick={onClose}
            title="Cerrar detalle"
            className="p-1.5 rounded-lg text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400 dark:hover:text-white hover:bg-black/5 dark:hover:bg-white/10 transition-colors"
          >
            <X size={18} />
          </button>
        </div>
      </div>

      {/* Diagrama Central Ampliado + Botón de Audio */}
      <div className="flex flex-col items-center bg-parchment/50 dark:bg-darkbg/50 p-4 rounded-2xl border border-parchment-border dark:border-jetcarbon-border">
        <ChordDiagram
          chord={chord}
          size="lg"
          showTitle={false}
          className="border-0 shadow-none bg-transparent dark:bg-transparent"
        />

        <button
          onClick={handlePlaySound}
          disabled={isPlaying}
          className={`mt-3 flex items-center justify-center gap-2 py-2 px-4 rounded-xl text-xs font-bold transition-all shadow-xs ${
            isPlaying
              ? 'bg-golden-dark text-white ring-2 ring-golden scale-95'
              : 'bg-golden hover:bg-golden-dark text-white hover:shadow-sm active:scale-98'
          }`}
        >
          <Volume2 size={16} className={isPlaying ? 'animate-bounce' : ''} />
          <span>{isPlaying ? 'Sonando acorde...' : 'Escuchar Acorde'}</span>
        </button>
      </div>

      {/* Notas Musicales que lo componen */}
      {chord.notes && chord.notes.length > 0 && (
        <div className="space-y-1.5">
          <span className="text-[11px] font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 flex items-center gap-1.5">
            <Music size={12} className="text-golden" />
            <span>Notas que lo componen</span>
          </span>
          <div className="flex flex-wrap gap-1.5">
            {chord.notes.map((note, idx) => (
              <span
                key={`note-${idx}`}
                className="px-2.5 py-1 text-xs font-bold rounded-lg bg-golden/10 text-golden-dark dark:text-golden border border-golden/25"
              >
                {note}
              </span>
            ))}
          </div>
        </div>
      )}

      {/* Guía de Colocación de Dedos */}
      {chord.description && (
        <div className="space-y-1.5 bg-white dark:bg-darkcard p-3 rounded-xl border border-parchment-border dark:border-jetcarbon-border">
          <span className="text-[11px] font-bold uppercase tracking-wider text-golden block">
            Paso a paso para tus dedos
          </span>
          <p className="text-xs text-jetcarbon dark:text-gray-200 leading-relaxed font-serif">
            {chord.description}
          </p>
        </div>
      )}

      {/* Detalle cuerda por cuerda */}
      <div className="space-y-1.5">
        <span className="text-[11px] font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 block">
          Posición exacta por cuerda
        </span>
        <div className="grid grid-cols-2 gap-1.5 text-[11px]">
          {chord.frets.map((fret, idx) => {
            const finger = chord.fingers ? chord.fingers[idx] : 0;
            const noteOnString = chord.stringNotes ? chord.stringNotes[idx] : undefined;
            return (
              <div
                key={`fret-info-${idx}`}
                className="flex items-center justify-between px-2.5 py-1.5 rounded-lg bg-parchment/60 dark:bg-darkbg/70 border border-parchment-border/60 dark:border-jetcarbon-border/60"
              >
                <span className="font-semibold text-jetcarbon dark:text-gray-300">
                  {stringNames[idx]}
                </span>
                <span className="font-mono text-golden-dark dark:text-golden font-bold">
                  {fret === -1
                    ? 'Muda (×)'
                    : fret === 0
                    ? 'Al aire (0)'
                    : `Traste ${fret}${finger > 0 ? ` (D${finger})` : ''}`}
                  {noteOnString && noteOnString !== 'X' ? ` • ${noteOnString}` : ''}
                </span>
              </div>
            );
          })}
        </div>
      </div>

      {/* Acordes complementarios habituales / Resoluciones */}
      {chord.resolvesTo && chord.resolvesTo.length > 0 && onSelectChord && (
        <div className="space-y-1.5 pt-1">
          <span className="text-[11px] font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 flex items-center gap-1.5">
            <Sparkles size={12} className="text-golden" />
            <span>Acordes que combinan bien</span>
          </span>
          <div className="flex flex-wrap gap-1.5">
            {chord.resolvesTo.map((target, idx) => (
              <button
                key={`target-${idx}`}
                onClick={() => onSelectChord(target)}
                className="px-2.5 py-1 text-xs font-bold rounded-lg bg-parchment dark:bg-darkbg text-jetcarbon dark:text-gray-200 border border-parchment-border dark:border-jetcarbon-border hover:border-golden hover:text-golden-dark dark:hover:text-golden transition-all"
              >
                {target}
              </button>
            ))}
          </div>
        </div>
      )}
    </div>
  );

  if (isModal) {
    return (
      <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/50 backdrop-blur-xs animate-in fade-in duration-150">
        <div
          className="bg-white dark:bg-darkcard border border-parchment-border dark:border-jetcarbon-border rounded-2xl max-w-md w-full p-5 shadow-2xl max-h-[90vh] overflow-y-auto"
          role="dialog"
          aria-modal="true"
        >
          {content}
        </div>
      </div>
    );
  }

  // Vista incrustada o fijada en el panel lateral
  return (
    <div className="bg-white dark:bg-darkcard border-2 border-golden/40 rounded-2xl p-4 shadow-sm animate-in fade-in duration-200">
      {content}
    </div>
  );
};
