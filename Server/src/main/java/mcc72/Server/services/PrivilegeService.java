package mcc72.Server.services;

import lombok.AllArgsConstructor;
import mcc72.Server.models.entity.Privilege;
import mcc72.Server.repositories.PrivilegeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrivilegeService {

    private PrivilegeRepository privilegeRepository;

    public List<Privilege> findAll(){
        if(privilegeRepository.findAll().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data available.");
        }

        for (Privilege privilege : privilegeRepository.findAll()){
            privilege.getName();
        }
        return privilegeRepository.findAll();
    }

    public List<Map<String, Object>> getAllMap(){
        return privilegeRepository.findAll().stream().map(privilege -> {
            Map<String, Object> m = new HashMap<>();
            m.put("privilegeId", privilege.getId());
            m.put("privilegeName", privilege.getName());
            return m;
        }).collect(Collectors.toList());
    }

    public Privilege findById(Integer id){
        if(!privilegeRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data is not existed.");
        }
        return privilegeRepository.findById(id).get();
    }

    public Privilege insert (Privilege p){
        if(privilegeRepository.existsById(p.getId())){
            throw new ResponseStatusException(HttpStatus.FOUND, "Data is existed");
        }
        return privilegeRepository.save(p);
    }

    public Privilege update (Privilege p){
        if(!privilegeRepository.existsById(p.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data is not existed.");
        }
        return privilegeRepository.save(p);
    }

    public String deleteById(Integer id){
        if(!privilegeRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data is not existed.");
        }
        Privilege p = findById(id);
        privilegeRepository.deleteById(id);
        return "Delete for " + p.getName() + "has been successful.";
    }
}
