package com.springbootcamp.repos;

import com.springbootcamp.models.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    @Query(value = "select * from CATEGORY where parent_id is null;", nativeQuery = true)
    List<Category> findByParentIdIsNull();

    Category findAllById(Long categoryId);

    Category findByName(String categoryName);
    List<Category> findAll();
    List<Category> findAll(Pageable pageable);
//
//    Category findById(Long categoryId);
}
