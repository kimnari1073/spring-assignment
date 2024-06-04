package com.example.firstproject.controller;


import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@Slf4j
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

//    //폼
//    @GetMapping("/member/create")
//    public String singUpForm(){
//        return "members/signUp";
//    }

    //회원가입
    @PostMapping("/member/create")
    public String createMember(MemberForm memberForm){
        log.info(memberForm.toString());
        memberRepository.save(memberForm.toEntity());
        return "redirect:/members";
    }

    //회원목록
    @GetMapping("/members")
    public String indexMembers(Model model){
        model.addAttribute("member",(List<Member>)memberRepository.findAll());
        return "members/index";
    }

    //회원 상세
    @GetMapping("/member/{id}")
    public String showMember(@PathVariable() Long id, Model model){
        Member member = memberRepository.findById(id).orElse(null);
        model.addAttribute("member",member);
        return "members/show";
    }

    //회원 수정 폼
    @GetMapping("member/{id}/edit")
    public String editMember(@PathVariable Long id, Model model){
        model.addAttribute("member",memberRepository.findById(id).orElse(null));
        return "members/edit";
    }

    //회원 수정
    @PostMapping("/member/update")
    public String updateMember(MemberForm memberForm){
        Member memberEntity = memberForm.toEntity();
        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);
        if(target != null){
            memberRepository.save(memberEntity);
        }
        return "redirect:/member/"+memberEntity.getId();
    }

    @GetMapping("member/{id}/delete")
    public String deleteMember(@PathVariable Long id, RedirectAttributes rttr){
        Member target = memberRepository.findById(id).orElse(null);
        if(target !=null){
            memberRepository.delete(target);
            rttr.addFlashAttribute("msg","회원 삭제 되었습니다.");
        }
        return "redirect:/members";
    }


}
