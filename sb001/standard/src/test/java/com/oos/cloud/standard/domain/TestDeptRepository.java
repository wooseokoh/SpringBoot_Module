package com.oos.cloud.standard.domain;

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

import java.util.ArrayList;
import java.util.List;

//@DataJpaTest // DB 와 관련된 컴포넌트만 메모리에 로딩
@Slf4j
@ActiveProfiles({"dev", "db-maria"})
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestDeptRepository {

    @Autowired
    private DeptRepository deptRepository;

    @Test
    @Order(1)
    @Commit
    public void A001_DEPT_TABLE_입력(){
        List deptList = new ArrayList<Dept>();
        deptList.add(Dept.builder().deptno(10).dname("ACCOUNTING").loc("TEXAS").build());
        deptList.add(Dept.builder().deptno(20).dname("RESEARCH").loc("DALLAS").build());
        deptList.add(Dept.builder().deptno(30).dname("SALES").loc("NEW YORK").build());
        deptList.add(Dept.builder().deptno(40).dname("OPERATIONS").loc("BOSTON").build());
        deptRepository.saveAll(deptList);

        Assertions.assertThat(deptRepository.findById(10).isPresent()).isEqualTo(true);
        Assertions.assertThat(deptRepository.findById(20).isPresent()).isEqualTo(true);
        Assertions.assertThat(deptRepository.findById(30).isPresent()).isEqualTo(true);
        Assertions.assertThat(deptRepository.findById(40).isPresent()).isEqualTo(true);
    }

    @Test
    @Order(2)
    @Commit
    public void A002_DEPT_TABLE_수정(){
        String changeDname = "ACCOUNTING2";
        deptRepository.save(Dept.builder().deptno(10).dname(changeDname).loc("TEXAS").build());
        Dept dept = deptRepository.findById(10).get();
        Assertions.assertThat(changeDname).isEqualTo(dept.getDname());
    }

    @Test
    @Order(3)
    @Commit
    public void A003_DEPT_TABLE_삭제(){
        Integer [] deptnos = {10,20,30,40};
        for(Integer deptno : deptnos){
            deptRepository.delete(Dept.builder().deptno(deptno).build());
            boolean isPresent = deptRepository.findById(deptno).isPresent();
            Assertions.assertThat(false).isEqualTo(isPresent);
        }
    }
}
