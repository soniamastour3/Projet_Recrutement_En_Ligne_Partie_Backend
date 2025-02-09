package com.example.administrateurservice.Services;

import com.example.administrateurservice.Dtos.request.AdministrateurRequest;
import com.example.administrateurservice.Dtos.response.AdministrateurResponse;


import java.util.HashMap;
import java.util.List;

public  interface AdministrateurService {
    AdministrateurResponse createadministrateur(AdministrateurRequest administrateur);
    List<AdministrateurResponse> allAdministrateur();
    AdministrateurResponse administrateurById(Long id);
    AdministrateurResponse updateadministrateur(AdministrateurRequest administrateurRequest, Long id);
    HashMap<String, String> deleteadministrateur(Long id);
    //ResponseEntity<?> registerAdmin(SignupRequest signUpRequest);
}
