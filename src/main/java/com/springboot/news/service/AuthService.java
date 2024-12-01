package com.springboot.news.service;

import com.springboot.news.payload.LoginDto;
import com.springboot.news.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
