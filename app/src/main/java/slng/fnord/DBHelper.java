package slng.fnord;

public class DBHelper {
    public User getUser(String id){
        return null;
    }

    public void addUser(User user) {
        String id = Common.makeMD5(user.getEmail());
    }

    public Services getServices() {
        return null;
    }

    public Service getService(String id) {
        return null;
    }

    public void addService(Service service) {

    }



}
