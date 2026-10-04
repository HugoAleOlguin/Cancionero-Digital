import React, { useState, useEffect } from 'react';
import { Hymn, TypographyType } from '../types/hymn';
import { HighlightText } from './HighlightText';
import { HymnLyricsWithChords } from './HymnLyricsWithChords';
import {
  HARMONIC_FAMILIES,
  ALL_MAJOR_KEYS,
  ALL_MINOR_KEYS,
  UKULELE_CHORDS_DB
} from '../data/ukuleleChords';
import { GUITAR_CHORDS_DB } from '../data/guitarChords';
import { transposeHymnChords, transposeKey } from '../util/chordTransposer';
import { playAcousticChord } from '../util/chordAudio';
import { ChordNotationType, formatChordNotation } from '../util/chordNotation';
import { Star, Share2, Check, Copy, Music, RotateCcw, FileDown } from 'lucide-react';

const YouTubeIcon = ({ size = 20, className = '' }: { size?: number; className?: string }) => (
  <svg
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill="currentColor"
    className={className}
  >
    <path d="M23.498 6.186a3.016 3.016 0 0 0-2.122-2.136C19.505 3.545 12 3.545 12 3.545s-7.505 0-9.377.505A3.017 3.017 0 0 0 .502 6.186C0 8.07 0 12 0 12s0 3.93.502 5.814a3.016 3.016 0 0 0 2.122 2.136c1.871.505 9.376.505 9.376.505s7.505 0 9.377-.505a3.015 3.015 0 0 0 2.122-2.136C24 15.93 24 12 24 12s0-3.93-.502-5.814zM9.545 15.568V8.432L15.818 12l-6.273 3.568z"/>
  </svg>
);

interface HymnChordsData {
  key: string;
  chords: Record<string, string>;
}

interface HymnCardProps {
  hymn: Hymn;
  query: string;
  isFavorite: boolean;
  onToggleFavorite: (id: number) => void;
  onOpenShare: (hymn: Hymn, versionIndex: number) => void;
  onOpenPdfStudio?: (hymn: Hymn, versionIndex: number) => void;
  typography: TypographyType;
  fontSize: number;
  chordNotation?: ChordNotationType;
}

