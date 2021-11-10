package ir.jiring.accounting.service.interfaces;

import ir.jiring.accounting.dto.UserDto;
import ir.jiring.accounting.model.User;
import ir.jiring.accounting.service.IGeneralService;

import java.math.BigDecimal;
import java.util.List;

public interface IUserService extends IGeneralService<User> {
    User loadUserByUsername(String username);

    void save(String firstName, String lastName, String username, String password);

    List<UserDto> getAllUsers();

    void update(Long id, String firstName, String lastName, String username, String password, BigDecimal amount);
}
