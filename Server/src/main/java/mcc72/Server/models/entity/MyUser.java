package mcc72.Server.models.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUser implements UserDetails {

    private final User user;

    public MyUser(User user){
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getUserRole()
                .forEach(
                        role -> {
                            String roleName = role.getName().toUpperCase();
                            authorities.add(new SimpleGrantedAuthority(roleName));
                            role.getPrivileges().forEach(
                                    privilege -> {
                                        String privilegeName = privilege.getName().toUpperCase();
                                        authorities.add(new SimpleGrantedAuthority(privilegeName));
                                    }
                            );
                        }
                );
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
