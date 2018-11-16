package slng.fnord;

public class HomeOwner extends User {
    public HomeOwner(String email, String username, String password) {
        super(email, username, password, UserTypes.HOMEOWNER);
    }
}
