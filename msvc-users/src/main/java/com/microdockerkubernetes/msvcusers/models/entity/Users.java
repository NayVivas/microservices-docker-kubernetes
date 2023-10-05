package com.microdockerkubernetes.msvcusers.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    @NotEmpty(message = "El nombre no puede ser vacio")
    private String name;
    @Column(name = "email", unique = true)
    @NotEmpty
    @Email
    private String email;
    private String password;
}
