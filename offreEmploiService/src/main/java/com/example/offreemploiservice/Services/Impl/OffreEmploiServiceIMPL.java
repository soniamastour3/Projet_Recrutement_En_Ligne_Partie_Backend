package com.example.offreemploiservice.Services.Impl;


import com.example.offreemploiservice.Dtos.request.OffreEmploiRequest;
import com.example.offreemploiservice.Dtos.response.CandidatureResponse;
import com.example.offreemploiservice.Dtos.response.EntrepriseResponse;
import com.example.offreemploiservice.Dtos.response.OffreEmploiResponse;
import com.example.offreemploiservice.Modele.OffreEmploi;
import com.example.offreemploiservice.OpenfeignClientOffreEmploi.CandidatureRestClient;
import com.example.offreemploiservice.OpenfeignClientOffreEmploi.EntrepriseRestClient;
import com.example.offreemploiservice.Repository.OffreEmploiDAO;
import com.example.offreemploiservice.Services.OffreEmploiService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OffreEmploiServiceIMPL implements OffreEmploiService {
    private final OffreEmploiDAO offreEmploiDaoconst;
    private final EntrepriseRestClient entrepriseRestClient;
    private final CandidatureRestClient candidatureRestClient;

    public OffreEmploiServiceIMPL(OffreEmploiDAO offreEmploiDaoconst,  EntrepriseRestClient entrepriseRestClient, CandidatureRestClient candidatureRestClient) {
        this.offreEmploiDaoconst = offreEmploiDaoconst;
        this.entrepriseRestClient = entrepriseRestClient;
        this.candidatureRestClient =candidatureRestClient;
    }

//    @Override
//    public void postuler(OffreEmploi offre) {
//
//        // Vérifiez que l'utilisateur n'a pas déjà postulé à cette offre
//        if (candidatureRestClient.existsByUserIdAndOffreEmploiId(user.getId(), offre.getId())) {
//            throw new RuntimeException("Vous avez déjà postulé à cette offre");
//        }
//
//        // Créer une nouvelle candidature
//        CandidatureResponse candidatureResponse = new CandidatureResponse();
//        candidatureResponse.setUser(user);
//        candidatureResponse.setOffreEmploi(offre);
//        candidatureResponse.setDateCandidature(LocalDate.now());
//        candidatureResponse.setStatus("En attente");
//
//        // Sauvegarder la candidature
//        candidatureRepository.save(candidature);
//    }

    @Override
    public OffreEmploiResponse createOffreEmploiEntrpriseCandidature(OffreEmploiRequest offreEmploiRequest, Long entreprise_id, Long candidature_id) {
        EntrepriseResponse entrepriseResponse = entrepriseRestClient.entreprisebyid(entreprise_id);
        CandidatureResponse candidatureResponse = candidatureRestClient.candidaturebyid(candidature_id);
        OffreEmploi off= OffreEmploiResponse.toEntity(offreEmploiRequest);
        off.setEntreprise_id(entrepriseResponse.getIdEntreprise());
        off.setEntrepriseResponse(entrepriseResponse);
        off.setCandidature_id(candidatureResponse.getIdCandidature());
        off.setCandidatureResponse(candidatureResponse);
        OffreEmploi savedoffreemploi=offreEmploiDaoconst.save(off);
        return OffreEmploiResponse.fromEntity(savedoffreemploi, candidatureRestClient, entrepriseRestClient);

    }

//    @Override
//    public List<OffreEmploiResponse> getAllByidEntreprise(Long idEntreprise) {
//        EntrepriseResponse entrepriseResponse = entrepriseRestClient.entreprisebyid(idEntreprise);
//        return offreEmploiDaoconst.findByEntreprise(
//                        entrepriseResponse.getIdEntreprise()
//                ).stream().
//                map(OffreEmploiResponse::fromEntity).
//                collect(Collectors.toList());
//
//    }

    @Override
    public OffreEmploiResponse updateOffreEmploiEnrepriseCandidature(OffreEmploiRequest offreEmploiRequest, Long id, Long entreprise_id, Long candidature_id){
        // Récupérer la sous-catégorie à partir de l'ID fourni
        OffreEmploi offreEmploi = offreEmploiDaoconst.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre Emploi non trouvée avec cet identifiant : " + id));


        EntrepriseResponse entrepriseResponse = entrepriseRestClient.entreprisebyid(entreprise_id);
        CandidatureResponse candidatureResponse = candidatureRestClient.candidaturebyid(candidature_id);
        if (offreEmploi != null) {
            OffreEmploi offreEmploi1 = OffreEmploiResponse.toEntity(offreEmploiRequest);
            offreEmploi1.setIdOffre(id);
            offreEmploi1.setTitre(offreEmploiRequest.getTitre());
            offreEmploi1.setDescription(offreEmploiRequest.getDescription());
            offreEmploi1.setTypeContrat(offreEmploiRequest.getTypeContrat());
            offreEmploi1.setSecteur(offreEmploiRequest.getSecteur());
            offreEmploi1.setAdresse(offreEmploiRequest.getAdresse());
            offreEmploi1.setEntreprise_id(entrepriseResponse.getIdEntreprise());
            offreEmploi1.setCandidature_id(candidatureResponse.getIdCandidature());
            OffreEmploi savedsub = offreEmploiDaoconst.save(offreEmploi1);
            return OffreEmploiResponse.fromEntity(savedsub, candidatureRestClient, entrepriseRestClient);
        } else {
            throw  new RuntimeException("offre Emploi not found ");
        }

    }

    @Override
    public void validateOffreEmploiById(Long id) {
        OffreEmploi offreEmploi = offreEmploiDaoconst.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre Emploi not found with this id: " + id));

        offreEmploi.setValidated(true); // Set validated to true
        offreEmploiDaoconst.save(offreEmploi); // Save updated entity
    }

    @Override
    public OffreEmploiResponse createoffreEmploi(OffreEmploiRequest offreEmploi){
        OffreEmploi o =OffreEmploiResponse.toEntity(offreEmploi);
        OffreEmploi savedoffreEmploi= offreEmploiDaoconst.save(o);
        return OffreEmploiResponse.fromEntity(savedoffreEmploi, candidatureRestClient, entrepriseRestClient );
    }

    @Override
    public List<OffreEmploiResponse> allOffreEmploi(){
        return offreEmploiDaoconst.findAll().stream()
                .map(offreEmploi -> {
                    // Créer la réponse à partir de l'entité
                    OffreEmploiResponse response = OffreEmploiResponse.fromEntity(offreEmploi, candidatureRestClient, entrepriseRestClient);

                    // Récupérer et inclure les détails de la candidature
                    if (offreEmploi.getCandidatureResponse() != null) {
                        response.setCandidature(
                                CandidatureResponse.fromEntity(offreEmploi.getCandidatureResponse())
                        );
                    }

                    // Récupérer et inclure les détails du entreprise
                    if (offreEmploi.getEntrepriseResponse() != null) {
                        response.setEntreprise(
                                EntrepriseResponse.fromEntity(offreEmploi.getEntrepriseResponse())
                        );
                    }

                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public OffreEmploiResponse offreEmploiById(Long id) {
        return offreEmploiDaoconst.findById(id)
                .map(offreEmploi -> {
                    // Créer la réponse à partir de l'entité
                    OffreEmploiResponse response = OffreEmploiResponse.fromEntity(offreEmploi, candidatureRestClient, entrepriseRestClient);

                    // Récupérer et inclure les détails de la candidature
                    if (offreEmploi.getCandidatureResponse() != null) {
                        response.setCandidature(
                                CandidatureResponse.fromEntity(offreEmploi.getCandidatureResponse())
                        );
                    }

                    // Récupérer et inclure les détails du entreprise
                    if (offreEmploi.getEntrepriseResponse() != null) {
                        response.setEntreprise(
                                EntrepriseResponse.fromEntity(offreEmploi.getEntrepriseResponse())
                        );
                    }

                    return response;
                })
                .orElseThrow(() ->new RuntimeException("Offre Emploi not found with this id:" +id));
    }

    @Override
    public OffreEmploiResponse updateoffreEmploi(OffreEmploiRequest offreEmploiRequest, Long id) {
        OffreEmploi offreEmploi=offreEmploiDaoconst.findById(id).orElseThrow(()->
                new RuntimeException("Offre Emploi not found with this id:" +id));
        if (offreEmploi != null) {
            OffreEmploi o = OffreEmploiResponse.toEntity(offreEmploiRequest);
            o.setIdOffre(id);
            o.setTitre(o.getTitre() == null ? offreEmploi.getTitre() : o.getTitre());
            OffreEmploi savedoffreEmploi = offreEmploiDaoconst.save(o);
            return OffreEmploiResponse.fromEntity(savedoffreEmploi, candidatureRestClient, entrepriseRestClient);
        }else{
            return null;
        }
    }


    @Override
    public HashMap<String, String> deleteoffreEmlpoi(Long id) {
        HashMap message =new HashMap<>();
        OffreEmploi o = offreEmploiDaoconst.findById(id).orElse(null);
        if (o != null) {
            try {
                offreEmploiDaoconst.deleteById(id);
                message.put("message", "Offre Emploi deleted successfully");
            }catch (Exception a){
                message.put("message", a.getMessage());
            }
        }else{
            message.put("message", "Offre Emploi not found: " +id);
        }
        return message;
    }

}
