package slng.fnord.Structures;

public class HomeOwnerInfo {
    private String id;
    private String address;
    private String username;

    public HomeOwnerInfo() {

    }

    public HomeOwnerInfo(HomeOwner homeOwner) {
        this.id = homeOwner.getId();
        this.address = homeOwner.getAddress();
        this.username = homeOwner.getUsername();
    }
}
