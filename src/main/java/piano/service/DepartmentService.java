package piano.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import piano.model.department.Department;
import piano.repository.department.DepartmentRepository;

@Service
@Component
@ComponentScan({"piano.repository.department"})
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartment() {
        return (List<Department>) departmentRepository.findAll();
    }

    public Department saveDepartment(Department dept) {
        return departmentRepository.save(dept);
    }
}