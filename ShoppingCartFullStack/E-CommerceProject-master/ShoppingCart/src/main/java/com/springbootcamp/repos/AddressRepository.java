package com.springbootcamp.repos;

import com.springbootcamp.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long>
{
    @Override
    Optional<Address> findById(Long id);
    Optional<Address> findByUserId(Long userId);

}
