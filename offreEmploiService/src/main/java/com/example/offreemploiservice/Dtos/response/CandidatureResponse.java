package com.example.offreemploiservice.Dtos.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidatureResponse {
    private Long idCandidature;
    private Long ref;
    private String etat;
    private int scoreTest;


    public static CandidatureResponse fromEntity(CandidatureResponse entity) {
        CandidatureResponse candidatureResponse = new CandidatureResponse();
        BeanUtils.copyProperties(entity, candidatureResponse);
        return candidatureResponse;
    }
}
