import React, { useState, useEffect } from 'react';
import {
  fetchAllReviews,
  createReview,
  updateReview,
  addHitsScrapsLikes,
  addCommentToReview,
  deleteComment,
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
    console.log('Updating review with id:', id);
    const updatedReview = await updateReview(id, { review_comment: 'Updated comment' });
    setReviews(reviews.map(review => (review.review_id === id ? updatedReview : review)));
  };

  const handleAddHitsScrapsLikes = async (id: number) => {
    console.log('Adding hits, scraps, and likes to review with id:', id);
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
    console.log('Adding comment to review with id:', reviewId);
    const commentData = {
      review_id: reviewId,
      user_num: 0, // 사용자 번호는 실제 데이터로 대체해야 함
      comment_text: comment,
    };
    const addedComment = await addCommentToReview(reviewId, commentData);
    console.log('Added comment:', addedComment);

    setReviews(reviews.map(review => 
      review.review_id === reviewId 
        ? { ...review, review_comments: (review.review_comments || 0) + 1, comments: [...(review.comments || []), addedComment] } 
        : review
    ));
    setNewComment({ ...newComment, [reviewId]: '' });
  };

  const handleDeleteComment = async (commentId: number) => {
    console.log('Deleting comment with id:', commentId);
    await deleteComment(commentId);
    // 댓글 삭제 후 데이터 갱신
  };

  return (
    <div className="bg-gray-100 min-h-screen">
      <Header />
      <div className="container mx-auto py-24">
        <h1 className="text-4xl font-bold text-center mb-12">Community Page</h1>
        <div className="space-y-8">
          {reviews.map((review) => (
            <div key={review.review_id} className="bg-white p-6 rounded-lg shadow-md">
              <h2 className="text-2xl font-semibold mb-2">{review.review_title}</h2>
              <p className="text-gray-700 mb-4">{review.review_comment}</p>
              <div className="text-sm text-gray-500 mb-4">
                <p>Rating: {review.review_rating} / 5</p>
                <p>By User: {review.user_num}</p>
                <p>Date: {new Date(review.timestamp).toLocaleDateString()}</p>
              </div>
              <div className="flex space-x-4 mb-4">
                <span>Hits: {review.review_hits}</span>
                <span>Scraps: {review.review_scraps}</span>
                <span>Likes: {review.review_likes}</span>
                <span>Comments: {review.review_comments}</span>
              </div>
              <button
                onClick={() => handleUpdateReview(review.review_id)}
                className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 mr-2"
              >
                Update Review
              </button>
              <button
                onClick={() => handleAddHitsScrapsLikes(review.review_id)}
                className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600"
              >
                Add Hits & Likes
              </button>
              <div className="mt-4">
                <input
                  type="text"
                  placeholder="Enter comment"
                  value={newComment[review.review_id] || ''}
                  onChange={(e) => handleCommentChange(review.review_id, e.target.value)}
                  className="w-full p-2 mb-2 border rounded"
                />
                <button
                  onClick={() => handleAddComment(review.review_id)}
                  className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                >
                  Add Comment
                </button>
              </div>
              <div className="mt-4 space-y-2">
                {review.comments && review.comments.map((comment: Comment) => (
                  <div key={comment.comment_id} className="bg-gray-100 p-2 rounded shadow-sm">
                    <p>{comment.comment_text}</p>
                    <p className="text-sm text-gray-500">By User: {comment.user_num}</p>
                    <p className="text-sm text-gray-500">Date: {new Date(comment.timestamp).toLocaleDateString()}</p>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>
        <div className="mt-12 bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-2xl font-semibold mb-4">Write a Review</h2>
          <input
            type="text"
            name="review_title"
            placeholder="Review Title"
            value={newReview.review_title}
            onChange={handleReviewChange}
            className="w-full p-2 mb-4 border rounded"
          />
          <textarea
            name="review_comment"
            placeholder="Review Comment"
            value={newReview.review_comment}
            onChange={handleReviewChange}
            className="w-full p-2 mb-4 border rounded"
          />
          <button
            onClick={handleCreateReview}
            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
          >
            Submit Review
          </button>
        </div>
      </div>
    </div>
  );
};

export default Community;
