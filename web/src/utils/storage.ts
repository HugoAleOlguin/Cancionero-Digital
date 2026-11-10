/**
 * Centralized, safe, and typed localStorage utilities.
 * Handles quota limits, private browsing restrictions, JSON corruption,
 * and legacy non-JSON storage formats gracefully.
 */

/**
 * Safely retrieves and parses an item from localStorage.
 * If the key does not exist, JSON parsing fails, or access is denied,
 * the provided fallback value is returned.
 *
 * @param key The localStorage key to retrieve
 * @param fallback The default value to return if retrieval or parsing fails
 */
export function safeGetItem<T>(key: string, fallback: T): T {
  try {
    if (typeof window === 'undefined' || !window.localStorage) {
      return fallback;
    }

    const item = window.localStorage.getItem(key);
    if (item === null || item === undefined) {
      return fallback;
    }

    try {
      const parsed = JSON.parse(item);

      // Handle legacy case where number was stored as a JSON string like "\"18\""
      if (typeof fallback === 'number' && typeof parsed === 'string') {
        const num = Number(parsed);
        if (!Number.isNaN(num)) {
          return num as unknown as T;
        }
      }

      return parsed as T;
    } catch {
      // If JSON.parse throws, handle legacy raw string/number representations
      if (typeof fallback === 'string') {
        return item as unknown as T;
      }
      if (typeof fallback === 'number') {
        const num = Number(item);
        if (!Number.isNaN(num)) {
          return num as unknown as T;
        }
      }
      return fallback;
    }
  } catch (error) {
    console.warn(`[storage] Error reading key "${key}":`, error);
    return fallback;
  }
}

/**
 * Safely serializes and saves an item into localStorage.
 * Catches QuotaExceededError, access-denied restrictions, and serialization errors.
 *
 * @param key The localStorage key to set
 * @param value The value to serialize and store
 * @returns true if successful, false otherwise
 */
export function safeSetItem<T>(key: string, value: T): boolean {
  try {
    if (typeof window === 'undefined' || !window.localStorage) {
      return false;
    }

    const serialized = JSON.stringify(value);
    window.localStorage.setItem(key, serialized);
    return true;
  } catch (error) {
    console.warn(`[storage] Error saving key "${key}":`, error);
    return false;
  }
}

/**
 * Safely removes an item from localStorage.
 *
 * @param key The localStorage key to remove
 * @returns true if successful, false otherwise
 */
export function safeRemoveItem(key: string): boolean {
  try {
    if (typeof window === 'undefined' || !window.localStorage) {
      return false;
    }

    window.localStorage.removeItem(key);
    return true;
  } catch (error) {
    console.warn(`[storage] Error removing key "${key}":`, error);
    return false;
  }
}
