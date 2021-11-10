package ir.jiring.accounting.dao.interfaces;

import ir.jiring.accounting.dao.IGeneralRepository;
import ir.jiring.accounting.dto.UserDto;
import ir.jiring.accounting.model.User;

import java.util.List;

public interface IUserRepository extends IGeneralRepository<User> {
    User loadUserByUsername(String username);

    List<UserDto> getAllUsers();
}
