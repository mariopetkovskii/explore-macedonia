package com.example.exploremacedoniabackend.models.userroles.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;


@Data
@Entity
@NoArgsConstructor
@Table(name = "users_table", schema = "userroles")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "date_created")
    private OffsetDateTime dateCreated;

    @Column(name = "date_modified")
    private OffsetDateTime dateModified;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;

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
