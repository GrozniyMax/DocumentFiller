package com.maxim.documentfiller.PostgresDB;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admins")
@Getter
@Setter
public class UserDAO {

    public enum Role {
        TEMPLATE_MANAGER,
        USER_MANAGER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;


    @Enumerated(EnumType.STRING)
    private Role role;

}
