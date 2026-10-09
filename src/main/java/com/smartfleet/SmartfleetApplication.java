package com.smartfleet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartfleetApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                SmartfleetApplication.class,
                args
        );
    }
}