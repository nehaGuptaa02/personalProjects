package com.springbootcamp.repos;

import com.springbootcamp.models.ProductVariation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductVariationRepository extends CrudRepository<ProductVariation,Long>
{

    List<ProductVariation> findAllByProductId(Long id, Pageable pageable);
}
