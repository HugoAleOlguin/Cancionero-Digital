import React, { useState } from 'react';
import { Hymn, TypographyType } from '../types/hymn';
import { HighlightText } from './HighlightText';
import { Star, Share2, Check, Copy } from 'lucide-react';

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

interface HymnCardProps {
  hymn: Hymn;
  query: string;
  isFavorite: boolean;
  onToggleFavorite: (id: number) => void;
  typography: TypographyType;
  fontSize: number;
}

export const HymnCard: React.FC<HymnCardProps> = ({
  hymn,
  query,
  isFavorite,
  onToggleFavorite,
  typography,
  fontSize
}) => {
  const [selectedVersion, setSelectedVersion] = useState<number>(0);
  const [copied, setCopied] = useState<boolean>(false);

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

  const handleShare = async () => {
    const textToShare = `${hymn.id} - ${hymn.title}\n\n${activeContent}`;
    if (navigator.share) {
      try {
        await navigator.share({
          title: `${hymn.id} - ${hymn.title}`,
          text: textToShare
        });
        return;
      } catch {
        // User cancelled or share failed, fallback to copy
      }
    }
    handleCopy();
  };

  return (
    <article
      id={`hymn-${hymn.id}`}
      className="bg-white dark:bg-darkcard border border-parchment-border dark:border-jetcarbon-border rounded-2xl p-6 sm:p-8 shadow-sm hover:shadow-md transition-shadow relative scroll-mt-24 mb-6"
    >
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 pb-4 border-b border-parchment-border/60 dark:border-jetcarbon-border/60">
        <div>
          <h2 className="text-xl sm:text-2xl font-bold tracking-tight text-jetcarbon dark:text-gray-100 flex items-center gap-2">
            <span className="text-golden font-black">#{hymn.id}</span>
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

          <button
            onClick={handleShare}
            title="Compartir alabanza"
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

      {/* Lyrics Stanzas */}
      <div
        className={`mt-6 space-y-5 ${fontClass} leading-relaxed text-jetcarbon dark:text-gray-200`}
        style={{ fontSize: `${fontSize}px` }}
      >
        {stanzas.map((stanza, idx) => (
          <div key={idx} className="whitespace-pre-line">
            <HighlightText text={stanza} query={query} />
          </div>
        ))}
      </div>
    </article>
  );
};
