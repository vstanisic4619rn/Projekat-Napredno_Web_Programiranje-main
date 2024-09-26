package domaci3.raf.domaci3.controllers;

import domaci3.raf.domaci3.model.Permission;
import domaci3.raf.domaci3.model.User;
import domaci3.raf.domaci3.requests.CreateRequest;
import domaci3.raf.domaci3.requests.UpdateRequest;
import domaci3.raf.domaci3.services.PermissionService;
import domaci3.raf.domaci3.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CRUDUserController {

    private final UserService userService;
    private final PermissionService permissionService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public CRUDUserController(UserService userService, PermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody CreateRequest usr) {
        try {
            boolean flag = true;
            if (checkPermissions("can_update_users") == 0) {
                return ResponseEntity.status(403).build();
            }

            if (usr.getEmail() != "" && usr.getLastName() != ""
                    && usr.getName() != "" && usr.getPassword() != "") {
                System.out.println(usr.getEmail() + "---------------------------------");
                for(User u : userService.findAll())
                    if(u.getEmail().equals(usr.getEmail())) flag= false;
                if(flag) {
                    List<Permission> perm = new ArrayList<>();
                    for (Permission p : permissionService.findAll()) {
                        for (String s : usr.getPermissions()) {
                            if (p.getPermissionName().equals(s)) {
                                perm.add(p);
                            }
                        }
                    }
                    String pw = passwordEncoder.encode(usr.getPassword());
                    User u = new User(usr.getName(), usr.getLastName(), usr.getEmail(), pw, perm);
                    userService.save(u);

                    return ResponseEntity.ok(usr);
                }else{
                    return ResponseEntity.status(400).build();
                }
            } else
                return ResponseEntity.status(400).build();
            //return ResponseEntity.ok(200);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).build();
        }


    }

    @GetMapping("/loadUsers")
    public ResponseEntity<?> loadAllUsers() {
        if (checkPermissions("can_read_users") == 0) {
            return ResponseEntity.status(403).build();
        } else {
            return ResponseEntity.ok(userService.findAll());
        }
    }


    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody UpdateRequest usr){
        try {
            boolean flag = true;
            if (checkPermissions("can_update_users") == 0) {
                return ResponseEntity.status(403).build();
            }

            if (usr.getEmail() != "" && usr.getLastName() != ""
                    && usr.getName() != "" && usr.getPassword() != "") {

                if(flag) {
                    List<Permission> perm = new ArrayList<>();
                    for (Permission p : permissionService.findAll()) {
                        for (String s : usr.getPermissions()) {
                            if (p.getPermissionName().equals(s)) {
                                perm.add(p);
                            }
                        }
                    }

                    Optional<User> optionalUser = userService.findById(usr.getUserId());
                    if(optionalUser.isPresent()) {
                        User u = optionalUser.get();
                        u.setName(usr.getName());
                        u.setEmail(usr.getEmail());
                        u.setLastName(usr.getLastName());
                        u.setPermissions(perm);
                        userService.save(u);
                        return ResponseEntity.ok(u);
                    }return ResponseEntity.notFound().build();


                }else{
                    return ResponseEntity.status(400).build();
                }
            } else
                return ResponseEntity.status(400).build();
            //return ResponseEntity.ok(200);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping(path = "/deleteUser/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        if (checkPermissions("can_delete_users") == 0) {
            return ResponseEntity.status(403).build();
        }
        userService.deleteById(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserFromTable(@PathVariable Long userId){
        Optional<User> optionalUser = userService.findById(userId);
        System.out.println(optionalUser);
        if(optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping(path="/permissions")
    public ResponseEntity<?> getPermissions(){
        try {
            UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.loadUserByEmail(u.getUsername());
            System.out.println(user.getPermissions());
            return ResponseEntity.ok(user.getPermissions());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).build();
        }
    }


    public int checkPermissions(String permission){
        UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.loadUserByEmail(u.getUsername());

        List<Permission> permList = user.getPermissions();
        for (Permission p : permList) {
            System.out.println(p.getPermissionName() +"/n");
            if (p.getPermissionName().equals(permission)) {
                return 1;
            }
        }
        return 0;
    }
}
