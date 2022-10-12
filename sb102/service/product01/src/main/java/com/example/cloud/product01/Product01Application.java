package com.example.cloud.product01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.cloud")
public class Product01Application {

    public static void main(String[] args) {
        SpringApplication.run(Product01Application.class, args);
    }

}
