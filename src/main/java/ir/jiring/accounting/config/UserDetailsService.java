package ir.jiring.accounting.config;

import ir.jiring.accounting.common.exceptions.ApplicationException;
import ir.jiring.accounting.model.Role;
import ir.jiring.accounting.model.User;
import ir.jiring.accounting.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private IUserService iUserService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        String lowerCaseUsername = username.toLowerCase(Locale.ENGLISH);
            User user = iUserService.loadUserByUsername(lowerCaseUsername);

            if (user == null) {
                throw new ApplicationException("Invalid username or password");
            } else {
                if (!user.isEnabled()) {
                    throw new DisabledException("User " + lowerCaseUsername + " is disabled!");
                }
            }
            user.setAuthorities(getAuthorities(user));
            return UserPrincipal.create(user);
    }

    public List<GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        for (Role role: user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getTitle()));
        }

        return grantedAuthorities;
    }
}
