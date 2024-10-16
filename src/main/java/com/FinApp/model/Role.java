package com.FinApp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // "ROLE_SELLER", "ROLE_MANAGER", "ROLE_BOSS"

    public Role (String name) {
        this.name = name;
    }
}
