package slng.fnord;

import org.junit.Test;

public class ServicesTests {
    @Test
    public void createServiceTest(){
        Services services = new Services();
        services.addService("Lawnmowing");
        services.deleteService("Lawnmowing");
    }
}
