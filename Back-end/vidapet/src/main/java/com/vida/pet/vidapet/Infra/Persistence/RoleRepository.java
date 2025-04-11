package com.vida.pet.vidapet.Infra.Persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vida.pet.vidapet.Core.Domain.Entities.Roles;

public interface RoleRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByName(String name);

    boolean existsByName(String name);

    List<Roles> findByNameIn(List<String> names);
}
