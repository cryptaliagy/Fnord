package slng.fnord.Structures;

import slng.fnord.Helpers.Interfaces.Identifiable;

public class ServiceProviderInfo implements Identifiable {
    private String id;
    private String company;

    public ServiceProviderInfo() {

    }

    public ServiceProviderInfo(ServiceProvider serviceProvider) {
        this.id = serviceProvider.getId();
        this.company = serviceProvider.getCompany();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
