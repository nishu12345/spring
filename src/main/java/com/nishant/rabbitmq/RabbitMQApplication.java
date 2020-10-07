package com.nishant.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class RabbitMQApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQApplication.class, args);
    }

}
