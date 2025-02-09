package com.example.offreemploiservice.Modele;

import com.example.offreemploiservice.Dtos.response.CandidatureResponse;
import com.example.offreemploiservice.Dtos.response.EntrepriseResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="offreEmploi")
public class OffreEmploi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOffre;
    private String titre;
    private String description;
    private String typeContrat;
    private String secteur;
    private String adresse;
    private boolean validated= false;
    private Long candidature_id;
    @Transient
    CandidatureResponse candidatureResponse;
    private Long entreprise_id;
    @Transient
    EntrepriseResponse entrepriseResponse;

}
