package com.springboot.news.service.impl;

import com.springboot.news.exception.NewsApiException;
import com.springboot.news.model.Role;
import com.springboot.news.model.User;
import com.springboot.news.payload.LoginDto;
import com.springboot.news.payload.RegisterDto;
import com.springboot.news.repository.RoleRepository;
import com.springboot.news.repository.UserRepository;
import com.springboot.news.security.JwtTokenProvider;
import com.springboot.news.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new NewsApiException(HttpStatus.BAD_REQUEST, "Ім'я користувача вже використовується");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new NewsApiException(HttpStatus.BAD_REQUEST, "Пошта вже використовується");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
        return "Користувач успішно зареєстрований";
    }
}
