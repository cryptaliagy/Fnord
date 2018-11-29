package slng.fnord;

import org.junit.Test;

import slng.fnord.Structures.Services;

import static com.google.common.truth.Truth.assertThat;

public class ServicesTests {
    @Test
    public void createServiceTest(){
        Services services = new Services();
        services.addService("Lawnmowing", 0.5);
        assertThat(services.hasService("Lawnmowing")).isTrue();
        services.deleteService("Lawnmowing");
    }
}
