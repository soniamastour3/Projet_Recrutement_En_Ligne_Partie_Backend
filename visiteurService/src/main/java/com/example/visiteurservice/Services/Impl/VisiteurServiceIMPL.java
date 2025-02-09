package com.example.visiteurservice.Services.Impl;

import com.example.visiteurservice.Dtos.request.VisiteurRequest;
import com.example.visiteurservice.Dtos.response.VisiteurResponse;
import com.example.visiteurservice.Modele.Visiteur;
import com.example.visiteurservice.Repository.VisiteurDAO;
import com.example.visiteurservice.Services.VisiteurService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisiteurServiceIMPL implements VisiteurService {
    public static VisiteurDAO visiteurDaoconst;

    public VisiteurServiceIMPL(VisiteurDAO visiteurDaoconst) {
        this.visiteurDaoconst = visiteurDaoconst;
    }

    @Override
    public VisiteurResponse createvisiteur(VisiteurRequest visiteur){
        Visiteur v =VisiteurResponse.toEntity(visiteur);
        Visiteur savedvisiteur= visiteurDaoconst.save(v);
        return VisiteurResponse.fromEntity(savedvisiteur);
    }

    @Override
    public List<VisiteurResponse> allVisiteur(){
        return visiteurDaoconst.findAll().stream()
                .map(VisiteurResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public VisiteurResponse visiteurById(Long id) {
        return visiteurDaoconst.findById(id)
                .map(VisiteurResponse::fromEntity)
                .orElseThrow(() ->new RuntimeException("Visiteur not found with this id:" +id));
    }

    @Override
    public VisiteurResponse updatevisiteur(VisiteurRequest visiteurRequest, Long id) {
        Visiteur visiteur=visiteurDaoconst.findById(id).orElseThrow(()->
                new RuntimeException("Visiteur not found with this id:" +id));
        if (visiteur != null) {
            Visiteur e = VisiteurResponse.toEntity(visiteurRequest);
            e.setIdVisiteur(id);
            e.setNom(e.getNom() == null ? visiteur.getNom() : e.getNom());
            Visiteur savedvisiteur = visiteurDaoconst.save(e);
            return VisiteurResponse.fromEntity(savedvisiteur);
        }else{
            return null;
        }
    }


    @Override
    public HashMap<String, String> deletevisiteur(Long id) {
        HashMap message =new HashMap<>();
        Visiteur v = visiteurDaoconst.findById(id).orElse(null);
        if (v != null) {
            try {
                visiteurDaoconst.deleteById(id);
                message.put("message", "Visiteur deleted successfully");
            }catch (Exception a){
                message.put("message", a.getMessage());
            }
        }else{
            message.put("message", "Visiteur not found: " +id);
        }
        return message;
    }
}
