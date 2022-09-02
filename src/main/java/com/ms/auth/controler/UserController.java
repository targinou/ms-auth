package com.ms.auth.controler;

import com.ms.auth.model.UserModel;
import com.ms.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list-all")
    public ResponseEntity<List<UserModel>> listarTodos() {
        return ResponseEntity.ok(userService.findAll());
    }
}
