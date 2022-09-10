package com.ms.auth.service;

import com.ms.auth.data.UserDetailData;
import com.ms.auth.model.UserModel;
import com.ms.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserModel> user = userRepository.findByLogin(username);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User [" + username + "] not found.");
        }

        return new UserDetailData(user);
    }

}
