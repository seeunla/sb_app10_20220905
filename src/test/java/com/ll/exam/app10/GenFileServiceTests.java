package com.ll.exam.app10;

import com.ll.exam.app10.app.home.controller.HomeController;
import com.ll.exam.app10.app.user.controller.MemberController;
import com.ll.exam.app10.app.user.entity.Member;
import com.ll.exam.app10.app.user.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;



import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
    @DisplayName("?????? ?????????")
    public void t1() throws Exception {
        // WHEN
        // GET /
        ResultActions resultActions = mvc.perform(get("/"))
                .andDo(print());

        //THEN
        // ??????
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(HomeController.class))
                .andExpect(handler().methodName("showMain"))
                .andExpect(content().string(containsString("??????")));
    }

    @Test
    @DisplayName("????????? ???")
    void t2() throws Exception {
        long count = memberService.count();
        assertThat(count).isGreaterThan(0);
    }

    @Test
    @DisplayName("user1??? ????????? ??? ????????????????????? ???????????? user1??? ???????????? ????????? ??????.")
    @WithUserDetails("user1")
    void t3() throws Exception {
        ResultActions resultActions = mvc
                .perform(
                        get("/member/profile")
                )
                .andDo(print());

        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("showProfile"))
                .andExpect(content().string(containsString("user1@test.com")));
    }
        // mockMvc??? ????????? ??????

    @Test
    @DisplayName("user4??? ????????? ??? ????????????????????? ???????????? user4??? ???????????? ????????? ??????.")
    @WithUserDetails("user4")
    void t4() throws Exception {
        // mockMvc??? ????????? ??????
        ResultActions resultActions = mvc
                .perform(
                        get("/member/profile")
                )
                .andDo(print());

        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("showProfile"))
                .andExpect(content().string(containsString("user4@test.com")));
    }

    @Test
    @DisplayName("????????????")
    void t5() throws Exception {
        String testUploadUrl = "https://picsum.photos/200/200";
        String originalFileName = "test.png";
        //?????? ????????????
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Resource> response = restTemplate.getForEntity(testUploadUrl, Resource.class);
        InputStream inputStream = response.getBody().getInputStream();

        MockMultipartFile profileImg = new MockMultipartFile(
                "profileImg",
                originalFileName,
                "image/png",
                inputStream
        );

        //????????????(MVC MOCK)
        ResultActions resultActions = mvc
                .perform(
                        multipart("/member/join")
                                .file(profileImg)
                                .param("username", "user5")
                                .param("password", "1234")
                                .param("email", "user5@test.com")
                                .characterEncoding("UTF-8")
                )
                .andDo(print());
        // 5??? ????????? ??????????????? ???, ?????????

        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/member/profile"))
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("join"));

        Member member = memberService.getMemberById(5L);

        assertThat(member).isNotNull();

        memberService.removeProfileImg(member);
    }
}
