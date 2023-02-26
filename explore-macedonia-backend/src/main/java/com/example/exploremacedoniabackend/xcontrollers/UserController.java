package com.example.exploremacedoniabackend.xcontrollers;

import com.example.exploremacedoniabackend.models.userroles.entity.User;
import com.example.exploremacedoniabackend.models.userroles.helpers.UserEmailDto;
import com.example.exploremacedoniabackend.models.userroles.helpers.UserInfoDto;
import com.example.exploremacedoniabackend.models.userroles.helpers.UserRegisterDto;
import com.example.exploremacedoniabackend.service.userroles.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/user")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDto userRegisterDto){
        return this.userService.register(userRegisterDto)
                .map(user -> ResponseEntity.ok().body("User is registered successfully. Please check your email to finish registration."))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/listAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> listAll(){
        return this.userService.findAll();
    }

    @PostMapping("/details")
    public ResponseEntity<UserInfoDto> userDetails(@RequestBody UserEmailDto userEmailDto){
        return this.userService.details(userEmailDto)
                .map(user -> {
                    UserInfoDto userInfoDto = new UserInfoDto();
                    userInfoDto.setEmail(user.getEmail());
                    userInfoDto.setFirstName(user.getFirstName());
                    userInfoDto.setLastName(user.getLastName());
                    return ResponseEntity.ok().body(userInfoDto);
                })
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}