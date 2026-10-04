import React, { useState } from 'react';
import { HarmonicFamily } from '../data/ukuleleChords';
import { splitLineIntoSyllables, SyllableToken } from '../util/syllabifier';
import { SyllableChordPopover } from './SyllableChordPopover';
import { ChordNotationType, formatChordNotation } from '../util/chordNotation';

interface HymnLyricsWithChordsProps {
  verses: string[];
  chords: Record<string, string>; // Mapa { "l0_s0": "C", "l0_s3": "G" }
  isEditing: boolean;
  activeFamily: HarmonicFamily;
  onAssignChord: (syllableId: string, chordSymbol: string) => void;
  onRemoveChord: (syllableId: string) => void;
  onChangeFamily: (keySymbol: string) => void;
  onPlayChordSound?: (chordSymbol: string) => void;
  notation?: ChordNotationType;
}

export const HymnLyricsWithChords: React.FC<HymnLyricsWithChordsProps> = ({
  verses,
  chords,
  isEditing,
  activeFamily,
  onAssignChord,
  onRemoveChord,
  onChangeFamily,
  onPlayChordSound,
  notation = 'latin'
}) => {
  const [activePopoverSylId, setActivePopoverSylId] = useState<string | null>(null);

  let globalLineCounter = 0;

  return (
    <div
      className="space-y-6 select-text text-center"
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

              // Agrupar sílabas en palabras para mantener cada palabra 100% unida
              const words: SyllableToken[][] = [];
              let currentWord: SyllableToken[] = [];

              tokens.forEach(tok => {
                currentWord.push(tok);
                if (tok.isWordEnd) {
                  words.push(currentWord);
                  currentWord = [];
                }
              });
              if (currentWord.length > 0) {
                words.push(currentWord);
              }

              return (
                <div
                  key={`line-${currentLineIdx}`}
                  className={`flex flex-wrap items-end justify-center leading-relaxed transition-all ${
                    hasChordsOnLine || isEditing ? 'pt-4' : ''
                  }`}
                >
                  {words.map((wordTokens, wIdx) => (
                    <span
                      key={`word-${currentLineIdx}-${wIdx}`}
                      className="inline-block mr-1.5 sm:mr-2 last:mr-0 whitespace-nowrap"
                    >
                      {wordTokens.map(token => {
                        const assignedChord = chords[token.id];
                        const isPopoverOpen = activePopoverSylId === token.id;
                        const formattedChord = assignedChord
                          ? formatChordNotation(assignedChord, notation)
                          : '';

                        return (
                          <span
                            key={token.id}
                            className="relative inline-block"
                          >
                            {/* Acorde flotando exactamente arriba de la sílaba SIN ensanchar la palabra */}
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
                                title={isEditing ? 'Cambiar o quitar nota' : `Tocar ${formattedChord}`}
                                className="absolute -top-4 left-1/2 -translate-x-1/2 text-[11px] sm:text-xs font-bold font-sans text-golden hover:text-golden-dark dark:hover:text-yellow-400 select-none whitespace-nowrap leading-none cursor-pointer z-10 transition-transform active:scale-90"
                              >
                                {formattedChord}
                              </button>
                            ) : null}

                            {/* Texto de la Sílaba: fluye continuo con las demás de la palabra */}
                            <span
                              onClick={e => {
                                if (isEditing) {
                                  e.stopPropagation();
                                  setActivePopoverSylId(isPopoverOpen ? null : token.id);
                                }
                              }}
                              className={`transition-colors ${
                                isEditing
                                  ? 'cursor-pointer hover:bg-golden/25 hover:text-golden-dark dark:hover:text-golden rounded-xs border-b border-dashed border-golden/50'
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
                                notation={notation}
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
                    </span>
                  ))}
                </div>
              );
            })}
          </div>
        );
      })}
    </div>
  );
};
