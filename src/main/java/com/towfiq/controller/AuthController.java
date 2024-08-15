package com.towfiq.controller;

import com.towfiq.component.JwtUtil;
import com.towfiq.dto.AuthenticationRequestDTO;
import com.towfiq.dto.JwtResponseDTO;
import com.towfiq.dto.RefreshTokenDTO;
import com.towfiq.dto.RefreshTokenRequestDTO;
import com.towfiq.service.MyUserDetailsService;
import com.towfiq.service.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final MyUserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          MyUserDetailsService userDetailsService,
                          RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public JwtResponseDTO createAuthenticationToken(@RequestBody AuthenticationRequestDTO loginRequest) throws Exception {
        System.out.println("found: " + loginRequest.toString());
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);
        System.out.println("found: " + authenticationResponse.toString());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        RefreshTokenDTO refreshToken = refreshTokenService.createRefreshToken(loginRequest.getUsername());
        return JwtResponseDTO.builder()
                .accessToken(jwtUtil.generateToken(userDetails))
                .token(refreshToken.getToken())
                .build();
    }

    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        RefreshTokenDTO refreshTokenDTO = refreshTokenService.findByToken(refreshTokenRequestDTO.getToken());
        String accessToken = jwtUtil.generateToken(refreshTokenDTO.getUserDetails());
        return JwtResponseDTO.builder()
                .accessToken(accessToken)
                .token(refreshTokenRequestDTO.getToken())
                .build();
    }
}

