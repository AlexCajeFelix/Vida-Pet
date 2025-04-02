package com.vida.pet.vidapet.App.UseCaseImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vida.pet.vidapet.App.Dtos.UserDto;
import com.vida.pet.vidapet.Core.Entities.Roles;
import com.vida.pet.vidapet.Core.Entities.User;
import com.vida.pet.vidapet.Core.UseCases.CreateUserUseCase;
import com.vida.pet.vidapet.Infra.Persistence.RoleRepository;
import com.vida.pet.vidapet.Infra.Persistence.UserRepository;

@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CreateUserUseCaseImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(UserDto userDto) {

        List<String> roleNames = List.of("ROLE_USER");

        Set<Roles> roles = roleRepository.findByNameIn(roleNames).stream()
                .collect(Collectors.toCollection(HashSet::new));

        if (roles.isEmpty()) {
            throw new RuntimeException("Role 'ROLE_USER' not found in the database.");
        }

        User user = new User(userDto);
        user.setRoles(roles);

        userRepository.save(user);

    }

}
