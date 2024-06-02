package com.dongyang.seoulTravel.repository.review;

import com.dongyang.seoulTravel.entity.review.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    @Override
    ArrayList<Review> findAll();

}

