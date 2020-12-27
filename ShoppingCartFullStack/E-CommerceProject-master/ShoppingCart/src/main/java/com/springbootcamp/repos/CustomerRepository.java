package com.springbootcamp.repos;

import com.springbootcamp.models.Address;
import com.springbootcamp.models.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Customer findByEmail(String email);

    Optional<Customer> findById(Long id);

    Iterable<Customer> findAll(Pageable pageable);

    Iterable<Address> findAddressByEmail(String username);

}
