package com.example.offreemploiservice.OpenfeignClientOffreEmploi;

import com.example.offreemploiservice.Dtos.response.EntrepriseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ENTREPRISESERVICE")
public interface EntrepriseRestClient {
    @GetMapping("/entreprises/getone/{id}")
    EntrepriseResponse entreprisebyid(@PathVariable Long id);
}
