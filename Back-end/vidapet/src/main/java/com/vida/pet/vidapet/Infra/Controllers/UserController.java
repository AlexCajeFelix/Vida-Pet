package com.vida.pet.vidapet.Infra.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vida.pet.vidapet.App.Dtos.UserDto;
import com.vida.pet.vidapet.Core.UseCases.CreateUserUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/create-user")
public class UserController {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @PostMapping
    public String createUser(@RequestBody @Valid UserDto userDto) {
        createUserUseCase.save(userDto);

        return "User created";

    }

    @GetMapping
    public String getUser() {
        return "User created";
    }
}
