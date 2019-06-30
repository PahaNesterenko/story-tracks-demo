package com.no.company.storytracksdemo.service.impl;

import com.no.company.storytracksdemo.repository.UserRepository;
import com.no.company.storytracksdemo.model.User;
import com.no.company.storytracksdemo.service.UserService;
import com.no.company.storytracksdemo.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Override
    public String signUp(User user) {
        User dbUser = userRepository.findByEmail(user.getEmail());
        if (dbUser != null) {
            throw new RuntimeException("User already exist.");
        }
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return jwtTokenUtil.generateToken(user);
    }
}
