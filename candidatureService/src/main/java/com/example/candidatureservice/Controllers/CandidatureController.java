package com.example.candidatureservice.Controllers;

import com.example.candidatureservice.Dtos.request.CandidatureRequest;
import com.example.candidatureservice.Dtos.response.CandidatureResponse;
import com.example.candidatureservice.Services.CandidatureService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/candidatures")
public class CandidatureController {
    public final CandidatureService candidatureService;

    public CandidatureController(CandidatureService candidatureService) {
        this.candidatureService = candidatureService;
    }

    @PostMapping("/save/{idAdmin}/{idCandidat}")
    public CandidatureResponse addCandidatureByIdAdministrateurAndIdCandidat(@RequestBody CandidatureRequest candidatureRequest, @PathVariable Long idAdmin, @PathVariable Long idCandidat) {
        return candidatureService.createCandidatureAdministrateurCandidat(candidatureRequest, idAdmin, idCandidat);
    }

    @PostMapping("/save")
    public CandidatureResponse addCandidature(@RequestBody CandidatureRequest candidatureRequest) {
        return candidatureService.createcandidature(candidatureRequest);
    }

    @GetMapping("/all")
    public List<CandidatureResponse> AllCandidature() {
        return candidatureService.allCandidature();
    }

    @GetMapping("/getone/{id}")
    public CandidatureResponse candidaturebyid(@PathVariable Long id) {
        return candidatureService.candidatureById(id);
    }

    @PutMapping("/update/{id}")
    public CandidatureResponse updatecandidature(@RequestBody CandidatureRequest candidatureRequest, @PathVariable Long id) {
        return candidatureService.updatecandidature(candidatureRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deletecandidature(@PathVariable Long id) {
        return candidatureService.deletecandidature(id);
    }


}
