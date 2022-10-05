package com.oos.cloud.standard.domain.dept;

import com.oos.cloud.standard.domain.dept.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptRepository extends JpaRepository<Dept, Integer> {
}
