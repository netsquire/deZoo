package piano.repository.department;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import piano.model.department.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Component
@Configuration
public interface DepartmentRepository extends CrudRepository<Department, Long>{}