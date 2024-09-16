package ru.romanow.camunda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CamundaApplication {
    public static void main(String[] args) {
        SpringApplication.run(CamundaApplication.class, args);
    }
}
