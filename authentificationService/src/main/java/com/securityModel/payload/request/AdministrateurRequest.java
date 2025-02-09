package com.securityModel.payload.request;


import com.securityModel.models.Role;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdministrateurRequest {
    private long idAdmin;
    private String nom;
    private String email;
    private String password;
    @Transient
    private Set<Role> roles;




    public AdministrateurRequest(String nom, String email, String password) {
        this.nom = nom;
        this.email = email;
        this.password = password;
    }
}
