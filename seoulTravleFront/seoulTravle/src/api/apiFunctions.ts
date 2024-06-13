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

export const getAccommodations = async () => {
  try {
    const response = await apiClient.get(ENDPOINTS.GET_ACCOMMODATIONS);
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}

export const getRestaurants = async () => {
  try {
    const response = await apiClient.get(ENDPOINTS.GET_RESTAURANTS);
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}

export const getTravelPlans = async () => {
  try {
    const response = await apiClient.get(ENDPOINTS.GET_TRAVELPLANS_ALL);
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}

export const postTravelPlan = async (travelPlan: any) => {
  try {
    const response = await apiClient.post(ENDPOINTS.POST_TRAVELPLANS, travelPlan);
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
} 

export const getReviews = async (id: number) => {
  try {
    const response = await apiClient.get(ENDPOINTS.GET_REVIEWS(id));
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}

export const postReview = async (review: any) => { 
  try {
    const response = await apiClient.post(ENDPOINTS.POST_REVIEWS, review);
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}
export const getDetailReview = async (id: number) => {
  try {
    const response = await apiClient.get(ENDPOINTS.GET_DETAILREVIEW(id));
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}
export const postDetailReview = async (detailReview: any) => {
  try {
    const response = await apiClient.post(ENDPOINTS.POST_DETAILREVIEW, detailReview);
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}
export const getComment = async (id: number) => {
  try {
    const response = await apiClient.get(ENDPOINTS.GET_COMMENT(id));
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}

// export const postComment = async (comment: any) => {
//   try {
//     const response = await apiClient.post(ENDPOINTS.POST_COMMENT, comment);
//     return response.data;
//   } catch (error) {
//     console.error('API 요청 중 오류 발생:', error);
//     throw error;
//   }
// }

export const getReply = async (id: number) => {
  try {
    const response = await apiClient.get(ENDPOINTS.GET_REPLY(id));
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}

// export const postReply = async (reply: any) => {
//   try {
//     const response = await apiClient.post(ENDPOINTS.POST_REPLY, reply);
//     return response.data;
//   } catch (error) {
//     console.error('API 요청 중 오류 발생:', error);
//     throw error;
//   }
// }

export const delReply = async (id: number) => {
  try {
    const response = await apiClient.delete(ENDPOINTS.DEL_REPLY(id));
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}

export const patchReply = async (id: number, reply: any) => {
  try {
    const response = await apiClient.patch(ENDPOINTS.PATCH_REPLY(id), reply);
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}

export const delComment = async (id: number) => {
  try {
    const response = await apiClient.delete(ENDPOINTS.DEL_COMMENT(id));
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}

export const patchComment = async (id: number, comment: any) => {
  try {
    const response = await apiClient.patch(ENDPOINTS.PATCH_COMMENT(id), comment);
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}

export const delDetailReview = async (id: number) => {
  try {
    const response = await apiClient.delete(ENDPOINTS.DEL_DETAILREVIEW(id));
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}

export const patchDetailReview = async (id: number, detailReview: any) => {
  try {
    const response = await apiClient.patch(ENDPOINTS.PATCH_DETAILREVIEW(id), detailReview);
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}

export const delReview = async (id: number) => {
  try {
    const response = await apiClient.delete(ENDPOINTS.DEL_REVIEWS(id));
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}

export const patchReview = async (id: number, review: any) => {
  try {
    const response = await apiClient.patch(ENDPOINTS.PATCH_REVIEWS(id), review);
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}

export const getReviewsAll = async () => {
  try {
    const response = await apiClient.get(ENDPOINTS.GET_REVIEWS_ALL);
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}

export const getPlacesAll = async () => {
  try {
    const response = await apiClient.get(ENDPOINTS.GET_PLACES_ALL);
    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    throw error;
  }
}


