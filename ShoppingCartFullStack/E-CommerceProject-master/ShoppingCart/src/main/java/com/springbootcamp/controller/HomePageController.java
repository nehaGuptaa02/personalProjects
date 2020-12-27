package com.springbootcamp.controller;

import com.springbootcamp.dtos.CategoryDto;
import com.springbootcamp.dtos.ParentCategoryResponseDto;
import com.springbootcamp.dtos.ProductDto;
import com.springbootcamp.services.HomeService;
import com.springbootcamp.utils.BaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomePageController {
    @Autowired
    private HomeService homeService;

    @GetMapping("/parentCategories")
    public List<ParentCategoryResponseDto> getAllParentCategories(HttpServletRequest httpServletRequest) {
        Principal principal = httpServletRequest.getUserPrincipal();
        String username = principal.getName();
        return homeService.getAllParentCategories(username);
    }
//    @GetMapping("/subCategories")
//    public List<String> getAllSubCategories(String categoryName,HttpServletRequest httpServletRequest) {
//        Principal principal =httpServletRequest.getUserPrincipal();
//        String username=principal.getName();
//        return homeService.getAllSubCategories(categoryName,username);
//    }

    @GetMapping("/categories")
    public ResponseEntity<BaseVO> getAllCategories() {

        return homeService.getAllCategories();
    }

    @GetMapping("/uptoAllChildCategories")
    public ResponseEntity<BaseVO> getAllChildCategories(@RequestParam("id") Long categoryId) {
        return homeService.getAllChildCategories(categoryId);
    }



}
