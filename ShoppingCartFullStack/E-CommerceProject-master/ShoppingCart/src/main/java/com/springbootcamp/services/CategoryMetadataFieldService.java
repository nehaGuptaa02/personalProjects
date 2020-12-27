package com.springbootcamp.services;

import com.springbootcamp.dtos.CategoryAdminResponseDto;
import com.springbootcamp.dtos.CategoryDto;
import com.springbootcamp.dtos.CategoryMetadataFieldDto;
import com.springbootcamp.enums.ErrorCode;
import com.springbootcamp.exceptions.ECommerceException;
import com.springbootcamp.models.Category;
import com.springbootcamp.models.CategoryMetadataField;
import com.springbootcamp.repos.CategoryMetadataFieldRepository;
import com.springbootcamp.repos.CategoryRepository;
import com.springbootcamp.transformers.CategoryMetadataFieldTransformer;
import com.springbootcamp.transformers.CategoryTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryMetadataFieldService {

    @Autowired
    private CategoryMetadataFieldRepository categoryMetadataFieldRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MessageSource messageSource;

    public String addMetadataField(String fieldName, Locale locale) {
        CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepository.findByName(fieldName);
        if(fieldName==null || fieldName.isEmpty() || fieldName.trim().length()==0)
            throw new ECommerceException(ErrorCode.NULL_VALUES);
        if (categoryMetadataField != null)
        {
            throw new ECommerceException(ErrorCode.METADATA_FIELD_ALREADY_EXISTS);
        }

        categoryMetadataField = new CategoryMetadataField();
        categoryMetadataField.setName(fieldName);
        categoryMetadataFieldRepository.save(categoryMetadataField);
        return messageSource.getMessage("message.MetadataField", null, locale);
    }

    public List<CategoryMetadataFieldDto> viewAllMetadataFields(Locale locale, Pageable pageable) {
        List<CategoryMetadataField> categoryMetadataFieldList = (List<CategoryMetadataField>) categoryMetadataFieldRepository.findAll(pageable);
        List<CategoryMetadataFieldDto> categoryMetadataFieldDtoList = new ArrayList<>();
        CategoryMetadataFieldTransformer categoryMetadataFieldTransformer = new CategoryMetadataFieldTransformer();
        categoryMetadataFieldList.forEach(categoryMetadataField -> {
            categoryMetadataFieldDtoList.add(categoryMetadataFieldTransformer.toDto(categoryMetadataField));
        });
        return categoryMetadataFieldDtoList;
    }


}
