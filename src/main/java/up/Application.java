package up;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import piano.model.person.Person;
import piano.model.department.Department;
import piano.repository.person.PersonRepository;
import piano.service.DepartmentService;
import piano.service.PersonService;

@SpringBootApplication
@ComponentScan({"piano.service", "piano.config"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Autowired
    private PersonService personService;

    @Autowired
    private DepartmentService deptService;

    @Autowired
    @Bean
    public CommandLineRunner run(PersonRepository repository) {
        return (args) -> {
            //savePersonDetails();
            System.out.println(savePersonDetails().getCity());
            System.out.println(saveDepartmentDetails().getName())   ;
            getAllPerson();
            getDepartments();
        };
    }

    public Person savePersonDetails() {
        Person person = new Person();
        person.setName("Chandra Shekhar Goka");
        person.setCity("Hyderabad");
        return personService.savePerson(person);
    }

    public Department saveDepartmentDetails() {
        Department dept = new Department();
        dept.setName("IT");
        return deptService.saveDepartment(dept);
    }

    public void getPersonDetails() {

    }

    private void getAllPerson() {
        List persons = personService.getAllPersons();
        persons.forEach(System.out::println);
    }

    private void getDepartments() {
        List depts = deptService.getAllDepartment();
        depts.forEach(System.out::println);
    }

}