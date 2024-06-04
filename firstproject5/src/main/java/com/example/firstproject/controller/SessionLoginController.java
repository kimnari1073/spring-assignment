package com.example.firstproject.controller;


import com.example.firstproject.dto.JoinRequest;
import com.example.firstproject.dto.LoginRequest;
import com.example.firstproject.entity.User;
import com.example.firstproject.entity.UserRole;
import com.example.firstproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/session-login")
public class SessionLoginController {
    @Autowired
    private UserService userService;


//    @GetMapping(value = {"","/"})
//    public String home(Model model,
//                       @SessionAttribute(name="userId", required = false)
//                       Long userId){
//        User loginUser = userService.getLoginUserById(userId);
//
////        model.addAttribute("nickname",loginUser.getNickname());
//        return "greetings";
//    }

    @GetMapping("/join")
    public String joinPage(Model model){
        model.addAttribute("joinRequest",new JoinRequest());
        return "sessionLogin/join";
    }


    //가입만
    @PostMapping("/join")
    public String join(@ModelAttribute JoinRequest joinRequest){
        userService.join(joinRequest);
        return "sessionLogin/login";
    }

    //로그인 폼
    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("loginRequest", new LoginRequest());
        return "sessionLogin/login";
    }

    //로그인
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest,
                        HttpServletRequest httpServletRequest,
                        RedirectAttributes rttr){
        try{
            User user = userService.login(loginRequest);
            httpServletRequest.getSession().invalidate();
            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute("userId",user.getId());
            session.setAttribute("nickname",user.getLoginId());

            session.setMaxInactiveInterval(1800); //30분
            return "redirect:/";

        }catch (NullPointerException e){
            rttr.addFlashAttribute("msg", "없는 회원입니다!");
            return "redirect:/session-login/login";
        }
    }

    @GetMapping("/info")
    public String userInfo(@SessionAttribute(name="userId",required = false)
                           Long userid,
                           Model model){
        User loginUser = userService.getLoginUserById(userid);
        if(loginUser==null){
            return "redirect:/session-login/login";
        }
        model.addAttribute("user",loginUser);
        return "sessionLogin/info";
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session !=null){
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String adminPage(@SessionAttribute(name="userId",required = false)Long userid){
        User loginUser = userService.getLoginUserById(userid);
        if(loginUser == null){
            return "redirect:/session-login/login";
        }
        if(!loginUser.getRole().equals(UserRole.ADMIN)){
            System.out.println("사용자");
            return "redirect:/";
        }
        return "sessionLogin/admin";
    }
}
