package mcc72.Server.services;

import lombok.AllArgsConstructor;
import mcc72.Server.models.dto.OvertimeDto;
import mcc72.Server.models.entity.HistoryOvertime;
import mcc72.Server.models.entity.Overtime;
import mcc72.Server.models.entity.User;
import mcc72.Server.repositories.HistoryOvertimeRepository;
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
public class HistoryOvertimeService {

    private HistoryOvertimeRepository historyOvertimeRepository;
    private UserRepository userRepository;

    public List<HistoryOvertime> getAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        return historyOvertimeRepository.orderHistoryPermission(user.getEmployee().getId());
    }

    public HistoryOvertime getById(int id){
        return historyOvertimeRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }

    public HistoryOvertime create(Overtime historyOvertime){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        HistoryOvertime ho = new HistoryOvertime();
        ho.setOvertime(historyOvertime);
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        ho.setDate_history(ts);
        ho.setEmployee(user.getEmployee());
        return historyOvertimeRepository.save(ho);
    }

    public HistoryOvertime update(int id, OvertimeDto historyOvertime){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        HistoryOvertime ho = getById(id);
        ho.setEmployee(user.getEmployee());
        return ho;
    }

    public HistoryOvertime delete (int id) {
        HistoryOvertime historyOvertime = getById(id);
        historyOvertimeRepository.delete(historyOvertime);
        return historyOvertime;
    }
}
