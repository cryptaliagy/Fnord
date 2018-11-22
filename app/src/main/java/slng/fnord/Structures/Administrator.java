package slng.fnord.Structures;

public class Administrator extends User {
    public Administrator(String email, String username, String password) {
        super(email, username, password, UserTypes.ADMIN);
    }

    public Administrator() {

    }
}
