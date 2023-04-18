package mcc72.Server.controllers;

import lombok.AllArgsConstructor;
import mcc72.Server.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("activatedUser")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MANAGER')")
public class ActivatedUserController {

    private UserService userService;

    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER', 'READ_MANAGER')")
    @GetMapping
    public Map<String, Object> home(){
        return userService.getLoginResponse();
    }
}
