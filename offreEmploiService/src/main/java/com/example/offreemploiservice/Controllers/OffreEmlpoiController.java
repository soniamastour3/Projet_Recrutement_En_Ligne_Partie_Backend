package com.example.offreemploiservice.Controllers;

import com.example.offreemploiservice.Dtos.request.OffreEmploiRequest;
import com.example.offreemploiservice.Dtos.response.OffreEmploiResponse;
import com.example.offreemploiservice.Services.OffreEmploiService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/offreEmplois")
public class OffreEmlpoiController {
    public final OffreEmploiService offreEmlpoiService;

    public OffreEmlpoiController(OffreEmploiService offreEmlpoiService) {
        this.offreEmlpoiService = offreEmlpoiService;
    }
    @PutMapping("/validate/{id}")
    public ResponseEntity<Map<String, String>> validateOffreEmploi(@PathVariable Long id) {
        offreEmlpoiService.validateOffreEmploiById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "offre Emlpoi validated successfully");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @PostMapping("/save/{idEnt}/{idCand}")
    public OffreEmploiResponse addOffreEmploiyByIdCandidatureAndIdEntreprise(@RequestBody OffreEmploiRequest offreEmploiRequest, @PathVariable Long idEnt, @PathVariable Long idCand) {
        return offreEmlpoiService.createOffreEmploiEntrpriseCandidature(offreEmploiRequest, idEnt, idCand );
    }
    @PostMapping("/save")
    public OffreEmploiResponse addOffreEmlpoi(@RequestBody OffreEmploiRequest offreEmlpoiRequest) {
        return offreEmlpoiService.createoffreEmploi(offreEmlpoiRequest);
    }

//    @GetMapping("/allE/{idEnt}")
//    public List<OffreEmploiResponse> AllOffreEmploiByIdEntreprise( @PathVariable Long idEnt) {
//        return offreEmlpoiService.getAllByidEntreprise(idEnt);
//    }

    @PutMapping(value = {"/updatee/{id}", "/updatee/{id}/{entreprise_id}/{candidature_id}"})
    public OffreEmploiResponse updateOffreEmploiDECandidatureAndEntreprise(@RequestBody OffreEmploiRequest offreEmploiRequest, @PathVariable Long id, @PathVariable Long entreprise_id, @PathVariable Long candidature_id) {
        return offreEmlpoiService.updateOffreEmploiEnrepriseCandidature(offreEmploiRequest, id,
                entreprise_id, candidature_id);
    }

    @GetMapping("/all")
    public List<OffreEmploiResponse> AllOffreEmlpoi() {
        return offreEmlpoiService.allOffreEmploi();
    }

    @GetMapping("/getone/{id}")
    public OffreEmploiResponse offreEmlpoibyid(@PathVariable Long id) {
        return offreEmlpoiService.offreEmploiById(id);
    }

    @PutMapping("/update/{id}")
    public OffreEmploiResponse updateoffreEmlpoi(@RequestBody OffreEmploiRequest offreEmlpoiRequest, @PathVariable Long id) {
        return offreEmlpoiService.updateoffreEmploi(offreEmlpoiRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deleteoffreEmlpoi(@PathVariable Long id) {
        return offreEmlpoiService.deleteoffreEmlpoi(id);
    }


}
