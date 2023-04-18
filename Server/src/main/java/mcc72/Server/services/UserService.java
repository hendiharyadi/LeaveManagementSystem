package mcc72.Server.services;

import lombok.AllArgsConstructor;
import mcc72.Server.models.dto.UserDto;
import mcc72.Server.models.entity.Role;
import mcc72.Server.models.entity.User;
import mcc72.Server.repositories.EmployeeRepository;
import mcc72.Server.repositories.RoleRepository;
import mcc72.Server.repositories.UserRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private EmployeeRepository employeeRepository;
    private MailContentBuilder mailContentBuilder;
    private JavaMailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found!")
                );
        if (!user.getIsActive()){
            throw new UsernameNotFoundException("User not found.");
        }
        if (user.getFailedAttempt() >= 3){
            throw new UsernameNotFoundException("Account locked. Contact support for more information.");
        }
        UserDetails ud = new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), getAuthorities());
        return ud;
    }

    public List<GrantedAuthority> getAuthorities(){
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public List<Map<String, Object>> getAllMap(){
        return userRepository.findAll().stream().map(role -> {
            Map<String, Object> m = new HashMap<>();
            m.put("userId", role.getId());
            m.put("userName", role.getUsername());
            m.put("userVerificationCode", role.getVerificationCode());
            m.put("userActivationStatus", role.getIsActive());
            m.put("userRoles", role.getUserRole());
            return m;
        }).collect(Collectors.toList());
    }

    public Map<String, Object> getLoginResponse(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> m = new HashMap<>();
        m.put("userId", userRepository.findByUsername(auth.getName()).get().getId());
        m.put("name", auth.getName());
        m.put("authorities", auth.getAuthorities().toString());
        return m;
    }

    public User insert (UserDto u){
        String verificationCode = UUID.randomUUID().toString();
        User ue = new User();
        ue.setUsername(u.getUsername());
        ue.setIsActive(false);
        ue.setVerificationCode(verificationCode);
        ue.setPassword(passwordEncoder.encode(u.getPassword()));
        ue.setUserRole(Collections.singletonList(roleRepository.findById(u.getRole_id()).get()));
        ue.setFailedAttempt(0);
        ue.setEmployee(employeeRepository.findByEmail(u.getEmail()).get());
        return userRepository.save(ue);
    }

    public void updateAttempt(User u){
        User ue = userRepository.findByUsername(u.getUsername()).get();
        userRepository.setFailedAttemptForUser(ue.getFailedAttempt() + 1, ue.getId());
    }

    public void senderVerifyMail(UserDto user){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("Verification Mail");
            String content = mailContentBuilder.build(user.getUsername());
            messageHelper.setText(content, true);
        };
        mailSender.send(messagePreparator);
    }

    public Boolean verify(String username, String token){
        String tokenDB = userRepository.findByUsername(username).get().getVerificationCode();
        int id = userRepository.findByUsername(username).get().getId();
        User user = userRepository.findByUsername(username).get();
        user.setIsActive(true);
        if(token.equalsIgnoreCase(tokenDB)){
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public List<User> getManagers(){
        Role role = roleRepository.findById(2).get();
        return userRepository.findByUserRole(role);
    }
}
