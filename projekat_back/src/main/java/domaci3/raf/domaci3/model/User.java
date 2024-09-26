package domaci3.raf.domaci3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@NamedQuery(name = "User.findAll", query = "SELECT u from User u")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    private String lastName;

    private String email;

    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy", fetch =  FetchType.EAGER)
    private List <Machine> machinesList;

    @ManyToMany
    @JoinTable(
            name = "users_permissions",
            joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "permissionId", referencedColumnName = "permissionId")
    )

    private List<Permission> permissions;

    public User(){}

    public User(String name, String lastName, String email, String password, List<Permission> permissions) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.permissions = permissions;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Machine> getMachinesList() {
        return machinesList;
    }

    public void setMachinesList(List<Machine> machinesList) {
        this.machinesList = machinesList;
    }

    @Override
    public String toString() {
        return "User{" +
                "permissions=" + permissions +
                '}';
    }
}