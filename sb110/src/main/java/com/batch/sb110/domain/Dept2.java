package com.batch.sb110.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Dept2 {
    @Id
    Integer deptNo;
    String dName;
    String loc;
}