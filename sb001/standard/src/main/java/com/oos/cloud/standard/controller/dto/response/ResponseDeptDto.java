package com.oos.cloud.standard.controller.dto.response;

import com.oos.cloud.standard.core.dto.ShareDto;
import com.oos.cloud.standard.domain.dept.Dept;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseDeptDto extends ShareDto {

    private Integer deptno;
    private String dname;
    private String loc;

    public ResponseDeptDto(Dept entity){
        this.deptno = entity.getDeptno();
        this.dname = entity.getDname();
        this.loc = entity.getLoc();
    }
}
