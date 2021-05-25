package com.bekkos.eksamen2021;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class Eksamen2021Application {

    public static void main(String[] args) {
        SpringApplication.run(Eksamen2021Application.class, args);
    }

}
