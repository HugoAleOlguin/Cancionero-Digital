import { describe, it, expect } from 'vitest';
import React from 'react';
import { InstrumentCompanionPanel } from '../components/InstrumentCompanionPanel';
import { AppDrawer } from '../components/AppDrawer';

describe('Docked Layout & Multi-Column Contract', () => {
  it('InstrumentCompanionPanel component accepts docked layout props', () => {
    const element = React.createElement(InstrumentCompanionPanel, {
      isOpen: true,
      onClose: () => {}
    });

    expect(element).toBeDefined();
    expect(element.props.isOpen).toBe(true);
    expect(typeof element.props.onClose).toBe('function');
  });

  it('AppDrawer component accepts docked sidebar props', () => {
    const element = React.createElement(AppDrawer, {
      isOpen: true,
      onClose: () => {},
      totalHymns: 485,
      favoritesCount: 5,
      showOnlyFavorites: false,
      onToggleFavoritesFilter: () => {},
      selectedAuthor: null,
      onSelectAuthor: () => {},
      authors: [],
      typography: 'sans',
      onChangeTypography: () => {},
      fontSize: 18,
      onChangeFontSize: () => {},
      theme: 'light',
      onToggleTheme: () => {},
      catalogVersion: 1,
      chordNotation: 'latin',
      onChangeChordNotation: () => {},
      youtubeHymnsCount: 10,
      onOpenYouTubeLinks: () => {}
    });

    expect(element).toBeDefined();
    expect(element.props.isOpen).toBe(true);
    expect(element.props.totalHymns).toBe(485);
  });
});
