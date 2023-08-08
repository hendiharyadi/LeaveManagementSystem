package mcc72.Server.services;

import lombok.AllArgsConstructor;
import mcc72.Server.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailContentBuilder {

    private TemplateEngine templateEngine;
    private UserRepository userRepository;

    public String build(String username){
        String verifyCode = userRepository.findByUsername(username).get().getVerificationCode();
        String verifyLink = "http://localhost:8082/api/user/verify/" + username + "/" + verifyCode;
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("verifyLink", verifyLink);
        return templateEngine.process("mail", context);
    }
}
