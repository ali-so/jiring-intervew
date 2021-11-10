package ir.jiring.accounting.security;

import ir.jiring.accounting.config.UserPrincipal;
import ir.jiring.accounting.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private static final Logger log = LoggerFactory.getLogger(SecurityUtils.class);

    public static boolean isAuthenticated() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return principal != null && !principal.equals("anonymousUser");
        } catch (Exception ex) {
            return false;
        }
    }

    public static User getAuthenticatedUser() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal != null && !principal.equals("anonymousUser")) {
                UserPrincipal userPrincipal = (UserPrincipal) principal;
                User user = new User(userPrincipal.getId());
                user.setUsername(userPrincipal.getUsername());

                return user;
            }
            else {
                log.info("Principal is null");
                return new User(1L);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new User(1L);
        }
    }

    public static Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Long userId = null;

        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal != null && !principal.equals("anonymousUser")) {
                UserPrincipal userPrincipal = (UserPrincipal) principal;
                userId = userPrincipal.getId();
            }
            else {
                log.info("Principal is null");
            }
        }
        else {
            log.info("Authentication is null");
        }

        return userId;
    }
}
