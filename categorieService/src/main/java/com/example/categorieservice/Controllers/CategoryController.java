package com.example.categorieservice.Controllers;


import com.example.categorieservice.Services.CategoryService;
import com.example.categorieservice.Dtos.request.CategoryRequest;
import com.example.categorieservice.Dtos.response.CategoryResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/categorys")
public class CategoryController {
    public final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/save")
    public CategoryResponse addCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createcategory(categoryRequest);
    }

    @GetMapping("/all")
    public List<CategoryResponse> AllCategory() {
        return categoryService.allCategory();
    }

    @GetMapping("/getone/{id}")
    public CategoryResponse categorybyid(@PathVariable Long id) {
        return categoryService.categoryById(id);
    }

    @PutMapping("/update/{id}")
    public CategoryResponse updatecategory(@RequestBody CategoryRequest categoryRequest, @PathVariable Long id) {
        return categoryService.updatecategory(categoryRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deletecategory(@PathVariable Long id) {
        return categoryService.deletecategory(id);
    }


}
