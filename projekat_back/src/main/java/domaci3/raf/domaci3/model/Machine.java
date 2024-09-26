package domaci3.raf.domaci3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "machine")
@NamedQuery(name = "Machine.findAll", query = "SELECT m FROM Machine m")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long machineId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status = Status.STOPPED;

    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User createdBy;


    private Date destroyDate;


    private Date createDate = new Date();

    @JsonIgnore
    @OneToMany(mappedBy = "machine", fetch =  FetchType.EAGER)
    private List<ErrorMessage> errorMessageList;

    public Machine(String name) {
        this.name = name;
    }

    public Machine(){};

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDestroyDate() {
        return destroyDate;
    }

    public void setDestroyDate(Date destroyDate) {
        this.destroyDate = destroyDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<ErrorMessage> getErrorMessageList() {
        return errorMessageList;
    }

    public void setErrorMessageList(List<ErrorMessage> errorMessageList) {
        this.errorMessageList = errorMessageList;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "machineId=" + machineId +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", active=" + active +
                ", createdBy=" + createdBy +
                ", destroyDate=" + destroyDate +
                ", createDate=" + createDate +
                ", errorMessageList=" + errorMessageList +
                '}';
    }
}
