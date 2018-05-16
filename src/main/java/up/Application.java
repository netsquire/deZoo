package up;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import piano.Piano;
import piano.model.person.Person;
import piano.service.PersonService;

@SpringBootApplication
@EntityScan({"piano.model.department", "piano.model.person"})
@EnableJpaRepositories({"piano.model.department", "piano.model.person"})
@ComponentScan("piano.service")
public class Application {

    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    public static final long ID = 3L;

    public static void main(String... args) {
        SpringApplication.run(Application.class);
    }

    @Autowired
    private PersonService personService;

//    @Autowired
//    private DepartmentService deptService;

    @Autowired
    @Bean
    public CommandLineRunner commandLineRunner() {
        return (args) -> {
            //System.out.println(savePersonDetails().getCity());
            //System.out.println(saveDepartmentDetails().getName())   ;
            getAllPerson();
            //getDepartments();
        };
    }

    private Person savePersonDetails() {
        Person person = new Person();
        person.setName("Chandra Shekhar Goka");
        person.setCity("Hyderabad");
        return personService.savePerson(person);
    }

    /*private Department saveDepartmentDetails() {
        Department dept = new Department();
        dept.setName(UUID.randomUUID().toString());
        return deptService.saveDepartment(dept);
    }*/

    private void getAllPerson() {
        System.out.println("=== Persons ===");
        List<Person> persons = personService.getAllPersons();
        persons.forEach(System.out::println);
    }

    /*private void getDepartments() {
        System.out.println("=== Departments ===");
        List<Department> departmentList = deptService.getAllDepartment();
        departmentList.forEach(System.out::println);
    }*/

}