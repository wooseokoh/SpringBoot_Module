package com.example.sb115;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Sb115Application {

    public static void main(String[] args) {
        SpringApplication.run(Sb115Application.class, args);
    }

}
