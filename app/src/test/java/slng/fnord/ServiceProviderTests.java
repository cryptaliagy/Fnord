package slng.fnord;

import org.junit.Test;

import slng.fnord.Helpers.Common;
import slng.fnord.Structures.Service;
import slng.fnord.Structures.ServiceProvider;
import slng.fnord.Structures.UserTypes;

import static com.google.common.truth.Truth.assertThat;

public class ServiceProviderTests {
    @Test
    public void createServiceTest(){
        ServiceProvider sp = (ServiceProvider) Common.makeUser("glitt73@uottawa.ca", "Graham", "p4$$w0rd", UserTypes.SERVICEPROVIDER);
        sp.addService("Cleaning");
        assertThat(sp.getServiceList().contains("Cleaning")).isTrue();
        sp.removeService("Cleaning");
        assertThat(sp.getServiceList().contains("Cleaning")).isFalse();

    }

    /*
    @Test
    public void serviceSPTest(){
        String serviceName = "Eating your food";
        ServiceProvider sp = (ServiceProvider) Common.makeUser("glitt73@uottawa.ca", "Graham", "p4$$w0rd", UserTypes.SERVICEPROVIDER);
        Service service = new Service(serviceName, 0.1);
        assertThat(service.isProvider(sp.getEmail())).isFalse();
        sp.addService(serviceName);
        service.addProvider(sp.getEmail(), true);
        assertThat(service.isProvider(sp.getEmail())).isTrue();
        assertThat(service.providerIsCertified(sp.getEmail())).isTrue();
        service.deleteProvider(sp.getEmail());
        assertThat(service.providerIsCertified(sp.getEmail())).isFalse();
        service.addProvider(sp.getEmail(), false);
        assertThat(service.providerIsCertified(sp.getEmail())).isFalse();

    }
    */
    
    //TODO: Restructure the serviceSPTest test to account for changes in how services store serviceProvider information
}
