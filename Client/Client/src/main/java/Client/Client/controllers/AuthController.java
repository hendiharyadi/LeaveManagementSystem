package Client.Client.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping("/login")
    public String login(){
        return "loginForm";
    }
}
