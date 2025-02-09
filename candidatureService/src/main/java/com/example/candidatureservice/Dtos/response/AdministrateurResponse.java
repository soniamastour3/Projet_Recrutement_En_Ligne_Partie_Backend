package com.example.candidatureservice.Dtos.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdministrateurResponse {
    private long idAdmin;
    private String nom;
    private String email;
    private String password;


    public static AdministrateurResponse fromEntity(AdministrateurResponse entity) {
        AdministrateurResponse administrateurResponse = new AdministrateurResponse();
        BeanUtils.copyProperties(entity, administrateurResponse);
        return administrateurResponse;
    }
}
