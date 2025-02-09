package com.example.candidatureservice.Dtos.response;

import com.example.candidatureservice.Dtos.request.CandidatureRequest;
import com.example.candidatureservice.Modele.Candidature;
import com.example.candidatureservice.OpeinfeignclientCandidature.CandidatRestClient;
import com.example.candidatureservice.OpeinfeignclientCandidature.AdministrateurRestClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidatureResponse {
    private Long idCandidature;
    private Long ref;
    private String etat;
    private int scoreTest;
    private AdministrateurResponse administrateur;
    private CandidatResponse candidat;



    //Création de l'objet CandidatureResponse
    public static CandidatureResponse fromEntity(Candidature entity, AdministrateurRestClient administrateurRestClient,
                                                 CandidatRestClient candidatRestClient) {
        CandidatureResponse candidatureResponse = new CandidatureResponse();
        BeanUtils.copyProperties(entity, candidatureResponse);
        if (entity.getAdministrateurId() != null) {
            candidatureResponse.setAdministrateur(
                    administrateurRestClient.administrateurbyid(entity.getAdministrateurId())
            );
        }

        if (entity.getCandidatId() != null) {
            candidatureResponse.setCandidat(
                    candidatRestClient.candidatbyid(entity.getCandidatId())
            );
        }
        return candidatureResponse;
    }
    public static Candidature toEntity(CandidatureRequest candidatureResponse) {
        Candidature c = new Candidature();
        BeanUtils.copyProperties(candidatureResponse, c);
        return c;
    }
}
