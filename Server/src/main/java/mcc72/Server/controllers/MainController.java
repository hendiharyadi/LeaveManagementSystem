package mcc72.Server.controllers;

import lombok.AllArgsConstructor;
import mcc72.Server.models.entity.User;
import mcc72.Server.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class MainController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) throws Exception{
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
        );
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        Map<String, Object> response = new HashMap<>();
        response.put("authorities", auth.getAuthorities()
                .stream().map(authority -> authority.getAuthority())
                .collect(Collectors.toList()));
        return response;
    }

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("get-user")
    public String getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("get-roles")
    public String getRoles(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().toString();
    }
}
