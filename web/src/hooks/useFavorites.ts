import { useState, useEffect, useCallback } from 'react';
import { safeGetItem, safeSetItem } from '../utils/storage';

const STORAGE_KEY = 'cancionero_favorites';

export function useFavorites() {
  const [favorites, setFavorites] = useState<Set<number>>(() => {
    const stored = safeGetItem<number[]>(STORAGE_KEY, []);
    return new Set<number>(stored);
  });

  useEffect(() => {
    safeSetItem(STORAGE_KEY, Array.from(favorites));
  }, [favorites]);

  const toggleFavorite = useCallback((id: number) => {
    setFavorites(prev => {
      const next = new Set(prev);
      if (next.has(id)) {
        next.delete(id);
      } else {
        next.add(id);
      }
      return next;
    });
  }, []);

  const isFavorite = useCallback((id: number) => favorites.has(id), [favorites]);

  return {
    favorites,
    toggleFavorite,
    isFavorite,
    favoritesCount: favorites.size
  };
}
