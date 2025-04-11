package com.vida.pet.vidapet.App.UseCaseImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;
import com.vida.pet.vidapet.App.Dtos.UserDto;
import com.vida.pet.vidapet.App.Enum.RoleEnum;
import com.vida.pet.vidapet.App.Mapper.UserMapper;
import com.vida.pet.vidapet.Core.Domain.Entities.Roles;
import com.vida.pet.vidapet.Core.Domain.Entities.User;
import com.vida.pet.vidapet.Core.Domain.Exeptions.CreateUserNotFoundException;
import com.vida.pet.vidapet.Core.Domain.Exeptions.RoleNotFoundException;
import com.vida.pet.vidapet.Core.Domain.UseCases.CreateUserUseCase;
import com.vida.pet.vidapet.Infra.Persistence.RoleRepository;
import com.vida.pet.vidapet.Infra.Persistence.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public CreateUserUseCaseImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;

    }

    @Transactional
    @Override
    public User createUser(UserDto userDto) {
        verifiedUserExists(userDto);
        User user = convertUser(userDto);
        log.info("Usuário criado com sucesso: {}", user);
        return userRepository.save(user);

    }

    public User convertUser(UserDto userDto) {
        Roles roleUser = roleRepository.findByName(RoleEnum.ROLE_USER.name())
                .orElseThrow(() -> new RoleNotFoundException("Role não encontrada no banco!"));
        User user = userMapper.toEntity(userDto, new HashSet<>(Set.of(roleUser)));
        user.setRoles(new HashSet<>(Set.of(roleUser)));
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            log.error("Usuário sem role: {}", user);
            throw new CreateUserNotFoundException("Usuário sem role: " + user);
        }

        return user;
    }

    private void verifiedUserExists(UserDto userDto) {

        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            userRepository.findByUsernameWithRoles(userDto.username())
                    .ifPresent(user -> {
                        log.warn("Usuário '{}' já cadastrado!", userDto.username());
                        throw new CreateUserNotFoundException("Usuário já cadastrado!");
                    });
        });

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            userRepository.findByEmailWithRoles(userDto.email())
                    .ifPresent(user -> {
                        log.warn("Usuário '{}' já cadastrado!", userDto.email());
                        throw new CreateUserNotFoundException("Usuário já cadastrado!");
                    });
        });

        try {
            CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2);
            allOf.join();
        } catch (Exception e) {
            if (e.getCause() instanceof CreateUserNotFoundException) {
                throw (CreateUserNotFoundException) e.getCause();
            }
            throw new RuntimeException(e);
        }
    }

}
