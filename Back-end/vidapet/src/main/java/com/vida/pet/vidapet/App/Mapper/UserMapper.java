package com.vida.pet.vidapet.App.Mapper;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.vida.pet.vidapet.App.Dtos.UserDto;
import com.vida.pet.vidapet.Core.Domain.Entities.Roles;
import com.vida.pet.vidapet.Core.Domain.Entities.User;

@Component
public class UserMapper {

    public User toEntity(UserDto userDto, Set<Roles> roles) {
        User user = new User();
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());
        user.setEnabled(userDto.enabled());
        user.setRoles(roles);
        return user;
    }

    public UserDto toDto(User user) {
        return new UserDto(user.getUsername(), user.getEmail(), user.getPassword(), user.getEnabled());
    }

}
