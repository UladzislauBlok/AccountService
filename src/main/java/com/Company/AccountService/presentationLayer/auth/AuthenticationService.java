package com.Company.AccountService.presentationLayer.auth;

import com.Company.AccountService.businessLayer.exception.CustomBAD_REQUEST_Exception;
import com.Company.AccountService.businessLayer.exception.CustomUNAUTHORIZED_Exception;
import com.Company.AccountService.businessLayer.logging.LogService;
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
    private final LogService logService;

    public AuthenticationResponse register(RegisterRequest request) {
        User user;
        if (userRepository.findAll().isEmpty()) {
            user = User.builder()
                    .name(request.getName())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(new ArrayList<>(Set.of(roleRepository.findByName("ROLE_ADMINISTRATOR").orElseThrow())))
                    .accountNonLocked(true)
                    .failedAttempt(0)
                    .build();
        } else {
            user = User.builder()
                    .name(request.getName())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(new ArrayList<>(Set.of(roleRepository.findByName("ROLE_USER").orElseThrow())))
                    .accountNonLocked(true)
                    .failedAttempt(0)
                    .build();
        }

        if(userRepository.findByEmail(request.getEmail()).isEmpty()) {
            userRepository.save(user);
            logService.createUserLog(request.getEmail());
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new CustomBAD_REQUEST_Exception("User exist!");
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow();
            user.setFailedAttempt(0);
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            final int MAX_FAILED_ATTEMPTS = 4;
            String userEmail = request.getEmail();
            logService.loginFailedLog(userEmail);
            if (userRepository.findByEmail(userEmail).isPresent()) {
                User user = userRepository.findByEmail(userEmail).orElseThrow();
                if (!user.getRoles().contains(roleRepository.findByName("ROLE_ADMINISTRATOR").orElseThrow())) {
                    if (user.getFailedAttempt() < MAX_FAILED_ATTEMPTS) {
                        user.setFailedAttempt(user.getFailedAttempt() + 1);
                        userRepository.save(user);
                    } else {
                        user.setAccountNonLocked(false);
                        userRepository.save(user);
                        logService.bruteForceLog(userEmail);
                        logService.lockUserBruteForceAttackLog(userEmail);
                    }
                }
            }
            throw new CustomUNAUTHORIZED_Exception("Incorrect authorization data");
        }
    }

    public void changePassword(ChangePassRequest request, Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomBAD_REQUEST_Exception("The passwords must be different!");
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        logService.changePasswordLog(user.getEmail());
    }
}
