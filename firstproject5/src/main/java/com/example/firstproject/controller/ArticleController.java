package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.entity.UserRole;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import com.example.firstproject.service.ArticleService;
import com.example.firstproject.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;

    //공지사항 작성
    @GetMapping("/articles/new")
    public String newArticleForm(HttpServletRequest httpServletRequest,
                                 RedirectAttributes rttr) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return "sessionLogin/login";
        }
        UserRole role = (UserRole)session.getAttribute("role");

        if ("ADMIN".equals(role.toString())) {
            // 관리자 권한 처리
            return "articles/new";
        } else {
            // 사용자 권한 처리
            rttr.addFlashAttribute("msg", "권한이 없습니다.");
            return "redirect:/articles";
        }
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {  //DTO ArticleForm
        Article saved = articleService.create(form);
        return "redirect:/articles/"+saved.getId();

    }
    //목록
    @GetMapping("/articles")
    public String index(Model model) {
        List<Article> articleEntityList = articleService.index();
        model.addAttribute("articleList", articleEntityList);
        return "articles/index";
    }

    //수정페이지
    @GetMapping("/articles/edit-page")
    public String getEditPage(ArticleForm form,
                              Model model,
                              HttpServletRequest httpServletRequest,
                              RedirectAttributes rttr) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return "sessionLogin/login";
        }
        UserRole role = (UserRole)session.getAttribute("role");

        if ("ADMIN".equals(role.toString())) {
            // 관리자 권한 처리
            model.addAttribute("article",form);
            return "articles/edit";
        } else {
            // 사용자 권한 처리
            rttr.addFlashAttribute("msg", "권한이 없습니다.");
            return "redirect:/articles";
        }



    }

    //게시글 수정
    @PostMapping("/articles/update")
    public String update(@RequestParam Long id, ArticleForm form) { //Long id는 없어도 된다. -> form에 id필드가 있기 때문에
        Article result = articleService.update(id, form);
        return "redirect:/articles/" + result.getId();
    }


    //게시글 상세페이지
    @GetMapping("/articles/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Article articleResult = articleService.show(id);
        List<CommentDto>commentsDtos = commentService.comments(id);
        model.addAttribute("article", articleResult);
        model.addAttribute("commentDtos",commentsDtos);
        return "articles/show";
    }


    //게시글 삭제
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id,
                         RedirectAttributes rttr,
                         HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return "sessionLogin/login";
        }
        UserRole role = (UserRole)session.getAttribute("role");

        if ("ADMIN".equals(role.toString())) {
            // 관리자 권한 처리
            Article articleResult = articleService.delete(id);
            if (articleResult != null) {
                rttr.addFlashAttribute("msg", "삭제됐습니다!");
            }
            return "redirect:/articles";
        } else {
            // 사용자 권한 처리
            rttr.addFlashAttribute("msg", "권한이 없습니다.");
            return "redirect:/articles";
        }
    }
}
