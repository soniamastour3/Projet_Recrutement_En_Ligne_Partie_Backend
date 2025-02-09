package com.example.candidatureservice.Dtos.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidatureRequest {
    private Long idCandidature;
    private Long ref;
    private String etat;
    private int scoreTest;
}
