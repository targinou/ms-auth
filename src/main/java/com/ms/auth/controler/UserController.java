package com.ms.auth.controler;

import com.ms.auth.model.UserModel;
import com.ms.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder encoder;

    public UserController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @GetMapping("/list-all")
    public ResponseEntity<List<UserModel>> listarTodos() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<UserModel> save(@RequestBody UserModel user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return ResponseEntity.ok(userService.save(user));
    }
}
