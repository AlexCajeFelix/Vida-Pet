package com.vida.pet.vidapet.Core.UseCases;

import com.vida.pet.vidapet.App.Dtos.UserDto;
import com.vida.pet.vidapet.Core.Entities.User;

public interface CreateUserUseCase {
    public User save(UserDto userDto);

}
