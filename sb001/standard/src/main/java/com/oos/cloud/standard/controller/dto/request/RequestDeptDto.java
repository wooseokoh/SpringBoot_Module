package com.oos.cloud.standard.controller.dto.request;

import com.oos.cloud.standard.domain.dept.Dept;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RequestDeptDto {

    private Integer deptno;
    private String dname;
    private String loc;

    @Builder
    public RequestDeptDto(Integer deptno, String dname, String loc){
        this.deptno = deptno;
        this.dname = dname;
        this.loc = loc;
    }

    public Dept toEntity(){
        return Dept.builder().deptno(deptno).dname(dname).loc(loc).build();
    }
}
