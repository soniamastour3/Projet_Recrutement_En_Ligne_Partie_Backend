package com.example.visiteurservice.Modele;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="visiteur")
public class Visiteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVisiteur;
    private String nom;
}
