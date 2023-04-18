package mcc72.Server.services;

import lombok.AllArgsConstructor;
import mcc72.Server.models.entity.Role;
import mcc72.Server.models.entity.User;
import mcc72.Server.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User is not found.")
                );
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), getAuthorities(user.getUserRole())
        );
    }

    public Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles){
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
        System.out.println(authorities);
        return authorities;
    }
}
