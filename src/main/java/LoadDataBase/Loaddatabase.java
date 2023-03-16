package LoadDataBase;

import Users.User;
import Users.userRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

;

@Configuration
public class Loaddatabase {

    public static final Logger log = LoggerFactory.getLogger(Loaddatabase.class);

    CommandLineRunner LoadDatabase(userRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new User("Bilbo Baggins", "burglar")));
            log.info("Preloading " + repository.save(new User("Frodo Baggins", "thief")));
        };
    }
}