package com.example.candidatureservice.Services;

import com.example.candidatureservice.Dtos.request.CandidatureRequest;
import com.example.candidatureservice.Dtos.response.CandidatureResponse;

import java.util.HashMap;
import java.util.List;

public interface CandidatureService {
    CandidatureResponse createCandidatureAdministrateurCandidat(CandidatureRequest candidatureRequest, Long administrateur_id, Long candidat_id );
    CandidatureResponse createcandidature(CandidatureRequest candidature);
    List<CandidatureResponse> allCandidature();
    CandidatureResponse candidatureById(Long id);
    CandidatureResponse updatecandidature(CandidatureRequest candidatureRequest, Long id);
    HashMap<String, String> deletecandidature(Long id);
}
