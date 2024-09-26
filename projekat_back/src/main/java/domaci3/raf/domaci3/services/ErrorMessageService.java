package domaci3.raf.domaci3.services;

import domaci3.raf.domaci3.model.ErrorMessage;
import domaci3.raf.domaci3.model.User;
import domaci3.raf.domaci3.repository.ErrorMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ErrorMessageService {

    private ErrorMessageRepository errorMessageRepository;

    @Autowired
    public ErrorMessageService(ErrorMessageRepository errorMessageRepository) {
        this.errorMessageRepository = errorMessageRepository;
    }


    public List<ErrorMessage> findErrors(User user){
        List<ErrorMessage> errorList = new ArrayList<>();
        errorList.addAll(errorMessageRepository.findAll());
        List<ErrorMessage> messageList = new ArrayList<>();

        for(ErrorMessage e: errorList){
            if(e.getMachine().getCreatedBy().equals(user)){
                messageList.add(e);
            }
        }

        return messageList;
    }

    public ErrorMessage save(ErrorMessage errorMessage){
        return errorMessageRepository.save(errorMessage);
    }
}
