package com.ms.auth.service;

import com.ms.auth.exception.ValidationException;
import com.ms.auth.model.UserModel;
import com.ms.auth.repository.UserRepository;
import org.example.helper.ValidateEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public Optional<UserModel> findByLogin (String login) {
        return userRepository.findByLogin(login);
    }

    public List<UserModel> findAll () {
        return userRepository.findAll();
    }

    public UserModel save(UserModel user) {

        if (user.getLogin() == null) {
            throw new ValidationException("Email must be informed.");
        }

        if (!ValidateEmail.isValidEmailAddressRegex(user.getLogin())){
            throw new ValidationException("Invalid email address.");
        }

        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new ValidationException("Email already in use.");
        }

        if (user.getPassword() == null) {
            throw new ValidationException("Password must be informed.");
        }

        if (user.getPassword().length() < 6) {
            throw new ValidationException("Password must be at least 6 characters.");
        }

        if (user.getName() == null) {
            throw new ValidationException("Name must be informed.");
        }

        if (user.getName().length() < 2) {
            throw new ValidationException("Name must be at least 2 characters.");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
