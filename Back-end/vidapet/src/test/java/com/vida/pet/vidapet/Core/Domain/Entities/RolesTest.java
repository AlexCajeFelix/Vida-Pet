package com.vida.pet.vidapet.Core.Domain.Entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class RolesTest {
    @Test
    public void ShouldCreatedRoleWhenRoleIsValid() {
        Roles role = new Roles();
        role.setName("ROLE_USER");

        assertNotNull(role);
        assertEquals("ROLE_USER", role.getName());
        assertTrue(role.getUsers().isEmpty());
    }

    @Test
    public void ShouldThrowExceptionWhenRoleNameIsNull() {
        Roles role = new Roles();
        role.setName(null);

        assertThrows(IllegalArgumentException.class, () -> {
            role.validateRole();
        });

    }

    @Test
    public void ShouldThrowExceptionWhenRoleNameIsEmpty() {
        Roles role = new Roles();
        role.setName("");

        assertThrows(IllegalArgumentException.class, () -> {
            role.validateRole();
        });
    }

    @Test
    public void ShouldAddUserToRole() {
        Roles role = new Roles();
        role.setName("ROLE_USER");

        User user = new User();
        role.addUser(user);
        assertEquals(1, role.getUsers().size());
    }

}
