package com.example.commentaireservice.Dtos.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidatResponse {
    private Long idCandidat;
    private String nom;
    private String prenom;
    private String email;
    private String Password;
    private int niveau;



}
