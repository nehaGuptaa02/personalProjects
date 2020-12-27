package com.springbootcamp.repos;

import com.springbootcamp.models.Seller;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface SellerRepository extends CrudRepository<Seller, Long> {
    Seller findByEmail(String email);


    Iterable<Seller> findAll(Pageable pageable);

    Seller findByCompanyName(String value);

    Seller findBygst(String gst);
}
