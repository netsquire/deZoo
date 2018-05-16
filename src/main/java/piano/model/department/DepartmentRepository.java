package piano.model.department;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

//@Service
public interface DepartmentRepository extends CrudRepository<Department, Long>{}