package com.example.offreemploiservice.OpenfeignClientOffreEmploi;

import com.example.offreemploiservice.Dtos.response.CandidatureResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "CANDIDATURESERVICE")
public interface CandidatureRestClient {
    @GetMapping("/candidatures/getone/{id}")
    CandidatureResponse candidaturebyid(@PathVariable Long id);

    @GetMapping("/candidatures/exists")
    boolean existsByUserIdAndOffreEmploiId(
            @RequestParam("userId") Long userId,
            @RequestParam("offreId") Long offreId
    );
}
