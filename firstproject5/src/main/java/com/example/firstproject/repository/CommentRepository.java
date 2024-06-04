package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    //쿼리메소드 만드는 법 2가지
    //1. @Query 어노테이션 사용
    //2. orm.xml 파일 이용
    @Query(value="SELECT * FROM comment WHERE article_id = :articleId",
        nativeQuery = true) //value 속성에 실행하려는 쿼리 작성
    List<Comment> findByArticleId(Long articleId);

    List<Comment> findByNickname(String nickname);
}
