package LoadDataBase;

import Users.User;
import Users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    public static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
       CommandLineRunner LoadDatabase(UserRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new User()));
            log.info("Preloading " + repository.save(new User()));
        };
    }
}