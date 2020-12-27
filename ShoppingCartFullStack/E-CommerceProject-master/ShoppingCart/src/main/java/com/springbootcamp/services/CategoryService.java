package com.springbootcamp.services;

import com.springbootcamp.dtos.CategoryAdminResponseDto;
import com.springbootcamp.dtos.CategoryDto;
import com.springbootcamp.dtos.CategoryMetadataFieldDto;
import com.springbootcamp.dtos.MetadataFieldValueInsertDto;
import com.springbootcamp.enums.ErrorCode;
import com.springbootcamp.exceptions.ECommerceException;
import com.springbootcamp.models.*;
import com.springbootcamp.repos.CategoryMetadataFieldRepository;
import com.springbootcamp.repos.CategoryMetadataFieldValuesRepository;
import com.springbootcamp.repos.CategoryRepository;
import com.springbootcamp.transformers.CategoryMetadataFieldTransformer;
import com.springbootcamp.transformers.CategoryTransformer;
import com.springbootcamp.utils.BaseVO;
import com.springbootcamp.utils.ErrorVO;
import com.springbootcamp.utils.ResponseVO;
import com.springbootcamp.utils.StringToSetParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMetadataFieldRepository fieldRepository;
    @Autowired
    CategoryMetadataFieldValuesRepository valuesRepository;


    public ResponseEntity<BaseVO> addCategory(String categoryName, Long parentId) {
        BaseVO response;
        if (categoryName == null || categoryName.isEmpty() || categoryName.trim().length() == 0) {
            response = new ErrorVO("Validation failed", "Please enter the category name", new Date());
            return new ResponseEntity<BaseVO>(response, HttpStatus.BAD_REQUEST);

        }
        String message = validateNewCategory(categoryName, parentId);
        if (!message.equals("valid")) {
            response = new ErrorVO("Validation failed", message, new Date());
            return new ResponseEntity<BaseVO>(response, HttpStatus.BAD_REQUEST);
        }

        Category category = new Category(categoryName);
        Category parent;
        if (parentId == null) {
            category.setParentCategory(null);
            categoryRepository.save(category);
        } else {
            Optional<Category> parentCategory = categoryRepository.findById(parentId);
            if (!parentCategory.isPresent()) {
                response = new ErrorVO("Invalid input", "Parent category not found", new Date());
                return new ResponseEntity<BaseVO>(response, HttpStatus.CONFLICT);
            } else {
                parent = parentCategory.get();
                category.setParentCategory(parent);
                parent.addSubCategory(category);
                categoryRepository.save(parent);
            }
        }
        response = new ResponseVO<Category>(null, "Success", new Date());
        return new ResponseEntity<BaseVO>(response, HttpStatus.CREATED);
    }

    private String validateNewCategory(String categoryName, Long parentId) {
        if (parentId == null) {
            Category preStored = categoryRepository.findByName(categoryName);
            if (preStored == null)
                return "valid";
            return "Category already exists. So, can not insert at the root level.";
        }

        //check uniqueness at the root level
        List<Category> preStored = categoryRepository.findByParentIdIsNull();

        for (Category c : preStored) {
            if (c.getName().equalsIgnoreCase(categoryName))
                return "Category already exists at the root level.";
        }

        Category parent = categoryRepository.findById(parentId).get();

        // check immediate children
        Set<Category> children = parent.getSubcategories();
        for (Category c : children) {
            if (c.getName().equalsIgnoreCase(categoryName))
                return "Sibling category exists with same name.";
        }

        // check product associations of parent
        Set<Product> products = parent.getProducts();
        if (!products.isEmpty())
            return "Parent category has product associations.";

        // check path from parent to root
        while (parent != null) {
            if (parent.getName().equalsIgnoreCase(categoryName))
                return "Category already exists as ancestor.";
            parent = parent.getParentCategory();
        }

        return "valid";
    }

    public ResponseEntity<BaseVO> getCategory(Long categoryId) {
        BaseVO response;
        if (categoryId == null) {
            response = new ErrorVO("Not Found", "Please enter the category Id.", new Date());
            return new ResponseEntity<BaseVO>(response, HttpStatus.NOT_FOUND);
        }

        Optional<Category> preStored = categoryRepository.findById(categoryId);
        if (!preStored.isPresent()) {
            response = new ErrorVO("Not Found", "Category with given id does not exist.", new Date());
            return new ResponseEntity<BaseVO>(response, HttpStatus.NOT_FOUND);
        }

        CategoryAdminResponseDto categoryAdminResponseDto = toCategoryAdminResponseDto(preStored.get());

        response = new ResponseVO<CategoryAdminResponseDto>(categoryAdminResponseDto, null, new Date());
        return new ResponseEntity<BaseVO>(response, HttpStatus.OK);
    }

    public CategoryAdminResponseDto toCategoryAdminResponseDto(Category category) {

        CategoryAdminResponseDto categoryAdminResponseDto = new CategoryAdminResponseDto();

        // get actual category with all parent tree
        CategoryTransformer categoryTransformer = new CategoryTransformer();
        CategoryDto categoryDto = categoryTransformer.toDto(category);
        categoryAdminResponseDto.setCategoryDto(categoryDto);
        // get child categories
        Set<CategoryDto> subCategories;
        if (category.getSubcategories() != null) {
            subCategories = new HashSet<>();

            category.getSubcategories().forEach((e) -> {
                CategoryDto categoryDto1 = categoryTransformer.toDto(category);
                categoryDto1.setParentId(category.getId());
                subCategories.add(categoryTransformer.toDto(e));
            });
            categoryAdminResponseDto.setSubCategories(subCategories);
        }

        // get the possible metadata fields and values
        Set<CategoryMetadataFieldDto> fieldValues;
        if (category.getCategoryMetadataFieldValuesSet() != null) {
            fieldValues = new HashSet<>();

            category.getCategoryMetadataFieldValuesSet().forEach((e) -> {
                CategoryMetadataFieldTransformer categoryMetadataFieldTransformer = new CategoryMetadataFieldTransformer();
                CategoryMetadataFieldDto categoryMetadataFieldDto = categoryMetadataFieldTransformer.toDto(e.getCategoryMetadataField());
                categoryMetadataFieldDto.setValues(StringToSetParser.toSetOfValues(e.getValue()));
                fieldValues.add(categoryMetadataFieldDto);
            });
            categoryAdminResponseDto.setCategoryMetadataFieldDtoSet(fieldValues);
        }
        return categoryAdminResponseDto;
    }

    public ResponseEntity<BaseVO>   getAllCategories(Pageable pageable) {
        BaseVO response;
        List<Category> categories = categoryRepository.findAll(pageable);
        List<CategoryAdminResponseDto> categoryDtos = new ArrayList<>();

        categories.forEach((category) -> {
            categoryDtos.add(toCategoryAdminResponseDto(category));
        });

        response = new ResponseVO<List>(categoryDtos, null, new Date());
        return new ResponseEntity<BaseVO>(response, HttpStatus.OK);
    }

    public ResponseEntity<BaseVO> updateCategory(Long id, String name) {
        BaseVO response;
        if (id == null || name == null || name.isEmpty() || name.trim().length() == 0) {

            response = new ErrorVO("Null Values", "Please enter the mandatory fields", new Date());
            return new ResponseEntity<BaseVO>(response, HttpStatus.NOT_FOUND);
        }

        Optional<Category> savedCategory = categoryRepository.findById(id);
        if (!savedCategory.isPresent()) {
            response = new ErrorVO("Not Found", "Category does not exist.", new Date());
            return new ResponseEntity<BaseVO>(response, HttpStatus.NOT_FOUND);
        }

        Category category = savedCategory.get();
        category.setName(name);
        categoryRepository.save(category);

        response = new ResponseVO<String>(null, "Success", new Date());
        return new ResponseEntity<BaseVO>(response, HttpStatus.OK);
    }

