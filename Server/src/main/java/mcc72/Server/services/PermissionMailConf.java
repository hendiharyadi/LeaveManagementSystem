package mcc72.Server.services;

import lombok.AllArgsConstructor;
import mcc72.Server.models.entity.LeaveType;
import mcc72.Server.models.entity.Status;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class PermissionMailConf {

    private TemplateEngine templateEngine;

    public String build(String firstName, LeaveType leaveType, Status status){
        Context context = new Context();
        context.setVariable("first_name", firstName);
        context.setVariable("leave_type", leaveType);
        context.setVariable("status", status);
        return templateEngine.process("permissionconfmail", context);
    }
}
