package com.example.commentaireservice.Modele;

import com.example.commentaireservice.Dtos.response.CandidatResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="commentaire")
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommentaire;
    private String contenu;
    private Date dateCreation;
    private Long candidat_id;
    @Transient
    private CandidatResponse candidatResponse;
}
