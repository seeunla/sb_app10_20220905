package com.ll.exam.app10.app.user.service;

import com.ll.exam.app10.app.user.entity.Member;
import com.ll.exam.app10.app.user.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class MemberServise {

    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    private MemberRepository memberRepository;

    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username).orElse(null);
    }

    public Member join(String username, String password, String email, MultipartFile profileImg) {
        String profileImgRelPath = "member/" + UUID.randomUUID().toString() + ".png";
        File profileImgFile = new File(genFileDirPath+ "/" + profileImgRelPath);

        profileImgFile.mkdir();

        try {
            profileImg.transferTo(profileImgFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Member member = Member.builder()
                .username(username)
                .email(email)
                .password(password)
                .profileImg(profileImgRelPath)
                .build();

        memberRepository.save(member);

        return member;
    }


}
