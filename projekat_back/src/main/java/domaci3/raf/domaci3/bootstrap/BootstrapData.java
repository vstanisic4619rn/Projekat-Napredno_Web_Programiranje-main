package domaci3.raf.domaci3.bootstrap;

import domaci3.raf.domaci3.model.Machine;
import domaci3.raf.domaci3.model.Permission;
import domaci3.raf.domaci3.model.Status;
import domaci3.raf.domaci3.model.User;
import domaci3.raf.domaci3.repository.MachineRepository;
import domaci3.raf.domaci3.repository.PermissionRepository;
import domaci3.raf.domaci3.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BootstrapData implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final MachineRepository machineRepository;
    private final PasswordEncoder passwordEncoder;

    public BootstrapData(PermissionRepository permissionRepository, MachineRepository machineRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.permissionRepository = permissionRepository;
        this.machineRepository = machineRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        String[] permissionList = {"can_read_users", "can_create_users", "can_update_users", "can_delete_users", "can_search_machines", "can_start_machines", "can_stop_machines", "can_restart_machines", "can_create_machines", "can_destroy_machines"};

        List<Permission> permissions = new ArrayList<>();
        List<Permission> permissions2 = new ArrayList<>();

        for(int i = 0; i< permissionList.length;i++){
            Permission permission = new Permission();
            permission.setPermissionName(permissionList[i]);
            permissions.add(permission);
            permissions2.add(permission);

        }

        System.out.println(permissionRepository.saveAll(permissions));
        User u = new User();
        u.setEmail("user1");
        u.setName("User");
        u.setLastName("Using");
        u.setPassword(this.passwordEncoder.encode("user1"));
        u.setPermissions(permissions);
        this.userRepository.save(u);
        User u2 = new User();
        u2.setEmail("user2");
        u2.setName("User2");
        u2.setLastName("user2");
        u2.setPassword(this.passwordEncoder.encode("user2"));
        u2.setPermissions(permissions2);
        this.userRepository.save(u2);
        System.out.println(u2.getPermissions());
        //List<Permission> list  = permissionRepository.permAccess(u.getUserId());
        //System.out.println(list);
        System.out.println("Data loaded!");

        Machine m = new Machine("Traktor");
        m.setCreatedBy(u2);
        this.machineRepository.save(m);
    }
}
