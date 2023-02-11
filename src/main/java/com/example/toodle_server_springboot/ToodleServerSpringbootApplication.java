package com.example.toodle_server_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ToodleServerSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToodleServerSpringbootApplication.class, args);
    }

}
