package com.vida.pet.vidapet.Core.Exeptions;

public class CreateUserNotFoundException extends RuntimeException {
    public CreateUserNotFoundException(String message) {
        super(message);
    }
}
