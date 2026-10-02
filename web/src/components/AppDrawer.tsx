import React from 'react';
import { X, Star, BookOpen, User, Type, Moon, Sun, Sliders } from 'lucide-react';
import { TypographyType, ThemeMode } from '../types/hymn';

interface AppDrawerProps {
  isOpen: boolean;
  onClose: () => void;
  totalHymns: number;
  favoritesCount: number;
  showOnlyFavorites: boolean;
  onToggleFavoritesFilter: () => void;
  selectedAuthor: string | null;
  onSelectAuthor: (author: string | null) => void;
  authors: { name: string; count: number }[];
  typography: TypographyType;
  onChangeTypography: (t: TypographyType) => void;
  fontSize: number;
  onChangeFontSize: (s: number) => void;
  theme: ThemeMode;
  onToggleTheme: () => void;
  catalogVersion: number;
}

export const AppDrawer: React.FC<AppDrawerProps> = ({
  isOpen,
  onClose,
  totalHymns,
  favoritesCount,
  showOnlyFavorites,
  onToggleFavoritesFilter,
  selectedAuthor,
  onSelectAuthor,
  authors,
  typography,
  onChangeTypography,
  fontSize,
  onChangeFontSize,
  theme,
  onToggleTheme,
  catalogVersion
}) => {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 overflow-hidden">
      {/* Backdrop */}
      <div
        className="absolute inset-0 bg-black/50 backdrop-blur-xs transition-opacity"
        onClick={onClose}
      />

      {/* Drawer Panel */}
      <aside className="absolute inset-y-0 left-0 max-w-xs sm:max-w-sm w-full bg-parchment-paper dark:bg-darkcard shadow-2xl flex flex-col z-10 transition-transform transform duration-300 border-r border-parchment-border dark:border-jetcarbon-border">
        {/* Header */}
        <div className="p-5 border-b border-parchment-border dark:border-jetcarbon-border flex items-center justify-between bg-white dark:bg-darkbg">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-xl bg-jetcarbon flex items-center justify-center border border-golden/40 shadow-sm">
              <span className="text-golden font-bold text-lg">✝</span>
            </div>
            <div>
              <h1 className="font-bold text-lg text-jetcarbon dark:text-gray-100">Cancionero Digital</h1>
              <p className="text-xs text-jetcarbon-muted dark:text-gray-400">Catálogo v{catalogVersion} • {totalHymns} cantos</p>
            </div>
          </div>
          <button
            onClick={onClose}
            className="p-2 rounded-xl text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400 dark:hover:text-white hover:bg-parchment dark:hover:bg-jetcarbon-light"
          >
            <X size={20} />
          </button>
        </div>

        {/* Scrollable Content */}
        <div className="flex-1 overflow-y-auto p-5 space-y-6">
          {/* Navigation / Filters Section */}
          <section>
            <h2 className="text-xs font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 mb-2">
              Filtros Principales
            </h2>
            <div className="space-y-1">
              <button
                onClick={() => {
                  if (showOnlyFavorites) onToggleFavoritesFilter();
                  onSelectAuthor(null);
                  onClose();
                }}
                className={`w-full flex items-center justify-between px-3.5 py-2.5 rounded-xl text-sm font-medium transition-all ${
                  !showOnlyFavorites && selectedAuthor === null
                    ? 'bg-golden text-white shadow-sm'
                    : 'text-jetcarbon dark:text-gray-200 hover:bg-parchment dark:hover:bg-jetcarbon-light'
                }`}
              >
                <div className="flex items-center gap-3">
                  <BookOpen size={18} />
                  <span>Todos los himnos</span>
                </div>
                <span className="text-xs opacity-80">{totalHymns}</span>
              </button>

              <button
                onClick={() => {
                  if (!showOnlyFavorites) onToggleFavoritesFilter();
                  onSelectAuthor(null);
                  onClose();
                }}
                className={`w-full flex items-center justify-between px-3.5 py-2.5 rounded-xl text-sm font-medium transition-all ${
                  showOnlyFavorites
                    ? 'bg-golden text-white shadow-sm'
                    : 'text-jetcarbon dark:text-gray-200 hover:bg-parchment dark:hover:bg-jetcarbon-light'
                }`}
              >
                <div className="flex items-center gap-3">
                  <Star size={18} className={showOnlyFavorites ? 'fill-white' : 'text-golden fill-golden'} />
                  <span>Favoritos</span>
                </div>
                <span className="text-xs opacity-80">{favoritesCount}</span>
              </button>
            </div>
          </section>

          {/* Typography Settings Section */}
          <section>
            <h2 className="text-xs font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 mb-2 flex items-center gap-1.5">
              <Type size={14} />
              <span>Tipografía de Lectura</span>
            </h2>
            <div className="grid grid-cols-3 gap-2">
              {(['serif', 'sans', 'mono'] as TypographyType[]).map(type => (
                <button
                  key={type}
                  onClick={() => onChangeTypography(type)}
                  className={`py-2 px-3 rounded-xl border text-sm font-medium transition-all text-center capitalize ${
                    typography === type
                      ? 'border-golden bg-golden/10 text-golden-dark dark:text-golden font-bold'
                      : 'border-parchment-border dark:border-jetcarbon-border text-jetcarbon dark:text-gray-300 hover:bg-white dark:hover:bg-jetcarbon-light'
                  }`}
                >
                  {type}
                </button>
              ))}
            </div>
          </section>

          {/* Font Size Slider */}
          <section>
            <div className="flex items-center justify-between mb-2">
              <h2 className="text-xs font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 flex items-center gap-1.5">
                <Sliders size={14} />
                <span>Tamaño de Letra</span>
              </h2>
              <span className="text-xs font-bold text-golden">{fontSize} px</span>
            </div>
            <div className="flex items-center gap-3">
              <button
                onClick={() => onChangeFontSize(Math.max(14, fontSize - 1))}
                className="w-8 h-8 rounded-lg bg-white dark:bg-darkbg border border-parchment-border dark:border-jetcarbon-border text-jetcarbon dark:text-gray-200 font-bold flex items-center justify-center hover:bg-golden/10"
              >
                -
              </button>
              <input
                type="range"
                min="14"
                max="30"
                step="1"
                value={fontSize}
                onChange={e => onChangeFontSize(Number(e.target.value))}
                className="flex-1 accent-golden h-2 bg-parchment-border dark:bg-jetcarbon-border rounded-lg cursor-pointer"
              />
              <button
                onClick={() => onChangeFontSize(Math.min(30, fontSize + 1))}
                className="w-8 h-8 rounded-lg bg-white dark:bg-darkbg border border-parchment-border dark:border-jetcarbon-border text-jetcarbon dark:text-gray-200 font-bold flex items-center justify-center hover:bg-golden/10"
              >
                +
              </button>
            </div>
          </section>

          {/* Theme Toggle Button */}
          <section>
            <h2 className="text-xs font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 mb-2">
              Tema Visual
            </h2>
            <button
              onClick={onToggleTheme}
              className="w-full flex items-center justify-between px-3.5 py-2.5 rounded-xl border border-parchment-border dark:border-jetcarbon-border bg-white dark:bg-darkbg text-sm font-medium text-jetcarbon dark:text-gray-200 hover:bg-golden/5 transition-colors"
            >
              <div className="flex items-center gap-3">
                {theme === 'dark' ? <Moon size={18} className="text-golden" /> : <Sun size={18} className="text-golden" />}
                <span>{theme === 'dark' ? 'Modo Oscuro' : 'Modo Claro (Pergamino)'}</span>
              </div>
              <span className="text-xs text-jetcarbon-muted dark:text-gray-400">Alternar</span>
            </button>
          </section>

          {/* Filter by Author Section (if any author has names) */}
          {authors.length > 0 && (
            <section>
              <div className="flex items-center justify-between mb-2">
                <h2 className="text-xs font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 flex items-center gap-1.5">
                  <User size={14} />
                  <span>Autores</span>
                </h2>
                {selectedAuthor && (
                  <button
                    onClick={() => onSelectAuthor(null)}
                    className="text-xs text-golden hover:underline"
                  >
                    Limpiar
                  </button>
                )}
              </div>
              <div className="max-h-48 overflow-y-auto space-y-1 pr-1">
                {authors.map(({ name, count }) => (
                  <button
                    key={name}
                    onClick={() => {
                      onSelectAuthor(selectedAuthor === name ? null : name);
                      onClose();
                    }}
                    className={`w-full flex items-center justify-between px-3 py-1.5 rounded-lg text-xs transition-colors ${
                      selectedAuthor === name
                        ? 'bg-golden/15 text-golden-dark dark:text-golden font-bold'
                        : 'text-jetcarbon dark:text-gray-300 hover:bg-white dark:hover:bg-jetcarbon-light'
                    }`}
                  >
                    <span className="truncate">{name}</span>
                    <span className="opacity-70 ml-2">{count}</span>
                  </button>
                ))}
              </div>
            </section>
          )}
        </div>

        {/* Footer */}
        <div className="p-4 border-t border-parchment-border dark:border-jetcarbon-border text-center text-xs text-jetcarbon-muted dark:text-gray-400 bg-white/50 dark:bg-darkbg/50">
          <p className="flex items-center justify-center gap-1.5 font-medium">
            <span className="w-2 h-2 rounded-full bg-green-500 inline-block animate-pulse"></span>
            100% Offline • PWA Ready
          </p>
        </div>
      </aside>
    </div>
  );
};
