package domaci3.raf.domaci3.repository;

import domaci3.raf.domaci3.model.ErrorMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorMessageRepository extends JpaRepository<ErrorMessage, Long> {
}
