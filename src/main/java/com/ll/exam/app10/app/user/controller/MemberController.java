package com.ll.exam.app10.app.user.controller;

import com.ll.exam.app10.app.user.entity.Member;
import com.ll.exam.app10.app.user.service.MemberServise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberServise memberServise;
    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }

    @PostMapping("/join")
    @ResponseBody
    public String join(String username, String password, String email, MultipartFile profileImg, HttpSession session) {
        Member member = memberServise.getMemberByUsername(username);

        if (member != null) {
            return "redirect:/?errorMsg=이미 가입된 회원입니다!";
        }

        Member newmember = memberServise.join(username, "{noop}", email,profileImg);

        session.setAttribute("loginedMemberId", member.getId());
        return "redirect:/member/profile";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session) {
        Long loginedMemberId = (Long) session.getAttribute("");
        boolean isLogined = loginedMemberId == null;

        if ( isLogined == false) {
            return "redirect:/?errorMsg=로그인 후 이용해주세요.";
        }
        return "/member/profile";
    }
}
