package piano.repository.person;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import piano.model.person.Person;

@Repository
@Component
@Configuration
public interface PersonRepository extends CrudRepository<Person, Long>{
        }