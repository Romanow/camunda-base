package ru.romanow.camunda

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class CamundaApplication

fun main(args: Array<String>) {
    SpringApplication.run(CamundaApplication::class.java, *args)
}
