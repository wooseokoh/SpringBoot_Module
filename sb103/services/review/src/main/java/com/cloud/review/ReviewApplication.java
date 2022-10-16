package com.cloud.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.cloud")
@SpringBootApplication
public class ReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewApplication.class, args);
    }

}
