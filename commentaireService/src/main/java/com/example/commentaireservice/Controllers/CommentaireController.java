package com.example.commentaireservice.Controllers;

import com.example.commentaireservice.Dtos.request.CommentaireRequest;
import com.example.commentaireservice.Dtos.response.CandidatResponse;
import com.example.commentaireservice.Dtos.response.CommentaireResponse;
import com.example.commentaireservice.Services.CommentaireService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/commentaires")
public class CommentaireController {
    public final CommentaireService commentaireService;

    public CommentaireController(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    @PostMapping("/save/{idCad}")
    public CommentaireResponse addCandidatByIdCandidat(@RequestBody CommentaireRequest commentaireRequest, @PathVariable Long idCad) {
        return commentaireService.createCommentaireCandidat(commentaireRequest, idCad );
    }
    @PostMapping("/save")
    public CommentaireResponse addCommentaire(@RequestBody CommentaireRequest commentaireRequest) {
        return commentaireService.createcommentaire(commentaireRequest);
    }
    @GetMapping("/all/{idCand}")
    public List<CommentaireResponse> AllCommentaireByIdCandidat( @PathVariable Long idCand) {
        return commentaireService.getAllByidCandidat(idCand);
    }

    @PutMapping(value = {"/updatee/{id}", "/updatee/{id}/{candidat_id}"})
    public CommentaireResponse updateCommentaireDECandidat(@RequestBody CommentaireRequest commentaireRequest, @PathVariable Long id, @PathVariable Long candidat_id) {
        return commentaireService.updateCommentaireCandidat(commentaireRequest, id,candidat_id);
    }

    @GetMapping("/all")
    public List<CommentaireResponse> AllCommentaire() {
        return commentaireService.allCommentaire();
    }

    @GetMapping("/getone/{id}")
    public CommentaireResponse commentairebyid(@PathVariable Long id) {
        return commentaireService.commentaireById(id);
    }

    @PutMapping("/update/{id}")
    public CommentaireResponse updatecommentaire(@RequestBody CommentaireRequest commentaireRequest, @PathVariable Long id) {
        return commentaireService.updatecommentaire(commentaireRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deletecommentaire(@PathVariable Long id) {
        return commentaireService.deletecommentaire(id);
    }


}
