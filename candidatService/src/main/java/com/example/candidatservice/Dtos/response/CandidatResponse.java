package com.example.candidatservice.Dtos.response;

import com.example.candidatservice.Dtos.request.CandidatRequest;
import com.example.candidatservice.Modele.Candidat;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidatResponse {
    private long idCandidat;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String file;
    private String cv;
    private boolean confirm= false;
   // private int niveau;


    public static CandidatResponse fromEntity(Candidat entity) {
        CandidatResponse candidatResponse = new CandidatResponse();
        BeanUtils.copyProperties(entity, candidatResponse); // Copie les propriétés identiques
        candidatResponse.setFile(entity.getFile());  // Assurez que ce champ est bien mappé si spécifique
        candidatResponse.setCv(entity.getCv());      // Même chose ici
        return candidatResponse;
    }




    public static Candidat toEntity(CandidatRequest candidatResponse) {
        Candidat c = new Candidat();
        BeanUtils.copyProperties(candidatResponse, c);
        return c;
    }
}
