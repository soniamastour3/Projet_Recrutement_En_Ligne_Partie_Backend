package com.example.entrepriseservice.Repository;

import com.example.entrepriseservice.Modele.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepriseDAO extends JpaRepository<Entreprise, Long> {
}
