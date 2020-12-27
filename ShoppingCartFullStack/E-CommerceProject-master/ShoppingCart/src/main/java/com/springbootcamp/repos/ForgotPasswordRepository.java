package com.springbootcamp.repos;


import com.springbootcamp.models.ForgotPasswordToken;
import com.springbootcamp.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRepository extends CrudRepository<ForgotPasswordToken, Long> {

    ForgotPasswordToken findByToken(String token);

    ForgotPasswordToken findByUser(User user);
}
