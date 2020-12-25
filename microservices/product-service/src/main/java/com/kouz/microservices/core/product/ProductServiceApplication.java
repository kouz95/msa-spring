package com.kouz.microservices.core.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@ComponentScan("com.kouz")
public class ProductServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProductServiceApplication.class, args);

        String mongoDbHost = context.getEnvironment().getProperty("spring.data.mongodb.host");
        String mongoDbPort = context.getEnvironment().getProperty("spring.data.mongodb.port");
        log.info("Connected to MongoDb: " + mongoDbHost + ":" + mongoDbPort);
    }
}
