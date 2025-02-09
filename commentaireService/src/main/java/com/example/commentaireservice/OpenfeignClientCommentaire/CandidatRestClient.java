package com.example.commentaireservice.OpenfeignClientCommentaire;


import com.example.commentaireservice.Dtos.response.CandidatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="CANDIDATSERVICE")
public interface CandidatRestClient {
    @GetMapping("/candidats/getone/{id}")
    CandidatResponse candidatbyid(@PathVariable Long id);
}
