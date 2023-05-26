package com.auth.ken.authjwt.repository;

import com.auth.ken.authjwt.model.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@EnableScan
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findBySub(String sub);
}
