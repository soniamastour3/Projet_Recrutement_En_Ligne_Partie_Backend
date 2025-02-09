package com.securityModel.OpenfeignClientAuth;

import com.securityModel.payload.request.AdministrateurRequest;

import com.securityModel.payload.response.AdministrateurResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="ADMINISTRATEURSERVICE")
public interface AdministrateurRestClient {
    @GetMapping("/administrateurs/getone/{id}")
    AdministrateurResponse administrateurbyid(@PathVariable Long id);

    @PostMapping("/administrateurs/save")
    AdministrateurResponse addAdministrateur(@RequestBody AdministrateurRequest administrateurRequest);





}
