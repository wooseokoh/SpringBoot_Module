package com.example.sb002.controller.api;

import com.example.sb002.dto.Dept;
import com.example.sb002.dto.Result;
import com.example.sb002.service.DeptService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class RestDeptController {
    final DeptService deptService;
    @RequestMapping(value = {"/dept.json"}, method = RequestMethod.GET)
    public Result deptGet(Integer deptNo){
        Dept dept = null;
        if(deptNo != null){
            dept = deptService.getDept(deptNo);
        }
        if(dept == null){
            return new Result("N");
        }else{
            return new Result("Y");
        }

    }
}