//    public ResponseEntity<BaseVO> getAllCategoriesForCustomer(Long id) {
//    }

    public ResponseEntity<BaseVO> getAllCategoriesForSeller() {
        BaseVO response;
        List<Category> categories = categoryRepository.findAll();
        List<CategoryAdminResponseDto> categoryDtos = new ArrayList<>();

        categories.forEach((category) -> {
            categoryDtos.add(toCategoryAdminResponseDto(category));
        });

        response = new ResponseVO<List>(categoryDtos, null, new Date());
        return new ResponseEntity<BaseVO>(response, HttpStatus.OK);

    }

    public ResponseEntity<BaseVO> getAllCategoriesForCustomer(Long id) {
        BaseVO response;
        CategoryTransformer categoryTransformer = new CategoryTransformer();
        if (id == null) {
            List<Category> rootCategories = categoryRepository.findByParentIdIsNull();
            List<CategoryDto> categoryDtos = new ArrayList<>();

            rootCategories.forEach((e) -> {
                categoryDtos.add(categoryTransformer.toDto(e));
            });
            response = new ResponseVO<List>(categoryDtos, null, new Date());
            return new ResponseEntity<BaseVO>(response, HttpStatus.OK);
        }
        Optional<Category> savedCategory = categoryRepository.findById(id);
        if (!savedCategory.isPresent()) {
            response = new ErrorVO("Not Found", "Category does not exist.", new Date());
            return new ResponseEntity<BaseVO>(response, HttpStatus.NOT_FOUND);
        }

        Category category = savedCategory.get();
        Set<Category> subCategories = category.getSubcategories();
        List<CategoryDto> subCategoryDtos = new ArrayList<>();

        subCategories.forEach((e) -> {
            subCategoryDtos.add(categoryTransformer.toDto(e));
        });
        response = new ResponseVO<List>(subCategoryDtos, null, new Date());
        return new ResponseEntity<BaseVO>(response, HttpStatus.OK);
    }

    public ResponseEntity<BaseVO> addMetadataFieldValuePair(MetadataFieldValueInsertDto fieldValueDtos) {
        BaseVO response;
        String message = validateCategoryMetadataFieldDto(fieldValueDtos, "creation");
        if (!message.equalsIgnoreCase("success")) {
            response = new ErrorVO("Validation failed", message, new Date());
            return new ResponseEntity<BaseVO>(response, HttpStatus.BAD_REQUEST);
        }

        return createMetadataFieldValuePair(fieldValueDtos);
    }

    public String validateCategoryMetadataFieldDto(MetadataFieldValueInsertDto fieldValueDtos, String purpose) {
        BaseVO response;
        String message;

        Optional<Category> savedCategory = categoryRepository.findById(fieldValueDtos.getCategoryId());
        if (!savedCategory.isPresent()) {
            message = "Category does not exist.";
            return message;
        }

        Category category = savedCategory.get();
        for (CategoryMetadataFieldDto fieldValuePair : fieldValueDtos.getFieldValues()) {
            Optional<CategoryMetadataField> field = fieldRepository.findById(fieldValuePair.getId());
            if (!field.isPresent()) {
                message = "Field id " + fieldValuePair.getId() + " not found";
                return message;
            }

            if (purpose.equalsIgnoreCase("creation")) {
                if (!field.get().getCategoryMetadataFieldValuesSet().isEmpty()) {
                    message = "Field values already exist for field " + fieldValuePair.getId();
                }
            }

            // if field is found valid, then check for values
            if (fieldValuePair.getValues().isEmpty()) {
                message = "No field values provided to insert for field id " + fieldValuePair.getId();
                return message;
            }
            //duplicate values error can not occur as I am storing values in a set.
        }

        message = "success";
        return message;
    }

    public ResponseEntity<BaseVO> createMetadataFieldValuePair(MetadataFieldValueInsertDto fieldValueDtos) {
        BaseVO response;
        Category category = categoryRepository.findById(fieldValueDtos.getCategoryId()).get();
        CategoryMetadataFieldValues categoryFieldValues = new CategoryMetadataFieldValues();
        CategoryMetadataField categoryField;

        for (CategoryMetadataFieldDto fieldValuePair : fieldValueDtos.getFieldValues()) {

            categoryField = fieldRepository.findById(fieldValuePair.getId()).get();
            String values = StringToSetParser.toCommaSeparatedString(fieldValuePair.getValues());

            categoryFieldValues.setValue(values);
            categoryFieldValues.setCategory(category);
            categoryFieldValues.setCategoryMetadataField(categoryField);

            valuesRepository.save(categoryFieldValues);
        }
        response = new ResponseVO<String>(null, "Success", new Date());
        return new ResponseEntity<BaseVO>(response, HttpStatus.CREATED);
    }
