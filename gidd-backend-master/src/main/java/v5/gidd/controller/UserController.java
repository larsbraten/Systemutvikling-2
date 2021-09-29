package v5.gidd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import v5.gidd.entities.message.StrictIndex;
import v5.gidd.entities.message.UpdateUserModel;
import v5.gidd.entities.user.attributes.Credentials;
import v5.gidd.entities.user.attributes.Persona;
import v5.gidd.entities.user.attributes.Quality;
import v5.gidd.usecase.UserInteractor;
import v5.gidd.entities.projection.LeaderboardUserModel;
import v5.gidd.entities.token.StrictToken;
import v5.gidd.entities.message.Message;
import v5.gidd.entities.user.User;
import v5.gidd.service.UserService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Contains endpoints responsible for user interactions. Controller, i.e. the superclass,
 * is responsible for formatting http-responses with whatever status-code the service yielded in the payload.
 * Please refer to service doc for more in depth detail.
 */

@RestController
public class UserController extends Controller<UserService> {

    @Autowired
    private UserService userService;

    /**
     * Retrieves a leaderboard of the top 50 users.
     * @return The leaderboard.
     */

    @GetMapping(value = "/user/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<User>> getUser(@PathVariable long id) {
        return mapRequest(new StrictIndex(id), userService::getUserById);
    }

    @GetMapping(value = "/users/leaderboard", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<List<LeaderboardUserModel>>> getLeaderBoard(){
        return mapRequest(userService::getLeaderBoard);
    }

    /**
     * Creates a new user.
     * @param user A model of the user to register.
     * @return Request response from server.
     */

    @PostMapping("/user")
    public ResponseEntity<Message<Void>> createUser(@RequestBody User user) {
        return mapRequest(user, userService::registerUser);
    }

    /**
     * Alters some existing user.
     * @param user Target model of user.
     * @return Request response from server.
     */

    @PutMapping(value = "/user", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<Void>> modifyUser(@RequestBody User user) {
        return mapRequest(user, userService::alterUser);
    }




    @PutMapping(value = "user/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<Void>> modifyUser(@RequestBody UpdateUserModel user, @PathVariable long id) {
        return mapRequest(new StrictIndex(id), i -> userService.editUser(user, i));
    }

    /**
     * Deletes an existing user.
     * @param user Target model specifying user to delete.
     * @return Request response from server.
     */

    //TODO change body to credentials?

    @DeleteMapping(value = "/user", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<Void>> deleteUser(@RequestBody User user) {
        return mapRequest(user, userService::deleteUser);
    }

    /**
     * Verifies registered user.
     * @param key Key appertaining to user registration.
     * @return Request response from server.
     */

    @PostMapping(value = "/user/verify/{key}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<Void>> verifyUser(@PathVariable String key){
        return mapRequest(new StrictToken(key), userService::verifyUser);
    }

    @Override
    protected UserService getService() {
        return userService;
    }
}
