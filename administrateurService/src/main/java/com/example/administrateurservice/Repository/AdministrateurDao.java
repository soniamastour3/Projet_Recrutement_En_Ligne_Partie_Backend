package com.example.administrateurservice.Repository;


import com.example.administrateurservice.Modele.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrateurDao extends JpaRepository<Administrateur, Long> {
}
