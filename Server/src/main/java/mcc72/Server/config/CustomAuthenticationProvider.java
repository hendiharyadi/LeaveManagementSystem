package mcc72.Server.config;

import lombok.AllArgsConstructor;
import mcc72.Server.models.entity.Role;
import mcc72.Server.models.entity.User;
import mcc72.Server.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Optional<User> optional = userRepository.findByUsername(username);
        if (optional.isPresent()) {
            User user = optional.get();
            if (!user.getIsActive()) {
                throw new UsernameNotFoundException("User not activated!!!");
            }
            if (user.getFailedAttempt() >= 3) {
                throw new BadCredentialsException("Account locked. Contact developer for more information.");
            }
            if (passwordEncoder.matches(password, user.getPassword())) {
                List<GrantedAuthority> authorities = getAuthorities(user.getUserRole());
                authorities.add(new SimpleGrantedAuthority(user.getUserRole().get(0).getName()));
//                 ur.setFailedAttemptForUser(0, user.getId());
                return new UsernamePasswordAuthenticationToken(username, password, authorities);
            } else {
                userRepository.setFailedAttemptForUser(user.getFailedAttempt() + 1, user.getId());
                System.out.println("updateAttempt success");
                throw new BadCredentialsException("Invalid password");
            }
        } else {
            throw new BadCredentialsException("No user registered with this details");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public List<GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles
                .forEach(
                        role -> {
                            authorities.add(new SimpleGrantedAuthority(role.getName()));
                            role.getPrivileges().forEach(
                                    privilege -> {
                                        authorities.add(new SimpleGrantedAuthority(privilege.getName()));
                                    }
                            );
                        }
                );
        return authorities;
    }
}
