package com.example.administrateurservice.Controllers;


import com.example.administrateurservice.Dtos.request.AdministrateurRequest;
import com.example.administrateurservice.Dtos.response.AdministrateurResponse;
import com.example.administrateurservice.Services.AdministrateurService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/administrateurs")
public class AdministrateurController {
    public final AdministrateurService administrateurService;


    public AdministrateurController(AdministrateurService administrateurService) {
        this.administrateurService = administrateurService;
    }

    @PostMapping("/save")
    public AdministrateurResponse addAdministrateur(@RequestBody AdministrateurRequest administrateurRequest) {
        return administrateurService.createadministrateur(administrateurRequest);
    }

    @GetMapping("/all")
    public List<AdministrateurResponse> AllAdministrateur() {
        return administrateurService.allAdministrateur();
    }

    @GetMapping("/getone/{id}")
    public AdministrateurResponse administrateurbyid(@PathVariable Long id) {
        return administrateurService.administrateurById(id);
    }



    @PutMapping("/update/{id}")
    public AdministrateurResponse updateadministrateur(@RequestBody AdministrateurRequest administrateurRequest, @PathVariable Long id) {
        return administrateurService.updateadministrateur(administrateurRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deleteadministrateur(@PathVariable Long id) {
        return administrateurService.deleteadministrateur(id);
    }



}
