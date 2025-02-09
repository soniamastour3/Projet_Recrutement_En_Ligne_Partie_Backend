package com.example.entrepriseservice.Services.Impl;

import com.example.entrepriseservice.Dtos.request.EntrepriseRequest;
import com.example.entrepriseservice.Dtos.response.EntrepriseResponse;
import com.example.entrepriseservice.Modele.Entreprise;
import com.example.entrepriseservice.Repository.EntrepriseDAO;
import com.example.entrepriseservice.Services.EntrepriseService;
import com.example.entrepriseservice.Utils.StoresService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntrepriseServiceIMPL implements EntrepriseService {
    public static EntrepriseDAO entrepriseDaoconst;
    private final StoresService storesService;

    public EntrepriseServiceIMPL(EntrepriseDAO entrepriseDaoconst, StoresService storesService) {
        this.entrepriseDaoconst = entrepriseDaoconst;
        this.storesService=storesService;
    }
@Override
    public EntrepriseResponse createentreprisewithlogo(EntrepriseRequest entrepriseRequest, MultipartFile logo) {
        Entreprise e=EntrepriseResponse.toEntity(entrepriseRequest);
        e.setNom(entrepriseRequest.getNom());
        e.setSpecialite(entrepriseRequest.getSpecialite());
        e.setCoordonees(entrepriseRequest.getCoordonees());
        // Store the file and set the paths
        if (logo != null && !logo.isEmpty()) {
            String storedLogo = storesService.store(logo);
            e.setLogo(storedLogo);
        } else {
            System.out.println("Logo is null or empty!");
        }

        Entreprise savedentreprise= entrepriseDaoconst.save(e);
        return EntrepriseResponse.fromEntity(savedentreprise);
    }

    @Override
    public EntrepriseResponse createentreprise(EntrepriseRequest candidat){
        Entreprise e =EntrepriseResponse.toEntity(candidat);
        Entreprise savedentreprise= entrepriseDaoconst.save(e);
        return EntrepriseResponse.fromEntity(savedentreprise);
    }

    @Override
    public void validateEntrepriseById(Long id) {
        Entreprise entreprise = entrepriseDaoconst.findById(id)
                .orElseThrow(() -> new RuntimeException("Entreprise not found with this id: " + id));

        entreprise.setValidated(true); // Set validated to true
        entrepriseDaoconst.save(entreprise); // Save updated entity
    }

    @Override
    public List<EntrepriseResponse> allEntreprise(){
        return entrepriseDaoconst.findAll().stream()
                .map(EntrepriseResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public EntrepriseResponse entrepriseById(Long id) {
        return entrepriseDaoconst.findById(id)
                .map(EntrepriseResponse::fromEntity)
                .orElseThrow(() ->new RuntimeException("Entreprise not found with this id:" +id));
    }

    @Override
    public EntrepriseResponse updateentreprise(EntrepriseRequest entrepriseRequest, Long id) {
        Entreprise entreprise=entrepriseDaoconst.findById(id).orElseThrow(()->
                new RuntimeException("Entreprise not found with this id:" +id));
        if (entreprise != null) {
            Entreprise e = EntrepriseResponse.toEntity(entrepriseRequest);
            e.setIdEntreprise(id);
            e.setNom(e.getNom() == null ? entreprise.getNom() : e.getNom());
            Entreprise savedentreprise = entrepriseDaoconst.save(e);
            return EntrepriseResponse.fromEntity(savedentreprise);
        }else{
            return null;
        }
    }


    @Override
    public HashMap<String, String> deleteentreprise(Long id) {
        HashMap message =new HashMap<>();
        Entreprise e = entrepriseDaoconst.findById(id).orElse(null);
        if (e != null) {
            try {
                entrepriseDaoconst.deleteById(id);
                message.put("message", "Entreprise deleted successfully");
            }catch (Exception a){
                message.put("message", a.getMessage());
            }
        }else{
            message.put("message", "Entreprise not found: " +id);
        }
        return message;
    }
}
