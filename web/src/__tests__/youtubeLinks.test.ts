import { describe, it, expect } from 'vitest';
import { filterHymnsWithYouTube, formatYouTubeListToClipboard } from '../utils/youtubeLinks';
import { Hymn } from '../types/hymn';

describe('YouTube Links Utility Engine', () => {
  const sampleHymns: Hymn[] = [
    {
      id: 1,
      title: 'Te he notado',
      content: 'Letra del canto 1...',
      link: 'https://www.youtube.com/watch?v=W7KbOLba7MM',
      isDeleted: false
    },
    {
      id: 2,
      title: 'A solas con Dios',
      content: 'Letra del canto 2...',
      link: undefined,
      isDeleted: false
    },
    {
      id: 3,
      title: 'Al amanecer',
      content: 'Letra del canto 3...',
      link: '   ',
      isDeleted: false
    },
    {
      id: 4,
      title: 'En la cruz',
      content: 'Letra del canto 4...',
      link: 'https://youtu.be/xyz123',
      isDeleted: false
    }
  ];

  it('filters only hymns that have valid non-empty YouTube links', () => {
    const withLinks = filterHymnsWithYouTube(sampleHymns);
    expect(withLinks.length).toBe(2);
    expect(withLinks[0].id).toBe(1);
    expect(withLinks[1].id).toBe(4);
  });

  it('formats list of hymns to clipboard string in [nombre] - [enlace] format', () => {
    const withLinks = filterHymnsWithYouTube(sampleHymns);
    const text = formatYouTubeListToClipboard(withLinks);
    
    expect(text).toContain('1. Te he notado - https://www.youtube.com/watch?v=W7KbOLba7MM');
    expect(text).toContain('4. En la cruz - https://youtu.be/xyz123');
    expect(text.split('\n').length).toBe(2);
  });
});
