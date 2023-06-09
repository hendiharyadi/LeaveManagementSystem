package mcc72.Server.services;

import lombok.AllArgsConstructor;
import mcc72.Server.models.entity.HistoryPermission;
import mcc72.Server.models.entity.Permission;
import mcc72.Server.models.entity.User;
import mcc72.Server.repositories.HistoryPermissionRepository;
import mcc72.Server.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class HistoryPermissionService {

    private HistoryPermissionRepository historyPermissionRepository;
    private UserRepository userRepository;

    public List<HistoryPermission> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        return historyPermissionRepository.orderHistoryPermission(user.getEmployee().getId());
    }

    public HistoryPermission getById(int id){
        return historyPermissionRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }

    public HistoryPermission create(Permission historyPermission, int id){
        HistoryPermission hp = new HistoryPermission();
        hp.setPermission(historyPermission);
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        hp.setDate_history(ts);
        hp.setEmployee(userRepository.findById(id).get().getEmployee());
        return historyPermissionRepository.save(hp);
    }

    public HistoryPermission update(int id, HistoryPermission historyPermission){
        HistoryPermission hp = historyPermissionRepository.findById(id).get();
        getById(id);
        hp.setDate_history(historyPermission.getDate_history());
        return historyPermissionRepository.save(historyPermission);
    }

    public HistoryPermission delete (int id){
        HistoryPermission historyPermission = getById(id);
        historyPermissionRepository.delete(historyPermission);
        return historyPermission;
    }

}
