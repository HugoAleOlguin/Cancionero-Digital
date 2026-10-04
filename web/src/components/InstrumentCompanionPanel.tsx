import React, { useState, useEffect } from 'react';
import {
  HARMONIC_FAMILIES,
  ALL_MAJOR_KEYS,
  ALL_MINOR_KEYS,
  UKULELE_CHORDS_DB,
  UkuleleChord
} from '../data/ukuleleChords';
import {
  GUITAR_CHORDS_DB,
  GUITAR_HARMONIC_FAMILIES
} from '../data/guitarChords';
import { ChordDiagram } from './ChordDiagram';
import { ChordDetailInspector } from './ChordDetailInspector';
import { X, Grid, BookMarked, Search, Music } from 'lucide-react';

export type InstrumentType = 'ukulele' | 'guitar';

interface InstrumentCompanionPanelProps {
  isOpen: boolean;
  onClose: () => void;
}

// Icono vectorial limpio de Ukelele (sin emojis ni cruces)
const UkuleleIcon = ({ size = 16 }: { size?: number }) => (
  <svg width={size} height={size} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <path d="M12 10c-2.2 0-4 1.5-4 4 0 2.8 2 4.5 4 4.5s4-1.7 4-4.5c0-2.5-1.8-4-4-4z" />
    <circle cx="12" cy="14" r="1.5" />
    <path d="M11 10V5h2v5" />
    <path d="M10.5 5h3V2h-3z" />
    <circle cx="9" cy="3" r="0.75" fill="currentColor" />
    <circle cx="15" cy="3" r="0.75" fill="currentColor" />
    <circle cx="9" cy="4.5" r="0.75" fill="currentColor" />
    <circle cx="15" cy="4.5" r="0.75" fill="currentColor" />
  </svg>
);

// Icono vectorial limpio de Guitarra (sin emojis ni cruces)
const GuitarIcon = ({ size = 16 }: { size?: number }) => (
  <svg width={size} height={size} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <path d="M12 9c-1.5 0-3 1-3 2.5 0 1.2.8 1.8 1.5 2-.8.5-1.5 1.5-1.5 3 0 2.5 1.8 4 3 4s3-1.5 3-4c0-1.5-.7-2.5-1.5-3 .7-.2 1.5-.8 1.5-2 0-1.5-1.5-2.5-3-2.5z" />
    <circle cx="12" cy="15" r="1.5" />
    <path d="M11 9V4h2v5" />
    <path d="M10.5 4h3V1h-3z" />
    <circle cx="9" cy="1.8" r="0.6" fill="currentColor" />
    <circle cx="15" cy="1.8" r="0.6" fill="currentColor" />
    <circle cx="9" cy="2.8" r="0.6" fill="currentColor" />
    <circle cx="15" cy="2.8" r="0.6" fill="currentColor" />
    <circle cx="9" cy="3.8" r="0.6" fill="currentColor" />
    <circle cx="15" cy="3.8" r="0.6" fill="currentColor" />
  </svg>
);

