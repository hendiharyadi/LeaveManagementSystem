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

    private PermissionRepository permissionRepository;
    private EmployeeRepository employeeRepository;
    private ConfirmationMailBuilder confirmationMailBuilder;
    private RequestMailBuilder requestMailBuilder;
    private JavaMailSender mailSender;
    private HistoryPermissionService historyPermissionService;
    private UserRepository userRepository;
    private PermissionMailReq permissionMailReq;
    private PermissionMailConf permissionMailConf;

    public List<Permission> getAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        return permissionRepository.orderPermission(user.getEmployee().getId());
    }

    public List<Permission> getByManager(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        return permissionRepository.findPermissionByManager(user.getEmployee());
    }

    public Permission getById(int id){
        return permissionRepository.findById(id).get();
    }

    public Permission create(PermissionDto permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        StockLeave sl = user.getEmployee().getStockLeave();
        LeaveType lt = permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN;
        Employee e = user.getEmployee();
        if (sl.getStock_available() <= 0 && permission.getLeave_type().equals(true)) {
            throw new Error("Your cuti quota has been running out. Please wait until next year.");
        } else {
            Permission permit = new Permission();
            permit.setLeave_type(lt);
            permit.setStart_leave(permission.getStart_leave());
            permit.setEnd_leave(permission.getEnd_leave());
            permit.setNote(permission.getNote());
            permit.setStatus(Status.PENDING);
            permit.setEmployee(employeeRepository.findById(user.getId()).get());
            permit.setManager(e.getManager());
            historyPermissionService.create(permit, e.getId());
            return permissionRepository.save(permit);
        }
    }

    public Permission update(Integer id, PermissionDto permission){
        Permission permit = permissionRepository.findById(id).get();
        Status stat = permission.getStatus() ? Status.APPROVED : Status.REJECTED;
        permit.setStatus(stat);
        historyPermissionService.create(permit, permit.getEmployee().getId());
        return permissionRepository.save(permit);
    }

    public Permission delete (Integer id){
        Permission permission = getById(id);
        permissionRepository.delete(permission);
        return permission;
    }

    public void sendConfirmationMail(Integer id, PermissionDto permission) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Permission permit = permissionRepository.findById(id).get();
            messageHelper.setTo(permit.getEmployee().getEmail());
            messageHelper.setSubject("Leave Confirmation Email");
            String content = confirmationMailBuilder.build(permit.getEmployee().getFirst_name(), permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN, permission.getStatus() ? Status.APPROVED : Status.REJECTED);
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }

    public void sendRequestMail(PermissionDto permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Employee m = employeeRepository.findById(user.getEmployee().getManager().getId()).get();
            Employee e = employeeRepository.findById(user.getEmployee().getId()).get();
            messageHelper.setTo(m.getEmail());
            messageHelper.setSubject("Cuti Request Email");
            String content = requestMailBuilder.build(permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN, permission.getStart_leave(), permission.getEnd_leave(), e.getFirst_name());
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }

    public void sendConfirmationPermitMail(PermissionDto permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Employee e = user.getEmployee();
            Permission p = permissionRepository.findById(user.getEmployee().getId()).get();
            messageHelper.setTo(e.getEmail());
            messageHelper.setSubject("Izin Confirmation email");
            String content = permissionMailConf.build(e.getFirst_name(), permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN, permission.getStatus() ? Status.APPROVED : Status.REJECTED);
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }

    public void sendRequestPermitMail(PermissionDto permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            Employee m = employeeRepository.findById(user.getEmployee().getManager().getId()).get();
            Employee e = employeeRepository.findById(user.getEmployee().getId()).get();
            messageHelper.setTo(m.getEmail());
            messageHelper.setSubject("Izin Request email");
            String content = permissionMailReq.build(permission.getLeave_type() ? LeaveType.CUTI : LeaveType.IZIN, permission.getStart_leave(), permission.getEnd_leave(), permission.getNote(), e.getFirst_name());
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }
}
