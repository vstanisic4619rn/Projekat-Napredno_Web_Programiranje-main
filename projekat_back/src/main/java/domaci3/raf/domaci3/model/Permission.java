package domaci3.raf.domaci3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "permission")
@NamedQuery(name = "Permission.findAll", query = "SELECT p FROM Permission p")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;

    private String permissionName;

    @ManyToMany
    @JoinTable(
            name = "users_permissions",
            joinColumns = @JoinColumn(name = "permissionId", referencedColumnName = "permissionId"),
            inverseJoinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId")
    )
    @JsonIgnore
    private List<User> users;

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return permissionName;
    }
}
