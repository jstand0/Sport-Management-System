package Users;

import org.springframework.data.jpa.repository.JpaRepository;


public interface userRepository extends JpaRepository<User, Long> {

}