package com.gumlayudong.gumlayudongbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GumlayudongBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GumlayudongBackendApplication.class, args);
    }

}
