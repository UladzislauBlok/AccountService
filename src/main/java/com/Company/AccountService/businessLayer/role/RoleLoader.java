package com.Company.AccountService.businessLayer.role;

import com.Company.AccountService.persistenceLayer.crudRepository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleLoader {
    private final RoleRepository repository;

    @Autowired
    public RoleLoader(RoleRepository repository) {
        this.repository = repository;
        createRoles();
    }
    private void createRoles() {
        try {
            if (repository.findByName("ROLE_ADMINISTRATOR").isEmpty()) {
                repository.save(new Role("ROLE_ADMINISTRATOR"));
                repository.save(new Role("ROLE_USER"));
                repository.save(new Role("ROLE_ACCOUNTANT"));
            }
        } catch (Exception e) {

        }
    }
}
