package com.example.commentaireservice.Repository;

import com.example.commentaireservice.Modele.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentaireDAO extends JpaRepository<Commentaire, Long> {
    @Query("select c from Commentaire c where c.candidat_id=:candidatId")
    List<Commentaire> findByCandidat(@Param("candidatId") Long candidatId);
}
