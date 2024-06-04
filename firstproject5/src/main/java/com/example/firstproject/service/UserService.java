package com.example.firstproject.service;

import com.example.firstproject.dto.JoinRequest;
import com.example.firstproject.dto.LoginRequest;
import com.example.firstproject.entity.User;
import com.example.firstproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void join(JoinRequest req) {
        userRepository.save(req.toEntity());
    }

    public User getLoginUserById(Long userId) {
        if(userId == null) return null;

        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElse(null);

    }

    public User login(LoginRequest req) {
        Optional<User> optionalUser = userRepository.findByLoginId(req.getLoginId());

        if(optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        if(!user.getPassword().equals(req.getPassword())) {
            return null;
        }

        return user;
    }
}