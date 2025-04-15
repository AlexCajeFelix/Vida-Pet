package com.vida.pet.vidapet.Core.Domain.Entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import com.vida.pet.vidapet.App.Dtos.UserDto;
import com.vida.pet.vidapet.Core.Domain.AggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
public class User extends AggregateRoot<User> {

    @NotBlank(message = "Coloque o username")
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String email;

    @Column(nullable = false, length = 60)
    @NotBlank
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

    }

    public User(UserDto userDto, Set<Roles> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("Roles cannot be null or empty");
        }
        this.username = userDto.username();
        this.email = userDto.email();
        this.password = userDto.password();
        this.enabled = userDto.enabled();
        this.roles = roles;

    }

    public Boolean deactiveUser() {
        if (this.enabled == null) {
            throw new IllegalArgumentException("Enabled cannot be null");
        }
        return this.enabled = false;
    }

}
