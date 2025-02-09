package com.example.offreemploiservice.Services;

import com.example.offreemploiservice.Dtos.request.OffreEmploiRequest;
import com.example.offreemploiservice.Dtos.response.OffreEmploiResponse;
import com.example.offreemploiservice.Modele.OffreEmploi;

import java.util.HashMap;
import java.util.List;

public interface OffreEmploiService {
    OffreEmploiResponse createoffreEmploi(OffreEmploiRequest offreEmploi);
    List<OffreEmploiResponse> allOffreEmploi();
    OffreEmploiResponse offreEmploiById(Long id);
    OffreEmploiResponse updateoffreEmploi(OffreEmploiRequest offreEmploiRequest, Long id);
    HashMap<String, String> deleteoffreEmlpoi(Long id);
    OffreEmploiResponse createOffreEmploiEntrpriseCandidature(OffreEmploiRequest offreEmploiRequest, Long entreprise_id, Long candidature_id);
   // List<OffreEmploiResponse> getAllByidEntreprise(Long idEntreprise);
    OffreEmploiResponse updateOffreEmploiEnrepriseCandidature(OffreEmploiRequest offreEmploiRequest, Long id, Long entreprise_id, Long candidature_id);
    void validateOffreEmploiById(Long id);
    //void postuler(OffreEmploi offre);
}
