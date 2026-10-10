import React, { useState, useEffect, useMemo } from 'react';
import { Hymn, TypographyType } from '../types/hymn';
import { ChordNotationType } from '../util/chordNotation';
import {
  PdfStudioConfig,
  paginateHymnContent,
  generateHymnPdf
} from '../utils/pdfGenerator';
import { PdfPagePreview } from './PdfPagePreview';
import {
  X,
  Download,
  RotateCcw,
  Sliders,
  Eye,
  Check
} from 'lucide-react';

interface PdfStudioModalProps {
  isOpen: boolean;
  onClose: () => void;
  hymn: Hymn | null;
  activeVersionIndex?: number;
  initialChordNotation?: ChordNotationType;
}

export const PdfStudioModal: React.FC<PdfStudioModalProps> = ({
  isOpen,
  onClose,
  hymn,
  activeVersionIndex = 0,
  initialChordNotation = 'latin'
}) => {
  if (!isOpen || !hymn) return null;

  const versions = [hymn.content, ...(hymn.extraVersions || [])];
  const initialContent = versions[activeVersionIndex] || hymn.content;

  // Cargar acordes guardados si existen
  const [storedChords, setStoredChords] = useState<Record<string, string>>({});
  const hasStoredChords = Object.keys(storedChords).length > 0;

  useEffect(() => {
    try {
      const stored = localStorage.getItem(`cancionero_chords_${hymn.id}`);
      if (stored) {
        const parsed = JSON.parse(stored);
        if (parsed.chords) {
          setStoredChords(parsed.chords);
        }
      }
    } catch {
      // fallback
    }
  }, [hymn.id]);

  // Configuración del Estudio PDF
  const [config, setConfig] = useState<PdfStudioConfig>(() => ({
    fontSize: 12,
    typography: 'serif',
    showHeader: true,
    showFooter: true,
    showChords: true,
    chordNotation: initialChordNotation,
    content: initialContent
  }));

  // Actualizar contenido cuando cambia de versión o de notación
  useEffect(() => {
    setConfig(prev => ({
      ...prev,
      content: initialContent,
      chordNotation: initialChordNotation
    }));
  }, [initialContent, initialChordNotation]);

  const [activeMobileTab, setActiveMobileTab] = useState<'editor' | 'preview'>('editor');
  const [isGenerating, setIsGenerating] = useState<boolean>(false);
  const [downloadSuccess, setDownloadSuccess] = useState<boolean>(false);

  // Paginación reactiva en tiempo real
  const pages = useMemo(() => {
    return paginateHymnContent(
      hymn,
      config,
      hasStoredChords && config.showChords ? storedChords : undefined
    );
  }, [hymn, config, storedChords, hasStoredChords]);

  const isContentModified = config.content.trim() !== initialContent.trim();

  const handleResetContent = () => {
    setConfig(prev => ({ ...prev, content: initialContent }));
  };

  const handleDownload = () => {
    try {
      setIsGenerating(true);
      generateHymnPdf(
        hymn,
        activeVersionIndex,
        config,
        hasStoredChords && config.showChords ? storedChords : undefined
      );
      setDownloadSuccess(true);
      setTimeout(() => {
        setDownloadSuccess(false);
        setIsGenerating(false);
        onClose();
      }, 700);
    } catch (err) {
      console.error('Error generando PDF', err);
      setIsGenerating(false);
    }
  };

  return (
    <div className="fixed inset-0 z-50 overflow-hidden flex items-center justify-center p-0 sm:p-4 bg-black/70 backdrop-blur-xs animate-in fade-in duration-200">
      {/* Contenedor Principal del Modal */}
      <div className="bg-parchment dark:bg-darkcard border border-parchment-border dark:border-jetcarbon-border rounded-none sm:rounded-2xl shadow-2xl flex flex-col w-full max-w-6xl h-full sm:h-[92vh] overflow-hidden">
        
        {/* Barra Superior Header */}
        <header className="px-5 py-3 border-b border-parchment-border dark:border-jetcarbon-border bg-white dark:bg-darkbg flex items-center justify-between shrink-0">
          <div className="flex items-center gap-3">
            <span className="w-8 h-8 rounded-xl bg-golden/10 flex items-center justify-center text-golden font-bold text-sm">
              ♪
            </span>
            <div>
              <div className="flex items-center gap-2">
                <h2 className="text-sm sm:text-base font-bold text-jetcarbon dark:text-gray-100">
                  Estudio de Impresión PDF
                </h2>
                <span className="text-xs px-2 py-0.5 rounded-full bg-golden/15 text-golden-dark dark:text-golden font-semibold">
                  {hymn.id}. {hymn.title}
                </span>
              </div>
              <p className="text-[11px] text-jetcarbon-muted dark:text-gray-400">
                Ajusta la apariencia y edita el texto con vista previa A4 en tiempo real.
              </p>
            </div>
          </div>

          <button
            onClick={onClose}
            className="p-2 rounded-xl text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400 dark:hover:text-gray-200 hover:bg-parchment dark:hover:bg-jetcarbon-light transition-colors"
            title="Cerrar"
          >
            <X size={18} />
          </button>
        </header>

        {/* Pestañas para Pantallas Móviles */}
        <div className="flex lg:hidden border-b border-parchment-border dark:border-jetcarbon-border bg-white dark:bg-darkbg text-xs font-semibold shrink-0">
          <button
            onClick={() => setActiveMobileTab('editor')}
            className={`flex-1 py-2.5 flex items-center justify-center gap-2 border-b-2 transition-all ${
              activeMobileTab === 'editor'
                ? 'border-golden text-golden-dark dark:text-golden bg-golden/5'
                : 'border-transparent text-jetcarbon-muted dark:text-gray-400'
            }`}
          >
            <Sliders size={14} />
            <span>Ajustes y Letra</span>
          </button>
          <button
            onClick={() => setActiveMobileTab('preview')}
            className={`flex-1 py-2.5 flex items-center justify-center gap-2 border-b-2 transition-all ${
              activeMobileTab === 'preview'
                ? 'border-golden text-golden-dark dark:text-golden bg-golden/5'
                : 'border-transparent text-jetcarbon-muted dark:text-gray-400'
            }`}
          >
            <Eye size={14} />
            <span>Vista Previa ({pages.length})</span>
          </button>
        </div>

        {/* Cuerpo del Modal: 2 Columnas */}
        <div className="flex-1 flex flex-col lg:flex-row overflow-hidden min-h-0">
          
          {/* Columna Izquierda: Opciones y Editor de Texto */}
          <div
            className={`w-full lg:w-[420px] xl:w-[450px] p-4 sm:p-5 overflow-y-auto space-y-3.5 border-r border-parchment-border dark:border-jetcarbon-border bg-parchment/60 dark:bg-darkbg/50 shrink-0 ${
              activeMobileTab === 'preview' ? 'hidden lg:block' : 'block'
            }`}
          >
            {/* 1. Tipografía */}
            <div className="bg-white dark:bg-darkcard border border-parchment-border dark:border-jetcarbon-border p-3 rounded-xl shadow-2xs space-y-2">
              <span className="text-[11px] font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 block">
                Fuente
              </span>
              <div className="grid grid-cols-3 gap-1.5 text-xs">
                {(['serif', 'sans', 'mono'] as TypographyType[]).map(t => (
                  <button
                    key={t}
                    onClick={() => setConfig(prev => ({ ...prev, typography: t }))}
                    className={`py-1.5 px-2 rounded-lg font-medium border capitalize transition-all ${
                      config.typography === t
                        ? 'border-golden bg-golden/10 text-golden-dark dark:text-golden font-bold shadow-2xs'
                        : 'border-parchment-border dark:border-jetcarbon-border text-jetcarbon dark:text-gray-300 hover:bg-parchment/60'
                    }`}
                  >
                    {t === 'serif' ? 'Serif' : t === 'sans' ? 'Sans' : 'Mono'}
                  </button>
                ))}
              </div>
            </div>

            {/* 2. Tamaño de Letra */}
            <div className="bg-white dark:bg-darkcard border border-parchment-border dark:border-jetcarbon-border p-3 rounded-xl shadow-2xs space-y-2">
              <div className="flex items-center justify-between">
                <span className="text-[11px] font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400">
                  Tamaño de letra
                </span>
                <span className="text-xs font-bold px-2 py-0.5 rounded bg-golden/15 text-golden-dark dark:text-golden">
                  {config.fontSize} pt
                </span>
              </div>
              <div className="flex items-center gap-2.5">
                <button
                  onClick={() => setConfig(prev => ({ ...prev, fontSize: Math.max(8, prev.fontSize - 0.5) }))}
                  className="w-7 h-7 rounded-lg border border-parchment-border dark:border-jetcarbon-border font-bold text-xs text-jetcarbon dark:text-gray-200 hover:bg-golden/10 transition-colors"
                >
                  -
                </button>
                <input
                  type="range"
                  min="8"
                  max="18"
                  step="0.5"
                  value={config.fontSize}
                  onChange={e => setConfig(prev => ({ ...prev, fontSize: Number(e.target.value) }))}
                  className="flex-1 accent-golden h-2 bg-parchment-border dark:bg-jetcarbon-border rounded-lg cursor-pointer"
                />
                <button
                  onClick={() => setConfig(prev => ({ ...prev, fontSize: Math.min(18, prev.fontSize + 0.5) }))}
                  className="w-7 h-7 rounded-lg border border-parchment-border dark:border-jetcarbon-border font-bold text-xs text-jetcarbon dark:text-gray-200 hover:bg-golden/10 transition-colors"
                >
                  +
                </button>
              </div>
            </div>

            {/* 3. Opciones del Documento */}
            <div className="bg-white dark:bg-darkcard border border-parchment-border dark:border-jetcarbon-border p-3 rounded-xl shadow-2xs space-y-2">
              <span className="text-[11px] font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400 block">
                Opciones
              </span>

              <div className="space-y-1.5 text-xs">
                {/* Encabezado */}
                <label className="flex items-center justify-between p-2 rounded-lg hover:bg-parchment/60 dark:hover:bg-darkbg cursor-pointer transition-colors">
                  <span className="font-medium text-jetcarbon dark:text-gray-200">
                    Título y encabezado
                  </span>
                  <input
                    type="checkbox"
                    checked={config.showHeader}
                    onChange={e => setConfig(prev => ({ ...prev, showHeader: e.target.checked }))}
                    className="w-4 h-4 accent-golden rounded cursor-pointer"
                  />
                </label>

                {/* Pie de página */}
                <label className="flex items-center justify-between p-2 rounded-lg hover:bg-parchment/60 dark:hover:bg-darkbg cursor-pointer transition-colors">
                  <span className="font-medium text-jetcarbon dark:text-gray-200">
                    Número de página
                  </span>
                  <input
                    type="checkbox"
                    checked={config.showFooter}
                    onChange={e => setConfig(prev => ({ ...prev, showFooter: e.target.checked }))}
                    className="w-4 h-4 accent-golden rounded cursor-pointer"
                  />
                </label>

                {/* Acordes Musicales */}
                <div className="p-2 rounded-lg bg-golden/5 border border-golden/20 space-y-2">
                  <label className="flex items-center justify-between cursor-pointer">
                    <span className="font-medium text-jetcarbon dark:text-gray-200">
                      Acordes musicales
                    </span>
                    <input
                      type="checkbox"
                      disabled={!hasStoredChords}
                      checked={hasStoredChords && config.showChords}
                      onChange={e => setConfig(prev => ({ ...prev, showChords: e.target.checked }))}
                      className="w-4 h-4 accent-golden rounded cursor-pointer disabled:opacity-40"
                    />
                  </label>

                  {hasStoredChords && config.showChords ? (
                    <div className="pt-1.5 border-t border-golden/15 flex items-center justify-between gap-2">
                      <span className="text-[10px] text-jetcarbon-muted dark:text-gray-400 uppercase font-semibold">
                        Notación:
                      </span>
                      <div className="flex gap-1">
                        <button
                          onClick={() => setConfig(prev => ({ ...prev, chordNotation: 'latin' }))}
                          className={`px-2 py-0.5 rounded text-[10px] font-bold transition-colors ${
                            config.chordNotation === 'latin'
                              ? 'bg-golden text-white'
                              : 'bg-white dark:bg-darkcard text-jetcarbon dark:text-gray-300 border border-golden/30'
                          }`}
                        >
                          DO RE MI
                        </button>
                        <button
                          onClick={() => setConfig(prev => ({ ...prev, chordNotation: 'anglo' }))}
                          className={`px-2 py-0.5 rounded text-[10px] font-bold transition-colors ${
                            config.chordNotation === 'anglo'
                              ? 'bg-golden text-white'
                              : 'bg-white dark:bg-darkcard text-jetcarbon dark:text-gray-300 border border-golden/30'
                          }`}
                        >
                          C D E
                        </button>
                      </div>
                    </div>
                  ) : !hasStoredChords ? (
                    <span className="text-[10px] text-jetcarbon-muted dark:text-gray-400 block italic">
                      Sin acordes guardados para este himno
                    </span>
                  ) : null}
                </div>
              </div>
            </div>

            {/* 4. Letra Editable */}
            <div className="bg-white dark:bg-darkcard border border-parchment-border dark:border-jetcarbon-border p-3 rounded-xl shadow-2xs space-y-2">
              <div className="flex items-center justify-between">
                <span className="text-[11px] font-bold uppercase tracking-wider text-jetcarbon-muted dark:text-gray-400">
                  Letra (editable)
                </span>
                {isContentModified && (
                  <button
                    onClick={handleResetContent}
                    className="text-[11px] font-semibold text-golden hover:underline flex items-center gap-1"
                    title="Restaurar letra original"
                  >
                    <RotateCcw size={11} />
                    Restaurar original
                  </button>
                )}
              </div>
              <textarea
                value={config.content}
                onChange={e => setConfig(prev => ({ ...prev, content: e.target.value }))}
                rows={9}
                className="w-full p-2.5 rounded-lg border border-parchment-border dark:border-jetcarbon-border bg-parchment/30 dark:bg-darkbg text-jetcarbon dark:text-gray-100 text-xs font-mono leading-relaxed focus:outline-none focus:border-golden resize-y"
                placeholder="Edita la letra aquí..."
              />
            </div>

          </div>

          {/* Columna Derecha: Vista Previa Fiel en Vivo */}
          <div
            className={`flex-1 p-4 sm:p-6 overflow-y-auto bg-neutral-100/90 dark:bg-black/40 flex flex-col items-center ${
              activeMobileTab === 'editor' ? 'hidden lg:flex' : 'flex'
            }`}
          >
            <div className="w-full max-w-[560px] flex items-center justify-between mb-3 text-xs">
              <span className="font-bold text-jetcarbon dark:text-gray-200">
                Vista previa
              </span>
              <span className="px-2 py-0.5 rounded-full bg-white dark:bg-darkcard border border-parchment-border dark:border-jetcarbon-border text-[11px] font-semibold text-jetcarbon-muted dark:text-gray-300">
                {pages.length} {pages.length === 1 ? 'página' : 'páginas'}
              </span>
            </div>

            {/* Listado de Páginas A4 */}
            <div className="w-full flex flex-col items-center pb-8">
              {pages.map(page => (
                <PdfPagePreview
                  key={`preview-page-${page.pageNumber}`}
                  page={page}
                  config={config}
                />
              ))}
            </div>
          </div>

        </div>

        {/* Barra Inferior Footer */}
        <footer className="px-5 py-3 border-t border-parchment-border dark:border-jetcarbon-border bg-white dark:bg-darkbg flex items-center justify-between shrink-0">
          <div className="text-xs text-jetcarbon-muted dark:text-gray-400">
            <span>{pages.length} {pages.length === 1 ? 'hoja A4' : 'hojas A4'} • {config.fontSize} pt</span>
          </div>

          <div className="flex items-center gap-2">
            <button
              onClick={onClose}
              className="px-4 py-2 rounded-xl text-xs font-semibold text-jetcarbon dark:text-gray-300 hover:bg-parchment dark:hover:bg-jetcarbon-light transition-colors"
            >
              Cancelar
            </button>
            <button
              onClick={handleDownload}
              disabled={isGenerating}
              className="px-5 py-2 rounded-xl text-xs font-bold bg-golden hover:bg-golden-dark text-white flex items-center gap-2 transition-all shadow-sm active:scale-95 disabled:opacity-50"
            >
              {downloadSuccess ? (
                <>
                  <Check size={15} />
                  <span>¡PDF Guardado!</span>
                </>
              ) : (
                <>
                  <Download size={15} />
                  <span>{isGenerating ? 'Generando...' : 'Descargar PDF'}</span>
                </>
              )}
            </button>
          </div>
        </footer>

      </div>
    </div>
  );
};
