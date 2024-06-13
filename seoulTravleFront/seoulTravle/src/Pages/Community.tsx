import React, { useState, useEffect } from 'react';
import {
  fetchAllReviews,
  createReview,
  updateReview,
  addHitsScrapsLikes,
  addCommentToReview,
  Review,
  Comment,
} from '../api/reviewApi';
import Header from '../components/Header';

const Community: React.FC = () => {
  const [reviews, setReviews] = useState<Review[]>([]);
  const [newReview, setNewReview] = useState<Omit<Review, 'review_id' | 'timestamp' | 'comments'>>({
    user_num: 0,
    planner_id: 0,
    review_rating: 0,
    review_title: '',
    review_comment: '',
    review_hits: 0,
    review_scraps: 0,
    review_likes: 0,
    review_comments: 0,
  });
  const [newComment, setNewComment] = useState<{ [key: number]: string }>({});

  useEffect(() => {
    const fetchData = async () => {
      const data = await fetchAllReviews();
      setReviews(data);
    };

    fetchData();
  }, []);

  const handleReviewChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setNewReview({ ...newReview, [name]: value });
  };

  const handleCreateReview = async () => {
    const createdReview = await createReview(newReview);
    setReviews([...reviews, { ...createdReview, comments: [] }]);
  };

  const handleUpdateReview = async (id: number) => {
    console.log('리뷰 업데이트 중:', id);
    const updatedReview = await updateReview(id, { review_comment: '업데이트된 댓글' });
    setReviews(reviews.map(review => (review.review_id === id ? updatedReview : review)));
  };

  const handleAddHitsScrapsLikes = async (id: number) => {
    console.log('조회수, 스크랩, 좋아요 추가 중:', id);
    const updatedReview = await addHitsScrapsLikes(id, {
      review_hits: 1,
      review_likes: 1,
    });
    setReviews(reviews.map(review => (review.review_id === id ? updatedReview : review)));
  };

  const handleCommentChange = (reviewId: number, commentText: string) => {
    setNewComment({ ...newComment, [reviewId]: commentText });
  };

  const handleAddComment = async (reviewId: number) => {
    const comment = newComment[reviewId];
    console.log('댓글 추가 중:', reviewId);
    const commentData = {
      review_id: reviewId,
      user_num: 0, // 실제 사용자 번호로 대체해야 함
      comment_text: comment,
    };
    const addedComment = await addCommentToReview(reviewId, commentData);
    console.log('추가된 댓글:', addedComment);

    setReviews(reviews.map(review => 
      review.review_id === reviewId 
        ? { ...review, review_comments: (review.review_comments || 0) + 1, comments: [...(review.comments || []), addedComment] } 
        : review
    ));
    setNewComment({ ...newComment, [reviewId]: '' });
  };

  return (
    <div className="bg-gray-100 min-h-screen">
      <Header />
      <div className="container mx-auto py-24">
        <h1 className="text-4xl font-bold text-center mb-12">커뮤니티 공간</h1>
        <div className="space-y-8">
          {reviews.map((review) => (
            <div key={review.review_id} className="bg-white p-6 rounded-lg shadow-md">
              <h2 className="text-2xl font-semibold mb-2">{review.review_title}</h2>
              <p className="text-gray-700 mb-4">{review.review_comment}</p>
              <div className="text-sm text-gray-500 mb-4">
                <p>평점: {review.review_rating} / 5</p>
                <p>사용자 번호: {review.user_num}</p>
                <p>날짜: {new Date(review.timestamp).toLocaleDateString()}</p>
              </div>
              <div className="flex space-x-4 mb-4">
                <span>조회수: {review.review_hits}</span>
                <span>스크랩: {review.review_scraps}</span>
                <span>좋아요: {review.review_likes}</span>
                <span>댓글 수: {review.review_comments}</span>
              </div>
              <button
                onClick={() => handleUpdateReview(review.review_id)}
                className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 mr-2"
              >
                리뷰 업데이트
              </button>
              <button
                onClick={() => handleAddHitsScrapsLikes(review.review_id)}
                className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600"
              >
                조회수 & 좋아요 추가
              </button>
              <div className="mt-4">
                <input
                  type="text"
                  placeholder="댓글 입력"
                  value={newComment[review.review_id] || ''}
                  onChange={(e) => handleCommentChange(review.review_id, e.target.value)}
                  className="w-full p-2 mb-2 border rounded"
                />
                <button
                  onClick={() => handleAddComment(review.review_id)}
                  className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                >
                  댓글 추가
                </button>
              </div>
              <div className="mt-4 space-y-2">
                {review.comments && review.comments.map((comment: Comment) => (
                  <div key={comment.comment_id} className="bg-gray-100 p-2 rounded shadow-sm">
                    <p>{comment.comment_text}</p>
                    <p className="text-sm text-gray-500">사용자 번호: {comment.user_num}</p>
                    <p className="text-sm text-gray-500">날짜: {new Date(comment.timestamp).toLocaleDateString()}</p>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>
        <div className="mt-12 bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-2xl font-semibold mb-4">리뷰 작성</h2>
          <input
            type="text"
            name="review_title"
            placeholder="리뷰 제목"
            value={newReview.review_title}
            onChange={handleReviewChange}
            className="w-full p-2 mb-4 border rounded"
          />
          <textarea
            name="review_comment"
            placeholder="리뷰 내용"
            value={newReview.review_comment}
            onChange={handleReviewChange}
            className="w-full p-2 mb-4 border rounded"
          />
          <button
            onClick={handleCreateReview}
            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
          >
            리뷰 제출
          </button>
        </div>
      </div>
    </div>
  );
};

export default Community;
