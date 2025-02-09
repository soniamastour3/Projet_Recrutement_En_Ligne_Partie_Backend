package com.example.administrateurservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class AdministrateurServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdministrateurServiceApplication.class, args);
    }

}
