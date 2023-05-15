package com.Company.AccountService.presentationLayer.controllerAdminOp;

import com.Company.AccountService.businessLayer.adminLogic.AdminService;
import com.Company.AccountService.businessLayer.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class adminController {

    private final AdminService adminService;

    @GetMapping("/admin/user/")
    public List<UserDTO> getUserList() {
        return adminService.getUserList();
    }
    
    @DeleteMapping("/admin/user/{userEmail}")
    public ResponseEntity<Map<String , String>> deleteUser(@PathVariable String userEmail) {
            adminService.deleteUser(userEmail);
            return ResponseEntity.ok(Map.of("user", userEmail, "status", "Deleted successfully!"));
    }

    @DeleteMapping("/admin/user/")
    public ResponseEntity<Map<String , String>> deleteUser() {
        adminService.deleteUsers();
        return ResponseEntity.ok(Map.of("status", "Deleted successfully!"));
    }

    @PutMapping("/admin/user/role")
    public UserDTO updateUserRole(@RequestBody UpdateRoleRequest updateRoleRequest) {
        return adminService.updateUserRole(updateRoleRequest);
    }

    @PutMapping("/admin/user/access")
    public ResponseEntity<Map<String , String>> updateUserAccess(
            @RequestBody UpdateUserAccessRequest updateUserAccessRequest) {
        return adminService.updateUserAccess(updateUserAccessRequest);
    }
}
