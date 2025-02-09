package com.example.offreemploiservice.Dtos.response;

//import com.example.entrepriseservice.Dtos.request.EntrepriseRequest;
//import com.example.entrepriseservice.Modele.Entreprise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntrepriseResponse {
    private Long idEntreprise;
    private String nom;
    private String specialite;
    private String coordonees;
    private String logo;
    private boolean validated;

    public static EntrepriseResponse fromEntity(EntrepriseResponse entity) {
        EntrepriseResponse entrepriseResponse = new EntrepriseResponse();
        BeanUtils.copyProperties(entity, entrepriseResponse);
        entrepriseResponse.setLogo(entity.getLogo());
        return entrepriseResponse;
    }

//    //Création de l'objet EntrepriseResponse
//    public static EntrepriseResponse fromEntity(Entreprise entity) {
//        EntrepriseResponse entrepriseResponse = new EntrepriseResponse();
//        BeanUtils.copyProperties(entity, entrepriseResponse);
//        return entrepriseResponse;
//    }
//    public static Entreprise toEntity(EntrepriseRequest entrepriseResponse) {
//        Entreprise e= new Entreprise();
//        BeanUtils.copyProperties(entrepriseResponse, e);
//        return e;
//    }
}
