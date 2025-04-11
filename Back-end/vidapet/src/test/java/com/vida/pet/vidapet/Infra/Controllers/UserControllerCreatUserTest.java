package com.vida.pet.vidapet.Infra.Controllers;

import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vida.pet.vidapet.App.Dtos.UserDto;
import com.vida.pet.vidapet.Core.Domain.Entities.Roles;
import com.vida.pet.vidapet.Core.Domain.Entities.User;
import com.vida.pet.vidapet.Core.Domain.Exeptions.CreateUserNotFoundException;
import com.vida.pet.vidapet.Core.Domain.Exeptions.RoleNotFoundException;
import com.vida.pet.vidapet.Infra.Persistence.RoleRepository;
import com.vida.pet.vidapet.Infra.Persistence.UserRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import java.lang.reflect.Field;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerCreatUserTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    private User user;
    private Roles roleUser;

    @BeforeEach
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

        user.setRoles(new HashSet<>(Set.of(roleUser)));
    }

    @Test
    void createUser_WhenValidUser_ShouldReturnOk() throws Exception {
        UserDto userDto = new UserDto("testuser231", "test321@example.com", "senha123", true);

        when(userRepository.findByUsernameWithRoles(userDto.username())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(roleUser));

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/create-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("{\"id\":1,\"username\":\"username\",\"email\":\"email@gmail.com\"}"));

        verify(userRepository, times(1)).findByUsernameWithRoles(userDto.username());
        verify(userRepository, times(1)).save(any(User.class));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("testuser231", savedUser.getUsername());
        assertEquals("test321@example.com", savedUser.getEmail());
        assertTrue(savedUser.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_USER")));

    }

    @Test
    void shouldReturnConflictWhenUserAlreadyExists() throws Exception {
        UserDto userDto = new UserDto("testuser231", "test321@example.com", "senha123", true);

        when(userRepository.findByUsernameWithRoles(userDto.username())).thenReturn(Optional.of(user));

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/create-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CreateUserNotFoundException))
                .andExpect(result -> {
                    Exception resolvedException = result.getResolvedException();
                    assertNotNull(resolvedException);
                    assertTrue(resolvedException instanceof CreateUserNotFoundException);
                });

        verify(userRepository, times(1)).findByUsernameWithRoles(userDto.username());
        verify(userRepository, never()).save(any(User.class));
    }

    // deve retornar um erro de role não encontrada
    @Test
    void shouldNotCreateUserWhenRoleNotFound() throws Exception {
        UserDto userDto = new UserDto("testuser231", "test321@example.com", "senha123", true);

        when(userRepository.findByUsernameWithRoles(userDto.username())).thenReturn(Optional.empty());

        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/create-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RoleNotFoundException))
                .andExpect(result -> {
                    Exception resolvedException = result.getResolvedException();
                    assertNotNull(resolvedException);
                    assertTrue(resolvedException instanceof RoleNotFoundException);
                    assertEquals("Role não encontrada no banco!", resolvedException.getMessage());
                });

        verify(userRepository, times(1)).findByUsernameWithRoles(userDto.username());
        verify(userRepository, never()).save(any(User.class));
    }

    // banco fora do ar
    @Test
    void shouldNotCreateUserWhenDatabaseIsDown() throws Exception {
        UserDto userDto = new UserDto("testuser231", "test321@example.com", "senha123", true);

        when(userRepository.findByUsernameWithRoles(userDto.username())).thenReturn(Optional.empty());
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(roleUser));

        // Simulando erro de banco de dados
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Banco de dados fora do ar"));

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/create-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> {
                    Exception resolvedException = result.getResolvedException();
                    assertNotNull(resolvedException);
                    assertTrue(resolvedException instanceof RuntimeException);
                    assertEquals("Banco de dados fora do ar", resolvedException.getMessage());
                });

        verify(userRepository, times(1)).findByUsernameWithRoles(userDto.username());
        verify(userRepository, times(1)).save(any(User.class));
    }

}