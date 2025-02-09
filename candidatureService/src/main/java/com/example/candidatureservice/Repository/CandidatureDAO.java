package com.example.candidatureservice.Repository;

import com.example.candidatureservice.Modele.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatureDAO extends JpaRepository<Candidature, Long> {
}
