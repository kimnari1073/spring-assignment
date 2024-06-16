package com.example.firstproject.controller;


import com.example.firstproject.entity.Blog;
import com.example.firstproject.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;
    @GetMapping("/blog")
    public String index(Model model){
        List<Blog> blogList =blogService.index();
        model.addAttribute("blogList",blogList);

        return "blog/index";
    }
}
