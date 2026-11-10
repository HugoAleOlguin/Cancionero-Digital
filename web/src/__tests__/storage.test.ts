import { describe, it, expect, beforeEach, afterEach, vi } from 'vitest';
import { safeGetItem, safeSetItem, safeRemoveItem } from '../utils/storage';

describe('Centralized Storage Helper (storage.ts)', () => {
  let mockStore: Record<string, string> = {};

  const mockLocalStorage = {
    getItem: vi.fn((key: string) => mockStore[key] ?? null),
    setItem: vi.fn((key: string, value: string) => {
      mockStore[key] = value;
    }),
    removeItem: vi.fn((key: string) => {
      delete mockStore[key];
    }),
    clear: vi.fn(() => {
      mockStore = {};
    })
  };

  beforeEach(() => {
    mockStore = {};
    vi.stubGlobal('window', {
      localStorage: mockLocalStorage
    });
  });

  afterEach(() => {
    vi.unstubAllGlobals();
    vi.restoreAllMocks();
  });

  it('retrieves default fallback when window is undefined or key does not exist', () => {
    const val = safeGetItem('non_existent', 'default_val');
    expect(val).toBe('default_val');

    const num = safeGetItem('non_existent_num', 42);
    expect(num).toBe(42);

    const arr = safeGetItem<number[]>('non_existent_arr', [1, 2]);
    expect(arr).toEqual([1, 2]);
  });

  it('stores and retrieves JSON serialized complex types', () => {
    const testList = [10, 20, 35];
    const saved = safeSetItem('my_list', testList);
    expect(saved).toBe(true);

    const retrieved = safeGetItem<number[]>('my_list', []);
    expect(retrieved).toEqual(testList);

    const testObj = { name: 'Gracia Sublime', id: 1 };
    safeSetItem('my_obj', testObj);
    expect(safeGetItem('my_obj', null)).toEqual(testObj);
  });

  it('handles legacy unquoted raw strings and unquoted numbers gracefully', () => {
    // Manually set raw unquoted string
    mockStore['raw_theme'] = 'dark';
    expect(safeGetItem('raw_theme', 'light')).toBe('dark');

    // Manually set raw string number
    mockStore['raw_font'] = '22';
    expect(safeGetItem('raw_font', 18)).toBe(22);
  });

  it('returns fallback when JSON parsing fails for complex types', () => {
    mockStore['corrupted_json'] = '{bad-json';
    expect(safeGetItem('corrupted_json', ['fallback'])).toEqual(['fallback']);
  });

  it('catches localStorage access or quota errors and returns fallback or false', () => {
    mockLocalStorage.setItem.mockImplementationOnce(() => {
      throw new Error('QuotaExceededError');
    });

    const success = safeSetItem('key', { data: 'test' });
    expect(success).toBe(false);

    mockLocalStorage.getItem.mockImplementationOnce(() => {
      throw new Error('SecurityError: Access Denied');
    });

    const result = safeGetItem('key', 'safe_default');
    expect(result).toBe('safe_default');
  });

  it('safely removes items from localStorage', () => {
    safeSetItem('to_remove', 'value');
    expect(safeGetItem('to_remove', '')).toBe('value');

    const removed = safeRemoveItem('to_remove');
    expect(removed).toBe(true);
    expect(safeGetItem('to_remove', 'empty')).toBe('empty');
  });
});
