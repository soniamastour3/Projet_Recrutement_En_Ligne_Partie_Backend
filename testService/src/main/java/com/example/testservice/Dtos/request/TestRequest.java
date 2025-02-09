package com.example.testservice.Dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestRequest {
    private Long idTest;
    private String titre;
    private String description;
    private int score;
}
