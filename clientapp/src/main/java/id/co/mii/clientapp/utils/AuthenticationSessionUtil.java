package id.co.mii.clientapp.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class AuthenticationSessionUtil {
    public Authentication authentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
}
