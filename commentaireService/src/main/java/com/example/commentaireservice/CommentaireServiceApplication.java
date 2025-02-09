package com.example.commentaireservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CommentaireServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentaireServiceApplication.class, args);
    }

}
