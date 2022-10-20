package com.cloud.sender.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Dept {
    Integer deptNo;
    String dName;
    String loc;
    String time;
}