package com.example.visiteurservice.Repository;

import com.example.visiteurservice.Modele.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisiteurDAO extends JpaRepository<Visiteur, Long> {
}
