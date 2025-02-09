package com.example.visiteurservice.Dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisiteurRequest {
    private Long idVisiteur;
    private String nom;
}
