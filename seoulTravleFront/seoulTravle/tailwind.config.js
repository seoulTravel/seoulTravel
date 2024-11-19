/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {},
    fontFamily: {
      'sans': ['Pretendard-Regular', 'sans-serif'],
      'serif': ['Noto Serif KR', 'serif'],
      'mono': ['Noto Sans KR', 'sans-serif'],
      'display': ['TTLaundryGothicB', 'sans-serif'],
      'body': ['Noto Sans KR', 'sans-serif'],
    }
  },
  plugins: [],
}
