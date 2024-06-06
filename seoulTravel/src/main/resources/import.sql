INSERT INTO seoultravel.review (user_num, planner_id, review_rating, review_title, review_comment, timestamp, review_hits, review_scraps, review_likes, review_comments) VALUES
(1, 1, 5, 'Amazing Journey', 'The tour was fantastic and exceeded my expectations.', '2024-05-01 10:30:00', 100, 50, 20, 30),
(2, 2, 4, 'Pleasant Trip', 'Enjoyed the trip. Well-organized and informative.', '2024-05-02 11:45:00', 80, 40, 15, 25),
(3, 3, 3, 'Mediocre Experience', 'The tour was average. Could have been better.', '2024-05-03 12:00:00', 60, 30, 10, 15),
(4, 4, 2, 'Not Satisfactory', 'The tour did not meet my expectations.', '2024-05-04 13:00:00', 40, 20, 5, 10),
(5, 5, 1, 'Terrible Tour', 'The worst tour I have ever been on.', '2024-05-05 14:00:00', 20, 10, 2, 5);

INSERT INTO detailreview (review_id, detail_day, detail_order, detail_title, detail_comment, detail_rating) VALUES
(1, 1, 1, 'Fantastic Start', 'The tour started off on a high note with an amazing guide.', 5),
(1, 1, 2, 'Scenic Views', 'The views during the tour were breathtaking.', 5),
(2, 2, 3, 'Interesting Facts', 'Learned a lot of interesting facts about the area.', 4),
(2, 2, 4, 'Great Food', 'The food provided during the tour was delicious.', 4),
(2, 3, 5, 'Friendly Guide', 'The guide was very friendly and knowledgeable.', 5);

INSERT INTO comment (review_id, user_num, comment_text, timestamp) VALUES
(1, 1001, 'Great review! Very helpful.', '2024-06-01 09:00:00'),
(1, 1002, 'I totally agree with your points.', '2024-06-01 10:00:00'),
(2, 1003, 'Thanks for sharing your experience.', '2024-06-02 11:00:00'),
(2, 1004, 'Well written review.', '2024-06-02 12:00:00'),
(3, 1005, 'I found this review insightful.', '2024-06-03 13:00:00');

INSERT INTO reply (comment_id, user_num, comment_text, timestamp) VALUES
(1, 1001, 'Great comment! Very insightful.', '2024-06-01 09:30:00'),
(1, 1002, 'I agree with your thoughts.', '2024-06-01 10:30:00'),
(2, 1003, 'Thank you for your feedback.', '2024-06-02 12:00:00'),
(2, 1004, 'Well said!', '2024-06-02 13:00:00'),
(3, 1005, 'I appreciate your response.', '2024-06-03 14:00:00');
