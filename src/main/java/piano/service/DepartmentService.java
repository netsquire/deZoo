package piano.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.List;

//@Service
//@Component
//@EntityScan("piano.model.department")
//@EnableJpaRepositories("piano.model.department")
//@ComponentScan("piano.model.department")
public class DepartmentService {

//    @Autowired
//    private DepartmentRepository departmentRepository;
//
//    public List<Department> getAllDepartment() {
//        return (List<Department>) departmentRepository.findAll();
//    }
//
//    public Department saveDepartment(Department dept) {
//        return departmentRepository.save(dept);
//    }
//
//    public Department take(Long id){
//        return departmentRepository.findById(id).orElse(new Department());
//    }
}