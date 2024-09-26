package domaci3.raf.domaci3.services;

import domaci3.raf.domaci3.model.Machine;
import domaci3.raf.domaci3.model.Permission;
import domaci3.raf.domaci3.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    private PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }


    public List<Permission> findAll() {
        return (List<Permission>) permissionRepository.findAll();
    }
}
