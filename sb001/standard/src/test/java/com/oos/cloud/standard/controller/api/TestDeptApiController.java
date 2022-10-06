package com.oos.cloud.standard.controller.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oos.cloud.standard.controller.dto.response.ResponseDeptDto;
import com.oos.cloud.standard.core.dto.ShareDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ActiveProfiles({"dev", "db-maria"})
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestDeptApiController {

    @Autowired
    MockMvc mockMvc;

    private String version = "v1";

    @Test
    @Order(1)
    public void A001_deptInsert() throws Exception{
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("deptno","10");
        params.add("dname","ACCOUNTING");
        params.add("loc","DALLAS");
        MvcResult mvcResult = mockMvc.perform(post("/api/" + version + "/dept")
                        .params(params))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.OK.value()))
                        .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();

        ResponseDeptDto responseDeptDto = gson.fromJson(content, ResponseDeptDto.class);
        log.debug(responseDeptDto.getMessage());
        Assertions.assertThat(responseDeptDto.getDeptno()).isEqualTo(10);
    }

    @Test
    @Order(2)
    public void A001_deptUpdate() throws Exception{
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("deptno","10");
        params.add("dname","ACCOUNTING2");
        params.add("loc","DALLAS");
        MvcResult mvcResult = mockMvc.perform(put("/api/" + version + "/dept")
                        .params(params))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.OK.value()))
                        .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();

        ResponseDeptDto responseDeptDto = gson.fromJson(content, ResponseDeptDto.class);
        Assertions.assertThat(responseDeptDto.getDname()).isEqualTo("ACCOUNTING2");
    }

    @Test
    @Order(3)
    public void A001_deptList() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/api/" + version + "/dept"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.OK.value()))
                        .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();

        List<ResponseDeptDto> responseDeptDtoList = gson.fromJson(content, new TypeToken<List<ResponseDeptDto>>(){}.getType());
        responseDeptDtoList.forEach(dept -> {
            log.debug(dept.toString());
        });
    }

    @Test
    @Order(4)
    public void A001_deptDetil() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/api/" + version + "/dept/10"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();

        ResponseDeptDto responseDeptDto = gson.fromJson(content, ResponseDeptDto.class);
        Assertions.assertThat(responseDeptDto.getDeptno()).isEqualTo(10);
    }

    @Test
    @Order(5)
    public void A001_deptDelete() throws Exception{
        MvcResult mvcResult = mockMvc.perform(delete("/api/" + version + "/dept/10"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();

        ShareDto shareDto = gson.fromJson(content, ShareDto.class);
//        log.debug(shareDto.toString());
    }
}
