package com.ms.auth.controler;

import com.ms.auth.model.UserModel;
import com.ms.auth.service.RabbitMqService;
import com.ms.auth.service.UserService;
import org.example.dto.EmailDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final RabbitMqService rabbitMqService;

    public UserController(UserService userService, PasswordEncoder encoder, RabbitMqService rabbitMqService) {
        this.userService = userService;
        this.encoder = encoder;
        this.rabbitMqService = rabbitMqService;
    }

    @GetMapping("/list-all")
    public ResponseEntity<List<UserModel>> listarTodos() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<UserModel> save(@RequestBody UserModel user) {

        EmailDTO emailDTO = new EmailDTO("Emanuel Targino",
                "emanueltargino1@gmail.com",
                user.getLogin(),
                "Novo usuário",
                "Caro, "+ user.getName() + ".\n\nSegue o login e senha da sua conta recém registrada. \nLogin: " + user.getLogin() + "\nSenha: " + user.getPassword());


        user = userService.save(user);

        this.rabbitMqService.sendMessage("ms.email", emailDTO);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/validate-password")
    public ResponseEntity<Boolean> validatePassword(@RequestParam String login,
                                                @RequestParam String password) {

        Optional<UserModel> optUser = userService.findByLogin(login);
        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        boolean valid = false;
        valid = encoder.matches(password, optUser.get().getPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(valid);

    }

}
