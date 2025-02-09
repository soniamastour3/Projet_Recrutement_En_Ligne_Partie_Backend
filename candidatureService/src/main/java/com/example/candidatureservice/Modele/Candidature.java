package com.example.candidatureservice.Modele;

import com.example.candidatureservice.Dtos.response.AdministrateurResponse;
import com.example.candidatureservice.Dtos.response.CandidatResponse;
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
@Table(name="candidature")
public class Candidature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCandidature;
    private Long ref;
    private String etat;
    private int scoreTest;
    private Long administrateurId;
    @Transient
    AdministrateurResponse administrateurResponse;

    private Long candidatId;
    @Transient
    CandidatResponse candidatResponse;



}
