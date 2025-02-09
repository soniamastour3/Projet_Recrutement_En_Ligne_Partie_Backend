package com.example.offreemploiservice.Dtos.response;


import com.example.offreemploiservice.Dtos.request.OffreEmploiRequest;
import com.example.offreemploiservice.Modele.OffreEmploi;
import com.example.offreemploiservice.OpenfeignClientOffreEmploi.CandidatureRestClient;
import com.example.offreemploiservice.OpenfeignClientOffreEmploi.EntrepriseRestClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OffreEmploiResponse {
    private Long idOffre;
    private String titre;
    private String description;
    private String typeContrat;
    private String secteur;
    private String adresse;
    private boolean validated;
    private CandidatureResponse candidature;
    private EntrepriseResponse entreprise;


    //Création de l'objet  OffreEmploiResponse
    public static  OffreEmploiResponse fromEntity(OffreEmploi entity, CandidatureRestClient candidatureRestClient,
                                                  EntrepriseRestClient entrepriseRestClient) {
        OffreEmploiResponse  offreEmploiResponse = new  OffreEmploiResponse();
        BeanUtils.copyProperties(entity, offreEmploiResponse);
        if (entity.getCandidature_id() != null) {
            offreEmploiResponse.setCandidature(
                    candidatureRestClient.candidaturebyid(entity.getCandidature_id())
            );
        }

        if (entity.getEntreprise_id() != null) {
            offreEmploiResponse.setEntreprise(
                    entrepriseRestClient.entreprisebyid(entity.getEntreprise_id())
            );
        }
        return  offreEmploiResponse;
    }
    public static  OffreEmploi toEntity( OffreEmploiRequest  offreEmploiResponse) {
        OffreEmploi o = new  OffreEmploi();
        BeanUtils.copyProperties(offreEmploiResponse, o);
        return o;
    }
}
