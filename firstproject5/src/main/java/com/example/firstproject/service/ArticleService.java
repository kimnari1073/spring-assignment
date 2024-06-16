package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index(){
        return (List<Article>) articleRepository.findAll();
    }

    public Article show(Long id){
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto){
        Article article = dto.toEntity();
        if(article.getId() !=null){
            return null;
        }
        return articleRepository.save(article);

    }

    public Article update(Long id, ArticleForm dto){
        Article article = dto.toEntity();
        Article target = articleRepository.findById(id).orElse(null);
        if(target==null || !id.equals(article.getId())){
            return null;
        }
        target.patch(article);
        return articleRepository.save(target);
    }

    public Article delete(Long id){
        Article target = articleRepository.findById(id).orElse(null);
        if(target==null){
            return null;
        }
        articleRepository.delete(target);
        return target;
    }

//    @Transactional
//    public List<Article> createArticles(List<ArticleForm> dtos){
//        List<Article> articleList = new ArrayList<>();
//
////        for (int i = 0; i <dtos.size() ; i++) {
////            ArticleForm dto = dtos.get(i);
////            Article entity = dto.toEntity();
////            articleList.add(entity);
////        }
////        for (int i = 0; i < articleList.size(); i++) {
////            Article article = articleList.get(i);
////            articleRepository.save(article);
////        }
//        dtos.forEach(dto -> articleList.add(dto.toEntity()));
//        articleRepository.saveAll(articleList);
//
//        try{
//            articleRepository.findById(-1L).orElseThrow();
//        }catch (Exception e){
//            throw new IllegalArgumentException("결제 실패!");
//        }
//        return articleList;
//    }
}
