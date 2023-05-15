package com.Company.AccountService.businessLayer.logging;


import com.Company.AccountService.persistenceLayer.crudRepository.LogRepository;
import com.Company.AccountService.persistenceLayer.crudRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LogService {

    private final LogRepository logRepository;
    private final UserRepository userRepository;


    public void createUserLog(String createdUserEmail) {
        Log log = Log.builder()
                .date(new Date())
                .action("CREATE_USER")
                .subject("Anonymous")
                .object(createdUserEmail)
                .path("/api/auth/signup")
                .build();
        logRepository.save(log);
    }

    public void loginFailedLog(String email) {
        Log log = Log.builder()
                    .date(new Date())
                    .action("LOGIN_FAILED")
                    .subject(email)
                    .object("/api/auth/authenticate")
                    .path("/api/auth/authenticate")
                    .build();
        logRepository.save(log);
    }

    public void bruteForceLog(String email) {
        Log log = Log.builder()
                .date(new Date())
                .action("BRUTE_FORCE")
                .subject(email)
                .object("/api/auth/authenticate")
                .path("/api/auth/authenticate")
                .build();
        logRepository.save(log);
    }

    public void lockUserBruteForceAttackLog(String email) {
        Log log = Log.builder()
                .date(new Date())
                .action("LOCK_USER")
                .subject(email)
                .object("Lock user " + email)
                .path("/api/auth/authenticate")
                .build();
        logRepository.save(log);
    }

    public void grandRoleLog(String email, String role) {
        String adminEmail = userRepository.findById(1).orElseThrow().getEmail();
        Log log = Log.builder()
                .date(new Date())
                .action("GRANT_ROLE")
                .subject(adminEmail)
                .object("Grant role " + role + " to " + email)
                .path("/api/admin/user/role")
                .build();
        logRepository.save(log);
    }

    public void removeRoleLog(String email, String role) {
        String adminEmail = userRepository.findById(1).orElseThrow().getEmail();
        Log log = Log.builder()
                .date(new Date())
                .action("REMOVE_ROLE")
                .subject(adminEmail)
                .object("Remove role " + role + " from " + email)
                .path("/api/admin/user/role")
                .build();
        logRepository.save(log);
    }

    public void deleteUserLog(String email) {
        String adminEmail = userRepository.findById(1).orElseThrow().getEmail();
        Log log = Log.builder()
                .date(new Date())
                .action("DELETE_USER")
                .subject(adminEmail)
                .object(email)
                .path("/api/admin/user")
                .build();
        logRepository.save(log);
    }

    public void changePasswordLog(String email) {
        Log log = Log.builder()
                .date(new Date())
                .action("CHANGE_PASSWORD")
                .subject(email)
                .object(email)
                .path("/api/auth/changepass")
                .build();
        logRepository.save(log);
    }

    public void accessDeniedLog(String email, String path) {
        Log log = Log.builder()
                .date(new Date())
                .action("ACCESS_DENIED")
                .subject(email)
                .object(path)
                .path(path)
                .build();
        logRepository.save(log);
    }

    public void lockUserByAdmin(String email) {
        String adminEmail = userRepository.findById(1).orElseThrow().getEmail();
        Log log = Log.builder()
                .date(new Date())
                .action("LOCK_USER")
                .subject(adminEmail)
                .object("Lock user " + email)
                .path("/api/admin/user/access")
                .build();
        logRepository.save(log);
    }

    public void unlockUserByAdmin(String email) {
        String adminEmail = userRepository.findById(1).orElseThrow().getEmail();
        Log log = Log.builder()
                .date(new Date())
                .action("UNLOCK_USER")
                .subject(adminEmail)
                .object("Unlock user " + email)
                .path("/api/admin/user/access")
                .build();
        logRepository.save(log);
    }

    public List<Log> getLogList() {
        return logRepository.findAll();
    }
}
