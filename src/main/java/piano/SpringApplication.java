package piano;

import examples.PianoApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.piano.reactor")
public class SpringApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringApplication.class);

    @Autowired
    private PianoApplication pianoApplication;

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
	}

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {

            System.out.println("Let's launch Piano app by Spring Boot:");
            pianoApplication.run();

        };
    }

}
