package domaci3.raf.domaci3.services;

import domaci3.raf.domaci3.model.Permission;
import domaci3.raf.domaci3.model.User;
import domaci3.raf.domaci3.repository.PermissionRepository;
import domaci3.raf.domaci3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService, IService<User, Long> {

    private UserRepository userRepository;
    private PermissionRepository permissionRepository;


    @Autowired
    public UserService(UserRepository userRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.permissionRepository=permissionRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("Email " + email + " not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    /*public List<Permission> getPermissionForUser(Long id){
        Iterable<Permission> iter = permissionRepository.permAccess(id);
        List<Permission> rez = new ArrayList<Permission>();
        iter.forEach(rez::add);
        return rez;
    }*/

    public User loadUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email);
        System.out.println(user.getPermissions());
        return user;

    }


    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public void deleteById(Long userId){
        userRepository.deleteById(userId);
    }

    public Optional<User> findById(Long userId){
        return userRepository.findById(userId);
    }

    @Override
    public <S extends User> S save(S var1) {
        return userRepository.save(var1);
    }
}
