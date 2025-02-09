package com.example.entrepriseservice.Dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntrepriseRequest {
    private Long idEntreprise;
    private String nom;
    private String specialite;
    private String coordonees;
    private MultipartFile logo;
}
