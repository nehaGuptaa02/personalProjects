package com.springbootcamp.repos;

import com.springbootcamp.models.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product,Long>
{

    Product findByName(String name);
    List<Product> findAllBySellerId(Long id, Pageable pageable);
    List<Product> findAll(Pageable pageable);

    List<Product> findByCategoryId(Long id, Pageable pageable);
}
