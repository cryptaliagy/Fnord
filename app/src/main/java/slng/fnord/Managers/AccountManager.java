package slng.fnord.Managers;

import java.util.Optional;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import slng.fnord.Helpers.Interfaces.Database;
import slng.fnord.Structures.HomeOwner;
import slng.fnord.Structures.ServiceProvider;
import slng.fnord.Structures.User;
import slng.fnord.Helpers.Enums.UserTypes;

public class AccountManager {
    private Database database;

    /**
     * Constructor; Initializes a db helper instance
     *
     */

    public AccountManager(Database database) {
        this.database = database;
    }

    /**
     * Requests user from the db helper and attempts to authenticate the user
     * @param email user's email
     * @param password user's password
     * @param successCallback callback function for successful authentication
     * @param failureCallback callback function for unsuccessful authentication
     */

    public void authenticateUser(String email, String password, Consumer<Optional<User>> successCallback, Consumer<String> failureCallback) {
        Disposable disposable = database.getUser(email).map(user -> {
            Optional<User> optionalUser;
            if (!user.isPresent()) {
                failureCallback.accept("Account does not exist");
                optionalUser = Optional.empty();
                return optionalUser;
            }
            // authentication successful
            if (user.get().checkPassword(password)) {
                return user;
            } else {
                failureCallback.accept("Password is incorrect");
                optionalUser = Optional.empty();
            }

            return optionalUser;
        }).subscribe(successCallback);

    }


    /**
     * Requests a user from the db helper, creating a new one if none were found and passing null
     * to the callback if one has been found.
     *
     * @param email new user's email
     * @param password new user's password
     * @param type new user's account type
     * @param callback callback function to receive new user object after the DB has received it
     */

    public void newUser(String email, String password, UserTypes type, Consumer<Optional<User>> callback) {
        Disposable disposable = database.getUser(email).subscribeOn(Schedulers.io())
                .map(user -> {

            System.out.println("Received user from upstream");
            Optional<User> optionalUser;
            // If a user does not exist, create one
            if (!user.isPresent()) {
                User newUser = makeUser(email, password, type);
                database.addUser(newUser);
                optionalUser = Optional.ofNullable(newUser);
            } else {
                optionalUser = Optional.empty();
            }

            System.out.println("New User Return");

            return optionalUser;

        }).subscribe(callback);
    }

    /**
     * Requests a user from the db helper and passes it to the callback
     *
     * @param email email of the user that is being searched
     * @param callback callback method to receive user object
     */
    public void getUser(String email, Consumer<Optional<User>> callback) {
        Disposable disposable = database.getUser(email).subscribe(callback);
    }


    /**
     * Requests that the db helper updates a user record on the database
     *
     * @param user the user to be updated
     */
    public void updateUser(User user) {
        database.updateUser(user);
    }


    /**
     * Factory method for users
     *
     * @param email new user email
     * @param password new user password
     * @param type new user type
     * @return user object with the specified data
     */
    private User makeUser(String email, String password, UserTypes type) {
        switch (type) {
            case HOMEOWNER:
                return new HomeOwner(email, password);
            case SERVICEPROVIDER:
                return new ServiceProvider(email, password);
        }

        return null; // In theory impossible
    }
}
