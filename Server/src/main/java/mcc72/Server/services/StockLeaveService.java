package mcc72.Server.services;

import lombok.AllArgsConstructor;
import mcc72.Server.models.dto.UserDto;
import mcc72.Server.models.entity.StockLeave;
import mcc72.Server.models.entity.User;
import mcc72.Server.repositories.EmployeeRepository;
import mcc72.Server.repositories.StockLeaveRepository;
import mcc72.Server.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class StockLeaveService {

    private StockLeaveRepository stockLeaveRepository;
    private EmployeeRepository employeeRepository;
    private UserRepository userRepository;

    public List<StockLeave> getAll(){
        return stockLeaveRepository.findAll();
    }

    public StockLeave getById(int id){
        return stockLeaveRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }

    public StockLeave create(UserDto stockLeave){
        StockLeave sl = new StockLeave();
        sl.setStock_available(12);
        sl.setEmployee(employeeRepository.findByEmail(stockLeave.getEmail()).get());
        return stockLeaveRepository.save(sl);
    }

    public StockLeave update(int id, StockLeave stockLeave){
        getById(id);
        stockLeave.setId(id);
        return stockLeaveRepository.save(stockLeave);
    }

    public StockLeave delete (int id){
        StockLeave stockLeave = getById(id);
        stockLeaveRepository.delete(stockLeave);
        return stockLeave;
    }

    public void updateCuti(int id, Integer permission) {
        User user = userRepository.findById(id).get();
        employeeRepository.setStockLeave(user.getEmployee().getStockLeave().getStock_available() - permission, user.getEmployee().getId());
    }
}
