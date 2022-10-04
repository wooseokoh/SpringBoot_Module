package com.oos.cloud.standard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

@Slf4j
@SpringBootApplication
public class StandardApplication implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${custom.message}")
    String message;

    public static void main(String[] args) {
        SpringApplication.run(StandardApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.debug("===========================");
        log.debug(message);
        log.debug("===========================");
    }
}
