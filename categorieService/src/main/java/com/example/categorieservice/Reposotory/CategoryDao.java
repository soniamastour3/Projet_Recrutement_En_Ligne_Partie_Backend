package com.example.categorieservice.Reposotory;


import com.example.categorieservice.Modele.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Long> {
}
