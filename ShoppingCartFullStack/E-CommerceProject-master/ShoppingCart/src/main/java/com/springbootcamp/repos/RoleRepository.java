package com.springbootcamp.repos;

import com.springbootcamp.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Role findByAuthority(String Authority);
}
