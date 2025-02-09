package com.example.entrepriseservice.Services;

import com.example.entrepriseservice.Dtos.request.EntrepriseRequest;
import com.example.entrepriseservice.Dtos.response.EntrepriseResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface EntrepriseService {
    EntrepriseResponse createentreprise(EntrepriseRequest candidat);
    List<EntrepriseResponse> allEntreprise();
    EntrepriseResponse entrepriseById(Long id);
    EntrepriseResponse updateentreprise(EntrepriseRequest entrepriseRequest, Long id);
    HashMap<String, String> deleteentreprise(Long id);
    void validateEntrepriseById(Long id);
    EntrepriseResponse createentreprisewithlogo(EntrepriseRequest entrepriseRequest, MultipartFile logo);
}
