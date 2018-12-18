package slng.fnord;

import org.junit.Test;

import slng.fnord.Helpers.Common;
import slng.fnord.Structures.Service.Service;
import slng.fnord.Structures.User.ServiceProvider;
import slng.fnord.Helpers.Enums.UserTypes;

import static com.google.common.truth.Truth.assertThat;

public class ServiceProviderTests {
    @Test
    public void createServiceTest(){
        ServiceProvider sp = new ServiceProvider("glitt73@uottawa.ca", "p4$$w0rd");
        sp.addService("Cleaning");
        assertThat(sp.getServiceList().contains("Cleaning")).isTrue();
        sp.removeService("Cleaning");
        assertThat(sp.getServiceList().contains("Cleaning")).isFalse();

    }


    @Test
    public void serviceSPTest(){
        String serviceName = "Eating your food";
        ServiceProvider sp = (ServiceProvider) Common.makeUser("glitt73@uottawa.ca", "p4$$w0rd", UserTypes.SERVICEPROVIDER);
        Service service = new Service(serviceName, 0.1);
        assertThat(service.isProvider(sp.getEmail())).isFalse();
        sp.addService(serviceName);
        sp.updateCertified(serviceName, true);
        service.addProvider(sp);
        assertThat(service.isProvider(sp.getCompany())).isTrue();
        assertThat(sp.isCertified(serviceName)).isTrue();
        assertThat(service.providerIsCertified(sp.getCompany())).isTrue();
        service.deleteProvider(sp.getCompany());
        sp.updateCertified(serviceName, false);
        assertThat(service.providerIsCertified(sp.getCompany())).isFalse();
        service.addProvider(sp);
        assertThat(service.providerIsCertified(sp.getCompany())).isFalse();

    }
}
