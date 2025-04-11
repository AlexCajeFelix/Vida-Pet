package com.vida.pet.vidapet.Core.Domain.Exeptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}
