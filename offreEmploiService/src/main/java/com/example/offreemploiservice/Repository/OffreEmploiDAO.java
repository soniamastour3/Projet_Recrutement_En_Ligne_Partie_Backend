package com.example.offreemploiservice.Repository;

import com.example.offreemploiservice.Modele.OffreEmploi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OffreEmploiDAO extends JpaRepository<OffreEmploi, Long> {
    @Query("select e from OffreEmploi e where e.entreprise_id=:entrepriseId")
    List<OffreEmploi> findByEntreprise(@Param("entrepriseId") Long entrepriseId);
}
