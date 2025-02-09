package com.example.testservice.Repository;

import com.example.testservice.Modele.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDAO extends JpaRepository<Test, Long> {
}
