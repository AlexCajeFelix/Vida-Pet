
package com.vida.pet.vidapet.Core.Domain.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import com.vida.pet.vidapet.App.Enum.RoleEnum;
import com.vida.pet.vidapet.Core.Domain.BaseEntity;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Roles extends BaseEntity<Roles> {

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Roles(RoleEnum roleEnum) {
        this.name = roleEnum.name();
    }

    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.users.add(user);
    }

    public void removeUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.users.remove(user);
    }

    public void validateRole() {
        if (this.name == null || this.name.trim().isEmpty()) {
            throw new IllegalArgumentException("Role name cannot be null or empty");
        }
    }

    public boolean isAdmin() {
        return RoleEnum.ROLE_ADMIN.name().equals(this.name);
    }

    @PrePersist
    public void onCreate() {
        validateRole();
    }

    public String getRoleSummary() {
        return "Role: " + this.name + ", Users: " + users.size();
    }

}
