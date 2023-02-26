package com.example.exploremacedoniabackend.repository.userroles;

import com.example.exploremacedoniabackend.models.userroles.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email=:email")
    User findByEmail(String email);
    @Query("select u from User u where u.id=:id")
    User findById(String id);
}