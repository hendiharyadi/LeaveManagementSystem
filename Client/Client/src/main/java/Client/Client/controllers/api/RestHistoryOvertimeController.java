package Client.Client.controllers.api;

import Client.Client.models.dto.HistoryOvertimeResponse;
import Client.Client.services.HistoryOvertimeService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history/overtime")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER', 'ROLE_USER')")
public class RestHistoryOvertimeController {

    private HistoryOvertimeService hoService;

    @GetMapping
    public List<HistoryOvertimeResponse> getAll(){
        return hoService.getAll();
    }

    @GetMapping("/{id}")
    public HistoryOvertimeResponse getById(@PathVariable int id){
        return hoService.getById(id);
    }
}

