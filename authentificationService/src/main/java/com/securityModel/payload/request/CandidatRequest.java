package com.securityModel.payload.request;

import com.securityModel.models.Role;
import com.securityModel.models.User;
import jakarta.persistence.Column;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidatRequest extends User {
    private long idCandidat;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private MultipartFile file;
    private MultipartFile cv;
    private String fileName;
    private String cvName;
//    private int niveau;
    private boolean confirm= false;
    private Set<Role> roles = new HashSet<>(); // Changez de Set<Role> à List<String>



    public CandidatRequest(String nom, String prenom, String email, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
    }




    public void setFile(String file) {
        this.fileName = file;
    }

    public void setCv(String cv) {
        this.cvName = cv;
    }


}
