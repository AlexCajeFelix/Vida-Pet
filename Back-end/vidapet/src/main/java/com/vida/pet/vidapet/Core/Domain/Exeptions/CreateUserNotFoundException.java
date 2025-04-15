package com.vida.pet.vidapet.Core.Domain.Exeptions;

public class CreateUserNotFoundException extends RuntimeException {
    public CreateUserNotFoundException(final String message) {
        super(message);
    }
}
