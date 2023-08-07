package Client.Client.controllers;

import Client.Client.models.dto.LoginRequest;
import Client.Client.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class LoginController {

    private AuthService authService;

    @GetMapping("/login")
    public String loginView(@RequestParam(required = false) String error, LoginRequest loginRequest, Authentication auth) {
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "auth/login";
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/login")
    public String login(LoginRequest loginRequest) throws Exception {
        Boolean isLogin = authService.userSignIn(loginRequest);
        if (!isLogin) {
            return "redirect:/login?error=true";
        }
        return "redirect:/dashboard";
    }


}
