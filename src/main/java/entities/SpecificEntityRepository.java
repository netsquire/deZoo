package entities;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;

@Configuration
public interface SpecificEntityRepository extends CrudRepository<SpecificEntity, String> {}
