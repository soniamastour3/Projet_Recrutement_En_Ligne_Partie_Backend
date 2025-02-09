package com.example.commentaireservice.Services;

import com.example.commentaireservice.Dtos.request.CommentaireRequest;
import com.example.commentaireservice.Dtos.response.CommentaireResponse;

import java.util.HashMap;
import java.util.List;

public interface CommentaireService {
    CommentaireResponse createcommentaire(CommentaireRequest commentaire);
    List<CommentaireResponse> allCommentaire();
    CommentaireResponse commentaireById(Long id);
    CommentaireResponse updatecommentaire(CommentaireRequest commentaireRequest, Long id);
    HashMap<String, String> deletecommentaire(Long id);
    CommentaireResponse createCommentaireCandidat(CommentaireRequest commentaireRequest, Long candidat_id);
    List<CommentaireResponse> getAllByidCandidat(Long idCandidat);
    CommentaireResponse updateCommentaireCandidat(CommentaireRequest commentaireRequest, Long id, Long candidat_id);
}
