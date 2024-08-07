package com.example.sjudormbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SjuDormBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SjuDormBackendApplication.class, args);
    }

}
