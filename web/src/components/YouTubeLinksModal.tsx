import React, { useState, useMemo } from 'react';
import { Hymn } from '../types/hymn';
import { formatYouTubeListToClipboard } from '../utils/youtubeLinks';
import { X, Search, Copy, Check, ExternalLink } from 'lucide-react';

interface YouTubeLinksModalProps {
  isOpen: boolean;
  onClose: () => void;
  hymns: Hymn[];
  onNotify?: (message: string) => void;
}

// Icono vectorial limpio de YouTube (sin emojis ni cruces)
const YouTubeIcon = ({ size = 18 }: { size?: number }) => (
  <svg width={size} height={size} viewBox="0 0 24 24" fill="currentColor">
    <path d="M23.498 6.186a3.016 3.016 0 0 0-2.122-2.136C19.505 3.545 12 3.545 12 3.545s-7.505 0-9.377.505A3.017 3.017 0 0 0 .502 6.186C0 8.07 0 12 0 12s0 3.93.502 5.814a3.016 3.016 0 0 0 2.122 2.136c1.871.505 9.376.505 9.376.505s7.505 0 9.377-.505a3.015 3.015 0 0 0 2.122-2.136C24 15.93 24 12 24 12s0-3.93-.502-5.814zM9.545 15.568V8.432L15.818 12l-6.273 3.568z"/>
  </svg>
);

