import { Hymn } from '../types/hymn';

/**
 * Filtra las alabanzas que disponen de un enlace válido de YouTube.
 */
export function filterHymnsWithYouTube(hymns: Hymn[]): Hymn[] {
  return hymns.filter(h => !!(h.link && h.link.trim().length > 0 && !h.isDeleted));
}

/**
 * Formatea una lista de alabanzas para copiar al portapapeles
 * en el formato: "[ID. Título] - [Enlace]" (ej: "1. Te he notado - https://...").
 */
export function formatYouTubeListToClipboard(hymns: Hymn[]): string {
  return hymns
    .filter(h => !!(h.link && h.link.trim().length > 0))
    .map(h => `${h.id}. ${h.title} - ${h.link!.trim()}`)
    .join('\n');
}
