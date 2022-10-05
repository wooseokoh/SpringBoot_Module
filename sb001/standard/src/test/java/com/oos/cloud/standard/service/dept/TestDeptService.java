package com.oos.cloud.standard.service.dept;

import com.oos.cloud.standard.controller.dto.response.ResponseDeptDto;
import com.oos.cloud.standard.domain.dept.Dept;
import com.oos.cloud.standard.domain.dept.DeptRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ActiveProfiles({"dev", "db-h2"})
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestDeptService {

    @Autowired
    DeptService deptService;

    @Autowired
    DeptRepository deptRepository;

    @Test
    @Order(1)
    @Commit
    @Transactional
    public void A001_DEPT_TABLE_입력() throws Exception{
        List<Dept> deptList = new ArrayList<Dept>();
        deptList.add(Dept.builder().deptno(10).dname("ACCOUNTING").loc("TEXAS").build());
        deptList.add(Dept.builder().deptno(20).dname("RESEARCH").loc("DALLAS").build());
        deptList.add(Dept.builder().deptno(30).dname("SALES").loc("NEW YORK").build());
        deptList.add(Dept.builder().deptno(40).dname("OPERATIONS").loc("BOSTON").build());

        for(Dept dept: deptList){
            deptService.deptInsert(dept);
        }
        for(Dept dept: deptList){
            Integer deptno = dept.getDeptno();
            ResponseDeptDto responseDeptDto = deptService.deptDetail(deptno);
            Assertions.assertThat(responseDeptDto.getDeptno()).isEqualTo(deptno);
        }
    }

    @Test
    @Order(2)
    @Commit
    @Transactional
    public void A002_DEPT_TABLE_수정(){
        String changeDname = "ACCOUNTING2";
        deptService.deptUpdate(Dept.builder().deptno(10).dname(changeDname).loc("TEXAS").build());
        ResponseDeptDto responseDeptDto = deptService.deptDetail(10);
        Assertions.assertThat(changeDname).isEqualTo(responseDeptDto.getDname());
    }

    @Test
    @Order(3)
    @Commit
    @Transactional
    public void A003_DEPT_TABLE_삭제(){
        Integer [] deptnos = {10,20,30,40};
        for(Integer deptno : deptnos){
            deptService.deptDelete(deptno);
            boolean isPresent = deptRepository.findById(deptno).isPresent();
            Assertions.assertThat(false).isEqualTo(isPresent);
        }
    }
}
