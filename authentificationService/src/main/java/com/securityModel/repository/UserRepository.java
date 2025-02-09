package com.securityModel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securityModel.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByNom(String nom);

  Boolean existsByNom(String nom);

  Boolean existsByEmail(String email);

  User findByEmail(String email);
}
