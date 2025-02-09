package com.example.categorieservice.Dtos.response;


import com.example.categorieservice.Dtos.request.CategoryRequest;
import com.example.categorieservice.Modele.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private Long idCategorie;
    private String nom;
    private String description;


    //Création de l'objet CategoryResponse
    public static CategoryResponse fromEntity(Category entity) {
        CategoryResponse categoryResponse = new CategoryResponse();
        BeanUtils.copyProperties(entity, categoryResponse);
        return categoryResponse;
    }
    public static Category toEntity(CategoryRequest categoryResponse) {
        Category c = new Category();
        BeanUtils.copyProperties(categoryResponse, c);
        return c;
    }

}
