package ir.jiring.accounting.service.impl;

import ir.jiring.accounting.dao.IGeneralRepository;
import ir.jiring.accounting.dao.interfaces.IUserRepository;
import ir.jiring.accounting.dto.UserDto;
import ir.jiring.accounting.model.User;
import ir.jiring.accounting.service.GeneralService;
import ir.jiring.accounting.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService extends GeneralService<User> implements IUserService {

    @Autowired
    private IUserRepository iUserRepository;

    @Override
    protected IGeneralRepository<User> getGeneralRepository() {
        return iUserRepository;
    }

    @Override
    public User loadUserByUsername(String username) {
        return iUserRepository.loadUserByUsername(username);
    }

    @Override
    @Transactional
    public void save(String firstName, String lastName, String username, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setAmount(new BigDecimal(0));
        user.setEnabled(true);

        super.add(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return iUserRepository.getAllUsers();
    }

    @Override
    @Transactional
    public void update(Long id, String firstName, String lastName, String username, String password, BigDecimal amount) {
        User user = loadById(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setAmount(amount);
    }
}
