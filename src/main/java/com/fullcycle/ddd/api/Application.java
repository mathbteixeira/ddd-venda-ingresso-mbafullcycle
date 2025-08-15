package com.fullcycle.ddd.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fullcycle.ddd")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}