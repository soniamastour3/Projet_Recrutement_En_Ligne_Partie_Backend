package com.example.visiteurservice.Services;

import com.example.visiteurservice.Dtos.request.VisiteurRequest;
import com.example.visiteurservice.Dtos.response.VisiteurResponse;

import java.util.HashMap;
import java.util.List;

public interface VisiteurService {
    VisiteurResponse createvisiteur(VisiteurRequest visiteur);
    List<VisiteurResponse> allVisiteur();
    VisiteurResponse visiteurById(Long id);
    VisiteurResponse updatevisiteur(VisiteurRequest visiteurRequest, Long id);
    HashMap<String, String> deletevisiteur(Long id);
}
