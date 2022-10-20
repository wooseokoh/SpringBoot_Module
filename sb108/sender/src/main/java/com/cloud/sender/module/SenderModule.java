package com.cloud.sender.module;

import com.cloud.sender.dto.Dept;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@AllArgsConstructor
@Component
public class SenderModule {

    final RabbitTemplate rabbitTemplate;

//    @Scheduled(fixedRate = 1000)
    public void sender1(){
        LocalDateTime nowDateTime = LocalDateTime.now();
        String time = nowDateTime.toString();
        System.out.println("=> "+time);
        rabbitTemplate.convertAndSend("time","time-first", time);
    }

    @Scheduled(fixedRate = 1000)
    public void sender2(){
        LocalDateTime nowDateTime = LocalDateTime.now();
        String time = nowDateTime.toString();
        Dept dept = new Dept(10, "testNo", "testName", time);
        System.out.println("1 << 보냈음: " + dept.toString());
        rabbitTemplate.convertAndSend("dept", "dept-first", dept);
    }
}
