package up;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import piano.Piano;

import static org.springframework.boot.SpringApplication.run;

//@SpringBootApplication
//@ComponentScan({"examples", "piano"})
//@EntityScan("piano/model/entities")
//@EnableJpaRepositories("piano/model/entities")
public class PianoApplication {

    private static final Logger log = LoggerFactory.getLogger(PianoApplication.class);

//    @Autowired
//    private Piano piano;

	public static void main(String[] args) {
		run(PianoApplication.class, args);
	}

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {

            System.out.println("Let's play Piano app by Spring Boot:");
            //piano.play();

        };
    }

}
