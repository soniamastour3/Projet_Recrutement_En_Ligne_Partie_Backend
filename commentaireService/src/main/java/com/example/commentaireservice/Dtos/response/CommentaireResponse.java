package com.example.commentaireservice.Dtos.response;

import com.example.commentaireservice.Dtos.request.CommentaireRequest;
import com.example.commentaireservice.Modele.Commentaire;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentaireResponse {
    private Long idCommentaire;
    private String contenu;
    private Date dateCreation;

    //Création de l'objet CommentaireResponse
    public static CommentaireResponse fromEntity(Commentaire entity) {
        CommentaireResponse commentaireResponse = new CommentaireResponse();
        BeanUtils.copyProperties(entity, commentaireResponse);
        return commentaireResponse;
    }
    public static Commentaire toEntity(CommentaireRequest commentaireResponse) {
        Commentaire c = new Commentaire();
        BeanUtils.copyProperties(commentaireResponse, c);
        return c;
    }
}
