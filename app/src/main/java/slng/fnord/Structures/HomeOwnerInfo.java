package slng.fnord.Structures;

import slng.fnord.Helpers.Interfaces.Identifiable;

public class HomeOwnerInfo implements Identifiable {
    private String id;
    private String address;

    public HomeOwnerInfo() {

    }

    public HomeOwnerInfo(HomeOwner homeOwner) {
        this.id = homeOwner.getId();
        this.address = homeOwner.getAddress();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
