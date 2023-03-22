package com.example.sb115.web;

import com.example.sb115.config.dummy.DummyObject;
import com.example.sb115.domain.user.UserRepository;
import com.example.sb115.dto.user.UserReqDto.JoinReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:db/teardown.sql") // teardown
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class UserControllerTest extends DummyObject {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String APPLICATION_JSON_UTF8 = "application/json; charset=utf-8";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.save(newUser("abcde"));
    }

    @Test
    public void join_success_test() throws Exception {
        // given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("abcd");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("abcd@gmail.com");

        String requestBody = om.writeValueAsString(joinReqDto);
        log.debug("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc
                .perform(post("/api/join").content(requestBody).contentType(APPLICATION_JSON_UTF8));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        log.debug("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isCreated());
    }
}