//    public ResponseEntity<BaseVO> updateMetadataFieldValuePair(MetadataFieldValueInsertDto fieldValueDtos) {
//        BaseVO response;
//        String message = validateCategoryMetadataFieldDto(fieldValueDtos, "creation");
//        if(!message.equalsIgnoreCase("success")){
//            response = new ErrorVO("Validation failed", message, new Date());
//            return new ResponseEntity<BaseVO>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        Category category = categoryRepository.findById(fieldValueDtos.getCategoryId()).get();
//        Optional<CategoryMetadataFieldValues> savedMetadataFieldValue;
//        CategoryMetadataFieldValues metadataFieldValue;
//        List<CategoryMetadataFieldDto> fieldValuePairs = fieldValueDtos.getFieldValues();
//
//        for(CategoryMetadataFieldDto fieldValuePair : fieldValuePairs){
//            CategoryMetadataField field = fieldRepository.findById(fieldValuePair.getId()).get();
//
//            CategoryMetadataFieldValueCompositeKey fieldValuePairId =
//                    new CategoryMetadataFieldValueCompositeKey(category.getId(), field.getId());
//
//            savedMetadataFieldValue = valuesRepository.findById(fieldValuePairId);
//            String values = null;
//            Set<String> valueSet;
//            if(savedMetadataFieldValue.isPresent()){
//                metadataFieldValue = savedMetadataFieldValue.get();
//                values = metadataFieldValue.getValue();
//                valueSet = StringToSetParser.toSetOfValues(values);
//            }
//            else{
//                metadataFieldValue = new CategoryMetadataFieldValues();
//                valueSet = new HashSet<>();
//            }
//
//            for(String value : fieldValuePair.getValues()){
//                valueSet.add(value);
//            }
//
//            values = StringToSetParser.toCommaSeparatedString(valueSet);
//
//            metadataFieldValue.setValue(values);
//            metadataFieldValue.setCategoryMetadataField(field);
//            metadataFieldValue.setCategory(category);
//
//            valuesRepository.save(metadataFieldValue);
//        }
//        response = new ResponseVO<String>(null, "Success", new Date());
//        return new ResponseEntity<BaseVO>(response, HttpStatus.OK);
//    }


}