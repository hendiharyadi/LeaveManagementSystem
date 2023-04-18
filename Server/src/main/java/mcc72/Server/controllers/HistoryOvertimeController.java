package mcc72.Server.controllers;

import lombok.AllArgsConstructor;
import mcc72.Server.models.entity.HistoryOvertime;
import mcc72.Server.services.HistoryOvertimeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/history-overtime")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER', 'ROLE_USER')")
public class HistoryOvertimeController {

    private HistoryOvertimeService historyOvertimeService;

    @GetMapping()
    public List<HistoryOvertime> getHistoryOvertimes(){
        return historyOvertimeService.getAll();
    }

    @GetMapping("/{id}")
    public HistoryOvertime getDetail(@PathVariable int id){
        return historyOvertimeService.getById(id);
    }
}
