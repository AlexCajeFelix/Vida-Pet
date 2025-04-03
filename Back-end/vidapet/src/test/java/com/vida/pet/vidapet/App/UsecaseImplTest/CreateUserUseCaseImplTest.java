package com.vida.pet.vidapet.App.UsecaseImplTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import com.vida.pet.vidapet.App.Dtos.UserDto;
import com.vida.pet.vidapet.App.UseCaseImpl.CreateUserUseCaseImpl;
import com.vida.pet.vidapet.Core.Entities.Roles;
import com.vida.pet.vidapet.Core.Entities.User;

import com.vida.pet.vidapet.Infra.Persistence.RoleRepository;
import com.vida.pet.vidapet.Infra.Persistence.UserRepository;

public class CreateUserUseCaseImplTest {

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCaseImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;
    private User user;
    private Roles roleUser;

    @Before
    void setUp() {

        user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setEmail("email@gmail.com");
        user.setPassword("password");
        user.setEnabled(true);

        roleUser = new Roles();
        roleUser.setId(1L);
        roleUser.setName("ROLE_USER");

        user.setRoles(new HashSet<>(Set.of(roleUser)));
    }

    @Test
    public void saveUserShouldUserisvalid() {
        UserDto userDto = new UserDto("testuser231", "test321@example.com", "senha123", true);

        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(roleUser));
        when(userRepository.save(any(User.class))).thenReturn(user);

        createUserUseCaseImpl.save(userDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();

        assertTrue(capturedUser.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_USER")));

        assertEquals("testuser231", capturedUser.getUsername());

        assertNotEquals(userDto.username(), capturedUser.getUsername());

    }

}
