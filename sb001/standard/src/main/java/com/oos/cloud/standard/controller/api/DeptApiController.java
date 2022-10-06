package com.oos.cloud.standard.controller.api;

import com.oos.cloud.standard.controller.dto.request.RequestDeptDto;
import com.oos.cloud.standard.controller.dto.response.ResponseDeptDto;
import com.oos.cloud.standard.core.dto.ShareDto;
import com.oos.cloud.standard.service.dept.DeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class DeptApiController {

    private final DeptService deptService;

    @GetMapping(value = "/dept")
    public List<ResponseDeptDto> deptList() throws Exception{
        return deptService.deptList();
    }

    @GetMapping(value = "/dept/{deptno}")
    public ResponseDeptDto deptDetail(@PathVariable("deptno") int deptno) throws Exception{
        return deptService.deptDetail(deptno);
    }

    @PostMapping(value = "/dept")
    public ResponseDeptDto deptInsert(RequestDeptDto requestDeptDto) throws Exception{
        return deptService.deptInsert(requestDeptDto.toEntity());
    }

    @PutMapping(value = "/dept")
    public ResponseDeptDto deptUpdate(RequestDeptDto requestDeptDto) throws Exception{
        return deptService.deptUpdate(requestDeptDto.toEntity());
    }

    @DeleteMapping(value = "/dept/{deptno}")
    public ShareDto deptDelete(@PathVariable("deptno") int deptno) throws Exception{
        deptService.deptDelete(deptno);
        return new ShareDto(true, "삭제");
    }
}
