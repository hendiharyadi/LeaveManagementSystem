package mcc72.Server.controllers;

import lombok.AllArgsConstructor;
import mcc72.Server.models.dto.UserDto;
import mcc72.Server.models.entity.User;
import mcc72.Server.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("user")
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
public class UserController {

    private UserService userService;

    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping
    public List<Map<String, Object>> getAllMap(){
        return userService.getAllMap();
    }

    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public User insert(@RequestBody UserDto userEntity){
        User urd = userService.insert(userEntity);
        userService.senderVerifyMail(userEntity);
        return urd;
    }

    @GetMapping("/verify/{username}/{token}")
    public String verify(@PathVariable String username,@PathVariable String token){
        Boolean isActivated = userService.verify(username,token);
        return isActivated ? "Account Activated." : "Invalid Verification Code.";
    }

    @GetMapping("/managers")
    public List<User> findManagers(){
        return userService.getManagers();
    }
}
