package slng.fnord.Structures;

import java.util.Calendar;

import slng.fnord.Helpers.Interfaces.Identifiable;

public class Booking implements Identifiable {
    private String id;
    private ServiceProviderInfo serviceProviderInfo;
    private HomeOwnerInfo homeOwnerInfo;
    private String service;

    private Calendar bookingDate;
    private int startTime;
    private int endTime;

    public Booking() {

    }

    public Booking(ServiceProvider serviceProvider, HomeOwner homeOwner, String service, Calendar bookingDate, int startTime, int endTime) {
        this.serviceProviderInfo = new ServiceProviderInfo(serviceProvider);
        this.homeOwnerInfo = new HomeOwnerInfo(homeOwner);
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.service = service;
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

    public Calendar getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Calendar bookingDate) {
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

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    
}
