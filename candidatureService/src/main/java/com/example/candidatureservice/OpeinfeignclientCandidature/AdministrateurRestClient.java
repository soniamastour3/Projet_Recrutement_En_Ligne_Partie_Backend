package com.example.candidatureservice.OpeinfeignclientCandidature;

import com.example.candidatureservice.Dtos.response.AdministrateurResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ADMINISTRATEURSERVICE")
public interface AdministrateurRestClient {
    @GetMapping("/administrateurs/getone/{id}")
   AdministrateurResponse administrateurbyid(@PathVariable Long id);

}
