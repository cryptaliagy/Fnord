package slng.fnord.Structures;

import slng.fnord.Helpers.Enums.UserTypes;

public class HomeOwner extends User {
    private String address;

    public HomeOwner() {

    }

    public HomeOwner(String email, String password) {
        super(email, password, UserTypes.HOMEOWNER);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
