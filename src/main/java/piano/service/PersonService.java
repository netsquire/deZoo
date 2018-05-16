package piano.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import piano.model.person.Person;
import piano.model.person.PersonRepository;

import java.util.List;

@Service
@EntityScan("piano.model.person")
@EnableJpaRepositories("piano.model.person")
@ComponentScan("piano.model.person")
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAllPersons() {
        return (List<Person>) personRepository.findAll();
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public Person take(long id) {
        return personRepository.findById(id).orElse(new Person());
    }
}