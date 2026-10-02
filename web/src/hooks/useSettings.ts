import { useState, useEffect } from 'react';
import { ThemeMode, TypographyType } from '../types/hymn';

const THEME_KEY = 'cancionero_theme';
const TYPO_KEY = 'cancionero_typography';
const FONT_SIZE_KEY = 'cancionero_font_size';

export function useSettings() {
  const [theme, setTheme] = useState<ThemeMode>(() => {
    try {
      const stored = localStorage.getItem(THEME_KEY) as ThemeMode;
      if (stored === 'light' || stored === 'dark') return stored;
    } catch {
      // fallback
    }
    return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
  });

  const [typography, setTypography] = useState<TypographyType>(() => {
    try {
      const stored = localStorage.getItem(TYPO_KEY) as TypographyType;
      if (stored === 'serif' || stored === 'sans' || stored === 'mono') return stored;
    } catch {
      // fallback
    }
    return 'serif';
  });

  const [fontSize, setFontSize] = useState<number>(() => {
    try {
      const stored = localStorage.getItem(FONT_SIZE_KEY);
      if (stored) {
        const val = parseInt(stored, 10);
        if (!isNaN(val) && val >= 14 && val <= 32) return val;
      }
    } catch {
      // fallback
    }
    return 18;
  });

  useEffect(() => {
    const root = document.documentElement;
    if (theme === 'dark') {
      root.classList.add('dark');
    } else {
      root.classList.remove('dark');
    }
    try {
      localStorage.setItem(THEME_KEY, theme);
    } catch {
      // fallback
    }
  }, [theme]);

  useEffect(() => {
    try {
      localStorage.setItem(TYPO_KEY, typography);
    } catch {
      // fallback
    }
  }, [typography]);

  useEffect(() => {
    try {
      localStorage.setItem(FONT_SIZE_KEY, fontSize.toString());
    } catch {
      // fallback
    }
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
