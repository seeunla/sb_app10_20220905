package com.ll.exam.app10;

import com.ll.exam.app10.app.home.controller.HomeController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GenFileServiceTests {
    @Autowired
    private MockMvc mvc;


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
}
