package piano.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import piano.model.person.Person;
import piano.repository.person.PersonRepository;

import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Component
@ComponentScan({"piano.repository.person"})
public class PersonService {

    @Autowired
    //@Qualifier("person")
    private PersonRepository personRepository;

    public List getAllPersons() {
        return (List) personRepository.findAll();
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }
}