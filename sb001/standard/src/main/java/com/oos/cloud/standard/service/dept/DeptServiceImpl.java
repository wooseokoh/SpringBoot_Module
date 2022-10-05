package com.oos.cloud.standard.service.dept;

import com.oos.cloud.standard.controller.dto.response.ResponseDeptDto;
import com.oos.cloud.standard.core.exception.BusinessException;
import com.oos.cloud.standard.domain.dept.Dept;
import com.oos.cloud.standard.domain.dept.DeptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {

    private final DeptRepository deptRepository;

    @Override
    public List<ResponseDeptDto> deptList() throws BusinessException {
        List<ResponseDeptDto> responseDeptDtoList = new ArrayList<>();
        deptRepository.findAll().forEach(dept -> {
            log.debug(dept.toString());
            responseDeptDtoList.add(new ResponseDeptDto(dept));
        });
        return responseDeptDtoList;
    }

    @Override
    public ResponseDeptDto deptDetail(Integer deptno) throws BusinessException {
        Optional<Dept> optionalDept = deptRepository.findById(deptno);
        Dept dept = optionalDept.orElseThrow(() -> new BusinessException("해당 부서 번호에 대한 정보가 없습니다"));
        return new ResponseDeptDto(dept);
    }

    @Override
    @Transactional
    public ResponseDeptDto deptInsert(Dept dept) throws BusinessException {
        Optional<Dept> optionalDept = deptRepository.findById(dept.getDeptno());
        if(!optionalDept.isPresent()){
            dept = deptRepository.save(dept);
        }else{
            throw new BusinessException("해당 부서 번호가 이미 존재합니다.");
        }
        return new ResponseDeptDto(dept);
    }

    @Override
    @Transactional
    public ResponseDeptDto deptUpdate(Dept dept) throws BusinessException {
        Optional<Dept> optionalDept = deptRepository.findById(dept.getDeptno());
        if(optionalDept.isPresent()){
            dept = deptRepository.save(dept);
        }else{
            throw new BusinessException("해당 부서 번호가 이미 존재합니다.[" + dept.getDeptno() + "]");
        }
        return new ResponseDeptDto(dept);
    }

    @Override
    @Transactional
    public void deptDelete(Integer deptno) throws BusinessException {
        deptRepository.deleteById(deptno);
    }
}
