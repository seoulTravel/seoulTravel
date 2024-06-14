// src/api/endpoints.ts
export const BASE_URL = import.meta.env.VITE_API_URL;

export const ENDPOINTS = {
  GET_SPOTS: `${BASE_URL}/spots`,
  GET_SPOT_SEARCH: `${BASE_URL}/spots/search`,
  GET_RESTAURANTS: `${BASE_URL}/restaurants`,
  GET_RESTAURANT_SEARCH: `${BASE_URL}/restaurants/search`,
  GET_PLACES: `${BASE_URL}/places`,
  GET_ACCOMMODATIONS: `${BASE_URL}/accommodations`,
  GET_ACCOMMODATION_SEARCH: `${BASE_URL}/accommodations/search`,

  GET_TRAVELPLANS: (id: number) => `${BASE_URL}/travelplans/${id}`,
  PUT_TRAVELPLANS: (id: number) => `${BASE_URL}/travelplans/${id}`,
  DEL_TRAVELPLANS: (id: number) => `${BASE_URL}/travelplans/${id}`,
  GET_TRAVELPLANS_ALL: `${BASE_URL}/api/travelPlans`,
  POST_TRAVELPLANS: `${BASE_URL}/api/travelPlans`,

  GET_REVIEWS: (id: number) => `${BASE_URL}/reviews/${id}`,
  DEL_REVIEWS: (id: number) => `${BASE_URL}/reviews/${id}`,
  PATCH_REVIEWS: (id: number) => `${BASE_URL}/reviews/${id}`,
  GET_REVIEWS_ALL: `${BASE_URL}/reviews`,
  POST_REVIEWS: `${BASE_URL}/reviews`,

  GET_PLACES_ALL: `${BASE_URL}/places`,

  GET_DETAILREVIEW: (id: number) => `${BASE_URL}/reviews/${id}/detailreview`,
  POST_DETAILREVIEW: `${BASE_URL}/detailreview`,
  DEL_DETAILREVIEW: (id: number) => `${BASE_URL}/detailreview/${id}`,
  PATCH_DETAILREVIEW: (id: number) => `${BASE_URL}/detailreview/${id}`,

  GET_COMMENT: (id: number) => `${BASE_URL}/review/${id}/comment`,
  POST_COMMENT: (id: number) => `${BASE_URL}/review/${id}/comment`,
  DEL_COMMENT: (id: number) => `${BASE_URL}/comment/${id}`,
  PATCH_COMMENT: (id: number) => `${BASE_URL}/comment/${id}`,

  GET_REPLY: (id: number) => `${BASE_URL}/comment/${id}/reply`,
  POST_REPLY: (id: number) => `${BASE_URL}/comment/${id}/reply`,
  DEL_REPLY: (id: number) => `${BASE_URL}/reply/${id}`,
  PATCH_REPLY: (id: number) => `${BASE_URL}/reply/${id}`,
};
