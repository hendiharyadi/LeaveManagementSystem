package mcc72.Server.services;

import lombok.AllArgsConstructor;
import mcc72.Server.models.dto.PermissionDto;
import mcc72.Server.models.entity.*;
import mcc72.Server.repositories.EmployeeRepository;
import mcc72.Server.repositories.PermissionRepository;
import mcc72.Server.repositories.UserRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PermissionService {

    private PermissionRepository pr;
    private EmployeeRepository er;
    private ConfirmationMailBuilder confirmationMailBuilder;
    private RequestMailBuilder requestMailBuilder;
    private JavaMailSender mailSender;
    private HistoryPermissionService hps;
    private UserRepository ur;
    private PermissionMailReq pmr;
    private PermissionMailConf pmc;

    public List<Permission> getAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ur.findByUsername(authentication.getName()).get();
        return pr.orderPermission(user.getEmployee().getId());
    }

    public List<Permission> getByManager(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ur.findByUsername(authentication.getName()).get();
        return pr.findPermissionByManager(user.getEmployee());
    }

    public Permission getById(int id){
        return pr.findById(id).get();
    }

    public Permission create(PermissionDto permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ur.findByUsername(authentication.getName()).get();
        StockLeave sl = user.getEmployee().getStockLeave();
        LeaveType lt = permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN;
        Employee e = user.getEmployee();
        if (sl.getStock_available() <= 0 && permission.getLeave_type().equals(true)) {
            throw new Error("Your cuti quota has been running out. Please wait until next year.");
        } else {
            Permission permit = new Permission();
            permit.setLeave_Type(lt);
            permit.setStart_leave(permission.getStart_leave());
            permit.setEnd_leave(permission.getEnd_leave());
            permit.setNote(permission.getNote());
            permit.setStatus(Status.PENDING);
            permit.setEmployee(er.findById(user.getId()).get());
            permit.setManager(e.getManager());
            hps.create(permit, e.getId());
            return pr.save(permit);
        }
    }

    public Permission update(Integer id, PermissionDto permission){
        Permission permit = pr.findById(id).get();
        Status stat = permission.getStatus() ? Status.APPROVED : Status.REJECTED;
        permit.setStatus(stat);
        hps.create(permit, permit.getEmployee().getId());
        return pr.save(permit);
    }

    public Permission delete (Integer id){
        Permission permission = getById(id);
        pr.delete(permission);
        return permission;
    }

    public void sendConfirmationMail(Integer id, PermissionDto permission) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Permission permit = pr.findById(id).get();
            messageHelper.setTo(permit.getEmployee().getEmail());
            messageHelper.setSubject("Leave Confirmation Email");
            String content = confirmationMailBuilder.build(permit.getEmployee().getFirst_name(), permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN, permission.getStatus() ? Status.APPROVED : Status.REJECTED);
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }

    public void sendRequestMail(PermissionDto permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ur.findByUsername(authentication.getName()).get();
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Employee m = er.findById(user.getEmployee().getManager().getId()).get();
            Employee e = er.findById(user.getEmployee().getId()).get();
            messageHelper.setTo(m.getEmail());
            messageHelper.setSubject("Cuti Request Email");
            String content = requestMailBuilder.build(permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN, permission.getStart_leave(), permission.getEnd_leave(), e.getFirst_name());
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }

    public void sendConfirmationPermitMail(PermissionDto permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ur.findByUsername(authentication.getName()).get();
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Employee e = user.getEmployee();
            Permission p = pr.findById(user.getEmployee().getId()).get();
            messageHelper.setTo(e.getEmail());
            messageHelper.setSubject("Izin Confirmation email");
            String content = pmc.build(e.getFirst_name(), permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN, permission.getStatus() ? Status.APPROVED : Status.REJECTED);
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }

    public void sendRequestPermitMail(PermissionDto permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ur.findByUsername(authentication.getName()).get();
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Employee m = er.findById(user.getEmployee().getManager().getId()).get();
            Employee e = er.findById(user.getEmployee().getId()).get();
            messageHelper.setTo(m.getEmail());
            messageHelper.setSubject("Izin Request email");
            String content = pmr.build(permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN, permission.getStart_leave(), permission.getEnd_leave(), permission.getNote(), e.getFirst_name());
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }
}
