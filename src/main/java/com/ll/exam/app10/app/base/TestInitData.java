package com.ll.exam.app10.app.base;

import com.ll.exam.app10.app.user.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("test")
public class TestInitData {
    @Bean
    CommandLineRunner init(MemberService memberServise, PasswordEncoder passwordEncoder) {
        return args -> {
            String password = passwordEncoder.encode("1234");
            memberServise.join("user1", password, "user1@test.com");
            memberServise.join("user2", password, "user2@test.com");
            memberServise.join("user3", password, "user3@test.com");
            memberServise.join("user4", password, "user4@test.com");
        };
    }
}
