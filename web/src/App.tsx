import { useState, useEffect, useMemo, useCallback } from 'react';
import type { Hymn, HymnCatalog } from './types/hymn';
import { createSearchableHymns, searchHymns, extractMatches } from './utils/searchEngine';
import { useFavorites } from './hooks/useFavorites';
import { useSettings } from './hooks/useSettings';
import { SearchHeader } from './components/SearchHeader';
import { HymnCard } from './components/HymnCard';
import { AppDrawer } from './components/AppDrawer';
import { ShareModal } from './components/ShareModal';
import { ArrowUp, BookX, Star } from 'lucide-react';

export function App() {
  const [catalog, setCatalog] = useState<HymnCatalog | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [query, setQuery] = useState<string>('');
  const [isDrawerOpen, setIsDrawerOpen] = useState<boolean>(false);
  const [showOnlyFavorites, setShowOnlyFavorites] = useState<boolean>(false);
  const [selectedAuthor, setSelectedAuthor] = useState<string | null>(null);
  const [currentMatchIndex, setCurrentMatchIndex] = useState<number>(0);
  const [showScrollTop, setShowScrollTop] = useState<boolean>(false);
  const [sharingHymn, setSharingHymn] = useState<{ hymn: Hymn; versionIndex: number } | null>(null);

  const { favorites, toggleFavorite, isFavorite, favoritesCount } = useFavorites();
  const { theme, toggleTheme, typography, setTypography, fontSize, setFontSize } = useSettings();

  // Load catalog on start with offline fallback
  useEffect(() => {
    async function loadCatalog() {
      try {
        const res = await fetch('/catalog.json');
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        const data: HymnCatalog = await res.json();
        setCatalog(data);
        try {
          localStorage.setItem('cancionero_cached_catalog', JSON.stringify(data));
        } catch {
          // localStorage might be full or disabled
        }
      } catch (err) {
        console.warn('Network fetch failed, attempting localStorage cached catalog...', err);
        try {
          const cached = localStorage.getItem('cancionero_cached_catalog');
          if (cached) {
            setCatalog(JSON.parse(cached));
          }
        } catch (cacheErr) {
          console.error('Failed to load cached catalog', cacheErr);
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

  // Monitor scroll for back to top button & auto-batch loading
  useEffect(() => {
    const handleScroll = () => {
      setShowScrollTop(window.scrollY > 400);
      if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 1000) {
        setVisibleLimit(prev => prev + 35);
      }
    };
    window.addEventListener('scroll', handleScroll, { passive: true });
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  // Searchables cache
  const searchables = useMemo(() => {
    if (!catalog?.hymns) return [];
    return createSearchableHymns(catalog.hymns);
  }, [catalog]);

  // Deep linking: detect ?id=XX or ?canto=XX or ?hymn=XX on initial load
  useEffect(() => {
    if (!catalog?.hymns || catalog.hymns.length === 0) return;
    const params = new URLSearchParams(window.location.search);
    const targetIdStr = params.get('id') || params.get('canto') || params.get('hymn');
    if (!targetIdStr) return;

    const targetId = parseInt(targetIdStr, 10);
    if (isNaN(targetId)) return;

    const targetIdx = searchables.findIndex(s => s.hymn.id === targetId);
    if (targetIdx !== -1) {
      if (targetIdx >= visibleLimit) {
        setVisibleLimit(targetIdx + 15);
      }
      setTimeout(() => {
        const el = document.getElementById(`hymn-${targetId}`);
        if (el) {
          el.scrollIntoView({ behavior: 'smooth', block: 'center' });
          el.classList.add('ring-2', 'ring-golden', 'ring-offset-4', 'ring-offset-parchment', 'dark:ring-offset-darkbg');
          setTimeout(() => {
            el.classList.remove('ring-2', 'ring-golden', 'ring-offset-4', 'ring-offset-parchment', 'dark:ring-offset-darkbg');
          }, 3000);
        }
      }, 250);
    }
  }, [catalog, searchables]);

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
  const scrollToMatch = useCallback((index: number) => {
    if (!matches || matches.length === 0) return;
    const match = matches[index];
    if (!match) return;

    const matchHymnIdx = filteredSearchables.findIndex(s => s.hymn.id === match.hymnId);
    if (matchHymnIdx >= 0 && matchHymnIdx >= visibleLimit) {
      setVisibleLimit(matchHymnIdx + 20);
    }

    setTimeout(() => {
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

  return (
    <div className="min-h-screen bg-parchment dark:bg-darkbg text-jetcarbon dark:text-gray-100 flex flex-col font-sans transition-colors duration-200">
      {/* Sticky Navigation & Search Header */}
      <SearchHeader
        query={query}
        onQueryChange={setQuery}
        onClear={() => setQuery('')}
        matchesCount={matches.length}
        currentMatchIndex={currentMatchIndex}
        onNextMatch={handleNextMatch}
        onPrevMatch={handlePrevMatch}
        onOpenDrawer={() => setIsDrawerOpen(true)}
        theme={theme}
        onToggleTheme={toggleTheme}
      />

      {/* Main Feed Container */}
      <main className="flex-1 max-w-4xl w-full mx-auto px-4 py-6">
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
                : `No hubo coincidencias para "${query}". Intenta buscar por una sola palabra clave o por el número del himno.`}
            </p>
            {query && (
              <button
                onClick={() => setQuery('')}
                className="px-4 py-2 bg-golden text-white rounded-xl text-sm font-semibold hover:bg-golden-dark transition-colors shadow-sm"
              >
                Limpiar búsqueda
              </button>
            )}
          </div>
        )}

        {/* Continuous List of Hymn Cards */}
        {!isLoading && filteredSearchables.length > 0 && (
          <div className="space-y-2">
            {filteredSearchables.slice(0, visibleLimit).map(item => (
              <HymnCard
                key={item.hymn.id}
                hymn={item.hymn}
                query={query}
                isFavorite={isFavorite(item.hymn.id)}
                onToggleFavorite={toggleFavorite}
                onOpenShare={(hymn, vIdx) => setSharingHymn({ hymn, versionIndex: vIdx })}
                typography={typography}
                fontSize={fontSize}
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

      {/* Floating Back to Top Button */}
      {showScrollTop && (
        <button
          onClick={scrollToTop}
          title="Volver arriba"
          className="fixed bottom-6 right-6 p-3.5 rounded-full bg-golden text-white shadow-xl hover:bg-golden-dark transition-all transform hover:scale-105 z-40 active:scale-95"
        >
          <ArrowUp size={22} />
        </button>
      )}

      {/* Slide-out Settings & Filters Drawer */}
      <AppDrawer
        isOpen={isDrawerOpen}
        onClose={() => setIsDrawerOpen(false)}
        totalHymns={searchables.length}
        favoritesCount={favoritesCount}
        showOnlyFavorites={showOnlyFavorites}
        onToggleFavoritesFilter={() => setShowOnlyFavorites(prev => !prev)}
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
      />

      {/* Share & PDF Export Modal */}
      <ShareModal
        isOpen={!!sharingHymn}
        onClose={() => setSharingHymn(null)}
        hymn={sharingHymn?.hymn || null}
        activeVersionIndex={sharingHymn?.versionIndex || 0}
      />
    </div>
  );
}
