import { useState, useEffect, useCallback } from 'react';

const STORAGE_KEY = 'cancionero_favorites';

export function useFavorites() {
  const [favorites, setFavorites] = useState<Set<number>>(() => {
    try {
      const stored = localStorage.getItem(STORAGE_KEY);
      if (stored) {
        return new Set<number>(JSON.parse(stored));
      }
    } catch (e) {
      console.error('Error reading favorites from localStorage', e);
    }
    return new Set<number>();
  });

  useEffect(() => {
    try {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(Array.from(favorites)));
    } catch (e) {
      console.error('Error saving favorites to localStorage', e);
    }
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
