package com.vida.pet.vidapet.Core.UseCases;

import com.vida.pet.vidapet.App.Dtos.UserDto;

public interface CreateUserUseCase {
    public void save(UserDto userDto);

}
