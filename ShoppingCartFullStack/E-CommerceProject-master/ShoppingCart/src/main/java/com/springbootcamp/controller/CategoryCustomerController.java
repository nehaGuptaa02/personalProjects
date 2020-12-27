package com.springbootcamp.controller;

import com.springbootcamp.services.CategoryService;
import com.springbootcamp.utils.BaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CategoryCustomerController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/categories")
    public ResponseEntity<BaseVO> getAllCategories(@RequestParam(required = false) Long id){
        return categoryService.getAllCategoriesForCustomer(id);
    }
}
