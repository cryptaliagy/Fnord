package slng.fnord.Structures;

import slng.fnord.Helpers.Enums.UserTypes;

public class HomeOwner extends User {
    private String name;

    public HomeOwner() {

    }

    public HomeOwner(String email, String password) {
        super(email, password, UserTypes.HOMEOWNER);
    }
}
