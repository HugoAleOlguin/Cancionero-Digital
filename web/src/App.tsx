import { useState, useEffect, useMemo, useCallback, useRef } from 'react';
import type { Hymn, HymnCatalog } from './types/hymn';
import { createSearchableHymns, searchHymns, extractMatches } from './utils/searchEngine';
import { useFavorites } from './hooks/useFavorites';
import { useSettings } from './hooks/useSettings';
import { SearchHeader } from './components/SearchHeader';
import { HymnCard } from './components/HymnCard';
import { AppDrawer } from './components/AppDrawer';
import { ShareModal } from './components/ShareModal';
import { PdfStudioModal } from './components/PdfStudioModal';
import { YouTubeLinksModal } from './components/YouTubeLinksModal';
import { InstrumentCompanionPanel } from './components/InstrumentCompanionPanel';
import { Toast } from './components/Toast';
import { ArrowUp, BookX, Star } from 'lucide-react';
import { ChordNotationType } from './utils/chordNotation';
import { filterHymnsWithYouTube } from './utils/youtubeLinks';
import { safeGetItem, safeSetItem } from './utils/storage';

export function App() {
  const [catalog, setCatalog] = useState<HymnCatalog | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [query, setQuery] = useState<string>('');
  const [isDrawerOpen, setIsDrawerOpen] = useState<boolean>(false);
  const [isYouTubeModalOpen, setIsYouTubeModalOpen] = useState<boolean>(false);
  const [showOnlyFavorites, setShowOnlyFavorites] = useState<boolean>(false);
  const [selectedAuthor, setSelectedAuthor] = useState<string | null>(null);
  const [currentMatchIndex, setCurrentMatchIndex] = useState<number>(0);
  const [showScrollTop, setShowScrollTop] = useState<boolean>(false);
  const [sharingHymn, setSharingHymn] = useState<{ hymn: Hymn; versionIndex: number } | null>(null);
  const [pdfStudioHymn, setPdfStudioHymn] = useState<{ hymn: Hymn; versionIndex: number } | null>(null);
  const [toastMessage, setToastMessage] = useState<string | null>(null);
  const showToast = useCallback((msg: string) => setToastMessage(msg), []);

  const handleOpenShare = useCallback((hymn: Hymn, versionIndex: number) => {
    setSharingHymn({ hymn, versionIndex });
  }, []);

  const handleCloseShare = useCallback(() => {
    setSharingHymn(null);
  }, []);

  const handleOpenPdfStudio = useCallback((hymn: Hymn, versionIndex: number) => {
    setPdfStudioHymn({ hymn, versionIndex });
  }, []);

  const handleClosePdfStudio = useCallback(() => {
    setPdfStudioHymn(null);
  }, []);

  const handleOpenYouTubeModal = useCallback(() => {
    setIsYouTubeModalOpen(true);
  }, []);

  const handleCloseYouTubeModal = useCallback(() => {
    setIsYouTubeModalOpen(false);
  }, []);

  const handleCloseDrawer = useCallback(() => {
    setIsDrawerOpen(false);
  }, []);

  const handleToggleDrawer = useCallback(() => {
    setIsDrawerOpen(prev => !prev);
  }, []);

  const handleCloseUkulele = useCallback(() => {
    setIsUkuleleOpen(false);
  }, []);

  const handleToggleUkulele = useCallback(() => {
    setIsUkuleleOpen(prev => !prev);
  }, []);

  const handleToggleFavoritesFilter = useCallback(() => {
    setShowOnlyFavorites(prev => !prev);
  }, []);
  const [chordNotation, setChordNotation] = useState<ChordNotationType>(() => {
    const stored = safeGetItem<ChordNotationType>('cancionero_chord_notation', 'latin');
    return stored === 'latin' || stored === 'anglo' ? stored : 'latin';
  });
  const [isUkuleleOpen, setIsUkuleleOpen] = useState<boolean>(() => {
    return safeGetItem<boolean>('cancionero_ukulele_open', true);
  });
  const [lastReadHymnId, setLastReadHymnId] = useState<number | null>(() => {
    return safeGetItem<number | null>('cancionero_last_viewed_hymn', null);
  });

  const visibleHymnIdRef = useRef<number | null>(null);
  const cardsContainerRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    safeSetItem('cancionero_ukulele_open', isUkuleleOpen);
  }, [isUkuleleOpen]);

  useEffect(() => {
    safeSetItem('cancionero_chord_notation', chordNotation);
  }, [chordNotation]);

  const { favorites, toggleFavorite, isFavorite, favoritesCount } = useFavorites();
  const handleToggleFavorite = useCallback((id: number) => {
    toggleFavorite(id);
  }, [toggleFavorite]);
  const { theme, toggleTheme, typography, setTypography, fontSize, setFontSize } = useSettings();

  // Load catalog on start with offline fallback
  useEffect(() => {
    async function loadCatalog() {
      try {
        const res = await fetch('/catalog.json');
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        const data: HymnCatalog = await res.json();
        setCatalog(data);
        safeSetItem('cancionero_cached_catalog', data);
      } catch (err) {
        console.warn('Network fetch failed, attempting localStorage cached catalog...', err);
        const cached = safeGetItem<HymnCatalog | null>('cancionero_cached_catalog', null);
        if (cached) {
          setCatalog(cached);
        }
      } finally {
        setIsLoading(false);
      }
    }
    loadCatalog();
  }, []);

  const [visibleLimit, setVisibleLimit] = useState<number>(35);

  // Reset limit on search or filter change
  useEffect(() => {
    setVisibleLimit(35);
  }, [query, selectedAuthor, showOnlyFavorites]);

  // Monitor scroll for back to top button, auto-batch loading & debounced reading position persistence
  useEffect(() => {
    let scrollTimeout: ReturnType<typeof setTimeout> | null = null;
    let ticking = false;

    const handleScroll = () => {
      if (!ticking) {
        window.requestAnimationFrame(() => {
          setShowScrollTop(window.scrollY > 400);
          if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 1000) {
            setVisibleLimit(prev => prev + 35);
          }
          ticking = false;
        });
        ticking = true;
      }

      // Debounce saving reading position (350ms)
      if (scrollTimeout) clearTimeout(scrollTimeout);
      scrollTimeout = setTimeout(() => {
        let activeId = visibleHymnIdRef.current;

        // O(1) hit-testing fallback if observer hasn't triggered yet
        if (activeId === null) {
          const sampleY = Math.min(Math.round(window.innerHeight * 0.35), 250);
          const sampleX = Math.round(window.innerWidth / 2);
          const el = document.elementFromPoint(sampleX, sampleY);
          const card = el?.closest('article[id^="hymn-"]');
          if (card) {
            const parsed = parseInt(card.id.replace('hymn-', ''), 10);
            if (!Number.isNaN(parsed)) {
              activeId = parsed;
            }
          }
        }

        if (activeId !== null && !Number.isNaN(activeId)) {
          safeSetItem('cancionero_last_viewed_hymn', activeId);
          setLastReadHymnId(activeId);
        }
      }, 350);
    };

    window.addEventListener('scroll', handleScroll, { passive: true });
    return () => {
      window.removeEventListener('scroll', handleScroll);
      if (scrollTimeout) clearTimeout(scrollTimeout);
    };
  }, []);

  // Searchables cache
  const searchables = useMemo(() => {
    if (!catalog?.hymns) return [];
    return createSearchableHymns(catalog.hymns);
  }, [catalog]);

  // Alabanzas con enlace de YouTube
  const youtubeHymns = useMemo(() => {
    if (!catalog?.hymns) return [];
    return filterHymnsWithYouTube(catalog.hymns);
  }, [catalog]);

  // Deep linking or restoring last read position on initial load
  useEffect(() => {
    if (!catalog?.hymns || catalog.hymns.length === 0) return;
    const params = new URLSearchParams(window.location.search);
    const targetIdStr = params.get('id') || params.get('canto') || params.get('hymn');

    let targetId: number | null = null;
    let isRestoringSaved = false;

    if (targetIdStr) {
      const parsed = parseInt(targetIdStr, 10);
      if (!isNaN(parsed)) targetId = parsed;
    } else {
      const saved = safeGetItem<number | null>('cancionero_last_viewed_hymn', null);
      if (saved !== null && saved > 1) {
        targetId = saved;
        isRestoringSaved = true;
      }
    }

    if (targetId === null) return;

    let targetEl: HTMLElement | null = null;
    let highlightTimer: ReturnType<typeof setTimeout> | undefined;
    let scrollTimer: ReturnType<typeof setTimeout> | undefined;

    const targetIdx = searchables.findIndex(s => s.hymn.id === targetId);
    if (targetIdx !== -1) {
      if (targetIdx >= visibleLimit) {
        setVisibleLimit(targetIdx + 15);
      }
      scrollTimer = setTimeout(() => {
        const el = document.getElementById(`hymn-${targetId}`);
        if (el) {
          targetEl = el;
          el.scrollIntoView({ behavior: 'smooth', block: 'center' });
          el.classList.add('ring-2', 'ring-golden', 'ring-offset-4', 'ring-offset-parchment', 'dark:ring-offset-darkbg');
          highlightTimer = setTimeout(() => {
            el.classList.remove('ring-2', 'ring-golden', 'ring-offset-4', 'ring-offset-parchment', 'dark:ring-offset-darkbg');
          }, 3000);

          if (isRestoringSaved) {
            showToast(`Continuando en el himno #${targetId}`);
          }
        }
      }, 300);
    }

    return () => {
      if (scrollTimer) clearTimeout(scrollTimer);
      if (highlightTimer) {
        clearTimeout(highlightTimer);
        targetEl?.classList.remove('ring-2', 'ring-golden', 'ring-offset-4', 'ring-offset-parchment', 'dark:ring-offset-darkbg');
      }
    };
  }, [catalog, searchables, showToast]);

  // Authors list for drawer
  const authorsList = useMemo(() => {
    const counts = new Map<string, number>();
    searchables.forEach(s => {
      const a = s.hymn.author?.trim();
      if (a) {
        counts.set(a, (counts.get(a) || 0) + 1);
      }
    });
    return Array.from(counts.entries())
      .map(([name, count]) => ({ name, count }))
      .sort((a, b) => b.count - a.count);
  }, [searchables]);

  // Filtered hymns based on search query, favorites and author
  const filteredSearchables = useMemo(() => {
    let result = searchables;

    // 1. Author filter
    if (selectedAuthor) {
      result = result.filter(s => s.hymn.author?.trim() === selectedAuthor);
    }

    // 2. Favorites filter
    if (showOnlyFavorites) {
      result = result.filter(s => favorites.has(s.hymn.id));
    }

    // 3. Search query
    if (query.trim()) {
      result = searchHymns(result, query);
    }

    return result;
  }, [searchables, selectedAuthor, showOnlyFavorites, favorites, query]);

  // Efficient visible hymn detection via IntersectionObserver
  useEffect(() => {
    if (typeof IntersectionObserver === 'undefined') return;

    const observer = new IntersectionObserver(
      entries => {
        for (const entry of entries) {
          if (entry.isIntersecting) {
            const hymnIdStr = entry.target.id.replace('hymn-', '');
            const id = parseInt(hymnIdStr, 10);
            if (!Number.isNaN(id)) {
              visibleHymnIdRef.current = id;
            }
          }
        }
      },
      {
        // Primary reading zone: top 10% to 50% of viewport
        rootMargin: '-10% 0px -50% 0px',
        threshold: 0
      }
    );

    const container = cardsContainerRef.current || document;
    const cards = container.querySelectorAll('article[id^="hymn-"]');
    cards.forEach(card => observer.observe(card));

    return () => {
      observer.disconnect();
    };
  }, [filteredSearchables, visibleLimit]);

  // Matches for navigation (< 1/N >)
  const matches = useMemo(() => {
    if (!query.trim()) return [];
    return extractMatches(filteredSearchables, query);
  }, [filteredSearchables, query]);

  // Reset match index when query changes
  useEffect(() => {
    setCurrentMatchIndex(0);
  }, [query]);

  // Scroll to match element, auto-expanding visible limit if needed
  const matchScrollTimerRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  useEffect(() => {
    return () => {
      if (matchScrollTimerRef.current) {
        clearTimeout(matchScrollTimerRef.current);
      }
    };
  }, []);

  const scrollToMatch = useCallback((index: number) => {
    if (!matches || matches.length === 0) return;
    const match = matches[index];
    if (!match) return;

    const matchHymnIdx = filteredSearchables.findIndex(s => s.hymn.id === match.hymnId);
    if (matchHymnIdx >= 0 && matchHymnIdx >= visibleLimit) {
      setVisibleLimit(matchHymnIdx + 20);
    }

    if (matchScrollTimerRef.current) clearTimeout(matchScrollTimerRef.current);
    matchScrollTimerRef.current = setTimeout(() => {
      const el = document.getElementById(`hymn-${match.hymnId}`);
      if (el) {
        el.scrollIntoView({ behavior: 'smooth', block: 'center' });
      }
    }, 60);
  }, [matches, filteredSearchables, visibleLimit]);

  const handleNextMatch = () => {
    if (matches.length === 0) return;
    const nextIdx = (currentMatchIndex + 1) % matches.length;
    setCurrentMatchIndex(nextIdx);
    scrollToMatch(nextIdx);
  };

  const handlePrevMatch = () => {
    if (matches.length === 0) return;
    const prevIdx = (currentMatchIndex - 1 + matches.length) % matches.length;
    setCurrentMatchIndex(prevIdx);
    scrollToMatch(prevIdx);
  };

  const scrollToTop = () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const handleJumpToLastRead = useCallback(() => {
    if (!lastReadHymnId) return;
    const targetIdx = searchables.findIndex(s => s.hymn.id === lastReadHymnId);
    if (targetIdx !== -1) {
      if (targetIdx >= visibleLimit) {
        setVisibleLimit(targetIdx + 15);
      }
      setTimeout(() => {
        const el = document.getElementById(`hymn-${lastReadHymnId}`);
        if (el) {
          el.scrollIntoView({ behavior: 'smooth', block: 'center' });
          el.classList.add('ring-2', 'ring-golden', 'ring-offset-4', 'ring-offset-parchment', 'dark:ring-offset-darkbg');
          setTimeout(() => {
            el.classList.remove('ring-2', 'ring-golden', 'ring-offset-4', 'ring-offset-parchment', 'dark:ring-offset-darkbg');
          }, 3000);
          showToast(`Continuando en el himno #${lastReadHymnId}`);
        }
      }, 100);
    }
  }, [lastReadHymnId, searchables, visibleLimit, showToast]);

  return (
    <div className="min-h-screen bg-parchment dark:bg-darkbg text-jetcarbon dark:text-gray-100 flex flex-col font-sans">
      {/* Sticky Navigation & Search Header */}
      <SearchHeader
        query={query}
        onQueryChange={setQuery}
        onClear={() => setQuery('')}
        matchesCount={matches.length}
        currentMatchIndex={currentMatchIndex}
        onNextMatch={handleNextMatch}
        onPrevMatch={handlePrevMatch}
        onOpenDrawer={handleToggleDrawer}
        isDrawerOpen={isDrawerOpen}
        theme={theme}
        onToggleTheme={toggleTheme}
        isUkuleleOpen={isUkuleleOpen}
        onToggleUkulele={handleToggleUkulele}
        lastReadId={lastReadHymnId}
        onJumpToLastRead={handleJumpToLastRead}
      />

      {/* Central Area: Clean 3-Column Docked Layout */}
      <div className="flex-1 flex w-full max-w-[1600px] mx-auto justify-center items-start min-h-[calc(100vh-4rem)]">
        {/* Barra Lateral Izquierda (si isDrawerOpen) */}
        {isDrawerOpen && (
          <AppDrawer
            isOpen={isDrawerOpen}
            onClose={handleCloseDrawer}
            totalHymns={searchables.length}
            favoritesCount={favoritesCount}
            showOnlyFavorites={showOnlyFavorites}
            onToggleFavoritesFilter={handleToggleFavoritesFilter}
            selectedAuthor={selectedAuthor}
            onSelectAuthor={setSelectedAuthor}
            authors={authorsList}
            typography={typography}
            onChangeTypography={setTypography}
            fontSize={fontSize}
            onChangeFontSize={setFontSize}
            theme={theme}
            onToggleTheme={toggleTheme}
            catalogVersion={catalog?.version || 1}
            chordNotation={chordNotation}
            onChangeChordNotation={setChordNotation}
            youtubeHymnsCount={youtubeHymns.length}
            onOpenYouTubeLinks={handleOpenYouTubeModal}
          />
        )}

        {/* Feed Central de Alabanzas (flex-1) */}
        <main className="flex-1 max-w-4xl w-full px-4 sm:px-6 py-6 min-w-0">
        {/* Active Filters Badges */}
        {(selectedAuthor || showOnlyFavorites) && (
          <div className="flex items-center gap-2 mb-6 flex-wrap">
            <span className="text-xs text-jetcarbon-muted dark:text-gray-400 font-semibold">Filtros activos:</span>
            {showOnlyFavorites && (
              <span className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-semibold bg-golden text-white shadow-xs">
                <Star size={12} className="fill-white" /> Favoritos ({favoritesCount})
                <button onClick={() => setShowOnlyFavorites(false)} className="hover:opacity-75">×</button>
              </span>
            )}
            {selectedAuthor && (
              <span className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-semibold bg-jetcarbon text-white dark:bg-jetcarbon-light">
                Autor: {selectedAuthor}
                <button onClick={() => setSelectedAuthor(null)} className="hover:opacity-75">×</button>
              </span>
            )}
          </div>
        )}

        {/* Loading skeleton */}
        {isLoading && (
          <div className="space-y-6 animate-pulse">
            {[1, 2, 3].map(n => (
              <div key={n} className="bg-white/60 dark:bg-darkcard/60 rounded-2xl p-6 h-64 border border-parchment-border dark:border-jetcarbon-border" />
            ))}
          </div>
        )}

        {/* Empty States */}
        {!isLoading && filteredSearchables.length === 0 && (
          <div className="text-center py-20 px-4">
            <div className="w-16 h-16 rounded-full bg-golden/10 text-golden mx-auto flex items-center justify-center mb-4">
              <BookX size={32} />
            </div>
            <h3 className="text-xl font-bold text-jetcarbon dark:text-gray-100 mb-2">
              {showOnlyFavorites
                ? 'No tienes alabanzas en favoritos'
                : 'No se encontraron alabanzas'}
            </h3>
            <p className="text-sm text-jetcarbon-muted dark:text-gray-400 max-w-md mx-auto mb-6">
              {showOnlyFavorites
                ? 'Marca la estrella dorada en cualquier alabanza para guardarla aquí y tenerla siempre a mano.'
                : query
                  ? `No hubo coincidencias para "${query}". Intenta buscar por una sola palabra clave o por el número del himno.`
                  : 'No se encontraron alabanzas con los filtros seleccionados.'}
            </p>
            {showOnlyFavorites || selectedAuthor ? (
              <button
                onClick={() => {
                  setShowOnlyFavorites(false);
                  setSelectedAuthor(null);
                  setQuery('');
                }}
                className="px-4 py-2 bg-golden text-white rounded-xl text-sm font-semibold hover:bg-golden-dark transition-colors shadow-sm"
              >
                Quitar todos los filtros
              </button>
            ) : query ? (
              <button
                onClick={() => setQuery('')}
                className="px-4 py-2 bg-golden text-white rounded-xl text-sm font-semibold hover:bg-golden-dark transition-colors shadow-sm"
              >
                Limpiar búsqueda
              </button>
            ) : null}
          </div>
        )}

        {/* Continuous List of Hymn Cards */}
        {!isLoading && filteredSearchables.length > 0 && (
          <div ref={cardsContainerRef} className="space-y-2">
            {filteredSearchables.slice(0, visibleLimit).map(item => (
              <HymnCard
                key={item.hymn.id}
                hymn={item.hymn}
                query={query}
                isFavorite={isFavorite(item.hymn.id)}
                onToggleFavorite={handleToggleFavorite}
                onOpenShare={handleOpenShare}
                onOpenPdfStudio={handleOpenPdfStudio}
                onNotify={showToast}
                typography={typography}
                fontSize={fontSize}
                chordNotation={chordNotation}
              />
            ))}

            {visibleLimit < filteredSearchables.length && (
              <div className="text-center py-6">
                <button
                  onClick={() => setVisibleLimit(prev => prev + 35)}
                  className="px-6 py-2.5 rounded-xl bg-white dark:bg-darkcard border border-parchment-border dark:border-jetcarbon-border text-jetcarbon dark:text-gray-200 text-sm font-semibold hover:bg-golden/10 hover:border-golden transition-all shadow-xs"
                >
                  Cargar más cantos ({filteredSearchables.length - visibleLimit} restantes)
                </button>
              </div>
            )}
          </div>
        )}
        </main>

        {/* Panel de Instrumentos Derecho (si isUkuleleOpen) */}
        {isUkuleleOpen && (
          <InstrumentCompanionPanel
            isOpen={isUkuleleOpen}
            onClose={handleCloseUkulele}
          />
        )}
      </div>

      {/* Floating Back to Top Button */}
      {showScrollTop && (
        <button
          onClick={scrollToTop}
          title="Volver arriba"
          className={`fixed bottom-6 ${isUkuleleOpen ? 'right-6 md:right-88 lg:right-104' : 'right-6'} p-3.5 rounded-full bg-golden text-white shadow-xl hover:bg-golden-dark transition-all transform hover:scale-105 z-30 active:scale-95`}
        >
          <ArrowUp size={22} />
        </button>
      )}

      {/* Share & PDF Export Modal */}
      <ShareModal
        isOpen={!!sharingHymn}
        onClose={handleCloseShare}
        hymn={sharingHymn?.hymn || null}
        activeVersionIndex={sharingHymn?.versionIndex || 0}
        onOpenPdfStudio={handleOpenPdfStudio}
      />

      {/* Estudio Interactivo de Edición e Impresión PDF */}
      <PdfStudioModal
        isOpen={!!pdfStudioHymn}
        onClose={handleClosePdfStudio}
        hymn={pdfStudioHymn?.hymn || null}
        activeVersionIndex={pdfStudioHymn?.versionIndex || 0}
        initialChordNotation={chordNotation}
      />

      {/* Modal de Alabanzas con Enlace de YouTube */}
      <YouTubeLinksModal
        isOpen={isYouTubeModalOpen}
        onClose={handleCloseYouTubeModal}
        hymns={youtubeHymns}
        onNotify={showToast}
      />

      {/* Notificación Toast Flotante */}
      <Toast
        message={toastMessage}
        onClose={() => setToastMessage(null)}
      />
    </div>
  );
}
