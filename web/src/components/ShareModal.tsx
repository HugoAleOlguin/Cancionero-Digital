import React, { useState } from 'react';
import { Hymn } from '../types/hymn';
import { generateHymnPdf } from '../utils/pdfGenerator';
import { X, Download, Link2, Copy, Check, Share2 } from 'lucide-react';

interface ShareModalProps {
  isOpen: boolean;
  onClose: () => void;
  hymn: Hymn | null;
  activeVersionIndex?: number;
}

// Icono vectorial limpio de WhatsApp (sin emojis)
const WhatsAppIcon = ({ size = 20 }: { size?: number }) => (
  <svg width={size} height={size} viewBox="0 0 24 24" fill="currentColor">
    <path d="M17.472 14.382c-.301-.15-1.782-.879-2.058-.98-.276-.1-.477-.15-.678.15-.2.302-.778.98-.954 1.18-.175.201-.351.226-.652.076-.301-.15-1.272-.469-2.423-1.496-.896-.799-1.501-1.786-1.677-2.087-.176-.302-.019-.465.132-.615.136-.135.301-.351.452-.527.15-.176.2-.302.301-.502.1-.201.05-.377-.025-.527-.075-.15-.678-1.633-.929-2.235-.245-.587-.494-.508-.678-.517-.176-.01-.377-.01-.578-.01-.201 0-.527.075-.803.376s-1.054 1.03-1.054 2.511 1.079 2.912 1.23 3.113c.15.201 2.124 3.243 5.146 4.549.719.311 1.28.497 1.718.636.722.23 1.378.197 1.898.12.579-.087 1.782-.728 2.033-1.431.251-.703.251-1.306.176-1.431-.076-.126-.277-.201-.578-.352zM12.04 2C6.51 2 2.02 6.49 2.02 12.02c0 1.94.55 3.75 1.51 5.28L2 22l4.85-1.48c1.47.88 3.19 1.38 5.02 1.38 5.53 0 10.02-4.49 10.02-10.02S17.57 2 12.04 2z" />
  </svg>
);

