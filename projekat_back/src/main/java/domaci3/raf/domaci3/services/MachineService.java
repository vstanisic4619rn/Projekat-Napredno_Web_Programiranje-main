package domaci3.raf.domaci3.services;

import domaci3.raf.domaci3.model.Machine;
import domaci3.raf.domaci3.model.User;
import domaci3.raf.domaci3.repository.MachineRepository;
import domaci3.raf.domaci3.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MachineService {
    private MachineRepository machineRepository;
    private PermissionRepository permissionRepository;

    @Autowired
    public MachineService(MachineRepository machineRepository, PermissionRepository permissionRepository) {
        this.machineRepository = machineRepository;
        this.permissionRepository = permissionRepository;
    }

    public List<Machine> findAll(){return (List<Machine>) machineRepository.findAll();}


    public Optional<Machine> findById(Long machineId){
        return machineRepository.findById(machineId);
    }

    public List<Machine> findForUser(User user){
        List<Machine> machineList = new ArrayList<>();
        machineList.addAll(machineRepository.findAll());
        List<Machine> usersMachines = new ArrayList<>();
        for(Machine m : machineList){
            if(m.getCreatedBy().equals(user) && m.isActive()){
                usersMachines.add(m);
            }
        }
        return usersMachines;
    }
    public Machine save(Machine machine) {
        return machineRepository.save(machine);
    }

}
