package com.sagar.repository;

import com.sagar.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
    boolean existsByEmailAndPassword(String email, String password);

    User findByRole(String admin);
}
