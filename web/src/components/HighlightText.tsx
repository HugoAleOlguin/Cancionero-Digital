import React from 'react';
import { normalizeText } from '../utils/searchEngine';

interface HighlightTextProps {
  text: string;
  query: string;
  className?: string;
}

export const HighlightText: React.FC<HighlightTextProps> = ({ text, query, className = '' }) => {
  const trimmed = query.trim();
  if (!trimmed) {
    return <span className={className}>{text}</span>;
  }

  const normText = normalizeText(text);
  const normQuery = normalizeText(trimmed);

  const parts: React.ReactNode[] = [];
  let lastIndex = 0;
  let matchIndex = normText.indexOf(normQuery, lastIndex);

  let keyCounter = 0;

  while (matchIndex !== -1) {
    // Normal text before match
    if (matchIndex > lastIndex) {
      parts.push(text.substring(lastIndex, matchIndex));
    }

    // Highlighted text
    const matchEnd = matchIndex + normQuery.length;
    parts.push(
      <mark
        key={`match-${keyCounter++}`}
        className="bg-golden/30 text-jetcarbon dark:text-golden-light font-bold rounded-sm px-0.5 border-b-2 border-golden"
      >
        {text.substring(matchIndex, matchEnd)}
      </mark>
    );

    lastIndex = matchEnd;
    matchIndex = normText.indexOf(normQuery, lastIndex);
  }

  // Trailing text
  if (lastIndex < text.length) {
    parts.push(text.substring(lastIndex));
  }

  return <span className={className}>{parts}</span>;
};
