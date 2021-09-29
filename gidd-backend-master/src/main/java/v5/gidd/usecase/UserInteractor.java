package v5.gidd.usecase;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import v5.gidd.entities.message.StrictIndex;

import v5.gidd.entities.projection.LeaderboardUserModel;

import v5.gidd.entities.message.Status;
import v5.gidd.entities.message.UpdateUserModel;
import v5.gidd.entities.user.User;
import v5.gidd.repo.UserRepo;
import v5.gidd.service.TokenService;
import v5.gidd.throwable.GiddException;

import java.util.List;

public class UserInteractor {

    /**
     * Register new user. Then sends verification email to user.
     * @param userRepo The user-repo to persist user.
     * @param tokenService The token-service necessary to relay information for user verification email sending.
     * @param user The user to register.
     * @throws GiddException if user already exists.
     */
    
    public static void registerUser(UserRepo userRepo, TokenService tokenService, User user){

        // Check if user exists in datastore
        if(userRepo.findUserByCredentialsEmail(user.getCredentials().getEmail()).isPresent()){
            throw new GiddException(Status.REGISTRATION_ERROR,"User already exists");
        }

        user.getCredentials().setPassword(new BCryptPasswordEncoder().encode(user.getCredentials().getPassword()));


        //user.setActive(true);
        // Save user to datastore
        userRepo.save(user);


        // Send verification to user. Might throw GiddException.
        tokenService.sendVerificationEmail(UserInteractor.loadUserByUsername(userRepo, user.getUsername()));
    }

    /**
     * Alters existing user.
     * @param userRepo The user-repo.
     * @param user The target-user model.
     * @throws GiddException if provided user does not exist.
     */

    public static void alterUser(UserRepo userRepo, User user){
        // Check if user does not exist in datastore
        if(userRepo.findUserByCredentialsEmail(user.getCredentials().getEmail()).isEmpty()){
            throw new GiddException(Status.REGISTRATION_ERROR, "User does not exists");
        }

        // Save user to datastore
        userRepo.save(user);
        // Done I think
    }


    public static void alterUser(UserRepo userRepo, UpdateUserModel userData, StrictIndex id){
        User currentUser = userRepo.findById(id.getRequestId()).orElse(null);

        if (currentUser == null) {
            throw new GiddException(Status.USER_NOT_FOUND, "User does not exist");
        }
        // make sure everything is alright with credentials
        if(userData.getPassword() != null && !userData.getPassword().equals("")) {
            currentUser.getCredentials().setPassword(userData.getPassword());
        }
        if(userData.getActivityLevel() != null){
            currentUser.setActivityLevel(userData.getActivityLevel());
        }
        if(userData.getPhoneNumber() != null && !userData.getPhoneNumber().equals("")) {
            currentUser.getContactInfo().setPhoneNumber(userData.getPhoneNumber());
        }

        currentUser.inspect();

        // Save user to datastore
        userRepo.save(currentUser);
    }

    /**
     * Deletes user.
     * @param userRepo The user-repo.
     * @param user The user.
     */

    public static void deleteUser(UserRepo userRepo, User user){
        userRepo.delete(user);
    }

    /**
     * Retreives user by id.
     * @param userRepo The user-repo.
     * @param id The id.
     * @return The user object.
     * @throws GiddException if user could not be found.
     */

    public static User getUserById(UserRepo userRepo, StrictIndex id) {
        return userRepo
                .findById(id.getRequestId())
                .orElseThrow(() -> new GiddException(Status.USER_NOT_FOUND, "User could not be traced by id."));
    }

    public static User getUser(UserRepo userRepo, User user){
        return getUserById(userRepo, new StrictIndex(user.getId()));
    }

    /**
     * Load user by email.
     * @param userRepo Repo necessary to retreive user.
     * @param email The email adress.
     * @return The user object.
     * @throws GiddException if user cannot be traced.
     */

    public static User loadUserByUsername(UserRepo userRepo, String email){
        return userRepo
                .findUserByCredentialsEmail(email)
                .orElseThrow(() -> new GiddException(Status.USER_NOT_FOUND, "User could not be traced by email."));
    }

    /**
     * Retrieves leaderboard.
     * @param userRepo The user-repo.
     * @return The leaderboard.
     */

    public static List<LeaderboardUserModel> getLeaderBoard(UserRepo userRepo) {
        return userRepo.listTopFiftyUsers();
    }
}
