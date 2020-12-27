package com.springbootcamp.controller;

import com.springbootcamp.services.CategoryService;
import com.springbootcamp.utils.BaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class CategorySellerController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<BaseVO> getAllCategories(){

        return categoryService.getAllCategoriesForSeller();
    }


}
