package com.oos.cloud.standard.controller.web;

import com.oos.cloud.standard.controller.dto.request.RequestDeptDto;
import com.oos.cloud.standard.service.dept.DeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DeptController {

    private final DeptService deptService;

    @GetMapping(value = "/dept")
    public String deptList(ModelMap modelMap) throws Exception{
        modelMap.addAttribute("deptList", deptService.deptList());
        return "deptList";
    }

    @GetMapping(value = "/dept/{deptno}")
    public String deptDetail(@PathVariable("deptno") int deptno, ModelMap modelMap) throws Exception{
        modelMap.addAttribute("dept", deptService.deptDetail(deptno));
        return "deptDetail";
    }

    @PostMapping(value = "/dept")
    public String deptInsert(RequestDeptDto requestDeptDto) throws Exception{
        deptService.deptInsert(requestDeptDto.toEntity());
        return "redirect:/dept";
    }

    @PostMapping(value = "/dept/update")
    public String deptUpdate(RequestDeptDto requestDeptDto) throws Exception{
        deptService.deptUpdate(requestDeptDto.toEntity());
        return "redirect:/dept";
    }

    @PostMapping(value = "/dept/delete")
    public String deptDelete(String deptno) throws Exception{
        deptService.deptDelete(Integer.parseInt(deptno));
        return "redirect:/dept";
    }
}
