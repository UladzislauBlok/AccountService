package com.Company.AccountService.presentationLayer.auth;

import com.Company.AccountService.businessLayer.exception.CustomBAD_REQUEST_Exception;
import com.Company.AccountService.persistenceLayer.crudRepository.RoleRepository;
import com.Company.AccountService.presentationLayer.configAuth.JwtService;
import com.Company.AccountService.businessLayer.user.User;
import com.Company.AccountService.persistenceLayer.crudRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user;
        if (userRepository.findAll().isEmpty()) {
            user = User.builder()
                    .name(request.getName())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(new ArrayList<>(Set.of(roleRepository.findByName("ROLE_ADMINISTRATOR").orElseThrow())))
                    .build();
        } else {
            user = User.builder()
                    .name(request.getName())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(new ArrayList<>(Set.of(roleRepository.findByName("ROLE_USER").orElseThrow())))
                    .build();
        }

        if(userRepository.findByEmail(request.getEmail()).isEmpty()) {
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new CustomBAD_REQUEST_Exception("User exist!");
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void changePassword(ChangePassRequest request, Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomBAD_REQUEST_Exception("The passwords must be different!");
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }
}
