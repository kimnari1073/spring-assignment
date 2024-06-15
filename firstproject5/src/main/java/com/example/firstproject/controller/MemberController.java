package com.example.firstproject.controller;


import com.example.firstproject.entity.User;
import com.example.firstproject.repository.UserRepository;
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
    private UserRepository userRepository;


    //회원목록
    @GetMapping("/members")
    public String indexMembers(Model model){
        model.addAttribute("member",(List<User>)userRepository.findAll());
        return "members/index";
    }

    //회원 상세
    @GetMapping("/member/{id}")
    public String showMember(@PathVariable() Long id, Model model){
        User user = userRepository.findById(id).orElse(null);
        model.addAttribute("member",user);
        return "members/show";
    }

    //회원 수정 폼
    @GetMapping("/member/{id}/edit")
    public String editMember(@PathVariable Long id, Model model){
        model.addAttribute("member",userRepository.findById(id).orElse(null));
        return "members/edit";
    }

//    회원 수정
    @PostMapping("/member/update")
    public String updateMember(User userForm){
        User target = userRepository.findById(userForm.getId()).orElse(null);
        if(target != null){
            userRepository.save(userForm);
        }
        return "redirect:/member/"+userForm.getId();
    }

    //회원삭제
    @GetMapping("/member/{id}/delete")
    public String deleteMember(@PathVariable Long id, RedirectAttributes rttr){
        User target = userRepository.findById(id).orElse(null);
        if(target !=null){
            userRepository.delete(target);
            rttr.addFlashAttribute("msg","회원 삭제 되었습니다.");
        }
        return "redirect:/members";
    }


}
