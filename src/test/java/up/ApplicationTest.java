package up;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import piano.model.person.Person;
import piano.service.DepartmentService;
import piano.service.PersonService;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationTest  extends AbstractTestNGSpringContextTests {

    private static final String CHANDRA_SHEKHAR_GOKA = UUID.randomUUID().toString();
    private static final String HYDERABAD = UUID.randomUUID().toString();
    private static final String DEPARTMENT_NAME = UUID.randomUUID().toString();
    private static final long ID = 3L;

    @Autowired
    private PersonService personService;

//    @Autowired
//    private DepartmentService departmentService;

    @Test
    public void testMySQL() {
        Person person = new Person();
        person.setName(CHANDRA_SHEKHAR_GOKA);
        person.setCity(HYDERABAD);
        Person savedPerson = personService.savePerson(person);

        Assert.assertEquals(HYDERABAD, savedPerson.getCity());
        Assert.assertEquals(CHANDRA_SHEKHAR_GOKA, savedPerson.getName());
        Assert.assertEquals(personService.take(ID).getName(), "MySQL");
    }

  /*  @Test
    public void testPostgreSQL() {
        Department dept = new Department();
        dept.setName(DEPARTMENT_NAME);
        Department savedDepartment = departmentService.saveDepartment(dept);

        Assert.assertEquals(DEPARTMENT_NAME, savedDepartment.getName());
        Assert.assertEquals(departmentService.take(ID).getName(), "PgSQL");
    }*/

    @Test
    void verifyDistributedInMultipleDbsContent(){
        Assert.assertEquals(personService.take(ID).getName(), "MySQL");
        //Assert.assertEquals(departmentService.take(ID).getName(), "PgSQL");
    }

}