export const ShareModal: React.FC<ShareModalProps> = ({
  isOpen,
  onClose,
  hymn,
  activeVersionIndex = 0
}) => {
  const [copiedLink, setCopiedLink] = useState(false);
  const [copiedLyrics, setCopiedLyrics] = useState(false);
  const [isGeneratingPdf, setIsGeneratingPdf] = useState(false);

  if (!isOpen || !hymn) return null;

  const versions = [hymn.content, ...(hymn.extraVersions || [])];
  const activeContent = versions[activeVersionIndex] || hymn.content;
  const directUrl = `${window.location.origin}/?id=${hymn.id}`;

  const handleDownloadPdf = () => {
    try {
      setIsGeneratingPdf(true);
      generateHymnPdf(hymn, activeVersionIndex);
      setTimeout(() => {
        setIsGeneratingPdf(false);
        onClose();
      }, 500);
    } catch (err) {
      console.error('Error generating PDF', err);
      setIsGeneratingPdf(false);
    }
  };

  const handleCopyLink = async () => {
    try {
      await navigator.clipboard.writeText(directUrl);
      setCopiedLink(true);
      setTimeout(() => setCopiedLink(false), 2000);
    } catch (err) {
      console.error('Error copying link', err);
    }
  };

  const handleCopyLyrics = async () => {
    try {
      const fullText = `${hymn.id}. ${hymn.title}\n${hymn.author ? `Autor: ${hymn.author}\n` : ''}\n${activeContent}`;
      await navigator.clipboard.writeText(fullText);
      setCopiedLyrics(true);
      setTimeout(() => setCopiedLyrics(false), 2000);
    } catch (err) {
      console.error('Error copying lyrics', err);
    }
  };

  const handleWhatsApp = () => {
    const message = `${hymn.id}. ${hymn.title}\n\nPuedes leer este canto aquí:\n${directUrl}`;
    const url = `https://api.whatsapp.com/send?text=${encodeURIComponent(message)}`;
    window.open(url, '_blank');
    onClose();
  };

  const handleNativeShare = async () => {
    if (navigator.share) {
      try {
        await navigator.share({
          title: `${hymn.id}. ${hymn.title}`,
          text: `${hymn.id}. ${hymn.title}\n\n${activeContent}`,
          url: directUrl
        });
        onClose();
      } catch {
        // Usuario canceló el modal nativo
      }
    }
  };

  return (
    <div className="fixed inset-0 z-50 overflow-hidden flex items-end sm:items-center justify-center p-0 sm:p-4">
      {/* Backdrop */}
      <div
        className="fixed inset-0 bg-black/60 backdrop-blur-xs transition-opacity"
        onClick={onClose}
      />

      {/* Modal / Bottom Sheet Panel */}
      <div className="relative w-full sm:max-w-md bg-white dark:bg-darkcard rounded-t-3xl sm:rounded-2xl border border-parchment-border dark:border-jetcarbon-border shadow-2xl overflow-hidden z-10 animate-in fade-in slide-in-from-bottom-6 sm:slide-in-from-bottom-2 duration-200">
        {/* Header */}
        <div className="p-5 border-b border-parchment-border dark:border-jetcarbon-border flex items-center justify-between">
          <div className="pr-4">
            <span className="text-xs font-bold uppercase tracking-wider text-golden">
              Compartir Alabanza
            </span>
            <h3 className="text-base sm:text-lg font-bold text-jetcarbon dark:text-gray-100 truncate mt-0.5">
              {hymn.id}. {hymn.title}
            </h3>
          </div>
          <button
            onClick={onClose}
            className="p-2 rounded-xl text-jetcarbon-muted hover:text-jetcarbon dark:text-gray-400 dark:hover:text-white hover:bg-parchment dark:hover:bg-jetcarbon-light transition-colors"
          >
            <X size={18} />
          </button>
        </div>

        {/* Options List */}
        <div className="p-4 space-y-2">
          {/* 1. Descargar PDF */}
          <button
            onClick={handleDownloadPdf}
            disabled={isGeneratingPdf}
            className="w-full flex items-center justify-between p-3.5 rounded-xl border border-parchment-border dark:border-jetcarbon-border bg-parchment/50 dark:bg-darkbg hover:bg-golden/10 hover:border-golden/60 transition-all text-left group"
          >
            <div className="flex items-center gap-3.5">
              <div className="w-10 h-10 rounded-xl bg-golden/15 text-golden-dark dark:text-golden flex items-center justify-center group-hover:scale-105 transition-transform">
                <Download size={20} />
              </div>
              <div>
                <p className="text-sm font-bold text-jetcarbon dark:text-gray-100">
                  {isGeneratingPdf ? 'Generando PDF...' : 'Descargar en PDF'}
                </p>
                <p className="text-xs text-jetcarbon-muted dark:text-gray-400">
                  Formato A4 con diseño litúrgico centrado
                </p>
              </div>
            </div>
          </button>

          {/* 2. Copiar Enlace Directo */}
          <button
            onClick={handleCopyLink}
            className="w-full flex items-center justify-between p-3.5 rounded-xl border border-parchment-border dark:border-jetcarbon-border bg-parchment/50 dark:bg-darkbg hover:bg-golden/10 hover:border-golden/60 transition-all text-left group"
          >
            <div className="flex items-center gap-3.5">
              <div className="w-10 h-10 rounded-xl bg-blue-500/10 text-blue-600 dark:text-blue-400 flex items-center justify-center group-hover:scale-105 transition-transform">
                {copiedLink ? <Check size={20} /> : <Link2 size={20} />}
              </div>
              <div>
                <p className="text-sm font-bold text-jetcarbon dark:text-gray-100">
                  {copiedLink ? '¡Enlace copiado al portapapeles!' : 'Copiar enlace directo'}
                </p>
                <p className="text-xs text-jetcarbon-muted dark:text-gray-400 truncate max-w-[230px]">
                  {directUrl}
                </p>
              </div>
            </div>
            {copiedLink && <span className="text-xs font-bold text-green-600">Listo</span>}
          </button>

          {/* 3. Compartir en WhatsApp */}
          <button
            onClick={handleWhatsApp}
            className="w-full flex items-center justify-between p-3.5 rounded-xl border border-parchment-border dark:border-jetcarbon-border bg-parchment/50 dark:bg-darkbg hover:bg-emerald-500/10 hover:border-emerald-500/50 transition-all text-left group"
          >
            <div className="flex items-center gap-3.5">
              <div className="w-10 h-10 rounded-xl bg-emerald-500/15 text-emerald-600 dark:text-emerald-400 flex items-center justify-center group-hover:scale-105 transition-transform">
                <WhatsAppIcon size={20} />
              </div>
              <div>
                <p className="text-sm font-bold text-jetcarbon dark:text-gray-100">
                  Compartir en WhatsApp
                </p>
                <p className="text-xs text-jetcarbon-muted dark:text-gray-400">
                  Enviar título y enlace directo a un contacto o grupo
                </p>
              </div>
            </div>
          </button>

          {/* 4. Copiar Letra Completa */}
          <button
            onClick={handleCopyLyrics}
            className="w-full flex items-center justify-between p-3.5 rounded-xl border border-parchment-border dark:border-jetcarbon-border bg-parchment/50 dark:bg-darkbg hover:bg-golden/10 hover:border-golden/60 transition-all text-left group"
          >
            <div className="flex items-center gap-3.5">
              <div className="w-10 h-10 rounded-xl bg-gray-500/10 text-jetcarbon dark:text-gray-300 flex items-center justify-center group-hover:scale-105 transition-transform">
                {copiedLyrics ? <Check size={20} className="text-green-600" /> : <Copy size={20} />}
              </div>
              <div>
                <p className="text-sm font-bold text-jetcarbon dark:text-gray-100">
                  {copiedLyrics ? '¡Letra copiada al portapapeles!' : 'Copiar texto de la alabanza'}
                </p>
                <p className="text-xs text-jetcarbon-muted dark:text-gray-400">
                  Texto plano formateado listo para pegar
                </p>
              </div>
            </div>
            {copiedLyrics && <span className="text-xs font-bold text-green-600">Listo</span>}
          </button>

          {/* 5. Compartir del Sistema (si está disponible) */}
          {typeof navigator !== 'undefined' && typeof navigator.share === 'function' && (
            <button
              onClick={handleNativeShare}
              className="w-full flex items-center justify-between p-3.5 rounded-xl border border-parchment-border dark:border-jetcarbon-border bg-parchment/50 dark:bg-darkbg hover:bg-golden/10 hover:border-golden/60 transition-all text-left group"
            >
              <div className="flex items-center gap-3.5">
                <div className="w-10 h-10 rounded-xl bg-purple-500/10 text-purple-600 dark:text-purple-400 flex items-center justify-center group-hover:scale-105 transition-transform">
                  <Share2 size={20} />
                </div>
                <div>
                  <p className="text-sm font-bold text-jetcarbon dark:text-gray-100">
                    Otras aplicaciones
                  </p>
                  <p className="text-xs text-jetcarbon-muted dark:text-gray-400">
                    Abrir el menú de compartir de tu teléfono
                  </p>
                </div>
              </div>
            </button>
          )}
        </div>

        {/* Footer */}
        <div className="px-5 py-3 border-t border-parchment-border dark:border-jetcarbon-border text-center text-xs text-jetcarbon-muted dark:text-gray-400 bg-white/50 dark:bg-darkbg/50">
          Cancionero Cristiano
        </div>
      </div>
    </div>
  );
};
