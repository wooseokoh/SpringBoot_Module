package com.batch.sb110;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class Sb110Application {

    public static void main(String[] args) {
        SpringApplication.run(Sb110Application.class, args);
    }

}
