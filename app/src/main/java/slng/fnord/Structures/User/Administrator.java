package slng.fnord.Structures.User;

import slng.fnord.Helpers.Enums.UserTypes;

public class Administrator extends User {
    public Administrator(String email, String username, String password) {
        super(email, password, UserTypes.ADMIN);
    }

    public Administrator() {

    }
}
