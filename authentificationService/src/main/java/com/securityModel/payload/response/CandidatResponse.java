package com.securityModel.payload.response;


import com.securityModel.models.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidatResponse extends User {
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
    //    private int niveau;

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    //    //Création de l'objet CandidatResponse
//    public static CandidatResponse fromEntity(CandidatRequest entity) {
//        CandidatResponse candidatResponse = new CandidatResponse();
//        candidatResponse.setFile(entity.getFile());
//        BeanUtils.copyProperties(entity, candidatResponse);
//        return candidatResponse;
//    }
//    public static Candidat toEntity(CandidatRequest candidatResponse) {
//        Candidat c = new Candidat();
//        BeanUtils.copyProperties(candidatResponse, c);
//        return c;
//    }
}
