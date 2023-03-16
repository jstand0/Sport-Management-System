package Users;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class userController {

    public final userRepository repository;

    userController(userRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value="/users",method = {RequestMethod.GET})
    List<User> all() {
        return repository.findAll();
    }

    @RequestMapping(value="/user/post",method = {RequestMethod.POST})
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @RequestMapping(value="/user/{id}",method = {RequestMethod.GET})
    User one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @RequestMapping(value="/user/{id}", method = {RequestMethod.PUT})
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(User -> {
                    User.setName(newUser.getName());
                    User.setRole(newUser.getRole());
                    return repository.save(User);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @RequestMapping(value="/user/{id}",headers = "key=val",method = {RequestMethod.DELETE})
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}