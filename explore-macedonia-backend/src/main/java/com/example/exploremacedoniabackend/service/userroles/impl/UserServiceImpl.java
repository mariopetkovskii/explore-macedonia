package com.example.exploremacedoniabackend.service.userroles.impl;

import com.example.exploremacedoniabackend.models.userroles.entity.User;
import com.example.exploremacedoniabackend.models.userroles.exceptions.PasswordsDoNotMatchException;
import com.example.exploremacedoniabackend.models.userroles.exceptions.UserAlreadyExistsException;
import com.example.exploremacedoniabackend.models.userroles.helpers.UserEmailDto;
import com.example.exploremacedoniabackend.models.userroles.helpers.UserRegisterDto;
import com.example.exploremacedoniabackend.repository.userroles.UserRepository;
import com.example.exploremacedoniabackend.service.userroles.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public Boolean passwordMatches(User user, String password) {
        return this.passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public void deleteUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email);
        this.userRepository.deleteById(user.getId());
    }

    @Override
    public Optional<User> register(UserRegisterDto userRegisterDto) {
        User user = this.userRepository.findByEmail(userRegisterDto.getEmail());
        if(user != null){
            throw new UserAlreadyExistsException();
        }
        if(!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())){
            throw new PasswordsDoNotMatchException();
        }
        User newUser = new User(
                userRegisterDto.getFirstName(),
                userRegisterDto.getLastName(),
                userRegisterDto.getEmail(),
                passwordEncoder.encode(userRegisterDto.getPassword()));
        this.userRepository.save(newUser);
        return Optional.of(newUser);
    }

    @Override
    public Optional<User> details(UserEmailDto userEmailDto) {
        return Optional.of(this.userRepository.findByEmail(userEmailDto.getEmail()));
    }
}