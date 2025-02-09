package com.securityModel.OpenfeignClientAuth;

import com.securityModel.payload.request.AdministrateurRequest;
import com.securityModel.payload.request.CandidatRequest;
import com.securityModel.payload.response.AdministrateurResponse;
import com.securityModel.payload.response.CandidatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name="CANDIDATSERVICE")
public interface CandidatRestClient {
    @GetMapping("/candidats/getone/{id}")
    CandidatResponse candidatbyid(@PathVariable long id);

    @PostMapping(value="/candidats/saveImageAndCv", consumes = { "multipart/form-data" })
    CandidatResponse createCandidatWithImageCv(CandidatRequest candidatRequest, @RequestPart(value="file") MultipartFile imageFile, @RequestPart(value="cv") MultipartFile cvFile);

    @PostMapping("/candidats/save")
    ResponseEntity<CandidatResponse> addCandidat(@RequestBody CandidatRequest candidatRequest);

    @GetMapping("/candidats/getone/email")
    CandidatResponse candidatbyemail(@RequestParam String email);


}
