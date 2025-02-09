package com.example.visiteurservice.Dtos.response;

import com.example.visiteurservice.Dtos.request.VisiteurRequest;
import com.example.visiteurservice.Modele.Visiteur;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisiteurResponse {
    private Long idVisiteur;
    private String nom;

    //Création de l'objet VisiteurResponse
    public static VisiteurResponse fromEntity(Visiteur entity) {
        VisiteurResponse visiteurResponse = new VisiteurResponse();
        BeanUtils.copyProperties(entity, visiteurResponse);
        return visiteurResponse;
    }
    public static Visiteur toEntity(VisiteurRequest visiteurResponse) {
        Visiteur v= new Visiteur();
        BeanUtils.copyProperties(visiteurResponse, v);
        return v;
    }
}
