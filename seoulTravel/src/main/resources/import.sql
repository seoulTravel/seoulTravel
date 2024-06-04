-- 리뷰 테이블 더미 데이터 삽입
--INSERT INTO review (user_num, planner_id, review_rating, review_title, review_comment, timestamp, review_hits, review_scraps, review_likes, review_comments) VALUES
--(1, 1, 5, 'Great Experience', 'The tour was fantastic and exceeded my expectations.', '2024-05-01 10:30:00', 100, 50, 20, 30),
--(2, 2, 4, 'Good Tour', 'Enjoyed the tour. Well-organized and informative.', '2024-05-02 11:45:00', 80, 40, 15, 25),
--(3, 3, 3, 'Average Tour', 'The tour was okay. Could have been better.', '2024-05-03 12:00:00', 60, 30, 10, 15),
--(4, 4, 2, 'Disappointing Tour', 'The tour did not meet my expectations.', '2024-05-04 13:00:00', 40, 20, 5, 10),
--(5, 5, 1, 'Terrible Experience', 'The worst tour I have ever been on.', '2024-05-05 14:00:00', 20, 10, 2, 5);
--
---- 디테일 리뷰 테이블 더미 데이터 삽입
--INSERT INTO detailreview (review_id, detail_day, detail_order, detail_title, detail_comment, detail_rating) VALUES
--(1, 1, 1, 'Fantastic Start', 'The tour started off on a high note with an amazing guide.', 5),
--(1, 1, 2, 'Scenic Views', 'The views during the tour were breathtaking.', 5),
--(1, 2, 3, 'Interesting Facts', 'Learned a lot of interesting facts about the area.', 4),
--(1, 2, 4, 'Great Food', 'The food provided during the tour was delicious.', 4),
--(1, 3, 5, 'Friendly Guide', 'The guide was very friendly and knowledgeable.', 5),
--(2, 1, 1, 'Informative Guide', 'The guide provided a lot of interesting information.', 4),
--(2, 1, 2, 'Beautiful Scenery', 'The scenery along the tour route was beautiful.', 4),
--(2, 2, 3, 'Enjoyable Activities', 'The activities planned during the tour were enjoyable.', 3),
--(2, 2, 4, 'Good Duration', 'The tour was not too long or too short.', 3),
--(2, 3, 5, 'Helpful Staff', 'The staff were helpful and accommodating.', 4);