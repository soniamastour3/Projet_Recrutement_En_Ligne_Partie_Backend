package com.example.testservice.Dtos.response;

import com.example.testservice.Dtos.request.TestRequest;
import com.example.testservice.Modele.Test;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResponse {
    private Long idTest;
    private String titre;
    private String description;
    private int score;

    //Création de l'objet TestResponse
    public static TestResponse fromEntity(Test entity) {
        TestResponse testResponse = new TestResponse();
        BeanUtils.copyProperties(entity, testResponse);
        return testResponse;
    }
    public static Test toEntity(TestRequest testResponse) {
        Test t = new Test();
        BeanUtils.copyProperties(testResponse, t);
        return t;
    }
}
