package com.example.commentaireservice.Services.Impl;

import com.example.commentaireservice.Dtos.request.CommentaireRequest;
import com.example.commentaireservice.Dtos.response.CandidatResponse;
import com.example.commentaireservice.Dtos.response.CommentaireResponse;
import com.example.commentaireservice.Modele.Commentaire;
import com.example.commentaireservice.OpenfeignClientCommentaire.CandidatRestClient;
import com.example.commentaireservice.Repository.CommentaireDAO;
import com.example.commentaireservice.Services.CommentaireService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentaireServiceIMPL implements CommentaireService {
    public static CommentaireDAO commentaireDaoconst;
    private final CandidatRestClient candidatRestClient;

    public CommentaireServiceIMPL(CommentaireDAO commentaireDaoconst, CandidatRestClient candidatRestClient) {
        this.commentaireDaoconst = commentaireDaoconst;
        this.candidatRestClient = candidatRestClient;
    }

    @Override
    public CommentaireResponse createCommentaireCandidat(CommentaireRequest commentaireRequest, Long candidat_id) {
        CandidatResponse candidatResponse = candidatRestClient.candidatbyid(candidat_id);
        Commentaire com= CommentaireResponse.toEntity(commentaireRequest);
        com.setCandidat_id(candidatResponse.getIdCandidat());
        com.setCandidatResponse(candidatResponse);
        Commentaire savedcommentaire=commentaireDaoconst.save(com);
        return CommentaireResponse.fromEntity(savedcommentaire);

    }
    @Override
    public List<CommentaireResponse> getAllByidCandidat(Long idCandidat) {
        CandidatResponse candidatResponse = candidatRestClient.candidatbyid(idCandidat);
        return commentaireDaoconst.findByCandidat(
                        candidatResponse.getIdCandidat()
                ).stream().
                map(CommentaireResponse::fromEntity).
                collect(Collectors.toList());

    }

    @Override
    public CommentaireResponse updateCommentaireCandidat(CommentaireRequest commentaireRequest, Long id, Long candidat_id){
        // Récupérer la sous-catégorie à partir de l'ID fourni
        Commentaire commentaire = commentaireDaoconst.findById(id)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvée avec cet identifiant : " + id));

        CandidatResponse candidatResponse = candidatRestClient.candidatbyid(candidat_id);
        if (commentaire != null) {
            Commentaire commentaire1 = CommentaireResponse.toEntity(commentaireRequest);
            commentaire1.setIdCommentaire(id);
            commentaire1.setContenu(commentaireRequest.getContenu());
            commentaire1.setDateCreation(commentaireRequest.getDateCreation());
            commentaire1.setCandidat_id(candidatResponse.getIdCandidat());
            commentaire1.setCandidatResponse(candidatResponse);
            Commentaire savedcom = commentaireDaoconst.save(commentaire1);
            return CommentaireResponse.fromEntity(savedcom);
        } else {
            throw  new RuntimeException("commentaire not found ");
        }

    }
    @Override
    public CommentaireResponse createcommentaire(CommentaireRequest commentaire){
        Commentaire c =CommentaireResponse.toEntity(commentaire);
        Commentaire savedcommentaire= commentaireDaoconst.save(c);
        return CommentaireResponse.fromEntity(savedcommentaire);
    }

    @Override
    public List<CommentaireResponse> allCommentaire(){
        return commentaireDaoconst.findAll().stream()
                .map(CommentaireResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CommentaireResponse commentaireById(Long id) {
        return commentaireDaoconst.findById(id)
                .map(CommentaireResponse::fromEntity)
                .orElseThrow(() ->new RuntimeException("Commentaire not found with this id:" +id));
    }

    @Override
    public CommentaireResponse updatecommentaire(CommentaireRequest commentaireRequest, Long id) {
        Commentaire commentaire=commentaireDaoconst.findById(id).orElseThrow(()->
                new RuntimeException("Commentaire not found with this id:" +id));
        if (commentaire != null) {
            Commentaire c = CommentaireResponse.toEntity(commentaireRequest);
            c.setIdCommentaire(id);
            c.setContenu(c.getContenu() == null ? commentaire.getContenu() : c.getContenu());
            Commentaire savedcommentaire = commentaireDaoconst.save(c);
            return CommentaireResponse.fromEntity(savedcommentaire);
        }else{
            return null;
        }
    }


    @Override
    public HashMap<String, String> deletecommentaire(Long id) {
        HashMap message =new HashMap<>();
        Commentaire c = commentaireDaoconst.findById(id).orElse(null);
        if (c != null) {
            try {
                commentaireDaoconst.deleteById(id);
                message.put("message", "Commentaire deleted successfully");
            }catch (Exception a){
                message.put("message", a.getMessage());
            }
        }else{
            message.put("message", "Commentaire not found: " +id);
        }
        return message;
    }
}
