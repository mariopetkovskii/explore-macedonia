package com.example.exploremacedoniabackend.service.userroles.interfaces;

import com.example.exploremacedoniabackend.models.userroles.entity.User;
import com.example.exploremacedoniabackend.models.userroles.helpers.UserEmailDto;
import com.example.exploremacedoniabackend.models.userroles.helpers.UserRegisterDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findByEmail(String email);

    User save(User user);

    Boolean passwordMatches(User user, String password);

    List<User> findAll();

    void deleteUserByEmail(String email);

    Optional<User> register(UserRegisterDto userRegisterDto);

    Optional<User> details(UserEmailDto userEmailDto);


}
