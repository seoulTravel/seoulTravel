package com.dongyang.seoulTravel.repository.member;

import com.dongyang.seoulTravel.entity.member.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Override
    ArrayList<Article> findAll();
}