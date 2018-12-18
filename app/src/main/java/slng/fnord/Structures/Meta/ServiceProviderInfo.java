package slng.fnord.Structures.Meta;

import slng.fnord.Structures.User.ServiceProvider;

public class ServiceProviderInfo {
    private String email;
    private String company;

    public ServiceProviderInfo() {

    }

    public ServiceProviderInfo(ServiceProvider serviceProvider) {
        this.email = serviceProvider.getEmail();
        this.company = serviceProvider.getCompany();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
