/**
 * Service Worker Nativo - Cancionero Cristiano PWA
 *
 * Estrategias implementadas:
 * - Shell y bundles estáticos: Cache-First para recursos inmutables con hash (/assets/*),
 *   Stale-While-Revalidate para el resto (HTML, favicon, manifest).
 * - Catálogo y versiones: Stale-While-Revalidate para apertura instantánea (0ms) y
 *   revalidación en segundo plano de '/catalog.json' y '/catalog_version.json'.
 * - Fuentes web: Cache-First para Google Fonts y gstatic.
 * - Activación: Limpieza automática de cachés antiguas y self.clients.claim().
 * - Soporte 100% Offline para garantizar acceso ininterrumpido en modo avión o túneles sin red.
 */

const CACHE_VERSION = 'v1.0.0';
const STATIC_CACHE_NAME = `cancionero-static-${CACHE_VERSION}`;
const DATA_CACHE_NAME = `cancionero-data-${CACHE_VERSION}`;
const FONT_CACHE_NAME = `cancionero-fonts-${CACHE_VERSION}`;

const CURRENT_CACHES = [STATIC_CACHE_NAME, DATA_CACHE_NAME, FONT_CACHE_NAME];

// Activos estáticos mínimos requeridos para funcionamiento offline del App Shell
const PRECACHE_STATIC_ASSETS = [
  '/',
  '/index.html',
  '/manifest.json',
  '/favicon.svg'
];

// Archivos de catálogo que permiten disponibilidad 100% offline inmediata
const PRECACHE_DATA_ASSETS = [
  '/catalog.json',
  '/catalog_version.json'
];

// Instalación: Precarga el shell y el catálogo en cachés dedicadas
self.addEventListener('install', (event) => {
  event.waitUntil(
    Promise.all([
      // Pre-cachear assets estáticos del App Shell
      caches.open(STATIC_CACHE_NAME).then((cache) => {
        return Promise.allSettled(
          PRECACHE_STATIC_ASSETS.map(async (url) => {
            try {
              const response = await fetch(url, { cache: 'no-cache' });
              if (response && response.ok) {
                await cache.put(url, response);
              }
            } catch (err) {
              console.warn(`[SW] Advertencia al pre-cachear static asset ${url}:`, err);
            }
          })
        );
      }),
      // Pre-cachear catálogo inicial para disponibilidad 100% offline
      caches.open(DATA_CACHE_NAME).then((cache) => {
        return Promise.allSettled(
          PRECACHE_DATA_ASSETS.map(async (url) => {
            try {
              const response = await fetch(url, { cache: 'no-cache' });
              if (response && response.ok) {
                await cache.put(url, response);
              }
            } catch (err) {
              console.warn(`[SW] Advertencia al pre-cachear data asset ${url}:`, err);
            }
          })
        );
      })
    ]).then(() => self.skipWaiting())
  );
});

// Activación: Purga de cachés antiguas y toma de control inmediata
self.addEventListener('activate', (event) => {
  event.waitUntil(
    caches.keys().then((cacheNames) => {
      return Promise.all(
        cacheNames
          .filter((cacheName) => !CURRENT_CACHES.includes(cacheName))
          .map((cacheName) => {
            console.log(`[SW] Purgando caché obsoleta: ${cacheName}`);
            return caches.delete(cacheName);
          })
      );
    }).then(() => self.clients.claim())
  );
});

// Manejo de mensajes (ej. forzar actualización inmediata)
self.addEventListener('message', (event) => {
  if (event.data && event.data.type === 'SKIP_WAITING') {
    self.skipWaiting();
  }
});

/**
 * Estrategia Cache-First:
 * Busca primero en caché. Si existe, lo entrega de inmediato sin consultar la red.
 * Si no está, consulta la red, guarda una copia en caché y devuelve la respuesta.
 */
async function cacheFirst(request, cacheName) {
  const cache = await caches.open(cacheName);
  const cachedResponse = await cache.match(request);
  if (cachedResponse) {
    return cachedResponse;
  }

  try {
    const networkResponse = await fetch(request);
    if (networkResponse && (networkResponse.status === 200 || networkResponse.type === 'opaque')) {
      cache.put(request, networkResponse.clone());
    }
    return networkResponse;
  } catch (error) {
    throw error;
  }
}

/**
 * Estrategia Stale-While-Revalidate:
 * Si el recurso está en caché, lo devuelve inmediatamente para lograr latencia 0ms,
 * y en paralelo revalida y actualiza la caché desde la red.
 * Si no está en caché, consulta la red y almacena el resultado.
 */
