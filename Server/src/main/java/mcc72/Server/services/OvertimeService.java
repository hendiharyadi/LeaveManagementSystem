package mcc72.Server.services;

import lombok.AllArgsConstructor;
import mcc72.Server.models.dto.OvertimeDto;
import mcc72.Server.models.entity.*;
import mcc72.Server.repositories.EmployeeRepository;
import mcc72.Server.repositories.OvertimeRepository;
import mcc72.Server.repositories.ProjectRepository;
import mcc72.Server.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class OvertimeService {

    private OvertimeRepository or;
    private EmployeeRepository er;
    private OtRequest otRequest;
    private OtConfirmation otConfirmation;
    private ProjectRepository pr;
    private JavaMailSender mailSender;
    private HistoryOvertimeService hos;
    private UserRepository ur;

    public List<Overtime> getAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ur.findByUsername(authentication.getName()).get();
        return or.orderOvertime(user.getEmployee().getId());
    }

    public List<Overtime> findByManager() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ur.findByUsername(authentication.getName()).get();
        return or.findOvertimeByManager(user.getEmployee());
    }

    public Overtime getById(int id){
        return or.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }

    public Overtime create(OvertimeDto o) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ur.findByUsername(authentication.getName()).get();
        Project p = pr.findById(o.getProject_id()).get();
        Overtime overtime = new Overtime();
        overtime.setNote(o.getNote());
        overtime.setStart_overtime(o.getStart_overtime());
        overtime.setEnd_overtime(o.getEnd_overtime());
        overtime.setStatus(Status.PENDING);
        overtime.setEmployee(er.findById(user.getId()).get());
        overtime.setProject(p);
        overtime.setManager(p.getManager());
        hos.create(overtime);
        return or.save(overtime);
    }

    public Overtime update(int id, OvertimeDto o){
        Overtime overtime = or.findById(id).get();
        Status stat = o.getStatus() ? Status.APPROVED : Status.REJECTED;
        overtime.setStatus(stat);
        hos.create(overtime);
        return or.save(overtime);
    }

    public Overtime delete (int id){
        Overtime overtime = getById(id);
        or.delete(overtime);
        return overtime;
    }

    public void sendConfirmationMail(Integer id, OvertimeDto overtime) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Overtime o = or.findById(id).get();
            messageHelper.setTo(o.getEmployee().getEmail());
            messageHelper.setSubject("Overtime Confirmation email");
            String content = otConfirmation.build(o.getEmployee().getFirst_name(), overtime.getStatus()? Status.APPROVED : Status.REJECTED);
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }

    public void sendRequestMail(OvertimeDto overtime) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ur.findByUsername(authentication.getName()).get();
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Employee e = er.findById(user.getEmployee().getId()).get();
            Project p = pr.findById(overtime.getProject_id()).get();
            messageHelper.setTo(p.getManager().getEmail());
            messageHelper.setSubject("Overtime Request email");
            String content = otRequest.build(overtime.getStart_overtime(),overtime.getEnd_overtime(), overtime.getNote(), e.getFirst_name() );
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }
}
