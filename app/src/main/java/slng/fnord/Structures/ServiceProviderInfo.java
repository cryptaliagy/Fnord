package slng.fnord.Structures;

public class ServiceProviderInfo {
    private String id;
    private String company;

    public ServiceProviderInfo() {

    }

    public ServiceProviderInfo(ServiceProvider serviceProvider) {
        this.id = serviceProvider.getId();
        this.company = serviceProvider.getCompany();
    }
}