export const YouTubeLinksModal: React.FC<YouTubeLinksModalProps> = ({
  isOpen,
  onClose,
  hymns,
  onNotify
}) => {
  const [search, setSearch] = useState<string>('');
  const [copiedAll, setCopiedAll] = useState<boolean>(false);
  const [copiedId, setCopiedId] = useState<number | null>(null);

  const filteredHymns = useMemo(() => {
    if (!search.trim()) return hymns;
    const q = search.trim().toLowerCase();
    return hymns.filter(h => 
      h.title.toLowerCase().includes(q) ||
      h.id.toString() === q ||
      (h.author && h.author.toLowerCase().includes(q))
    );
  }, [hymns, search]);

  if (!isOpen) return null;

  const handleCopyAll = async () => {
    try {
      const fullText = formatYouTubeListToClipboard(filteredHymns);
      await navigator.clipboard.writeText(fullText);
      setCopiedAll(true);
      onNotify?.(`${filteredHymns.length} enlaces copiados al portapapeles`);
      setTimeout(() => setCopiedAll(false), 2200);
    } catch (err) {
      console.error('Error al copiar enlaces', err);
    }
  };

  const handleCopySingle = async (hymn: Hymn) => {
    if (!hymn.link) return;
    try {
      const line = `${hymn.id}. ${hymn.title} - ${hymn.link}`;
      await navigator.clipboard.writeText(line);
      setCopiedId(hymn.id);
      onNotify?.(`Enlace de "${hymn.title}" copiado`);
      setTimeout(() => setCopiedId(null), 2000);
    } catch (err) {
      console.error('Error al copiar enlace individual', err);
    }
  };

  return (
    <div className="fixed inset-0 z-50 overflow-hidden flex items-center justify-center p-0 sm:p-4 bg-black/70 backdrop-blur-xs animate-in fade-in duration-200">
      {/* Contenedor Principal */}
      <div className="bg-parchment dark:bg-darkcard border border-parchment-border dark:border-jetcarbon-border rounded-none sm:rounded-2xl shadow-2xl flex flex-col w-full max-w-2xl h-full sm:h-[88vh] overflow-hidden">
        
        {/* Cabecera */}
        <header className="px-5 py-3.5 border-b border-parchment-border dark:border-jetcarbon-border bg-white dark:bg-darkbg flex items-center justify-between shrink-0">
          <div className="flex items-center gap-3">
            <div className="w-8 h-8 rounded-xl bg-red-600/10 text-red-600 flex items-center justify-center">
              <YouTubeIcon size={18} />
            </div>
            <div>
              <div className="flex items-center gap-2">
                <h2 className="text-sm sm:text-base font-bold text-jetcarbon dark:text-gray-100">
                  Alabanzas con Enlace de YouTube
                </h2>
                <span className="text-xs px-2 py-0.5 rounded-full bg-golden/15 text-golden-dark dark:text-golden font-semibold">
                  {hymns.length} cantos
                </span>
              </div>
              <p className="text-[11px] text-jetcarbon-muted dark:text-gray-400">
                Lista de enlaces directos para escuchar y compartir.
              </p>
            </div>
          </div>

          <button
            onClick={onClose}
            className="p-2 rounded-xl text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400 dark:hover:text-white hover:bg-parchment dark:hover:bg-jetcarbon-light transition-colors"
            title="Cerrar"
          >
            <X size={18} />
          </button>
        </header>

        {/* Barra de Herramientas: Buscador y Botón Global de Copiado */}
        <div className="p-4 border-b border-parchment-border dark:border-jetcarbon-border bg-white/70 dark:bg-darkbg/50 shrink-0 space-y-2.5">
          {/* Botón Global Prominente para Copiar Todos */}
          <button
            onClick={handleCopyAll}
            className="w-full py-2.5 px-4 rounded-xl font-bold text-xs bg-golden hover:bg-golden-dark text-white flex items-center justify-center gap-2 transition-all shadow-sm active:scale-98"
          >
            {copiedAll ? (
              <>
                <Check size={16} />
                <span>¡{filteredHymns.length} enlaces copiados al portapapeles!</span>
              </>
            ) : (
              <>
                <Copy size={16} />
                <span>Copiar todos los enlaces ({filteredHymns.length})</span>
              </>
            )}
          </button>

          {/* Campo de Búsqueda Rápida */}
          <div className="relative flex items-center">
            <div className="absolute left-3 text-jetcarbon-muted dark:text-gray-400 pointer-events-none">
              <Search size={15} />
            </div>
            <input
              type="text"
              value={search}
              onChange={e => setSearch(e.target.value)}
              placeholder="Filtrar por número, título o autor..."
              className="w-full pl-9 pr-8 py-2 rounded-xl bg-parchment/60 dark:bg-darkcard border border-parchment-border dark:border-jetcarbon-border text-xs text-jetcarbon dark:text-gray-100 placeholder-jetcarbon-muted/70 focus:outline-none focus:border-golden transition-colors"
            />
            {search && (
              <button
                onClick={() => setSearch('')}
                className="absolute right-2.5 p-1 text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400"
              >
                <X size={13} />
              </button>
            )}
          </div>
        </div>

        {/* Lista Simple de Alabanzas */}
        <div className="flex-1 overflow-y-auto divide-y divide-parchment-border/60 dark:divide-jetcarbon-border/60">
          {filteredHymns.length === 0 ? (
            <div className="py-16 text-center text-xs text-jetcarbon-muted dark:text-gray-400">
              No se encontraron alabanzas que coincidan con &ldquo;{search}&rdquo;.
            </div>
          ) : (
            filteredHymns.map(hymn => {
              const isItemCopied = copiedId === hymn.id;

              return (
                <div
                  key={hymn.id}
                  className="px-4 py-3 flex items-center justify-between gap-3 hover:bg-white/80 dark:hover:bg-darkbg/60 transition-colors"
                >
                  <div className="min-w-0 flex-1">
                    <div className="flex items-baseline gap-2">
                      <span className="font-bold text-xs text-golden">
                        {hymn.id}.
                      </span>
                      <span className="font-semibold text-xs text-jetcarbon dark:text-gray-200 truncate">
                        {hymn.title}
                      </span>
                    </div>
                    {hymn.author && (
                      <p className="text-[10px] text-jetcarbon-muted dark:text-gray-400 truncate mt-0.5">
                        {hymn.author}
                      </p>
                    )}
                  </div>

                  <div className="flex items-center gap-1 shrink-0">
                    {/* Botón copiar enlace individual */}
                    <button
                      onClick={() => handleCopySingle(hymn)}
                      title="Copiar este enlace"
                      className="p-1.5 rounded-lg text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400 dark:hover:text-gray-200 hover:bg-parchment dark:hover:bg-jetcarbon-light transition-colors"
                    >
                      {isItemCopied ? (
                        <Check size={15} className="text-green-600" />
                      ) : (
                        <Copy size={15} />
                      )}
                    </button>

                    {/* Botón abrir enlace en YouTube */}
                    <a
                      href={hymn.link}
                      target="_blank"
                      rel="noopener noreferrer"
                      title="Abrir en YouTube"
                      className="p-1.5 rounded-lg text-red-600 hover:bg-red-50 dark:hover:bg-red-950/30 transition-colors flex items-center gap-1 text-xs font-semibold"
                    >
                      <ExternalLink size={15} />
                    </a>
                  </div>
                </div>
              );
            })
          )}
        </div>

        {/* Pie */}
        <footer className="px-5 py-2.5 border-t border-parchment-border dark:border-jetcarbon-border bg-white dark:bg-darkbg flex items-center justify-between text-xs text-jetcarbon-muted dark:text-gray-400 shrink-0">
          <span>Mostrando <strong>{filteredHymns.length}</strong> de {hymns.length} alabanzas</span>
          <button
            onClick={onClose}
            className="px-3 py-1 rounded-lg hover:bg-parchment dark:hover:bg-jetcarbon-light font-semibold text-jetcarbon dark:text-gray-300 transition-colors"
          >
            Cerrar
          </button>
        </footer>

      </div>
    </div>
  );
};
