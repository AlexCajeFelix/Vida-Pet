package com.vida.pet.vidapet.Infra.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.vida.pet.vidapet.App.Dtos.UserDto;
import com.vida.pet.vidapet.App.Dtos.UserResponseCreatedDto;
import com.vida.pet.vidapet.Core.Entities.User;
import com.vida.pet.vidapet.Core.UseCases.CreateUserUseCase;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/create-user")
@Slf4j
public class UserController {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @PostMapping
    public ResponseEntity<UserResponseCreatedDto> createUser(@RequestBody @Valid UserDto userDto,
            ServletUriComponentsBuilder uriBuilder) {
        log.info("📩 Recebendo requisição para criar usuário: {}", userDto);

        User recursoSalvo = createUserUseCase.createUser(userDto);
        log.debug("🛠 Usuário salvo no banco: {}", recursoSalvo);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(recursoSalvo.getId())
                .toUri();
        log.info("✅ Usuário criado com sucesso! ID: {}, URI: {}", recursoSalvo.getId(), uri);
        var userResponseDto = new UserResponseCreatedDto(
                recursoSalvo.getId(),
                recursoSalvo.getUsername(),
                recursoSalvo.getEmail());
        log.info("📩 Enviando resposta para o cliente: {}", userResponseDto);

        return ResponseEntity.created(uri).body(userResponseDto);

    }

    @GetMapping
    public String getUser() {
        return "User created";
    }
}
