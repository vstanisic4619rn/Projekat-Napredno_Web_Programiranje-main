package domaci3.raf.domaci3.repository;

import domaci3.raf.domaci3.model.Permission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {



}
