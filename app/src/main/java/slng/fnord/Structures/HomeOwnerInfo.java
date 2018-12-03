package slng.fnord.Structures;

public class HomeOwnerInfo {
    private String email;
    private String address;

    public HomeOwnerInfo() {

    }

    public HomeOwnerInfo(HomeOwner homeOwner) {
        this.email = homeOwner.getEmail();
        this.address = homeOwner.getAddress();
    }

    public String getEmail() {
        return email;
    }

    public void setId(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
