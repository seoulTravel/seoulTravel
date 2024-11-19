// src/api/api.ts
import axios from 'axios';

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_URL, // 기본 URL 설정
  headers: {
    'Content-Type': 'application/json',
  },
});

export default apiClient;
