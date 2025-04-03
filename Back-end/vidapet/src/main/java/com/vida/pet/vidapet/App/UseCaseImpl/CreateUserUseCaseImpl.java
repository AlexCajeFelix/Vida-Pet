package com.vida.pet.vidapet.App.UseCaseImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.vida.pet.vidapet.App.Dtos.UserDto;
import com.vida.pet.vidapet.Core.Entities.Roles;
import com.vida.pet.vidapet.Core.Entities.User;
import com.vida.pet.vidapet.Core.Exeptions.RoleNotFoundException;
import com.vida.pet.vidapet.Core.UseCases.CreateUserUseCase;
import com.vida.pet.vidapet.Infra.Persistence.RoleRepository;
import com.vida.pet.vidapet.Infra.Persistence.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CreateUserUseCaseImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User save(UserDto userDto) {
        log.info(" Tratando usuario: {}", userDto);

        User user = convertUser(userDto);
        return userRepository.save(user);
    }

    @Transactional
    public User convertUser(UserDto userDto) {
        try {
            log.info(" verificando a role do usuario: {}", userDto);

            List<String> roleNames = List.of("ROLE_USER");
            Set<Roles> roles = roleRepository.findByNameIn(roleNames).stream()
                    .collect(Collectors.toCollection(HashSet::new));

            log.info(" recuperando as roles: {}", roles);

            if (roles.isEmpty()) {
                log.error("Role não existe no banco");
                throw new RoleNotFoundException("Role nao encontrada no banco");
            }

            log.info(" Criando o usuario: {}", userDto);

            User user = new User(userDto);

            log.info(" SETANDO as roles: {}", roles);

            user.setRoles(roles);

            log.info(" Usuário criado: {}", user);

            return user;

        } catch (RoleNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

}
