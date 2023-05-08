package com.Company.AccountService.businessLayer.user;

import com.Company.AccountService.businessLayer.role.Role;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "id",
        "name",
        "lastname",
        "email",
        "roles",
})
public class UserDTO {
    private long id;
    private String name;
    private String lastname;
    private String email;
    private List<String> roles;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        roles = user.getRoles().stream()
                .map(Role::getName).collect(Collectors.toList());
    }
}
