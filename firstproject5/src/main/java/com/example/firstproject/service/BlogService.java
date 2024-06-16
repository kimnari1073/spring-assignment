package com.example.firstproject.service;

import com.example.firstproject.entity.Blog;
import com.example.firstproject.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> index() {return (List<Blog>) blogRepository.findAll();}
}
