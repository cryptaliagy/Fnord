package slng.fnord;

import org.junit.Test;

import slng.fnord.Structures.HomeOwner;
import slng.fnord.Structures.ServiceProvider;
import slng.fnord.Structures.User;
import slng.fnord.Structures.UserTypes;

import static com.google.common.truth.Truth.assertThat;

public class AccountTest {

    /* Unnecessary test as of commit 97fa70
    @Test
    public void adminAccountTest(){
        // values should be verified by now, so only valid strings will be used
        Accounts accounts = new Accounts();
        accounts.makeAccount("glitt073@uottawa.ca", "Graham", "1337Potato", UserTypes.ADMIN);
        User user = accounts.getUser("glitt073@uottawa.ca");
        assertThat(user!=null).isTrue();
        assertThat(user.getType()==UserTypes.ADMIN).isTrue();
    }*/

    @Test
    public void spAccountTest(){
        // values should be verified by now, so only valid strings will be used
        User user = new ServiceProvider("glitt073@uottawa.ca", "Graham", "1337Potato");
        assertThat(user!=null).isTrue();
        assertThat(user.getType()==UserTypes.SERVICEPROVIDER).isTrue();
    }

    @Test
    public void homeOwnerAccountTest(){
        // values should be verified by now, so only valid strings will be used
        User user = new HomeOwner("glitt073@uottawa.ca", "Graham", "1337Potato");
        assertThat(user!=null).isTrue();
        assertThat(user.getType()==UserTypes.HOMEOWNER).isTrue();
    }
}
