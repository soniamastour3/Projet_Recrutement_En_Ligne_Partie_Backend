package com.example.administrateurservice.Dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdministrateurRequest {
    private Long idAdmin;
    private String nom;
    private String email;
    private String password;
}
