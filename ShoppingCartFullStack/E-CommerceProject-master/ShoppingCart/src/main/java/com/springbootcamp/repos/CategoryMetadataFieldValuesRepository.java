package com.springbootcamp.repos;

import com.springbootcamp.models.CategoryMetadataFieldValueCompositeKey;
import com.springbootcamp.models.CategoryMetadataFieldValues;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryMetadataFieldValuesRepository extends CrudRepository<CategoryMetadataFieldValues,Long> {

}
