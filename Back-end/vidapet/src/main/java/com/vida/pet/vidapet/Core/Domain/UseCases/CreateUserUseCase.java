package com.vida.pet.vidapet.Core.Domain.UseCases;

import com.vida.pet.vidapet.App.Dtos.UserDto;
import com.vida.pet.vidapet.Core.Domain.Entities.User;

public interface CreateUserUseCase {
    public User createUser(UserDto userDto);

}
