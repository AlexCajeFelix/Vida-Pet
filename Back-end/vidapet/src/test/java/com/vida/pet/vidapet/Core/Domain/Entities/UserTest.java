package com.vida.pet.vidapet.Core.Domain.Entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import com.vida.pet.vidapet.App.Dtos.UserDto;
import com.vida.pet.vidapet.App.Enum.RoleEnum;

public class UserTest {

    @Test
    public void shouldCreateUserWhenUserIsValid() {
        UserDto userDto = new UserDto("username", "email@gmail.com", "password", true);
        User user = new User(userDto);

        Roles roleUser = new Roles(RoleEnum.ROLE_USER);
        roleUser.setName("ROLE_USER");

        user.setRoles(new HashSet<>(Arrays.asList(roleUser)));

        assertNotNull(user);
        assertEquals("username", user.getUsername());
        assertEquals("email@gmail.com", user.getEmail());

        assertTrue(user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_USER")));
    }

    @Test
    public void shouldDeactivateUser() {
        UserDto userDto = new UserDto("username", "email@gmail.com", "password", true);
        User user = new User(userDto);
        user.deactiveUser();
        assertEquals(false, user.getEnabled());
    }

    @Test
    void shouldDeactivateUserWhenUserIsDisabled() {
        UserDto userDto = new UserDto("username", "email@gmail.com", "password", false);
        User user = new User(userDto);

        assertEquals(false, user.getEnabled());
    }

}
