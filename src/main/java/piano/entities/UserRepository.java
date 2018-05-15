package piano.entities;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Configuration
public interface UserRepository extends JpaRepository<User, Integer> { }