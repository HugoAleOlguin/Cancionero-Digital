import React, { useState } from 'react';
import { HarmonicFamily } from '../data/ukuleleChords';
import { splitLineIntoSyllables, SyllableToken } from '../util/syllabifier';
import { SyllableChordPopover } from './SyllableChordPopover';

interface HymnLyricsWithChordsProps {
  verses: string[];
  chords: Record<string, string>; // Mapa { "l0_s0": "C", "l0_s3": "G" }
  isEditing: boolean;
  activeFamily: HarmonicFamily;
  onAssignChord: (syllableId: string, chordSymbol: string) => void;
  onRemoveChord: (syllableId: string) => void;
  onChangeFamily: (keySymbol: string) => void;
  onPlayChordSound?: (chordSymbol: string) => void;
}

export const HymnLyricsWithChords: React.FC<HymnLyricsWithChordsProps> = ({
  verses,
  chords,
  isEditing,
  activeFamily,
  onAssignChord,
  onRemoveChord,
  onChangeFamily,
  onPlayChordSound
}) => {
  const [activePopoverSylId, setActivePopoverSylId] = useState<string | null>(null);

  // Mapear cada estrofa y línea
  let globalLineCounter = 0;

  return (
    <div
      className="space-y-6 select-text"
      onClick={() => {
        if (activePopoverSylId) setActivePopoverSylId(null);
      }}
    >
      {verses.map((verse, vIdx) => {
        const lines = verse.split('\n');

        return (
          <div key={`verse-${vIdx}`} className="space-y-3">
            {lines.map((rawLine) => {
              const currentLineIdx = globalLineCounter++;
              const line = rawLine.trim();

              if (!line) {
                return <div key={`empty-${currentLineIdx}`} className="h-2" />;
              }

              const tokens: SyllableToken[] = splitLineIntoSyllables(line, currentLineIdx);
              const hasChordsOnLine = tokens.some(t => !!chords[t.id]);

              return (
                <div
                  key={`line-${currentLineIdx}`}
                  className={`flex flex-wrap items-end justify-center text-center transition-all ${
                    hasChordsOnLine || isEditing ? 'pt-4 leading-relaxed' : 'leading-relaxed'
                  }`}
                >
                  {tokens.map(token => {
                    const assignedChord = chords[token.id];
                    const isPopoverOpen = activePopoverSylId === token.id;

                    return (
                      <span
                        key={token.id}
                        className={`relative inline-flex flex-col items-center ${
                          token.isWordEnd ? 'mr-1.5 sm:mr-2' : 'mr-0'
                        }`}
                      >
                        {/* Acorde flotando encima de la sílaba */}
                        {assignedChord ? (
                          <button
                            type="button"
                            onClick={e => {
                              e.stopPropagation();
                              if (isEditing) {
                                setActivePopoverSylId(token.id);
                              } else {
                                onPlayChordSound?.(assignedChord);
                              }
                            }}
                            title={isEditing ? 'Cambiar o quitar nota' : `Tocar ${assignedChord}`}
                            className="text-[11px] sm:text-xs font-bold font-sans text-golden hover:text-golden-dark dark:hover:text-yellow-400 select-none leading-none mb-1 transition-transform active:scale-90"
                          >
                            {assignedChord}
                          </button>
                        ) : isEditing ? (
                          /* Espacio reservado para poder hacer clic en modo edición */
                          <span className="h-3.5 block" />
                        ) : null}

                        {/* Texto de la Sílaba */}
                        <span
                          onClick={e => {
                            if (isEditing) {
                              e.stopPropagation();
                              setActivePopoverSylId(isPopoverOpen ? null : token.id);
                            }
                          }}
                          className={`transition-colors ${
                            isEditing
                              ? 'cursor-pointer px-0.5 rounded hover:bg-golden/20 hover:text-golden-dark dark:hover:text-golden border-b border-dashed border-golden/40'
                              : ''
                          }`}
                        >
                          {token.leadingPunct}
                          {token.text}
                          {token.trailingPunct}
                        </span>

                        {/* Popover selector de notas flotante */}
                        {isPopoverOpen && isEditing && (
                          <SyllableChordPopover
                            currentChord={assignedChord}
                            activeFamily={activeFamily}
                            onSelectChord={chordSym => {
                              onAssignChord(token.id, chordSym);
                              setActivePopoverSylId(null);
                            }}
                            onRemoveChord={() => {
                              onRemoveChord(token.id);
                              setActivePopoverSylId(null);
                            }}
                            onChangeFamily={newKey => {
                              onChangeFamily(newKey);
                            }}
                            onClose={() => setActivePopoverSylId(null)}
                          />
                        )}
                      </span>
                    );
                  })}
                </div>
              );
            })}
          </div>
        );
      })}
    </div>
  );
};
