package com.springbootcamp.repos;


import com.springbootcamp.models.CategoryMetadataField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryMetadataFieldRepository extends CrudRepository<CategoryMetadataField,Long> {
    CategoryMetadataField findByName(String fieldName);
    List<CategoryMetadataField> findAll(Pageable pageable);

}
