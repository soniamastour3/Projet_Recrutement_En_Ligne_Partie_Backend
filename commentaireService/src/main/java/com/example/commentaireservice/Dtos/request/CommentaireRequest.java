package com.example.commentaireservice.Dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentaireRequest {
    private Long idCommentaire;
    private String contenu;
    private Date dateCreation;
}
