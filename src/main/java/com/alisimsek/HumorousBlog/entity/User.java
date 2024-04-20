package com.alisimsek.HumorousBlog.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"mail"}))
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String mail;

    private boolean active = false;

    private String activationToken;

    @Lob
    private String image;

}
