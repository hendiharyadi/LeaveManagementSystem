package mcc72.Server.services;

import lombok.AllArgsConstructor;
import mcc72.Server.models.entity.LeaveType;
import org.hibernate.sql.Template;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class PermissionMailReq {

    private TemplateEngine templateEngine;

    public String build (LeaveType leaveType, String start, String end, String note, String firstName){
        Context context = new Context();
        context.setVariable("leave_type", leaveType);
        context.setVariable("start_leave", start);
        context.setVariable("end_leave", end);
        context.setVariable("note", note);
        context.setVariable("first_name", firstName);
        return templateEngine.process("permissioneqmail", context);
    }
}
