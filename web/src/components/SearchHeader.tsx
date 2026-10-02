import React, { useRef } from 'react';
import { Search, X, ChevronLeft, ChevronRight, Menu, Moon, Sun } from 'lucide-react';
import { ThemeMode } from '../types/hymn';

interface SearchHeaderProps {
  query: string;
  onQueryChange: (q: string) => void;
  onClear: () => void;
  matchesCount: number;
  currentMatchIndex: number;
  onNextMatch: () => void;
  onPrevMatch: () => void;
  onOpenDrawer: () => void;
  theme: ThemeMode;
  onToggleTheme: () => void;
}

export const SearchHeader: React.FC<SearchHeaderProps> = ({
  query,
  onQueryChange,
  onClear,
  matchesCount,
  currentMatchIndex,
  onNextMatch,
  onPrevMatch,
  onOpenDrawer,
  theme,
  onToggleTheme
}) => {
  const inputRef = useRef<HTMLInputElement>(null);

  const handleClear = () => {
    onClear();
    if (inputRef.current) {
      inputRef.current.focus();
    }
  };

  return (
    <header className="sticky top-0 z-30 bg-parchment/90 dark:bg-darkbg/90 backdrop-blur-md border-b border-parchment-border dark:border-jetcarbon-border transition-colors">
      <div className="max-w-4xl mx-auto px-4 py-3 flex items-center gap-3">
        {/* Drawer button */}
        <button
          onClick={onOpenDrawer}
          title="Abrir menú"
          className="p-2.5 rounded-xl text-jetcarbon dark:text-gray-200 hover:bg-black/5 dark:hover:bg-white/10 transition-colors"
        >
          <Menu size={22} />
        </button>

        {/* Search Input Box */}
        <div className="flex-1 relative flex items-center">
          <div className="absolute left-3.5 pointer-events-none text-jetcarbon-muted dark:text-gray-400">
            <Search size={18} />
          </div>
          <input
            ref={inputRef}
            type="text"
            value={query}
            onChange={e => onQueryChange(e.target.value)}
            placeholder="Buscar por número, título o letra..."
            className="w-full pl-10 pr-24 py-2.5 rounded-xl bg-white dark:bg-darkcard border border-parchment-border dark:border-jetcarbon-border text-jetcarbon dark:text-gray-100 placeholder-jetcarbon-muted/70 dark:placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-golden/50 focus:border-golden text-sm sm:text-base shadow-inner transition-all"
          />

          {/* Controls inside input */}
          <div className="absolute right-2 flex items-center gap-1">
            {query && (
              <button
                onClick={handleClear}
                title="Limpiar búsqueda"
                className="p-1 rounded-lg text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400 dark:hover:text-white"
              >
                <X size={16} />
              </button>
            )}
          </div>
        </div>

        {/* Match Navigation (when search has results) */}
        {matchesCount > 0 && query.trim() !== '' && (
          <div className="hidden sm:flex items-center gap-1 bg-white dark:bg-darkcard border border-parchment-border dark:border-jetcarbon-border rounded-xl px-2 py-1 shadow-sm text-xs font-semibold">
            <button
              onClick={onPrevMatch}
              title="Coincidencia anterior"
              className="p-1 rounded hover:bg-parchment dark:hover:bg-jetcarbon-light text-jetcarbon dark:text-gray-200"
            >
              <ChevronLeft size={16} />
            </button>
            <span className="px-1 text-jetcarbon dark:text-gray-300 min-w-[50px] text-center">
              {currentMatchIndex + 1} / {matchesCount}
            </span>
            <button
              onClick={onNextMatch}
              title="Siguiente coincidencia"
              className="p-1 rounded hover:bg-parchment dark:hover:bg-jetcarbon-light text-jetcarbon dark:text-gray-200"
            >
              <ChevronRight size={16} />
            </button>
          </div>
        )}

        {/* Quick Theme Toggle */}
        <button
          onClick={onToggleTheme}
          title={theme === 'dark' ? 'Modo claro' : 'Modo oscuro'}
          className="p-2.5 rounded-xl text-jetcarbon dark:text-golden-light hover:bg-black/5 dark:hover:bg-white/10 transition-colors"
        >
          {theme === 'dark' ? <Sun size={20} /> : <Moon size={20} />}
        </button>
      </div>

      {/* Mobile Match Counter Sub-bar */}
      {matchesCount > 0 && query.trim() !== '' && (
        <div className="sm:hidden flex items-center justify-between px-4 py-1.5 bg-golden-subtle/80 dark:bg-golden-darkSubtle/80 border-t border-golden/20 text-xs font-semibold">
          <span className="text-jetcarbon dark:text-golden-light">
            {matchesCount} {matchesCount === 1 ? 'coincidencia' : 'coincidencias'} encontrada{matchesCount === 1 ? '' : 's'}
          </span>
          <div className="flex items-center gap-2">
            <button
              onClick={onPrevMatch}
              className="p-1 rounded bg-white dark:bg-darkcard shadow-xs text-jetcarbon dark:text-gray-200"
            >
              <ChevronLeft size={16} />
            </button>
            <span className="text-jetcarbon dark:text-gray-200">
              {currentMatchIndex + 1} de {matchesCount}
            </span>
            <button
              onClick={onNextMatch}
              className="p-1 rounded bg-white dark:bg-darkcard shadow-xs text-jetcarbon dark:text-gray-200"
            >
              <ChevronRight size={16} />
            </button>
          </div>
        </div>
      )}
    </header>
  );
};
