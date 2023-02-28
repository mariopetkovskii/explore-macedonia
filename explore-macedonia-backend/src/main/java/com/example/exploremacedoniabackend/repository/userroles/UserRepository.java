package com.example.exploremacedoniabackend.repository.userroles;

import com.example.exploremacedoniabackend.models.userroles.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}