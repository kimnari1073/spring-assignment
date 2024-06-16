package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long>,
        JpaRepository<Article, Long> {
    @Query(value = "SELECT * FROM Article ORDER BY created_at DESC LIMIT 3", nativeQuery = true)
    List<Article> findTop3ByOrderByCreatedAtDesc();
}
