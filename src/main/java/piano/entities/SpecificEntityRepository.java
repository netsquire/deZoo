package piano.entities;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Configuration
@Component
public interface SpecificEntityRepository extends CrudRepository<SpecificEntity, String> {}
