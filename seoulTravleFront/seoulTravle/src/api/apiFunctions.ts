// src/api/apiFunctions.ts
import apiClient from './api';
import { ENDPOINTS } from './endpoints';

export const getSpots = async () => {
  try {
    const response = await apiClient.get(ENDPOINTS.GET_SPOTS);
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
};

export const getPlaces = async () => {
  try {
    const response = await apiClient.get(ENDPOINTS.GET_PLACES);
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
};

// 필요한 다른 함수들을 추가합니다
export const getTravelPlan = async (id: number) => {
  try {
    const response = await apiClient.get(ENDPOINTS.GET_TRAVELPLANS(id));
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
};

