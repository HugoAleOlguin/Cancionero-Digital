export interface Hymn {
  id: number;
  title: string;
  content: string;
  author?: string;
  link?: string;
  updatedAt?: string;
  isDeleted?: boolean;
  extraVersions?: string[];
}

export interface HymnCatalog {
  version: number;
  updatedAt: string;
  totalHymns: number;
  hymns: Hymn[];
}

export interface MatchOccurrence {
  hymnId: number;
  isTitle: boolean;
  stanzaIndex: number; // -1 if in title
  charRange: [number, number];
}

export interface SearchableHymn {
  hymn: Hymn;
  normalizedTitle: string;
  normalizedAuthor: string;
  normalizedContent: string;
  splitStanzas: string[];
}

export type TypographyType = 'serif' | 'sans' | 'mono';
export type ThemeMode = 'light' | 'dark';
