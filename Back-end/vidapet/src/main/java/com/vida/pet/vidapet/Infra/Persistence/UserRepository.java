package com.vida.pet.vidapet.Infra.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vida.pet.vidapet.Core.Entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
