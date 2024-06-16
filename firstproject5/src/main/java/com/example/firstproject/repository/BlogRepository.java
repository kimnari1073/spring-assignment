package com.example.firstproject.repository;

import com.example.firstproject.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BlogRepository extends CrudRepository<Blog, Long>,
        JpaRepository<Blog, Long> {
}
