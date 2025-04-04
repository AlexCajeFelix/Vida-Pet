package com.vida.pet.vidapet.App.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RoleDto(
        @JsonProperty("name") String name) {
}
