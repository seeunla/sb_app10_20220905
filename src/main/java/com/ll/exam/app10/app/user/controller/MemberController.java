package com.ll.exam.app10.app.user.controller;

import com.ll.exam.app10.app.user.entity.Member;
import com.ll.exam.app10.app.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberServise;
    private final PasswordEncoder passwordEncoder;
    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }

    @PostMapping("/join")
    @ResponseBody
    public String join(HttpServletRequest request, String username, String password, String email, MultipartFile profileImg) {
        Member oldmember = memberServise.getMemberByUsername(username);

        String passwordClearText = password;
        password = passwordEncoder.encode(password);

        if (oldmember != null) {
            return "redirect:/?errorMsg=이미 가입된 회원입니다!";
        }

        Member member = memberServise.join(username, password, email,profileImg);

        try {
            request.login(username, password);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/member/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String showProfile(Principal principal, Model model) {
        Member loginedMember = memberServise.getMemberByUsername(principal.getName());

        model.addAttribute("loginedMember", loginedMember);
        return "/member/profile";
    }
}
