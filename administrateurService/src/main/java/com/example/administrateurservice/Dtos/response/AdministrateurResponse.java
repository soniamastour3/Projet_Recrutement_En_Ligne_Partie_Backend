package com.example.administrateurservice.Dtos.response;

import com.example.administrateurservice.Dtos.request.AdministrateurRequest;
import com.example.administrateurservice.Modele.Administrateur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdministrateurResponse {
    private Long idAdmin;
    private String nom;
    private String email;
    private String password;


    //Création de l'objet AdministrateurResponse
    public static AdministrateurResponse fromEntity(Administrateur entity) {
        AdministrateurResponse administrateurResponse = new AdministrateurResponse();
        BeanUtils.copyProperties(entity, administrateurResponse);
        return administrateurResponse;
    }
    public static Administrateur toEntity(AdministrateurRequest administrateurResponse) {
        Administrateur a= new Administrateur();
        BeanUtils.copyProperties(administrateurResponse, a);
        return a;
    }



    public AdministrateurResponse(String nom, String email, String password) {
        this.nom = nom;
        this.email = email;
        this.password = password;
    }


}

