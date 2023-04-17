package mcc72.Server.services;

import lombok.AllArgsConstructor;
import mcc72.Server.models.entity.Status;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class OtConfirmation {

    private TemplateEngine templateEngine;

    public String build(String firstName, Status status) {
        Context context = new Context();
        context.setVariable("first_name", firstName);
        context.setVariable("status", status);
        return templateEngine.process("otconfirmation", context);
    }
}
