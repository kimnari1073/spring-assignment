package com.example.firstproject.controller;

import com.example.firstproject.entity.Article;
import com.example.firstproject.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FirstController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public String getArticles(Model model) {
        List<Article> latestArticles = articleService.getLatestArticles();
        model.addAttribute("articleList", latestArticles);
        return "index"; // Mustache 템플릿 파일명 (articleList.mustache)
    }
}
