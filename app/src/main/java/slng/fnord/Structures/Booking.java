package slng.fnord.Structures;

import java.util.Date;

import slng.fnord.Helpers.Interfaces.Identifiable;

public class Booking implements Identifiable {
    private String id;
    private ServiceProviderInfo serviceProviderInfo;
    private HomeOwnerInfo homeOwnerInfo;
    private Date bookingDate;

    public Booking() {

    }

    public Booking(String id, ServiceProviderInfo serviceProviderInfo, HomeOwnerInfo homeOwnerInfo, Date bookingDate) {
        this.id = id;
        this.serviceProviderInfo = serviceProviderInfo;
        this.homeOwnerInfo = homeOwnerInfo;
        this.bookingDate = bookingDate;
    }

    public ServiceProviderInfo getServiceProviderInfo() {
        return serviceProviderInfo;
    }

    public void setServiceProviderInfo(ServiceProviderInfo serviceProviderInfo) {
        this.serviceProviderInfo = serviceProviderInfo;
    }

    public HomeOwnerInfo getHomeOwnerInfo() {
        return homeOwnerInfo;
    }

    public void setHomeOwnerInfo(HomeOwnerInfo homeOwnerInfo) {
        this.homeOwnerInfo = homeOwnerInfo;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    
}
