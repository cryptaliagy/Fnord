package slng.fnord.Structures;

import slng.fnord.Structures.User;
import slng.fnord.Structures.UserTypes;

public class HomeOwner extends User {
    private String address;

    public HomeOwner() {

    }

    public HomeOwner(String email, String username, String password) {
        super(email, username, password, UserTypes.HOMEOWNER);
    }

    public String getAddress() {
        return address;
    }

    public void setAddreess(String address) {
        this.address = address;
    }
}