export const InstrumentCompanionPanel: React.FC<InstrumentCompanionPanelProps> = ({
  isOpen,
  onClose
}) => {
  const [instrument, setInstrument] = useState<InstrumentType>(() => {
    try {
      const stored = localStorage.getItem('cancionero_instrument');
      if (stored === 'ukulele' || stored === 'guitar') return stored;
    } catch {
      // fallback
    }
    return 'guitar';
  });

  const [selectedKey, setSelectedKey] = useState<string>('C');
  const [tonalityMode, setTonalityMode] = useState<'major' | 'minor'>('major');
  const [activeTab, setActiveTab] = useState<'family' | 'all'>('family');
  const [searchQuery, setSearchQuery] = useState<string>('');
  const [filterType, setFilterType] = useState<string>('all');

  // Estado del Inspector Interactivo de Acorde
  const [selectedChord, setSelectedChord] = useState<UkuleleChord | null>(null);
  const [isInspectorModal, setIsInspectorModal] = useState<boolean>(false);

  useEffect(() => {
    try {
      localStorage.setItem('cancionero_instrument', instrument);
    } catch {
      // fallback
    }
  }, [instrument]);

  if (!isOpen) return null;

  // Base de datos activa según instrumento
  const isGuitar = instrument === 'guitar';
  const activeChordsDb = isGuitar ? GUITAR_CHORDS_DB : UKULELE_CHORDS_DB;
  const activeFamilies = isGuitar ? GUITAR_HARMONIC_FAMILIES : HARMONIC_FAMILIES;

  const currentFamily = activeFamilies[selectedKey] || activeFamilies['C'];

  // Cambiar instrumento preservando el acorde activo
  const handleInstrumentChange = (nextInst: InstrumentType) => {
    setInstrument(nextInst);
    if (selectedChord) {
      const nextDb = nextInst === 'guitar' ? GUITAR_CHORDS_DB : UKULELE_CHORDS_DB;
      const equivalent = nextDb[selectedChord.symbol];
      if (equivalent) {
        setSelectedChord(equivalent);
      }
    }
  };

  // Buscar y seleccionar acorde por símbolo o nombre (para resoluciones armónicas)
  const handleSelectChordBySymbolOrName = (target: string) => {
    const direct = activeChordsDb[target];
    if (direct) {
      setSelectedChord(direct);
      return;
    }
    // Buscar por nombre
    const found = Object.values(activeChordsDb).find(
      c => c.name.toLowerCase() === target.toLowerCase() || c.symbol.toLowerCase() === target.toLowerCase()
    );
    if (found) {
      setSelectedChord(found);
    }
  };

  // Lista de todos los acordes filtrada
  const allChordsList: UkuleleChord[] = Object.values(activeChordsDb).filter(c => {
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
    <>
      <aside className="w-80 xl:w-96 flex-shrink-0 bg-white dark:bg-darkcard border-l border-parchment-border dark:border-jetcarbon-border flex flex-col h-[calc(100vh-65px)] sticky top-[65px] shadow-lg overflow-hidden animate-in slide-in-from-right duration-200">
        {/* Header del Panel */}
        <div className="p-4 border-b border-parchment-border dark:border-jetcarbon-border bg-parchment/60 dark:bg-darkbg/60">
          <div className="flex items-center justify-between mb-3">
            <div>
              <h3 className="font-bold text-sm text-jetcarbon dark:text-gray-100 flex items-center gap-1.5">
                <Music size={15} className="text-golden" />
                <span>Acompañamiento Musical</span>
              </h3>
              <p className="text-[11px] text-jetcarbon-muted dark:text-gray-400">
                {isGuitar ? 'Guitarra • Afinación E-A-D-G-B-E' : 'Ukelele • Afinación G-C-E-A'}
              </p>
            </div>

            <button
              onClick={onClose}
              title="Cerrar panel de instrumentos"
              className="p-1.5 rounded-lg text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400 dark:hover:text-white hover:bg-black/5 dark:hover:bg-white/10 transition-colors"
            >
              <X size={18} />
            </button>
          </div>

          {/* Conmutador Simple y Directo: Ukelele vs Guitarra con sus SVGs */}
          <div className="grid grid-cols-2 p-1 bg-parchment-border/40 dark:bg-darkbg/80 rounded-xl gap-1">
            <button
              onClick={() => handleInstrumentChange('ukulele')}
              className={`flex items-center justify-center gap-2 py-2 px-3 rounded-lg text-xs font-bold transition-all ${
                instrument === 'ukulele'
                  ? 'bg-golden text-white shadow-xs'
                  : 'text-jetcarbon-muted dark:text-gray-400 hover:text-jetcarbon dark:hover:text-gray-200'
              }`}
            >
              <UkuleleIcon size={16} />
              <span>Ukelele</span>
            </button>
            <button
              onClick={() => handleInstrumentChange('guitar')}
              className={`flex items-center justify-center gap-2 py-2 px-3 rounded-lg text-xs font-bold transition-all ${
                instrument === 'guitar'
                  ? 'bg-golden text-white shadow-xs'
                  : 'text-jetcarbon-muted dark:text-gray-400 hover:text-jetcarbon dark:hover:text-gray-200'
              }`}
            >
              <GuitarIcon size={16} />
              <span>Guitarra</span>
            </button>
          </div>
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
            <span>Todos ({Object.keys(activeChordsDb).length})</span>
          </button>
        </div>

        {/* Contenido según Tab */}
        <div className="flex-1 overflow-y-auto p-4 space-y-4">
          {/* Inspector Fijado en Barra Lateral (si hay un acorde seleccionado) */}
          {selectedChord && (
            <div className="mb-2">
              <ChordDetailInspector
                chord={selectedChord}
                instrument={instrument}
                isModal={false}
                onClose={() => setSelectedChord(null)}
                onSelectChord={handleSelectChordBySymbolOrName}
                onToggleDock={() => setIsInspectorModal(true)}
              />
            </div>
          )}

          {activeTab === 'family' ? (
            <>
              {/* Selector de Tono Base Predominante (Mayores vs Menores) */}
              <div>
                <div className="flex items-center justify-between mb-2">
                  <label className="text-[11px] font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 block">
                    Tono Base del Canto
                  </label>
                  <div className="flex gap-1 text-[10px] font-bold bg-parchment dark:bg-darkbg p-0.5 rounded-lg border border-parchment-border dark:border-jetcarbon-border">
                    <button
                      onClick={() => {
                        setTonalityMode('major');
                        if (!ALL_MAJOR_KEYS.some(k => k.symbol === selectedKey)) {
                          setSelectedKey('C');
                        }
                      }}
                      className={`px-2 py-0.5 rounded-md transition-colors ${
                        tonalityMode === 'major'
                          ? 'bg-golden text-white'
                          : 'text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400'
                      }`}
                    >
                      Mayores (12)
                    </button>
                    <button
                      onClick={() => {
                        setTonalityMode('minor');
                        if (!ALL_MINOR_KEYS.some(k => k.symbol === selectedKey)) {
                          setSelectedKey('Am');
                        }
                      }}
                      className={`px-2 py-0.5 rounded-md transition-colors ${
                        tonalityMode === 'minor'
                          ? 'bg-golden text-white'
                          : 'text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400'
                      }`}
                    >
                      Menores (7)
                    </button>
                  </div>
                </div>

                <div className="grid grid-cols-4 gap-1.5">
                  {(tonalityMode === 'major' ? ALL_MAJOR_KEYS : ALL_MINOR_KEYS).map(k => (
                    <button
                      key={k.symbol}
                      onClick={() => setSelectedKey(k.symbol)}
                      className={`py-1.5 px-2 rounded-lg text-xs font-bold border transition-all text-center ${
                        selectedKey === k.symbol
                          ? 'bg-golden text-white border-golden shadow-2xs scale-98'
                          : 'bg-parchment dark:bg-darkbg text-jetcarbon dark:text-gray-300 border-parchment-border dark:border-jetcarbon-border hover:border-golden/50'
                      }`}
                    >
                      <span>{k.name}</span>
                      <span className="text-[10px] opacity-75 ml-0.5">({k.symbol})</span>
                    </button>
                  ))}
                </div>
              </div>

              {/* Tarjetas de Acordes Acompañantes Organizados */}
              <div className="space-y-3 pt-1">
                <div className="flex items-center justify-between">
                  <span className="text-xs font-bold text-jetcarbon dark:text-gray-200">
                    {currentFamily.keyName} ({currentFamily.keySymbol}) en {isGuitar ? 'Guitarra' : 'Ukelele'}
                  </span>
                  <span className="text-[11px] text-golden font-semibold">
                    {currentFamily.isMinor ? 'Tono Menor' : 'Grados I • IV • V • vi'}
                  </span>
                </div>

                {/* Tónica Principal */}
                <div>
                  <span className="text-[10px] font-bold uppercase tracking-wider text-golden block mb-1">
                    1. Tónica Principal (Inicio / Reposo)
                  </span>
                  <ChordDiagram
                    chord={currentFamily.tonic}
                    size="lg"
                    interactive
                    isSelected={selectedChord?.symbol === currentFamily.tonic.symbol}
                    onClick={() => setSelectedChord(currentFamily.tonic)}
                  />
                </div>

                {/* Subdominante y Dominante */}
                <div>
                  <span className="text-[10px] font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 block mb-1">
                    2. Acompañamiento y Tensión ({currentFamily.isMinor ? 'iv y V' : 'IV y V'})
                  </span>
                  <div className="grid grid-cols-2 gap-2">
                    <ChordDiagram
                      chord={currentFamily.subdominant}
                      size="sm"
                      interactive
                      isSelected={selectedChord?.symbol === currentFamily.subdominant.symbol}
                      onClick={() => setSelectedChord(currentFamily.subdominant)}
                    />
                    <ChordDiagram
                      chord={currentFamily.dominant}
                      size="sm"
                      interactive
                      isSelected={selectedChord?.symbol === currentFamily.dominant.symbol}
                      onClick={() => setSelectedChord(currentFamily.dominant)}
                    />
                  </div>
                </div>

                {/* Dominante 7 y Relativa */}
                <div>
                  <span className="text-[10px] font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 block mb-1">
                    3. {currentFamily.isMinor ? 'Relativa Mayor y Dominante 7' : 'Relativa Menor y Séptima'}
                  </span>
                  <div className="grid grid-cols-2 gap-2">
                    <ChordDiagram
                      chord={currentFamily.relativeMinor}
                      size="sm"
                      interactive
                      isSelected={selectedChord?.symbol === currentFamily.relativeMinor.symbol}
                      onClick={() => setSelectedChord(currentFamily.relativeMinor)}
                    />
                    <ChordDiagram
                      chord={currentFamily.dominant7}
                      size="sm"
                      interactive
                      isSelected={selectedChord?.symbol === currentFamily.dominant7.symbol}
                      onClick={() => setSelectedChord(currentFamily.dominant7)}
                    />
                  </div>
                </div>

                {/* Secundarios frecuentes */}
                <div>
                  <span className="text-[10px] font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 block mb-1">
                    4. Acordes Secundarios Frecuentes
                  </span>
                  <div className="grid grid-cols-2 gap-2">
                    <ChordDiagram
                      chord={currentFamily.secondary1}
                      size="sm"
                      interactive
                      isSelected={selectedChord?.symbol === currentFamily.secondary1.symbol}
                      onClick={() => setSelectedChord(currentFamily.secondary1)}
                    />
                    <ChordDiagram
                      chord={currentFamily.secondary2}
                      size="sm"
                      interactive
                      isSelected={selectedChord?.symbol === currentFamily.secondary2.symbol}
                      onClick={() => setSelectedChord(currentFamily.secondary2)}
                    />
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
                  placeholder="Buscar acorde (ej. Dm, Fa7, Sol...)"
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
                  { id: 'm7', label: 'm7' },
                  { id: 'maj7', label: 'maj7' },
                  { id: 'sus4', label: 'sus4' }
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

              {/* Grilla de Acordes Interactivos */}
              <div className="grid grid-cols-2 gap-2 max-h-[calc(100vh-270px)] overflow-y-auto pr-1">
                {allChordsList.map(chord => (
                  <ChordDiagram
                    key={chord.symbol}
                    chord={chord}
                    size="sm"
                    interactive
                    isSelected={selectedChord?.symbol === chord.symbol}
                    onClick={() => setSelectedChord(chord)}
                  />
                ))}
              </div>
            </div>
          )}
        </div>

        {/* Pie del Panel */}
        <div className="p-3 border-t border-parchment-border dark:border-jetcarbon-border bg-parchment/40 dark:bg-darkbg/40 text-center text-[10px] text-jetcarbon-muted dark:text-gray-400">
          Haz clic en cualquier acorde para inspeccionar notas y digitación
        </div>
      </aside>

      {/* Modal Centrado Overlay (cuando el usuario solicita pantalla completa) */}
      {selectedChord && isInspectorModal && (
        <ChordDetailInspector
          chord={selectedChord}
          instrument={instrument}
          isModal={true}
          onClose={() => setIsInspectorModal(false)}
          onSelectChord={handleSelectChordBySymbolOrName}
          onToggleDock={() => setIsInspectorModal(false)}
        />
      )}
    </>
  );
};
