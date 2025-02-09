package com.example.visiteurservice.Controllers;

import com.example.visiteurservice.Dtos.request.VisiteurRequest;
import com.example.visiteurservice.Dtos.response.VisiteurResponse;
import com.example.visiteurservice.Services.VisiteurService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/visiteurs")
public class VisiteurController {
    public final VisiteurService visiteurService;

    public VisiteurController(VisiteurService visiteurService) {
        this.visiteurService = visiteurService;
    }

    @PostMapping("/save")
    public VisiteurResponse addVisiteur(@RequestBody VisiteurRequest visiteurRequest) {
        return visiteurService.createvisiteur(visiteurRequest);
    }

    @GetMapping("/all")
    public List<VisiteurResponse> AllVisiteur() {
        return visiteurService.allVisiteur();
    }

    @GetMapping("/getone/{id}")
    public VisiteurResponse visiteurbyid(@PathVariable Long id) {
        return visiteurService.visiteurById(id);
    }

    @PutMapping("/update/{id}")
    public VisiteurResponse updatevisiteur(@RequestBody VisiteurRequest visiteurRequest, @PathVariable Long id) {
        return visiteurService.updatevisiteur(visiteurRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deletevisiteur(@PathVariable Long id) {
        return visiteurService.deletevisiteur(id);
    }



}