async function staleWhileRevalidate(request, cacheName, fallbackUrl = null) {
  const cache = await caches.open(cacheName);
  const cachedResponse = await cache.match(request);

  const networkFetch = fetch(request)
    .then((networkResponse) => {
      if (networkResponse && (networkResponse.status === 200 || networkResponse.type === 'opaque')) {
        cache.put(request, networkResponse.clone());
      }
      return networkResponse;
    })
    .catch(() => null);

  if (cachedResponse) {
    return cachedResponse;
  }

  const networkResponse = await networkFetch;
  if (networkResponse) {
    return networkResponse;
  }

  if (fallbackUrl) {
    const fallbackResponse = await cache.match(fallbackUrl);
    if (fallbackResponse) {
      return fallbackResponse;
    }
  }

  throw new Error(`[SW] Recurso no disponible offline ni en red: ${request.url}`);
}

/**
 * Manejo de navegación SPA (modo HTML):
 * Retorna inmediatamente /index.html de caché si estamos offline o en recarga rápida,
 * manteniendo la caché actualizada en segundo plano cuando hay conexión.
 */
async function handleNavigation(request) {
  const cache = await caches.open(STATIC_CACHE_NAME);
  const cachedResponse = await cache.match(request) || await cache.match('/index.html') || await cache.match('/');

  const networkFetch = fetch(request)
    .then((networkResponse) => {
      if (networkResponse && networkResponse.status === 200) {
        cache.put(request, networkResponse.clone());
        cache.put('/index.html', networkResponse.clone());
      }
      return networkResponse;
    })
    .catch(() => null);

  if (cachedResponse) {
    return cachedResponse;
  }

  const networkResponse = await networkFetch;
  if (networkResponse) {
    return networkResponse;
  }

  const fallback = await cache.match('/index.html');
  if (fallback) {
    return fallback;
  }

  return new Response('Modo Offline: No hay contenido disponible en caché para esta página.', {
    status: 503,
    statusText: 'Service Unavailable',
    headers: { 'Content-Type': 'text/plain; charset=utf-8' }
  });
}

// Interceptor de peticiones Fetch
self.addEventListener('fetch', (event) => {
  const request = event.request;

  // Solo interceptar peticiones GET
  if (request.method !== 'GET') {
    return;
  }

  const url = new URL(request.url);

  // Ignorar esquemas especiales (extensiones, dev-tools, etc.)
  if (url.protocol !== 'http:' && url.protocol !== 'https:') {
    return;
  }

  // Ignorar solicitudes internas del servidor de desarrollo Vite (HMR)
  if (
    url.pathname.startsWith('/@vite') ||
    url.pathname.startsWith('/@fs') ||
    url.pathname.startsWith('/@id') ||
    url.searchParams.has('import') ||
    url.pathname.includes('hot-update')
  ) {
    return;
  }

  // 1. Peticiones de Navegación (HTML de páginas completas)
  if (request.mode === 'navigate') {
    event.respondWith(handleNavigation(request));
    return;
  }

  // 2. Catálogo y metadatos de versión (/catalog.json, /catalog_version.json)
  if (url.pathname.endsWith('/catalog.json') || url.pathname.endsWith('/catalog_version.json')) {
    event.respondWith(staleWhileRevalidate(request, DATA_CACHE_NAME));
    return;
  }

  // 3. Fuentes web externas (Google Fonts y gstatic)
  if (url.hostname.includes('fonts.googleapis.com') || url.hostname.includes('fonts.gstatic.com')) {
    event.respondWith(cacheFirst(request, FONT_CACHE_NAME));
    return;
  }

  // 4. Bundles Vite inmutables con hash en el nombre (/assets/*)
  if (url.pathname.startsWith('/assets/')) {
    event.respondWith(cacheFirst(request, STATIC_CACHE_NAME));
    return;
  }

  // 5. Resto de assets estáticos locales (logos, favicon, manifest, scripts o estilos)
  if (
    url.origin === self.location.origin &&
    (url.pathname.endsWith('.svg') ||
     url.pathname.endsWith('.png') ||
     url.pathname.endsWith('.ico') ||
     url.pathname.endsWith('.json') ||
     url.pathname.endsWith('.js') ||
     url.pathname.endsWith('.css'))
  ) {
    event.respondWith(staleWhileRevalidate(request, STATIC_CACHE_NAME));
    return;
  }

  // 6. Para cualquier otra petición del mismo origen: Stale-While-Revalidate
  if (url.origin === self.location.origin) {
    event.respondWith(staleWhileRevalidate(request, STATIC_CACHE_NAME));
  }
});
