package ir.jiring.accounting.web.rest;

import ir.jiring.accounting.dto.UserDto;
import ir.jiring.accounting.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @PostMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getUser(String firstName, String lastName, String username, String password) {
        iUserService.save(firstName, lastName, username, password);
        return "User saved.";
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public List<UserDto> getUsers() {
        return iUserService.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUser(@PathVariable Long id) {
        iUserService.deleteById(id);
        return "User Deleted.";
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateUser(@PathVariable Long id, String firstName, String lastName, String username, String password, BigDecimal amount) {
        iUserService.update(id, firstName, lastName, username, password, amount);
        return "User updated.";
    }
}
