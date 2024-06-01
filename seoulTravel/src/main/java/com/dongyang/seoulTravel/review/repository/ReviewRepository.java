package com.dongyang.seoulTravel.review.repository;

import com.dongyang.seoulTravel.review.entity.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    @Override
    ArrayList<Review> findAll();
}

