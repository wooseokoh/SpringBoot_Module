package com.kafkatuto.sb111.controller;

import com.kafkatuto.sb111.kafka.JsonKafkaProducer;
import com.kafkatuto.sb111.payload.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka")
public class JsonMessageController {
    private JsonKafkaProducer kafkaProducer;

    public JsonMessageController(JsonKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    // http:localhost:8080/api/v1/kafka/publish?message=hello world
    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody User user){
        kafkaProducer.sendMessage(user);
        return ResponseEntity.ok("Json Message sent to the topic");
    }
}
