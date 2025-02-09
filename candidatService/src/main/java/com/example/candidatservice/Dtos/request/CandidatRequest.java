package com.example.candidatservice.Dtos.request;
import jakarta.persistence.Transient;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidatRequest {
    private long idCandidat;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private MultipartFile file;
    private MultipartFile cv;
    private boolean confirm= false;
    //private int niveau;


}
