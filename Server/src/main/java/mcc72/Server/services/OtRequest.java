package mcc72.Server.services;

import lombok.AllArgsConstructor;
import org.hibernate.sql.Template;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class OtRequest {

    private TemplateEngine templateEngine;

    public String build(String start, String end, String note, String firstName){
        Context context = new Context();
        context.setVariable("start_overtime", start);
        context.setVariable("end_overtime", end);
        context.setVariable("note", note);
        context.setVariable("first_name", firstName);
        return templateEngine.process("otrequestmail", context);
    }
}
