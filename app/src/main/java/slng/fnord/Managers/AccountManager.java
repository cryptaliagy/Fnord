package slng.fnord.Managers;

import io.reactivex.functions.Consumer;
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

    public void authenticateUser(String email, String password, Consumer<User> successCallback, Consumer<String> failureCallback) {
        database.getUser(email).map(user -> {
            if (user == null) {
                failureCallback.accept("Account does not exist");
                return null;
            }
            // authentication successful
            if (user.checkPassword(password)) {
                return user;
            } else {
                failureCallback.accept("Password is incorrect");
            }

            return null;
        }).subscribe(successCallback).dispose();

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

    public void newUser(String email, String password, UserTypes type, Consumer<User> callback) {
        database.getUser(email).map(user -> {
            // If a user does not exist, create one
            if (user == null) {
                User newUser = makeUser(email, password, type);
                database.addUser(newUser);
                return newUser;
            }

            // If a user does exist, send null to the callback
            return null;

        }).subscribe(callback).dispose();

    }

    /**
     * Requests a user from the db helper and passes it to the callback
     *
     * @param email email of the user that is being searched
     * @param callback callback method to receive user object
     */
    public void getUser(String email, Consumer<User> callback) {
        database.getUser(email).subscribe(callback).dispose();
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