export const HymnCard: React.FC<HymnCardProps> = ({
  hymn,
  query,
  isFavorite,
  onToggleFavorite,
  onOpenShare,
  onOpenPdfStudio,
  typography,
  fontSize,
  chordNotation = 'latin'
}) => {
  const [selectedVersion, setSelectedVersion] = useState<number>(0);
  const [copied, setCopied] = useState<boolean>(false);

  // Estado del editor de acordes
  const [showChords, setShowChords] = useState<boolean>(false);
  const [isEditingChords, setIsEditingChords] = useState<boolean>(false);

  const [chordsData, setChordsData] = useState<HymnChordsData>(() => {
    try {
      const stored = localStorage.getItem(`cancionero_chords_${hymn.id}`);
      if (stored) {
        return JSON.parse(stored);
      }
    } catch {
      // fallback
    }
    return { key: 'C', chords: {} };
  });

  // Guardar en localStorage cuando cambien los acordes
  useEffect(() => {
    try {
      if (Object.keys(chordsData.chords).length > 0) {
        localStorage.setItem(`cancionero_chords_${hymn.id}`, JSON.stringify(chordsData));
      } else {
        localStorage.removeItem(`cancionero_chords_${hymn.id}`);
      }
    } catch {
      // fallback
    }
  }, [chordsData, hymn.id]);

  const versions = [hymn.content, ...(hymn.extraVersions || [])];
  const activeContent = versions[selectedVersion] || hymn.content;
  const stanzas = activeContent.split(/\n\s*\n/).filter(s => s.trim().length > 0);

  const fontClass = {
    serif: 'font-serif',
    sans: 'font-sans',
    mono: 'font-mono'
  }[typography];

  const handleCopy = async () => {
    try {
      const textToCopy = `${hymn.id} - ${hymn.title}\n${hymn.author ? `Autor: ${hymn.author}\n` : ''}\n${activeContent}`;
      await navigator.clipboard.writeText(textToCopy);
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
    } catch (err) {
      console.error('Failed to copy', err);
    }
  };

  const handleAssignChord = (syllableId: string, chordSymbol: string) => {
    setChordsData(prev => ({
      ...prev,
      chords: {
        ...prev.chords,
        [syllableId]: chordSymbol
      }
    }));
  };

  const handleRemoveChord = (syllableId: string) => {
    setChordsData(prev => {
      const nextChords = { ...prev.chords };
      delete nextChords[syllableId];
      return {
        ...prev,
        chords: nextChords
      };
    });
  };

  const handleChangeKey = (newKey: string) => {
    setChordsData(prev => ({
      ...prev,
      key: newKey
    }));
  };

  const handleTranspose = (semitones: number) => {
    setChordsData(prev => ({
      key: transposeKey(prev.key, semitones),
      chords: transposeHymnChords(prev.chords, semitones)
    }));
  };

  const handleResetChords = () => {
    if (window.confirm('¿Deseas borrar todos los acordes colocados en este himno?')) {
      setChordsData({ key: 'C', chords: {} });
    }
  };

  const handlePlayChord = (chordSymbol: string) => {
    const inst = (localStorage.getItem('cancionero_instrument') as 'ukulele' | 'guitar') || 'guitar';
    const db = inst === 'guitar' ? GUITAR_CHORDS_DB : UKULELE_CHORDS_DB;
    const chord = db[chordSymbol];
    if (chord) {
      playAcousticChord(chord, inst).catch(() => {});
    }
  };

  const hasChords = Object.keys(chordsData.chords).length > 0;
  const activeFamily = HARMONIC_FAMILIES[chordsData.key] || HARMONIC_FAMILIES['C'];

  return (
    <article
      id={`hymn-${hymn.id}`}
      className="bg-white dark:bg-darkcard border border-parchment-border dark:border-jetcarbon-border rounded-2xl p-6 sm:p-8 shadow-sm hover:shadow-md transition-shadow relative scroll-mt-24 mb-6"
    >
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 pb-4 border-b border-parchment-border/60 dark:border-jetcarbon-border/60">
        <div>
          <h2 className="text-xl sm:text-2xl font-bold tracking-tight text-jetcarbon dark:text-gray-100 flex items-center gap-2">
            <span className="text-golden font-bold">{hymn.id}.</span>
            <HighlightText text={hymn.title} query={query} />
          </h2>
          {hymn.author && (
            <p className="text-xs uppercase tracking-wider font-semibold text-jetcarbon-muted dark:text-gray-400 mt-1">
              <HighlightText text={hymn.author} query={query} />
            </p>
          )}
        </div>

        {/* Action Buttons */}
        <div className="flex items-center gap-1.5 self-end sm:self-center">
          {/* Botón de Acordes para Músicos */}
          <button
            onClick={() => {
              setShowChords(!showChords);
              if (showChords) setIsEditingChords(false);
            }}
            title={showChords ? 'Ocultar acordes' : 'Ver y editar acordes musicales'}
            className={`p-2 rounded-xl transition-all relative ${
              showChords || hasChords
                ? 'bg-golden/10 text-golden-dark dark:text-golden border border-golden/30'
                : 'text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400 dark:hover:text-gray-200 hover:bg-parchment dark:hover:bg-jetcarbon-light'
            }`}
          >
            <Music size={20} />
            {hasChords && !showChords && (
              <span className="absolute top-1 right-1 w-2 h-2 rounded-full bg-golden" />
            )}
          </button>

          {hymn.link && (
            <a
              href={hymn.link}
              target="_blank"
              rel="noopener noreferrer"
              title="Escuchar en YouTube"
              className="p-2 rounded-xl text-red-600 hover:bg-red-50 dark:hover:bg-red-950/30 transition-colors"
            >
              <YouTubeIcon size={20} />
            </a>
          )}

          <button
            onClick={handleCopy}
            title={copied ? '¡Copiado!' : 'Copiar alabanza'}
            className="p-2 rounded-xl text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400 dark:hover:text-gray-200 hover:bg-parchment dark:hover:bg-jetcarbon-light transition-colors relative"
          >
            {copied ? <Check size={20} className="text-green-600" /> : <Copy size={20} />}
          </button>

          {onOpenPdfStudio && (
            <button
              onClick={() => onOpenPdfStudio(hymn, selectedVersion)}
              title="Estudio de impresión y PDF"
              className="p-2 rounded-xl text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400 dark:hover:text-gray-200 hover:bg-parchment dark:hover:bg-jetcarbon-light transition-colors"
            >
              <FileDown size={20} />
            </button>
          )}

          <button
            onClick={() => onOpenShare(hymn, selectedVersion)}
            title="Compartir o descargar alabanza"
            className="p-2 rounded-xl text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400 dark:hover:text-gray-200 hover:bg-parchment dark:hover:bg-jetcarbon-light transition-colors"
          >
            <Share2 size={20} />
          </button>

          <button
            onClick={() => onToggleFavorite(hymn.id)}
            title={isFavorite ? 'Quitar de favoritos' : 'Agregar a favoritos'}
            className="p-2 rounded-xl text-golden hover:bg-golden-subtle dark:hover:bg-golden-darkSubtle transition-colors"
          >
            <Star
              size={22}
              className={isFavorite ? 'fill-golden text-golden' : 'text-golden'}
            />
          </button>
        </div>
      </div>

      {/* Version Tabs (if extraVersions exists) */}
      {versions.length > 1 && (
        <div className="flex gap-2 mt-4 pb-2 border-b border-parchment-border/40 dark:border-jetcarbon-border/40 overflow-x-auto">
          {versions.map((_, idx) => (
            <button
              key={idx}
              onClick={() => setSelectedVersion(idx)}
              className={`px-3 py-1 rounded-full text-xs font-semibold transition-all ${
                selectedVersion === idx
                  ? 'bg-golden text-white shadow-sm'
                  : 'bg-parchment dark:bg-jetcarbon-light text-jetcarbon-muted dark:text-gray-300 hover:bg-golden/10'
              }`}
            >
              {idx === 0 ? 'Original' : `Versión ${idx + 1}`}
            </button>
          ))}
        </div>
      )}

      {/* Barra de Herramientas de Acordes para Músicos - Tira compacta de una sola línea */}
      {showChords && (
        <div className="flex flex-wrap items-center justify-between gap-x-3 gap-y-2 px-3 py-1.5 mt-3 rounded-xl bg-golden/5 dark:bg-golden/10 border border-golden/20 text-xs animate-in fade-in duration-150">
          <div className="flex items-center gap-2">
            <span className="font-semibold text-jetcarbon-muted dark:text-gray-400 text-[11px] uppercase tracking-wide">
              Tono:
            </span>
            <select
              value={chordsData.key}
              onChange={e => handleChangeKey(e.target.value)}
              className="py-1 px-2 rounded-lg bg-white dark:bg-darkcard border border-golden/30 text-golden-dark dark:text-golden font-bold text-xs focus:outline-none cursor-pointer"
            >
              <optgroup label="Tonalidades Mayores">
                {ALL_MAJOR_KEYS.map(k => (
                  <option key={k.symbol} value={k.symbol}>
                    {chordNotation === 'latin'
                      ? `${formatChordNotation(k.symbol, 'latin')} (${k.symbol})`
                      : k.symbol}
                  </option>
                ))}
              </optgroup>
              <optgroup label="Tonalidades Menores">
                {ALL_MINOR_KEYS.map(k => (
                  <option key={k.symbol} value={k.symbol}>
                    {chordNotation === 'latin'
                      ? `${formatChordNotation(k.symbol, 'latin')} (${k.symbol})`
                      : k.symbol}
                  </option>
                ))}
              </optgroup>
            </select>
          </div>

          <div className="flex items-center gap-2">
            {/* Controles de Transporte de Tono (-1 / +1) */}
            <div className="flex items-center rounded-lg border border-parchment-border dark:border-jetcarbon-border overflow-hidden bg-white dark:bg-darkcard text-xs shadow-2xs">
              <button
                onClick={() => handleTranspose(-1)}
                title="Bajar 1 semitono (-1)"
                className="px-2 py-0.5 font-bold hover:bg-golden/10 text-jetcarbon dark:text-gray-200 transition-colors"
              >
                -1
              </button>
              <span className="px-1 text-[10px] text-jetcarbon-muted border-x border-parchment-border dark:border-jetcarbon-border">
                Tono
              </span>
              <button
                onClick={() => handleTranspose(1)}
                title="Subir 1 semitono (+1)"
                className="px-2 py-0.5 font-bold hover:bg-golden/10 text-jetcarbon dark:text-gray-200 transition-colors"
              >
                +1
              </button>
            </div>

            {/* Alternar Modo Edición / Lectura */}
            <button
              onClick={() => setIsEditingChords(!isEditingChords)}
              className={`px-3 py-1 rounded-lg font-bold text-xs transition-all ${
                isEditingChords
                  ? 'bg-golden text-white shadow-xs'
                  : 'bg-white dark:bg-darkcard text-jetcarbon dark:text-gray-200 border border-golden/30 hover:bg-golden/10'
              }`}
            >
              {isEditingChords ? 'Guardar' : 'Editar Acordes'}
            </button>

            {/* Reiniciar acordes si tiene */}
            {hasChords && (
              <button
                onClick={handleResetChords}
                title="Borrar todos los acordes de esta alabanza"
                className="p-1 rounded-lg text-red-600 dark:text-red-400 hover:bg-red-50 dark:hover:bg-red-950/30 transition-colors"
              >
                <RotateCcw size={14} />
              </button>
            )}
          </div>
        </div>
      )}

      {/* Lyrics Stanzas (con soporte de acordes sílaba a sílaba o lectura normal) */}
      <div
        className={`mt-6 ${fontClass} text-jetcarbon dark:text-gray-200`}
        style={{ fontSize: `${fontSize}px` }}
      >
        {showChords ? (
          <HymnLyricsWithChords
            verses={stanzas}
            chords={chordsData.chords}
            isEditing={isEditingChords}
            activeFamily={activeFamily}
            onAssignChord={handleAssignChord}
            onRemoveChord={handleRemoveChord}
            onChangeFamily={handleChangeKey}
            onPlayChordSound={handlePlayChord}
            notation={chordNotation}
          />
        ) : (
          <div className="space-y-5 leading-relaxed">
            {stanzas.map((stanza, idx) => (
              <div key={idx} className="whitespace-pre-line text-center">
                <HighlightText text={stanza} query={query} />
              </div>
            ))}
          </div>
        )}
      </div>
    </article>
  );
};
