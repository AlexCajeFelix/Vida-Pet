package com.vida.pet.vidapet.App.Dtos;

import com.vida.pet.vidapet.Core.Domain.Entities.User;

public record UserDto(
        String username,
        String email,
        String password,
        Boolean enabled)

{

    public UserDto(User user) {
        this(user.getUsername(), user.getEmail(), user.getPassword(), user.getEnabled());
    }

}
