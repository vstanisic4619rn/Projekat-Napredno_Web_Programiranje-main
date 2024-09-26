package domaci3.raf.domaci3.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "errorMessage")
@NamedQuery(name = "ErrorMessage.findAll", query = "SELECT e from ErrorMessage e")
public class ErrorMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long errorMessageId;

    private String message;

    private String operation;

    @ManyToOne
    @JoinColumn(name = "machineId")
    private Machine machine;

    private LocalDate errorDate = LocalDate.now();

    public ErrorMessage(){}

    public Long getErrorMessageId() {
        return errorMessageId;
    }

    public void setErrorMessageId(Long errorMessageId) {
        this.errorMessageId = errorMessageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public LocalDate getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(LocalDate errorDate) {
        this.errorDate = errorDate;
    }
}
