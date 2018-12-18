package slng.fnord.Managers;

import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import slng.fnord.Database.DBHelper;
import slng.fnord.Helpers.Common;
import slng.fnord.Database.DBObserver;
import slng.fnord.Database.Interfaces.Database;
import slng.fnord.Structures.Service.Booking;
import slng.fnord.Structures.User.HomeOwner;
import slng.fnord.Structures.Meta.HomeOwnerInfo;
import slng.fnord.Structures.Service.Service;
import slng.fnord.Structures.User.ServiceProvider;
import slng.fnord.Structures.Meta.ServiceProviderInfo;
import slng.fnord.Structures.User.User;
import slng.fnord.Helpers.Enums.UserTypes;

public class AccountManager {
    private Database database;
    private static AccountManager instance = new AccountManager();

    /**
     * Singleton instance retriever (with proper connection to the database)
     * @return singleton instance of the Manager object
     */
    public static AccountManager getInstance() {
        return instance;
    }

    /**
     * Constructor; necessary to prevent instantiating Manager and set up proper DB object
     */
    private AccountManager() {
        this.database = DBHelper.getInstance();
    }

    /**
     * Constructor; initializes the object with the specified database instance
     * Kept for testing purposes.
     */
    @Deprecated
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
        }).subscribe(new DBObserver<Optional<User>>() {
            @Override
            public void onNext(Optional<User> user) {
                if (user.isPresent()) {
                    try {
                        successCallback.accept(user.get());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        successCallback.accept(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    /**
     * Requests a user from the db helper, creating a new homeowner if none were found and passing
     * an empty optional to the callback if one has been found.
     *
     * @param email new user's email
     * @param password new user's password
     * @param callback callback function to receive new user object after the DB has received it
     */

    public void newUser(String email, String password, UserTypes type, Consumer<User> callback) {
        database.getUser(email)
                .map(user -> {
            Optional<User> optionalUser;
            // If a user does not exist, create one
            if (!user.isPresent()) {
                User newUser = makeUser(email, password, type);
                database.addUser(newUser);
                optionalUser = Optional.ofNullable(newUser);
            } else {
                optionalUser = Optional.empty();
            }
            return optionalUser;

        }).subscribe(new DBObserver<Optional<User>>() {
            @Override
            public void onNext(Optional<User> user) {
                User extracted = Common.extractOptional(user);
                try {
                    callback.accept(extracted);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Requests a user from the db helper and passes it to the callback
     *
     * @param email email of the user that is being searched
     * @param callback callback method to receive user object
     */
    public void getUser(String email, Consumer<User> callback) {
        database.getUser(email).subscribe(new DBObserver<Optional<User>>() {
            @Override
            public void onNext(Optional<User> user) {
                User extracted = Common.extractOptional(user);
                try {
                    callback.accept(extracted);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Requests that the db helper updates a user record on the database
     *
     * @param user the user to be updated
     */
    public void updateUser(User user) {
        database.updateUser(user);

        if (user.getType().equals(UserTypes.SERVICEPROVIDER)) {
            ServiceProvider provider = (ServiceProvider) user;
            Observable.fromIterable(provider.getServiceList()).subscribe(new DBObserver<String>() {
                @Override
                public void onNext(String s) {
                    database.getService(s).subscribe(new DBObserver<Optional<Service>>() {
                        @Override
                        public void onNext(Optional<Service> optionalService) {
                            Service service = Common.extractOptional(optionalService);
                            service.addProvider(provider);
                            database.updateService(service);
                        }
                    });
                }
            });

            Observable.fromIterable(provider.getBookings()).subscribe(new DBObserver<String>() {
                @Override
                public void onNext(String s) {
                    database.getBooking(s).subscribe(new DBObserver<Optional<Booking>>() {
                        @Override
                        public void onNext(Optional<Booking> booking) {
                            Booking extracted = Common.extractOptional(booking);
                            ServiceProviderInfo newInfo = new ServiceProviderInfo(provider);
                            extracted.setServiceProviderInfo(newInfo);
                            database.updateBooking(extracted);
                        }
                    });
                }
            });

        } else {
            HomeOwner owner = (HomeOwner) user;
            Observable.fromIterable(user.getBookings()).subscribe(new DBObserver<String>() {
                @Override
                public void onNext(String s) {
                    database.getBooking(s).subscribe(new DBObserver<Optional<Booking>>() {
                        @Override
                        public void onNext(Optional<Booking> booking) {
                            Booking extracted = Common.extractOptional(booking);
                            HomeOwnerInfo newInfo = new HomeOwnerInfo(owner);
                            extracted.setHomeOwnerInfo(newInfo);
                            database.updateBooking(extracted);
                        }
                    });
                }
            });
        }
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
