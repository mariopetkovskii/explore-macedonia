package com.example.exploremacedoniabackend.models.userroles.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.OffsetDateTime;


@Data
@Entity
@Table(name = "users_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean isEnabled;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateModified;
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public User(){
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isEnabled = true;
        this.dateCreated = OffsetDateTime.now();
        this.dateModified = OffsetDateTime.now();
        this.role = Role.ROLE_USER;
    }

}
