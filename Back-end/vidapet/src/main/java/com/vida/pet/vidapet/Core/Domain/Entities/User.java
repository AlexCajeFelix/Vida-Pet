package com.vida.pet.vidapet.Core.Domain.Entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.vida.pet.vidapet.App.Dtos.UserDto;
import com.vida.pet.vidapet.Core.Domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseEntity<User> {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public User(UserDto userDto) {

        this.username = userDto.username();
        this.email = userDto.email();
        this.password = userDto.password();
        this.enabled = userDto.enabled();
        this.roles = new HashSet<>();

        verify();
    }

    private void verify() {
        if (this.username == null || this.username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
        if (this.email == null || this.email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        if (this.password == null || this.password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }

        if (this.enabled == null) {
            throw new IllegalArgumentException("Enabled cannot be null");
        }
    }

    public Boolean deactiveUser() {
        if (this.enabled == null) {
            throw new IllegalArgumentException("Enabled cannot be null");
        }
        return this.enabled = false;
    }

}
