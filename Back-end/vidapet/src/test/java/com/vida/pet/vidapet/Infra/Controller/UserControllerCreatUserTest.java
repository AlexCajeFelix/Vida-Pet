package com.vida.pet.vidapet.Infra.Controller;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

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
import com.vida.pet.vidapet.Core.Entities.Roles;
import com.vida.pet.vidapet.Core.Entities.User;
import com.vida.pet.vidapet.Infra.Persistence.RoleRepository;
import com.vida.pet.vidapet.Infra.Persistence.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

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

    // test de integração

    @BeforeEach
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
    void createUser_WhenValidUser_ShouldReturnOk() throws Exception {
        UserDto userDto = new UserDto("testuser231", "test321@example.com", "senha123", true);

        Mockito.when(roleRepository.findByNameIn(List.of("ROLE_USER"))).thenReturn(List.of(roleUser));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/create-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().string("User created"));

        verify(roleRepository, times(1)).findByNameIn(List.of("ROLE_USER"));
        verify(userRepository, times(1)).save(any(User.class));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("testuser231", savedUser.getUsername());
        assertEquals("test321@example.com", savedUser.getEmail());
        assertTrue(savedUser.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_USER")));

    }

}
