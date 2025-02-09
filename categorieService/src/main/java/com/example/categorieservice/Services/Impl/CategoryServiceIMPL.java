package com.example.categorieservice.Services.Impl;



import com.example.categorieservice.Services.CategoryService;
import com.example.categorieservice.Dtos.request.CategoryRequest;
import com.example.categorieservice.Dtos.response.CategoryResponse;
import com.example.categorieservice.Modele.Category;
import com.example.categorieservice.Reposotory.CategoryDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceIMPL implements CategoryService {
    public static CategoryDao categoryDaoconst;

    public CategoryServiceIMPL(CategoryDao categoryDaoconst) {
        this.categoryDaoconst = categoryDaoconst;
    }

    @Override
    public CategoryResponse createcategory(CategoryRequest category){
        Category c =CategoryResponse.toEntity(category);
        Category savedcategory= categoryDaoconst.save(c);
        return CategoryResponse.fromEntity(savedcategory);
    }

    @Override
    public List<CategoryResponse> allCategory(){
        return categoryDaoconst.findAll().stream()
                .map(CategoryResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse categoryById(Long id) {
        return categoryDaoconst.findById(id)
                .map(CategoryResponse::fromEntity)
                .orElseThrow(() ->new RuntimeException("Category not found with this id:" +id));
    }

    @Override
    public CategoryResponse updatecategory(CategoryRequest categoryRequest, Long id) {
        Category category=categoryDaoconst.findById(id).orElseThrow(()->
                new RuntimeException("Category not found with this id:" +id));
        if (category != null) {
            Category c = CategoryResponse.toEntity(categoryRequest);
            c.setIdCategorie(id);
            c.setNom(c.getNom() == null ? category.getNom() : c.getNom());
            Category savedcategory = categoryDaoconst.save(c);
            return CategoryResponse.fromEntity(savedcategory);
        }else{
            return null;
        }
    }


    @Override
    public HashMap<String, String> deletecategory(Long id) {
        HashMap message =new HashMap<>();
        Category c = categoryDaoconst.findById(id).orElse(null);
        if (c != null) {
            try {
                categoryDaoconst.deleteById(id);
                message.put("message", "Category deleted successfully");
            }catch (Exception a){
                message.put("message", a.getMessage());
            }
        }else{
            message.put("message", "Category not found: " +id);
        }
        return message;
    }
    }

