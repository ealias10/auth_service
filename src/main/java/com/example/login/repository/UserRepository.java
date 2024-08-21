package com.example.login.repository;
import com.example.login.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<Users, UUID> {

    @Query("select u from Users u where u.username=:name and u.active='true'")
    Users getUserByName(@Param("name") String name);

    @Query(
            "SELECT u FROM Users u WHERE (u.username=:userNameOrEmail or u.email=:userNameOrEmail) and u.active=True")
    Optional<Users> findUserByNameOrEmailAndActiveTrue(
            @Param("userNameOrEmail") String userNameOrEmail);


}
