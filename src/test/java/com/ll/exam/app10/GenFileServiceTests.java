package com.ll.exam.app10;

import com.ll.exam.app10.app.home.controller.HomeController;
import com.ll.exam.app10.app.user.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles({"base-addi", "test"})
public class GenFileServiceTests {
    @Autowired
    private MockMvc mvc;


    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("안녕 업로드")
    public void t1() throws Exception {
        // WHEN
        // GET /
        ResultActions resultActions = mvc.perform(get("/"))
                .andDo(print());

        //THEN
        // 안녕
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(HomeController.class))
                .andExpect(handler().methodName("main"))
                .andExpect(content().string(containsString("안녕")));
    }

    @Test
    @DisplayName("회원의 수")
    void t2() throws Exception {
        long count = memberService.count();
        assertThat(count).isGreaterThan(0);
    }

    @Test
    @DisplayName("user1로 로그인 후 프로필페이지에 접속하면 user1의 이메일이 보여야 한다.")
    @Rollback(false)
    void t3() throws Exception {
        // mockMvc로 로그인 처리
    }

    @Test
    @DisplayName("user4로 로그인 후 프로필페이지에 접속하면 user4의 이메일이 보여야 한다.")
    @Rollback(false)
    void t4() throws Exception {
        // mockMvc로 로그인 처리
    }
}
