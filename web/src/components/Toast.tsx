import React, { useEffect } from 'react';

interface ToastProps {
  message: string | null;
  onClose: () => void;
  duration?: number;
}

export const Toast: React.FC<ToastProps> = ({ message, onClose, duration = 2200 }) => {
  useEffect(() => {
    if (!message) return;
    const timer = setTimeout(() => {
      onClose();
    }, duration);
    return () => clearTimeout(timer);
  }, [message, onClose, duration]);

  if (!message) return null;

  return (
    <div className="fixed bottom-6 sm:bottom-8 left-1/2 -translate-x-1/2 z-50 pointer-events-none animate-in fade-in slide-in-from-bottom-3 duration-200">
      <div className="bg-[#1C1917] text-white dark:bg-white dark:text-[#1C1917] px-4 py-2.5 rounded-2xl shadow-xl border border-golden/30 flex items-center gap-2.5 text-xs font-semibold backdrop-blur-md">
        <span className="w-2 h-2 rounded-full bg-golden animate-pulse" />
        <span>{message}</span>
      </div>
    </div>
  );
};
