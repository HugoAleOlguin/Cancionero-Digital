import React from 'react';
import ReactDOM from 'react-dom/client';
import { App } from './App';
import './index.css';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

// Registro de Service Worker para soporte PWA 100% Offline
if ('serviceWorker' in navigator) {
  window.addEventListener('load', () => {
    navigator.serviceWorker
      .register('/sw.js')
      .then((registration) => {
        console.log('[PWA] Service Worker registrado exitosamente con scope:', registration.scope);
      })
      .catch((error) => {
        console.error('[PWA] Error al registrar el Service Worker:', error);
      });
  });
}

