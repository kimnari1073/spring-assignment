package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto>comments(Long articleId){
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        List<CommentDto> dtos = new ArrayList<CommentDto>();
        for(int i=0; i<comments.size();i++){
            Comment c = comments.get(i);
            CommentDto dto = CommentDto.createCommentDto(c);
            dtos.add(dto);

        }
        return dtos;
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto commentDto){
        Article article = articleRepository.findById(articleId)
                .orElseThrow(()-> new IllegalArgumentException("댓글 생성 실패!"+
                        "대상 게시글이 없습니다."));

        Comment comment = Comment.createComment(commentDto,article);
        Comment created = commentRepository.save(comment);

        return CommentDto.createCommentDto(created);
    }

}
