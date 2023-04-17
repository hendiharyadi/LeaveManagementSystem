package mcc72.Server.services;

import lombok.AllArgsConstructor;
import mcc72.Server.models.entity.LeaveType;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class RequestMailBuilder {

    private TemplateEngine templateEngine;

    public String build(LeaveType leaveType, String startLeave, String endLeave, String employee){
        Context context = new Context();
        context.setVariable("leave_type", leaveType);
        context.setVariable("start_leave", startLeave);
        context.setVariable("end_leave", endLeave);
        context.setVariable("first_name", employee);
        return templateEngine.process("requestmail", context);
    }
}
