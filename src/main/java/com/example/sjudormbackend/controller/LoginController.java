package com.example.sjudormbackend.controller;
import com.example.sjudormbackend.dto.LoginRequest;
import com.example.sjudormbackend.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/login")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;


    @PostMapping("/perform-login")
    public String performLogin(@RequestBody LoginRequest loginRequest) {
        // 로그인 URL
        String loginUrl = "https://happydorm.sejong.ac.kr/00/0000_login.kmc";

        return loginService.performLogin(loginUrl, loginRequest);
    }
}