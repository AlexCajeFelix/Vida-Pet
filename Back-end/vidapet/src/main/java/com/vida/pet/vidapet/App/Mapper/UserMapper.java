package com.vida.pet.vidapet.App.Mapper;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.vida.pet.vidapet.App.Dtos.UserDto;
import com.vida.pet.vidapet.Core.Domain.Entities.Roles;
import com.vida.pet.vidapet.Core.Domain.Entities.User;

@Component
public class UserMapper {

    public User toEntity(UserDto userDto, Set<Roles> roles) {
        return new User(userDto, roles);
    }

    public UserDto toDto(User user) {
        return new UserDto(user.getUsername(), user.getEmail(), user.getPassword(), user.getEnabled());
    }

}
