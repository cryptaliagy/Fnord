package slng.fnord;

public class Administrator extends User {
    public Administrator(String email, String username, String password) {
        super(email, username, password, UserTypes.ADMIN);
    }
}
