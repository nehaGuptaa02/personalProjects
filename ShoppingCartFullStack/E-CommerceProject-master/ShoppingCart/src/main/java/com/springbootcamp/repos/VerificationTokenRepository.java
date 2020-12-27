package com.springbootcamp.repos;

import com.springbootcamp.models.User;
import com.springbootcamp.models.VerificationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken,Long>
{
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
