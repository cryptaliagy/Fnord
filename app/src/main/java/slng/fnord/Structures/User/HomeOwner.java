package slng.fnord.Structures.User;

import slng.fnord.Helpers.Enums.UserTypes;

public class HomeOwner extends User {
    private String name;

    public HomeOwner() {

    }

    public HomeOwner(String email, String password) {
        super(email, password, UserTypes.HOMEOWNER);
        name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
