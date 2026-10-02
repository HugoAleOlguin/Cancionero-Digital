/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        golden: {
          DEFAULT: '#C5A03A',
          light: '#DFC36D',
          dark: '#9E7E24',
          subtle: '#FAF3E0',
          darkSubtle: '#2E2715',
        },
        parchment: {
          DEFAULT: '#FDFBF7',
          paper: '#F7F4EC',
          border: '#E8E3D7',
        },
        jetcarbon: {
          DEFAULT: '#1E242B',
          light: '#2E3640',
          muted: '#5A6573',
          border: '#323C47',
        },
        darkbg: '#12161A',
        darkcard: '#1A2128',
      },
      fontFamily: {
        serif: ['Merriweather', 'Georgia', 'Cambria', 'serif'],
        sans: ['Inter', 'system-ui', '-apple-system', 'BlinkMacSystemFont', 'sans-serif'],
        mono: ['JetBrains Mono', 'Fira Code', 'Courier New', 'monospace'],
      }
    },
  },
  plugins: [],
}
