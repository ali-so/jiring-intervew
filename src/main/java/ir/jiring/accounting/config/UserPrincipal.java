package ir.jiring.accounting.config;

import ir.jiring.accounting.common.exceptions.ApplicationException;
import ir.jiring.accounting.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String email, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = email;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        if (user == null) {
            throw new ApplicationException("User not found.");
        }

        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.getAuthorities()
        );
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
