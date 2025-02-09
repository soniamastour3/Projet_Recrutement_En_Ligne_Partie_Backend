package com.example.offreemploiservice.Dtos.request;

import com.example.offreemploiservice.Dtos.response.EntrepriseResponse;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OffreEmploiRequest {
    private Long idOffre;
    private String titre;
    private String description;
    private String typeContrat;
    private String secteur;
    private String adresse;


}
