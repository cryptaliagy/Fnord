package slng.fnord;
//

import java.util.HashMap;

import slng.fnord.Helpers.Common;
import slng.fnord.Structures.User;
import slng.fnord.Structures.UserTypes;
//


/**
 * DEPRECATED
 */

public class Accounts {
    //admin
    private boolean adminExists = false;

    private HashMap<String, User> userData = new HashMap<>();

    //public methods to add account
    public void makeAccount(String email, String username, String password, UserTypes type) {
        if (type.equals(UserTypes.ADMIN)) {
            adminExists = true;
        }
        String id = Common.makeMD5(email);
        User user = Common.makeUser(email, username, password, type);
        userData.put(id, user);
    }

    //getters
    public boolean existsAdmin() {
        return adminExists;
    }

    public boolean existsAccount(String email) {
        String id = Common.makeMD5(email);

        return userData.containsKey(id);
    }

    public User getUser(String email) {
        String id = Common.makeMD5(email);

        return userData.get(id);
    }
}
