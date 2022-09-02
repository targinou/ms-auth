package com.ms.auth.service;

import com.ms.auth.model.UserModel;
import com.ms.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<UserModel> findByLogin (String login) {
        return userRepository.findByLogin(login);
    }

    public List<UserModel> findAll () {
        return userRepository.findAll();
    }

}
