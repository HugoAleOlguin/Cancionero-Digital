import { useState, useEffect } from 'react';
import { ThemeMode, TypographyType } from '../types/hymn';
import { safeGetItem, safeSetItem } from '../utils/storage';

const THEME_KEY = 'cancionero_theme';
const TYPO_KEY = 'cancionero_typography';
const FONT_SIZE_KEY = 'cancionero_font_size';

export function useSettings() {
  const [theme, setTheme] = useState<ThemeMode>(() => {
    const systemDefault: ThemeMode =
      typeof window !== 'undefined' && window.matchMedia('(prefers-color-scheme: dark)').matches
        ? 'dark'
        : 'light';
    const stored = safeGetItem<ThemeMode>(THEME_KEY, systemDefault);
    return stored === 'light' || stored === 'dark' ? stored : systemDefault;
  });

  const [typography, setTypography] = useState<TypographyType>(() => {
    const stored = safeGetItem<TypographyType>(TYPO_KEY, 'serif');
    return stored === 'serif' || stored === 'sans' || stored === 'mono' ? stored : 'serif';
  });

  const [fontSize, setFontSize] = useState<number>(() => {
    const stored = safeGetItem<number>(FONT_SIZE_KEY, 18);
    return typeof stored === 'number' && !Number.isNaN(stored) && stored >= 14 && stored <= 32
      ? stored
      : 18;
  });

  useEffect(() => {
    const root = document.documentElement;
    if (theme === 'dark') {
      root.classList.add('dark');
    } else {
      root.classList.remove('dark');
    }
    safeSetItem(THEME_KEY, theme);
  }, [theme]);

  useEffect(() => {
    safeSetItem(TYPO_KEY, typography);
  }, [typography]);

  useEffect(() => {
    safeSetItem(FONT_SIZE_KEY, fontSize);
  }, [fontSize]);

  const toggleTheme = () => setTheme(prev => (prev === 'light' ? 'dark' : 'light'));

  return {
    theme,
    setTheme,
    toggleTheme,
    typography,
    setTypography,
    fontSize,
    setFontSize
  };
}
