
package com.vida.pet.vidapet.App.UseCaseImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.vida.pet.vidapet.App.Dtos.UserDto;

import com.vida.pet.vidapet.Core.Domain.Entities.Roles;
import com.vida.pet.vidapet.Core.Domain.Entities.User;

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
    void setUp() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

        user = new User();

        user.setUsername("username");
        user.setEmail("email@gmail.com");
        user.setPassword("password");
        user.setEnabled(true);

        Field idField = User.class.getSuperclass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(user, 1L);

        roleUser = new Roles();

        roleUser.setName("ROLE_USER");

        idField.set(roleUser, 1L);

        user.setRoles(new HashSet<>(Set.of(roleUser)));
    }

    @Test
    public void saveUserShouldUserisvalid() {
        UserDto userDto = new UserDto("testuser231", "test321@example.com", "senha123", true);

        when(userRepository.findByUsernameWithRoles(userDto.username())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(roleUser));

        createUserUseCaseImpl.createUser(userDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertTrue(capturedUser.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_USER")));
        assertEquals("testuser231", capturedUser.getUsername());
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    public void shouldNotSaveUserWhenUserIsInvalid() {
        UserDto userDto = new UserDto("", "invalido@example.com", "senha123", true); // Nome inválido
        when(userRepository.findByUsernameWithRoles(userDto.username())).thenReturn(Optional.empty());
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(roleUser));

        assertThrows(IllegalArgumentException.class, () -> {
            createUserUseCaseImpl.createUser(userDto);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void shouldNotSaveUserWhenEmailIsInvalid() {
        UserDto userDto = new UserDto("testuser", "invalido@example", "senha123", true); // Email inválido

        when(userRepository.findByUsernameWithRoles(userDto.username())).thenReturn(Optional.empty());
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(roleUser));

        assertThrows(IllegalArgumentException.class, () -> {
            createUserUseCaseImpl.createUser(userDto);
        });

        verify(userRepository, never()).save(any(User.class));
    }

}