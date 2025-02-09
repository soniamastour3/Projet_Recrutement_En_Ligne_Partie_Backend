package com.example.entrepriseservice.Controllers;

import com.example.entrepriseservice.Dtos.request.EntrepriseRequest;
import com.example.entrepriseservice.Dtos.response.EntrepriseResponse;
import com.example.entrepriseservice.Services.EntrepriseService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping ("/entreprises")

public class EntrepriseController {
    public final EntrepriseService entrepriseService;

    public EntrepriseController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @PostMapping(value = "/saveLogo", consumes = { "multipart/form-data" })
    public EntrepriseResponse createEntrepriseWithLogo(
            EntrepriseRequest entrepriseRequest,
            @RequestPart(value="logo") MultipartFile logo) {
        return entrepriseService.createentreprisewithlogo(entrepriseRequest, logo);
    }
    @PostMapping("/save")
    public EntrepriseResponse addEntreprise(@RequestBody EntrepriseRequest entrepriseRequest) {
        return entrepriseService.createentreprise(entrepriseRequest);
    }
    @PutMapping("/validate/{id}")
    public ResponseEntity<Map<String, String>> validateEntreprise(@PathVariable Long id) {
        entrepriseService.validateEntrepriseById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Entreprise validated successfully");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @GetMapping("/all")
    public List<EntrepriseResponse> AllEntreprise() {
        return entrepriseService.allEntreprise();
    }

    @GetMapping("/getone/{id}")
    public EntrepriseResponse entreprisebyid(@PathVariable Long id) {
        return entrepriseService.entrepriseById(id);
    }

    @PutMapping("/update/{id}")
    public EntrepriseResponse updateentreprise(@RequestBody EntrepriseRequest entrepriseRequest, @PathVariable Long id) {
        return entrepriseService.updateentreprise(entrepriseRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deleteentreprise(@PathVariable Long id) {
        return entrepriseService.deleteentreprise(id);
    }


}
