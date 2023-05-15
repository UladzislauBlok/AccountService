package com.Company.AccountService.businessLayer.adminLogic;

import com.Company.AccountService.businessLayer.employee.EmployeePayment;
import com.Company.AccountService.businessLayer.exception.CustomBAD_REQUEST_Exception;
import com.Company.AccountService.businessLayer.exception.CustomNOT_FOUND_Exception;
import com.Company.AccountService.businessLayer.logging.LogService;
import com.Company.AccountService.businessLayer.user.User;
import com.Company.AccountService.businessLayer.user.UserDTO;
import com.Company.AccountService.persistenceLayer.crudRepository.EmployeePaymentsRepository;
import com.Company.AccountService.persistenceLayer.crudRepository.RoleRepository;
import com.Company.AccountService.persistenceLayer.crudRepository.UserRepository;
import com.Company.AccountService.presentationLayer.controllerAdminOp.UpdateRoleRequest;
import com.Company.AccountService.presentationLayer.controllerAdminOp.UpdateUserAccessRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final EmployeePaymentsRepository employeePaymentsRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final LogService logService;

    public List<UserDTO> getUserList() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserDTO::new).toList();
    }

    public void deleteUser(String userEmail) {
        if (userRepository.findByEmail(userEmail).isEmpty()) {
            throw new CustomNOT_FOUND_Exception("User not found!");
        }
        var foundedUser = userRepository.findByEmail(userEmail).orElseThrow();
        if (foundedUser.getRoles().contains(roleRepository.findByName("ROLE_ADMINISTRATOR").orElseThrow())) {
            throw new CustomBAD_REQUEST_Exception("Can't remove ADMINISTRATOR role!");
        }
        List<EmployeePayment> employeePaymentList =  employeePaymentsRepository.findAllByEmployeeId(foundedUser.getId());
        employeePaymentsRepository.deleteAll(employeePaymentList);
        userRepository.delete(foundedUser);
        logService.deleteUserLog(foundedUser.getEmail());
    }

    public UserDTO updateUserRole(UpdateRoleRequest updateRoleRequest) {
        if (userRepository.findByEmail(updateRoleRequest.getUserEmail()).isEmpty()) {
            throw new CustomNOT_FOUND_Exception("User not found!");
        }
        if (roleRepository.findByName("ROLE_" + updateRoleRequest.getRole()).isEmpty()) {
            throw new CustomNOT_FOUND_Exception("Role not found!");
        }
        var foundedUser = userRepository.findByEmail(updateRoleRequest.getUserEmail()).orElseThrow();
        if (updateRoleRequest.getOperation().equals("GRANT")) {
            if (foundedUser.getRoles().contains(roleRepository.findByName("ROLE_ADMINISTRATOR").orElseThrow())
            || updateRoleRequest.getRole().equals("ADMINISTRATOR")) {
                throw new CustomBAD_REQUEST_Exception("The user cannot combine administrative and business roles!");
            }
            foundedUser.getRoles().add(0, roleRepository.findByName("ROLE_" + updateRoleRequest.getRole()).orElseThrow());
            userRepository.save(foundedUser);
            logService.grandRoleLog(foundedUser.getEmail(), updateRoleRequest.getRole());
            return new UserDTO(foundedUser);
        }
        if (updateRoleRequest.getOperation().equals("REMOVE")) {
            if (foundedUser.getRoles().contains(roleRepository.findByName("ROLE_ADMINISTRATOR").orElseThrow())) {
                throw new CustomBAD_REQUEST_Exception("Can't remove ADMINISTRATOR role!");
            }
            if (!foundedUser.getRoles().contains(roleRepository.findByName("ROLE_" + updateRoleRequest.getRole()).orElseThrow())) {
                throw new CustomBAD_REQUEST_Exception("The user does not have a role!");
            }
            if (foundedUser.getRoles().size() == 1) {
                throw new CustomBAD_REQUEST_Exception("The user must have at least one role!");
            }
            foundedUser.getRoles().remove(roleRepository.findByName("ROLE_" + updateRoleRequest.getRole()).orElseThrow());
            userRepository.save(foundedUser);
            logService.removeRoleLog(foundedUser.getEmail(), updateRoleRequest.getRole());
            return new UserDTO(foundedUser);
        }
        throw new CustomBAD_REQUEST_Exception("Unknown operation");
    }

    public void deleteUsers() {
        employeePaymentsRepository.deleteAll();
        userRepository.deleteAll();
    }

    public ResponseEntity<Map<String, String>> updateUserAccess(UpdateUserAccessRequest updateUserAccessRequest) {
        if (userRepository.findByEmail(updateUserAccessRequest.getUserEmail()).isEmpty()) {
            throw new CustomNOT_FOUND_Exception("User not found!");
        }
        var foundedUser = userRepository.findByEmail(updateUserAccessRequest.getUserEmail()).orElseThrow();
        if (foundedUser.getRoles().contains(roleRepository.findByName("ROLE_ADMINISTRATOR").orElseThrow())) {
            throw new CustomBAD_REQUEST_Exception("Can't lock the ADMINISTRATOR!");
        }
        if (updateUserAccessRequest.getOperation().equals("LOCK")) {
            foundedUser.setAccountNonLocked(false);
            userRepository.save(foundedUser);
            logService.lockUserByAdmin(foundedUser.getEmail());
            return ResponseEntity.ok(Map.of("status", "User " + foundedUser.getEmail() + " locked!"));
        } else if (updateUserAccessRequest.getOperation().equals("UNLOCK")) {
            foundedUser.setAccountNonLocked(true);
            foundedUser.setFailedAttempt(0);
            userRepository.save(foundedUser);
            logService.unlockUserByAdmin(foundedUser.getEmail());
            return ResponseEntity.ok(Map.of("status", "User " + foundedUser.getEmail() + " unlocked!"));
        }
        throw new CustomBAD_REQUEST_Exception("Unknown operation");
    }
}
