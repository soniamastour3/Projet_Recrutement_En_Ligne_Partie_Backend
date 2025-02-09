package com.example.candidatureservice.Services.Impl;

import com.example.candidatureservice.Dtos.request.CandidatureRequest;
import com.example.candidatureservice.Dtos.response.AdministrateurResponse;
import com.example.candidatureservice.Dtos.response.CandidatResponse;
import com.example.candidatureservice.Dtos.response.CandidatureResponse;
import com.example.candidatureservice.Modele.Candidature;
import com.example.candidatureservice.OpeinfeignclientCandidature.CandidatRestClient;
import com.example.candidatureservice.OpeinfeignclientCandidature.AdministrateurRestClient;
import com.example.candidatureservice.Repository.CandidatureDAO;
import com.example.candidatureservice.Services.CandidatureService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidatureServiceIMPL implements CandidatureService {
    public static CandidatureDAO candidatureDaoconst;
    private final CandidatRestClient candidatRestClient;
    private final AdministrateurRestClient administrateurRestClient;

    public CandidatureServiceIMPL(CandidatureDAO candidatureDaoconst, CandidatRestClient candidatRestClient, AdministrateurRestClient administrateurRestClient) {
        this.candidatureDaoconst = candidatureDaoconst;
        this.candidatRestClient= candidatRestClient;
        this.administrateurRestClient = administrateurRestClient;
    }

    @Override
    public CandidatureResponse createCandidatureAdministrateurCandidat(CandidatureRequest candidatureRequest, Long administrateur_id, Long candidat_id ) {
        // Vérifier la réponse des microservices
        AdministrateurResponse administrateurResponse = administrateurRestClient.administrateurbyid(administrateur_id);
        if (administrateurResponse == null) {
            throw new RuntimeException("Administrateur not found with ID: " + administrateur_id);
        }

        CandidatResponse candidatResponse = candidatRestClient.candidatbyid(candidat_id);
        if (candidatResponse == null) {
            throw new RuntimeException("Candidat not found with ID: " + candidat_id);
        }

        Candidature cand= CandidatureResponse.toEntity(candidatureRequest);
        cand.setAdministrateurId(administrateurResponse.getIdAdmin());
        cand.setAdministrateurResponse(administrateurResponse);
        cand.setCandidatId(candidatResponse.getIdCandidat());
        cand.setCandidatResponse(candidatResponse);
        Candidature savedcandidature=candidatureDaoconst.save(cand);
        return CandidatureResponse.fromEntity(savedcandidature, administrateurRestClient, candidatRestClient);

    }
    @Override
    public CandidatureResponse createcandidature(CandidatureRequest candidature){
        Candidature c =CandidatureResponse.toEntity(candidature);
        Candidature savedcandidat= candidatureDaoconst.save(c);
        return CandidatureResponse.fromEntity(savedcandidat, administrateurRestClient, candidatRestClient);
    }

    @Override
    public List<CandidatureResponse> allCandidature(){
        return candidatureDaoconst.findAll().stream()
                .map(candidature -> {
                    // Créer la réponse à partir de l'entité
                    CandidatureResponse response = CandidatureResponse.fromEntity(candidature, administrateurRestClient, candidatRestClient);

                    // Récupérer et inclure les détails de l'administrateur
                    if (candidature.getAdministrateurResponse() != null) {
                        response.setAdministrateur(
                                AdministrateurResponse.fromEntity(candidature.getAdministrateurResponse())
                        );
                    }

                    // Récupérer et inclure les détails du candidat
                    if (candidature.getCandidatResponse() != null) {
                        response.setCandidat(
                                CandidatResponse.fromEntity(candidature.getCandidatResponse())
                        );
                    }

                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CandidatureResponse candidatureById(Long id) {
        return candidatureDaoconst.findById(id)
                .map(candidature -> {
                    // Créer la réponse à partir de l'entité
                    CandidatureResponse response = CandidatureResponse.fromEntity(candidature, administrateurRestClient, candidatRestClient);

                    // Récupérer et inclure les détails de l'administrateur
                    if (candidature.getAdministrateurResponse() != null) {
                        response.setAdministrateur(
                                AdministrateurResponse.fromEntity(candidature.getAdministrateurResponse())
                        );
                    }

                    // Récupérer et inclure les détails du candidat
                    if (candidature.getCandidatResponse() != null) {
                        response.setCandidat(
                                CandidatResponse.fromEntity(candidature.getCandidatResponse())
                        );
                    }

                    return response;
                })
                .orElseThrow(() ->new RuntimeException("Candidature not found with this id:" +id));
    }

    @Override
    public CandidatureResponse updatecandidature(CandidatureRequest candidatureRequest, Long id) {
        Candidature candidature=candidatureDaoconst.findById(id).orElseThrow(()->
                new RuntimeException("Candidature not found with this id:" +id));
        if (candidature != null) {
            Candidature c = CandidatureResponse.toEntity(candidatureRequest);
            c.setIdCandidature(id);
            c.setEtat(c.getEtat() == null ? candidature.getEtat() : c.getEtat());
            Candidature savedcandidat = candidatureDaoconst.save(c);
            return CandidatureResponse.fromEntity(savedcandidat, administrateurRestClient, candidatRestClient);
        }else{
            return null;
        }
    }


    @Override
    public HashMap<String, String> deletecandidature(Long id) {
        HashMap message =new HashMap<>();
        Candidature c = candidatureDaoconst.findById(id).orElse(null);
        if (c != null) {
            try {
                candidatureDaoconst.deleteById(id);
                message.put("message", "Candidature deleted successfully");
            }catch (Exception a){
                message.put("message", a.getMessage());
            }
        }else{
            message.put("message", "Candidature not found: " +id);
        }
        return message;
    }
}
