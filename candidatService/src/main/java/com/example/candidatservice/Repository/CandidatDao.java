package com.example.candidatservice.Repository;

import com.example.candidatservice.Modele.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatDao extends JpaRepository<Candidat, Long> {
    Candidat findByEmail(String email);
}
