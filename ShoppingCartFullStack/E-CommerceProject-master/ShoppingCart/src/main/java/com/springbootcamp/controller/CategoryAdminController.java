package com.springbootcamp.controller;

import com.springbootcamp.dtos.CategoryDto;
import com.springbootcamp.dtos.CategoryMetadataFieldDto;
import com.springbootcamp.dtos.MetadataFieldValueInsertDto;
import com.springbootcamp.services.CategoryMetadataFieldService;
import com.springbootcamp.services.CategoryService;
import com.springbootcamp.utils.BaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/admin")
public class CategoryAdminController
{
    @Autowired
    private CategoryMetadataFieldService categoryMetadataFieldService;
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/addMetadataField")
    public String addMetadataField(@RequestParam("fieldName")String fieldName, HttpServletRequest httpServletRequest)
    {
        Locale locale=httpServletRequest.getLocale();
        return categoryMetadataFieldService.addMetadataField(fieldName,locale);
    }
    @GetMapping("/viewAllMetadataFields")
    public List<CategoryMetadataFieldDto> viewAllMetadataFields( HttpServletRequest httpServletRequest,
                                                                @PageableDefault(page = 0, value = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable)
    {
        Locale locale=httpServletRequest.getLocale();
        return categoryMetadataFieldService.viewAllMetadataFields(locale,pageable);
    }
    @PostMapping("/addCategory")
    public ResponseEntity<BaseVO> addCategory(@RequestParam("categoryName")String categoryName,
                                              @RequestParam(value="parentId",required = false) Long parentId) {
        return categoryService.addCategory(categoryName, parentId);
    }

    @GetMapping("/category")
    public ResponseEntity<BaseVO> getCategory(@RequestParam("id") Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @GetMapping("/categories")
    public ResponseEntity<BaseVO> getAllCategories( @PageableDefault(page = 0, value = 15, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {

        return categoryService.getAllCategories(pageable);
    }
    @PutMapping("/updateCategory")
    public ResponseEntity<BaseVO> updateCategory(@RequestParam("id") Long id, @RequestParam("name") String name){
        return categoryService.updateCategory(id, name);
    }

    @PostMapping("/addMetadataValueField")
    public ResponseEntity<BaseVO> addMetadataFieldValues(@RequestBody MetadataFieldValueInsertDto fieldValueDtos){
        return categoryService.addMetadataFieldValuePair(fieldValueDtos);
    }
//    @PutMapping("/metadata-field-values")
//    public ResponseEntity<BaseVO> updateMetadataFieldValues(@RequestBody MetadataFieldValueInsertDto fieldValueDtos){
//        return categoryService.updateMetadataFieldValuePair(fieldValueDtos);
//    }

//    @PutMapping("/metadata-field-values")
//    public ResponseEntity<BaseVO> updateMetadataFieldValues(@RequestBody MetadataFieldValueInsertDto fieldValueDtos){
//        return categoryService.updateMetadataFieldValuePair(fieldValueDtos);
//    }
//
//    @GetMapping("/category/filtering-details/{categoryId}")
//    public ResponseEntity<BaseVO> getFilteringDetailsForCategory(@PathVariable Long categoryId){
//        return categoryService.getFilteringDetailsForCategory(categoryId);
//    }



}
