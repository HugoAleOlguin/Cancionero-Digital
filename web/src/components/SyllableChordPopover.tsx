import React, { useState } from 'react';
import { HarmonicFamily, UKULELE_CHORDS_DB, ALL_MAJOR_KEYS, ALL_MINOR_KEYS } from '../data/ukuleleChords';
import { X, Trash2, ChevronDown, Plus } from 'lucide-react';

interface SyllableChordPopoverProps {
  currentChord?: string;
  activeFamily: HarmonicFamily;
  onSelectChord: (chordSymbol: string) => void;
  onRemoveChord: () => void;
  onChangeFamily: (keySymbol: string) => void;
  onClose: () => void;
}

export const SyllableChordPopover: React.FC<SyllableChordPopoverProps> = ({
  currentChord,
  activeFamily,
  onSelectChord,
  onRemoveChord,
  onChangeFamily,
  onClose
}) => {
  const [showOtherChords, setShowOtherChords] = useState(false);
  const [showKeySelector, setShowKeySelector] = useState(false);

  // Acordes principales de la familia armónica base
  const familyChords = [
    activeFamily.tonic,
    activeFamily.subdominant,
    activeFamily.dominant,
    activeFamily.dominant7,
    activeFamily.relativeMinor,
    activeFamily.secondary1,
    activeFamily.secondary2,
  ].filter(Boolean);

  const allAvailableSymbols = Object.keys(UKULELE_CHORDS_DB);

  return (
    <div
      className="absolute bottom-full left-1/2 -translate-x-1/2 mb-2 z-50 w-64 bg-white dark:bg-darkcard border border-golden/50 dark:border-golden/40 rounded-xl p-2.5 shadow-xl animate-in zoom-in-95 duration-100 select-none text-left"
      role="dialog"
      onClick={e => e.stopPropagation()}
    >
      {/* Flechita apuntadora hacia la sílaba */}
      <div className="absolute -bottom-1.5 left-1/2 -translate-x-1/2 w-3 h-3 bg-white dark:bg-darkcard border-b border-r border-golden/50 dark:border-golden/40 rotate-45" />

      {/* Cabecera del popover */}
      <div className="flex items-center justify-between pb-1.5 mb-1.5 border-b border-parchment-border dark:border-jetcarbon-border text-xs">
        <button
          onClick={() => setShowKeySelector(!showKeySelector)}
          className="flex items-center gap-1 font-bold text-jetcarbon dark:text-gray-200 hover:text-golden transition-colors"
        >
          <span className="text-[10px] uppercase text-jetcarbon-muted dark:text-gray-400 font-normal">Tono:</span>
          <span className="text-golden-dark dark:text-golden">{activeFamily.keyName} ({activeFamily.keySymbol})</span>
          <ChevronDown size={13} className="text-golden" />
        </button>

        <button
          onClick={onClose}
          className="p-1 rounded-md text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400 dark:hover:text-white"
        >
          <X size={14} />
        </button>
      </div>

      {/* Selector desplegable de Tonalidad si se pulsa */}
      {showKeySelector && (
        <div className="mb-2 p-1.5 bg-parchment/60 dark:bg-darkbg/70 rounded-lg border border-parchment-border dark:border-jetcarbon-border max-h-32 overflow-y-auto">
          <span className="text-[9px] font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 block mb-1">
            Cambiar Tono Base
          </span>
          <div className="grid grid-cols-4 gap-1">
            {[...ALL_MAJOR_KEYS, ...ALL_MINOR_KEYS].map(k => (
              <button
                key={k.symbol}
                onClick={() => {
                  onChangeFamily(k.symbol);
                  setShowKeySelector(false);
                }}
                className={`py-0.5 px-1 rounded text-[10px] font-bold text-center transition-colors ${
                  activeFamily.keySymbol === k.symbol
                    ? 'bg-golden text-white'
                    : 'bg-white dark:bg-darkcard text-jetcarbon dark:text-gray-300 hover:bg-golden/10'
                }`}
              >
                {k.name}
              </button>
            ))}
          </div>
        </div>
      )}

      {/* Chips Rápidos de la Familia Armónica */}
      <div className="grid grid-cols-3 gap-1 mb-2">
        {familyChords.map((chord, idx) => {
          const isSelected = currentChord === chord.symbol;
          return (
            <button
              key={`fam-ch-${idx}-${chord.symbol}`}
              onClick={() => onSelectChord(chord.symbol)}
              className={`py-1 px-1.5 rounded-lg text-xs font-bold text-center border transition-all ${
                isSelected
                  ? 'bg-golden text-white border-golden shadow-2xs'
                  : 'bg-parchment/60 dark:bg-darkbg/60 text-jetcarbon dark:text-gray-200 border-parchment-border dark:border-jetcarbon-border hover:border-golden hover:text-golden-dark dark:hover:text-golden active:scale-95'
              }`}
            >
              <span>{chord.name}</span>
              <span className="text-[9px] opacity-75 ml-0.5 font-normal">({chord.symbol})</span>
            </button>
          );
        })}
      </div>

      {/* Selector de otro acorde personalizado */}
      {showOtherChords && (
        <div className="mb-2 p-1.5 bg-parchment/60 dark:bg-darkbg/70 rounded-lg border border-parchment-border dark:border-jetcarbon-border max-h-28 overflow-y-auto">
          <div className="grid grid-cols-4 gap-1">
            {allAvailableSymbols.map(sym => (
              <button
                key={`other-${sym}`}
                onClick={() => {
                  onSelectChord(sym);
                  setShowOtherChords(false);
                }}
                className="py-1 px-1 rounded text-[10px] font-bold text-center bg-white dark:bg-darkcard text-jetcarbon dark:text-gray-300 hover:bg-golden/20 hover:text-golden-dark transition-colors"
              >
                {sym}
              </button>
            ))}
          </div>
        </div>
      )}

      {/* Botones inferiores: + Otro / Quitar */}
      <div className="flex items-center justify-between pt-1 border-t border-parchment-border dark:border-jetcarbon-border text-[11px]">
        <button
          onClick={() => setShowOtherChords(!showOtherChords)}
          className="flex items-center gap-1 font-semibold text-golden-dark dark:text-golden hover:underline"
        >
          <Plus size={12} />
          <span>{showOtherChords ? 'Menos' : 'Otro acorde'}</span>
        </button>

        {currentChord && (
          <button
            onClick={onRemoveChord}
            className="flex items-center gap-1 text-red-600 dark:text-red-400 hover:underline font-semibold"
          >
            <Trash2 size={12} />
            <span>Quitar nota</span>
          </button>
        )}
      </div>
    </div>
  );
};
