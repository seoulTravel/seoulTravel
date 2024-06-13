// src/api/reviewApi.ts
import { ENDPOINTS } from './endpoints';

export interface Review {
  review_id: number;
  user_num: number;
  planner_id: number;
  review_rating: number;
  review_title: string;
  review_comment: string;
  timestamp: string;
  review_hits: number;
  review_scraps: number;
  review_likes: number;
  review_comments: number;
  comments: Comment[];
}

export interface Comment {
  comment_id: number;
  review_id: number;
  user_num: number;
  comment_text: string;
  timestamp: string;
}

export const fetchAllReviews = async () => {
  const response = await fetch(ENDPOINTS.GET_REVIEWS_ALL);
  const reviews: Review[] = await response.json();
  
  // 각 리뷰의 코멘트를 불러옵니다.
  const reviewsWithComments = await Promise.all(
    reviews.map(async (review) => {
      const commentsResponse = await fetch(ENDPOINTS.GET_COMMENT(review.review_id));
      const comments = await commentsResponse.json();
      return { ...review, comments, review_comments: comments.length };
    })
  );
  
  return reviewsWithComments;
};

export const fetchReviewById = async (id: number) => {
  const response = await fetch(ENDPOINTS.GET_REVIEWS(id));
  const review: Review = await response.json();
  const commentsResponse = await fetch(ENDPOINTS.GET_COMMENT(id));
  const comments = await commentsResponse.json();
  return { ...review, comments, review_comments: comments.length };
};

export const createReview = async (review: Omit<Review, 'review_id' | 'timestamp' | 'comments'>) => {
  const response = await fetch(ENDPOINTS.POST_REVIEWS, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(review),
  });
  return response.json();
};

export const updateReview = async (id: number, partialReview: Partial<Review>) => {
  const existingReview = await fetchReviewById(id);
  if (!existingReview) {
    throw new Error('Review not found');
  }

  const updatedReview = {
    ...existingReview,
    ...partialReview,
  };

  console.log('Updating review with data:', updatedReview);

  const response = await fetch(ENDPOINTS.PATCH_REVIEWS(id), {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(updatedReview),
  });
  return response.json();
};

export const deleteReview = async (id: number) => {
  console.log('Deleting review with id:', id);

  const response = await fetch(ENDPOINTS.DEL_REVIEWS(id), {
    method: 'DELETE',
  });
  return response.json();
};

export const addHitsScrapsLikes = async (id: number, data: Partial<Review>) => {
  const existingReview = await fetchReviewById(id);
  if (!existingReview) {
    throw new Error('Review not found');
  }

  const updatedReview = {
    ...existingReview,
    ...data,
  };

  console.log('Adding hits, scraps, and likes with data:', updatedReview);

  const response = await fetch(ENDPOINTS.PATCH_REVIEWS(id), {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(updatedReview),
  });

  return response.json();
};

export const addCommentToReview = async (reviewId: number, comment: Omit<Comment, 'comment_id' | 'timestamp'>) => {
  console.log('Adding comment to review with id:', reviewId);

  const newComment = {
    ...comment,
    review_id: reviewId,
  };

  try {
    const response = await fetch(ENDPOINTS.POST_COMMENT(reviewId), {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(newComment),
    });

    const responseData = await response.json();
    console.log('Server response:', responseData);

    return responseData;
  } catch (error) {
    console.error('Error adding comment:', error);
    throw error;
  }
};

export const deleteComment = async (commentId: number) => {
  console.log('Deleting comment with id:', commentId);

  const response = await fetch(ENDPOINTS.DEL_COMMENT(commentId), {
    method: 'DELETE',
  });
  return response.json();
};
