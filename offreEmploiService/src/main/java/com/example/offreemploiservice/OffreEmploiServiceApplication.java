package com.example.offreemploiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OffreEmploiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OffreEmploiServiceApplication.class, args);
    }

}
