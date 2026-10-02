import React, { useState } from 'react';
import {
  HARMONIC_FAMILIES,
  PREDOMINANT_KEYS,
  UKULELE_CHORDS_DB,
  UkuleleChord
} from '../data/ukuleleChords';
import { UkuleleChordDiagram } from './UkuleleChordDiagram';
import { X, Music2, Grid, BookMarked, Search } from 'lucide-react';

interface UkuleleCompanionPanelProps {
  isOpen: boolean;
  onClose: () => void;
}

export const UkuleleCompanionPanel: React.FC<UkuleleCompanionPanelProps> = ({
  isOpen,
  onClose
}) => {
  const [selectedKey, setSelectedKey] = useState<string>('C');
  const [activeTab, setActiveTab] = useState<'family' | 'all'>('family');
  const [searchQuery, setSearchQuery] = useState<string>('');
  const [filterType, setFilterType] = useState<string>('all');

  if (!isOpen) return null;

  const currentFamily = HARMONIC_FAMILIES[selectedKey] || HARMONIC_FAMILIES['C'];

  // Lista de todos los acordes filtrada
  const allChordsList: UkuleleChord[] = Object.values(UKULELE_CHORDS_DB).filter(c => {
    if (filterType !== 'all' && c.type !== filterType) return false;
    if (searchQuery.trim()) {
      const q = searchQuery.trim().toLowerCase();
      return (
        c.name.toLowerCase().includes(q) ||
        c.symbol.toLowerCase().includes(q) ||
        c.typeName.toLowerCase().includes(q)
      );
    }
    return true;
  });

  return (
    <aside className="w-80 xl:w-96 flex-shrink-0 bg-white dark:bg-darkcard border-l border-parchment-border dark:border-jetcarbon-border flex flex-col h-[calc(100vh-65px)] sticky top-[65px] shadow-lg overflow-hidden animate-in slide-in-from-right duration-200">
      {/* Header del Panel */}
      <div className="p-4 border-b border-parchment-border dark:border-jetcarbon-border flex items-center justify-between bg-parchment/60 dark:bg-darkbg/60">
        <div className="flex items-center gap-2.5">
          <div className="w-8 h-8 rounded-lg bg-golden/15 text-golden-dark dark:text-golden flex items-center justify-center">
            <Music2 size={18} />
          </div>
          <div>
            <h3 className="font-bold text-sm text-jetcarbon dark:text-gray-100">
              Guía de Ukelele
            </h3>
            <p className="text-[11px] text-jetcarbon-muted dark:text-gray-400">
              Afinación G-C-E-A • Asistente de Alabanza
            </p>
          </div>
        </div>

        <button
          onClick={onClose}
          title="Cerrar panel de ukelele"
          className="p-1.5 rounded-lg text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400 dark:hover:text-white hover:bg-black/5 dark:hover:bg-white/10"
        >
          <X size={18} />
        </button>
      </div>

      {/* Tabs: Familia Armónica vs Diccionario Completo */}
      <div className="flex border-b border-parchment-border dark:border-jetcarbon-border px-3 pt-2 bg-parchment/30 dark:bg-darkbg/30">
        <button
          onClick={() => setActiveTab('family')}
          className={`flex-1 pb-2 text-xs font-bold border-b-2 flex items-center justify-center gap-1.5 transition-all ${
            activeTab === 'family'
              ? 'border-golden text-golden-dark dark:text-golden'
              : 'border-transparent text-jetcarbon-muted dark:text-gray-400 hover:text-jetcarbon'
          }`}
        >
          <BookMarked size={14} />
          <span>Familia Armónica</span>
        </button>
        <button
          onClick={() => setActiveTab('all')}
          className={`flex-1 pb-2 text-xs font-bold border-b-2 flex items-center justify-center gap-1.5 transition-all ${
            activeTab === 'all'
              ? 'border-golden text-golden-dark dark:text-golden'
              : 'border-transparent text-jetcarbon-muted dark:text-gray-400 hover:text-jetcarbon'
          }`}
        >
          <Grid size={14} />
          <span>Todos ({Object.keys(UKULELE_CHORDS_DB).length})</span>
        </button>
      </div>

      {/* Contenido según Tab */}
      <div className="flex-1 overflow-y-auto p-4 space-y-4">
        {activeTab === 'family' ? (
          <>
            {/* Selector de Tono Base Predominante (DO, SOL, RE, etc.) */}
            <div>
              <label className="text-[11px] font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 block mb-2">
                Tono Base de la Alabanza
              </label>
              <div className="grid grid-cols-4 gap-1.5">
                {PREDOMINANT_KEYS.map(k => (
                  <button
                    key={k.symbol}
                    onClick={() => setSelectedKey(k.symbol)}
                    className={`py-1.5 px-2 rounded-lg text-xs font-bold border transition-all text-center ${
                      selectedKey === k.symbol
                        ? 'bg-golden text-white border-golden shadow-2xs'
                        : 'bg-parchment dark:bg-darkbg text-jetcarbon dark:text-gray-300 border-parchment-border dark:border-jetcarbon-border hover:border-golden/50'
                    }`}
                  >
                    <span>{k.name}</span>
                    <span className="text-[10px] opacity-75 ml-1">({k.symbol})</span>
                  </button>
                ))}
              </div>
            </div>

            {/* Tarjetas de Acordes Acompañantes Organizados */}
            <div className="space-y-3 pt-1">
              <div className="flex items-center justify-between">
                <span className="text-xs font-bold text-jetcarbon dark:text-gray-200">
                  Acordes para tono {currentFamily.keyName} ({currentFamily.keySymbol})
                </span>
                <span className="text-[11px] text-golden font-semibold">
                  Grados I • IV • V • vi
                </span>
              </div>

              {/* Tónica Principal (I) */}
              <div>
                <span className="text-[10px] font-bold uppercase tracking-wider text-golden block mb-1">
                  1. Tónica Principal (Inicio / Reposo)
                </span>
                <UkuleleChordDiagram chord={currentFamily.tonic} size="lg" />
              </div>

              {/* Subdominante y Dominante (IV y V / V7) */}
              <div>
                <span className="text-[10px] font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 block mb-1">
                  2. Acompañamiento y Tensión (IV y V / V7)
                </span>
                <div className="grid grid-cols-2 gap-2">
                  <UkuleleChordDiagram chord={currentFamily.subdominant} size="sm" />
                  <UkuleleChordDiagram chord={currentFamily.dominant} size="sm" />
                </div>
              </div>

              {/* Dominante 7 y Relativa Menor */}
              <div>
                <span className="text-[10px] font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 block mb-1">
                  3. Relativa Menor y Séptima
                </span>
                <div className="grid grid-cols-2 gap-2">
                  <UkuleleChordDiagram chord={currentFamily.relativeMinor} size="sm" />
                  <UkuleleChordDiagram chord={currentFamily.dominant7} size="sm" />
                </div>
              </div>

              {/* Secundarios frecuentes */}
              <div>
                <span className="text-[10px] font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 block mb-1">
                  4. Secundarios Frecuentes
                </span>
                <div className="grid grid-cols-2 gap-2">
                  <UkuleleChordDiagram chord={currentFamily.secondary1} size="sm" />
                  <UkuleleChordDiagram chord={currentFamily.secondary2} size="sm" />
                </div>
              </div>
            </div>
          </>
        ) : (
          /* Pestaña: Diccionario Completo */
          <div className="space-y-3">
            {/* Buscador de acorde */}
            <div className="relative">
              <Search size={14} className="absolute left-3 top-3 text-jetcarbon-muted dark:text-gray-400" />
              <input
                type="text"
                value={searchQuery}
                onChange={e => setSearchQuery(e.target.value)}
                placeholder="Buscar (ej. Dm, Fa7, G...)"
                className="w-full pl-8 pr-3 py-1.5 text-xs rounded-lg border border-parchment-border dark:border-jetcarbon-border bg-parchment/60 dark:bg-darkbg text-jetcarbon dark:text-gray-100 placeholder-jetcarbon-muted/70 focus:outline-none focus:border-golden"
              />
            </div>

            {/* Filtros rápidos por tipo */}
            <div className="flex gap-1 overflow-x-auto pb-1">
              {[
                { id: 'all', label: 'Todos' },
                { id: 'major', label: 'Mayores' },
                { id: 'minor', label: 'Menores' },
                { id: '7th', label: '7mas' },
                { id: 'm7', label: 'm7' }
              ].map(f => (
                <button
                  key={f.id}
                  onClick={() => setFilterType(f.id)}
                  className={`px-2 py-1 rounded text-[10px] font-semibold whitespace-nowrap transition-colors ${
                    filterType === f.id
                      ? 'bg-golden text-white'
                      : 'bg-parchment dark:bg-darkbg text-jetcarbon-muted hover:text-jetcarbon'
                  }`}
                >
                  {f.label}
                </button>
              ))}
            </div>

            {/* Grilla de Acordes */}
            <div className="grid grid-cols-2 gap-2 max-h-[calc(100vh-270px)] overflow-y-auto pr-1">
              {allChordsList.map(chord => (
                <UkuleleChordDiagram key={chord.symbol} chord={chord} size="sm" />
              ))}
            </div>
          </div>
        )}
      </div>

      {/* Pie del Panel */}
      <div className="p-3 border-t border-parchment-border dark:border-jetcarbon-border bg-parchment/40 dark:bg-darkbg/40 text-center text-[10px] text-jetcarbon-muted dark:text-gray-400">
        Ukelele Soprano / Concierto / Tenor
      </div>
    </aside>
  );
};
