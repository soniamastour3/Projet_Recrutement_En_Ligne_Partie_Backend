package com.example.candidatservice.Controllers;

import com.example.candidatservice.Dtos.request.CandidatRequest;
import com.example.candidatservice.Dtos.response.CandidatResponse;
import com.example.candidatservice.Services.CandidatService;
import com.example.candidatservice.Utils.StoresService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/candidats")
public class CandidatController {
    @Autowired
    private StoresService storesService;

    public final CandidatService candidatService;

    public CandidatController(CandidatService candidatService) {
        this.candidatService = candidatService;
    }


    @PostMapping(value = "/saveImageAndCv", consumes = { "multipart/form-data" })
    public CandidatResponse createCandidatWithImageCv(
            CandidatRequest candidatRequest,
             @RequestPart(value="file") MultipartFile imageFile,
             @RequestPart(value = "cv") MultipartFile cvFile) {
        return candidatService.createcandidatwithimageandcv(candidatRequest, imageFile, cvFile);
    }

    @PostMapping("/save")
    public ResponseEntity<CandidatResponse> addCandidat(@RequestBody CandidatRequest candidatRequest) {
        CandidatResponse response =candidatService.createcandidat(candidatRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public List<CandidatResponse> AllCandidat() {
        return candidatService.allCandidat();
    }

    @GetMapping("/getone/{id}")
    public CandidatResponse candidatbyid(@PathVariable long id) {
        return candidatService.candidatById(id);
    }

    @GetMapping("/getone/email")
    public CandidatResponse candidatbyemail(@RequestParam String email) {
        return candidatService.candidatByEmail(email);
    }

    @PutMapping("/update/{id}")
    public CandidatResponse updatecandidat(@RequestBody CandidatRequest candidatRequest, @PathVariable Long id) {
        return candidatService.updatecandidat(candidatRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deletecandidat(@PathVariable Long id) {
        return candidatService.deletecandidat(id);
    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storesService.loadFile(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}

