package com.example.candidatservice.Modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="candidat")
public class Candidat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCandidat;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String file;
    private String cv;
    private boolean confirm= false;




    public void setFile(String file) {
        this.file = file;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }


    //private int niveau;

}
