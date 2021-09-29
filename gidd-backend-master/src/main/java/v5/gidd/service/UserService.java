package v5.gidd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import v5.gidd.entities.message.*;
import v5.gidd.entities.projection.LeaderboardUserModel;
import v5.gidd.entities.message.Message;
import v5.gidd.entities.projection.LeaderboardUserModel;
import v5.gidd.entities.token.StrictToken;
import v5.gidd.entities.user.User;
import v5.gidd.repo.UserRepo;
import v5.gidd.throwable.GiddException;
import v5.gidd.usecase.UserInteractor;

import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service
public class UserService extends Service<UserRepo> implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TokenService tokenService;



    public Message<User> getUserById(StrictIndex id){
        return query(id, UserInteractor::getUserById);
    }


    /**
     * Registers new user.
     * @param user User-model to register.
     * @return Request status from server.
     */

    public Message<Void> registerUser(User user) {
        return execute(user, u -> UserInteractor.registerUser(userRepo, tokenService, u));
    }

    /**
     * Alters existing user.
     * @param user User target-model.
     * @return Request status from server.
     */

    public Message<Void> alterUser(User user) {
        return execute(user, UserInteractor::alterUser);
    }


    public Message<Void> editUser(UpdateUserModel user, StrictIndex id) {
        return execute(user, u -> UserInteractor.alterUser(userRepo, u, id));
    }

    /**
     * Deletes existing user.
     * @param user Target user to delete.
     * @return Request status from server.
     */

    public Message<Void> deleteUser(User user){
        return execute(user, UserInteractor::deleteUser);
    }

    /**
     * Gets leaderboard of top 50 users.
     * @return The leaderboard.
     */

    public Message<List<LeaderboardUserModel>> getLeaderBoard() {
        return query(UserInteractor::getLeaderBoard);
    }

    /**
     * Verifies user which have clicked to supplied url in email.
     * @param strictToken Token embedded in email url.
     * @return Request status from server.
     */

    public Message<Void> verifyUser(StrictToken strictToken){
        return execute(strictToken, tokenService::verifyUser);
    }

    @Override
    protected UserRepo getRepo() {
        return userRepo;
    }

    /**
     * Return user-details for spring security in order to validate client agent.
     * @param username Username of user.
     * @return The user-details.
     * @throws UsernameNotFoundException Thrown when not found. Handled by spring security.
     */

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserInteractor.loadUserByUsername(userRepo, username);
    }

    /**
     * Get the user object belonging to the current session.
     * @return The user.
     */

    public User getThisUser(){
        return loadUserByUsername(Optional.ofNullable(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName())
                .orElseThrow(() -> new GiddException(Status.SERVICE_UNAVAILABLE, "User session not recognised")));
    }
}
